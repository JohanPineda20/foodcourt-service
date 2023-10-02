package com.pragma.foodcourtservice.domain.usecase;

import com.pragma.foodcourtservice.domain.api.IOrderServicePort;
import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.exception.DomainException;
import com.pragma.foodcourtservice.domain.model.*;
import com.pragma.foodcourtservice.domain.spi.feignclient.IMessengerFeignClientPort;
import com.pragma.foodcourtservice.domain.spi.feignclient.ITrackingFeignClientPort;
import com.pragma.foodcourtservice.domain.spi.persistence.IDishPersistencePort;
import com.pragma.foodcourtservice.domain.spi.persistence.IOrderPersistencePort;
import com.pragma.foodcourtservice.domain.spi.persistence.IRestaurantPersistencePort;
import com.pragma.foodcourtservice.domain.spi.securitycontext.ISecurityContextPort;
import feign.FeignException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final ISecurityContextPort securityContextPort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IMessengerFeignClientPort messengerFeignClientPort;
    private final ITrackingFeignClientPort trackingFeignClientPort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, ISecurityContextPort securityContextPort, IRestaurantPersistencePort restaurantPersistencePort, IDishPersistencePort dishPersistencePort, IMessengerFeignClientPort messengerFeignClientPort, ITrackingFeignClientPort trackingFeignClientPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.securityContextPort = securityContextPort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.messengerFeignClientPort = messengerFeignClientPort;
        this.trackingFeignClientPort = trackingFeignClientPort;
    }

    @Override
    public void save(OrderModel orderModel) {
        Long customerId = getIdFromSecurityContext();
        List<OrderModel> orderModelList = orderPersistencePort.findByCustomerId(customerId);
        int i = 0;
        while(i < orderModelList.size()){
            if(!orderModelList.get(i).getStatus().equals(StatusEnumModel.CANCELLED) && !orderModelList.get(i).getStatus().equals(StatusEnumModel.DELIVERED)) throw new DomainException("Customer cannot create a new order because has an order in progress");
            i++;
        }

        RestaurantModel restaurantModel = restaurantPersistencePort.findById(orderModel.getRestaurant().getId());
        if(restaurantModel == null) throw new DataNotFoundException("Restaurant not found");

        orderModel.setRestaurant(restaurantModel);
        orderModel.setCustomerId(customerId);
        orderModel.setCreatedAt(LocalDateTime.now());
        orderModel.setStatus(StatusEnumModel.PENDING);
        OrderModel orderModel1 = orderPersistencePort.save(orderModel);

        List<OrderDishModel> dishes = orderModel.getDishes();
        for(OrderDishModel orderDishModel: dishes){
            DishModel dishModel = dishPersistencePort.findById(orderDishModel.getDish().getId());
            if(dishModel == null) throw new DataNotFoundException("Dish not found");
            if(dishModel.getRestaurant().getId() != orderModel.getRestaurant().getId()) throw new DomainException("Dish " + dishModel.getName() + " does not belong to the restaurant");
            if(Boolean.FALSE.equals(dishModel.getActive())) throw new DomainException("Dish " + dishModel.getName() + " is not available");
            orderDishModel.setDish(dishModel);
            orderDishModel.setOrder(orderModel1);
        }
        orderPersistencePort.saveOrderDish(dishes);
        trackingOrder(orderModel1, "");
    }

    @Override
    public List<OrderModel> getAllOrdersByRestaurantAndStatus(Integer page, Integer size, String status) {
        Long employeeId = getIdFromSecurityContext();
        RestaurantEmployeeModel restaurantEmployeeModel = restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(employeeId);
        if(restaurantEmployeeModel == null) throw new DataNotFoundException("Employee not found");

        Long restaurantId = restaurantEmployeeModel.getRestaurant().getId();
        List<OrderModel> orderModelList = orderPersistencePort.getAllOrdersByRestaurant(page, size, restaurantId);
        if(orderModelList.isEmpty()) throw new DataNotFoundException("There are no orders in the restaurant");
        for(OrderModel orderModel: orderModelList) { //add orderdishes to orders
            List<OrderDishModel> dishes = orderPersistencePort.getAllDishesByOrderId(orderModel.getId());
            orderModel.setDishes(dishes);
        }

        if (status != null) {
            StatusEnumModel statusEnumModel = getStatusEnumModel(status);
            if (statusEnumModel == null) throw new DomainException("Status " + status + " does not exist or is wrong!!!");

            orderModelList = orderPersistencePort.getAllOrdersByRestaurantAndStatus(page, size, restaurantId, statusEnumModel);
            if(orderModelList.isEmpty()) throw new DataNotFoundException("There are no orders in the restaurant with that status");
            for(OrderModel orderModel: orderModelList) { //add orderdishes to orders
                List<OrderDishModel> dishes = orderPersistencePort.getAllDishesByOrderId(orderModel.getId());
                orderModel.setDishes(dishes);
            }
        }

        return orderModelList;
    }

    @Override
    public void takeOrder(Long id) {
        OrderModel orderModel = getOrderModel(id);

        RestaurantEmployeeModel restaurantEmployeeModel = validateEmployeeBelongsToRestaurant(orderModel);

        if(!orderModel.getStatus().equals(StatusEnumModel.PENDING)
                || orderModel.getRestaurantEmployee() != null) throw new DomainException("Order cannot be taken");

        orderModel.setStatus(StatusEnumModel.IN_PREPARATION);
        orderModel.setRestaurantEmployee(restaurantEmployeeModel);
        orderPersistencePort.save(orderModel);
        trackingOrder(orderModel, StatusEnumModel.PENDING.name());
    }

    @Override
    public void readyOrder(Long id) {
        OrderModel orderModel = getOrderModel(id);

        RestaurantEmployeeModel restaurantEmployeeModel = validateEmployeeBelongsToRestaurant(orderModel);

        if(!orderModel.getStatus().equals(StatusEnumModel.IN_PREPARATION)
                || orderModel.getRestaurantEmployee() == null) throw new DomainException("Order cannot be ready");

        if(restaurantEmployeeModel.getId() != orderModel.getRestaurantEmployee().getId()) throw new DomainException("Employee cannot mark the order as ready because the order was taken by another employee");

        orderModel.setStatus(StatusEnumModel.READY);
        orderPersistencePort.save(orderModel);

        String securityPin = createSecurityPin(orderModel);
        sendSMS("Your order is ready to pickup. You can get it by showing the following security pin: " + securityPin, "+573115330169");
        trackingOrder(orderModel, StatusEnumModel.IN_PREPARATION.name());
    }

    @Override
    public void deliverOrder(Long id, String pin) {
        OrderModel orderModel = getOrderModel(id);

        RestaurantEmployeeModel restaurantEmployeeModel = validateEmployeeBelongsToRestaurant(orderModel);

        if(!orderModel.getStatus().equals(StatusEnumModel.READY)
                || orderModel.getRestaurantEmployee() == null) throw new DomainException("Order cannot be delivered");

        if(restaurantEmployeeModel.getId() != orderModel.getRestaurantEmployee().getId()) throw new DomainException("Employee cannot deliver the order because the order was taken by another employee");

        if(!createSecurityPin(orderModel).equals(pin)) throw new DomainException("Order cannot be delivered because the security pin is wrong");

        orderModel.setStatus(StatusEnumModel.DELIVERED);
        orderModel.setDurationMinutes(ChronoUnit.MINUTES.between(orderModel.getCreatedAt(), LocalDateTime.now()));
        orderPersistencePort.save(orderModel);
        trackingOrder(orderModel, StatusEnumModel.READY.name());
    }

    @Override
    public void cancelOrder(Long id) {
        OrderModel orderModel = getOrderModel(id);

        Long customerId = getIdFromSecurityContext();
        if(customerId != orderModel.getCustomerId()) throw new DomainException("The customer must be the same customer who placed the order");

        if(!orderModel.getStatus().equals(StatusEnumModel.PENDING)
                || orderModel.getRestaurantEmployee() != null) {
            sendSMS("Sorry, your order is already in preparation and cannot be canceled", "+573115330169");
            throw new DomainException("Order cannot be canceled");
        }
        orderModel.setStatus(StatusEnumModel.CANCELLED);
        orderPersistencePort.save(orderModel);
        trackingOrder(orderModel, StatusEnumModel.PENDING.name());
    }

    @Override
    public List<TrackingModel> getHistoryOrder(Long id) {
        OrderModel orderModel = getOrderModel(id);

        Long customerId = getIdFromSecurityContext();
        if(customerId != orderModel.getCustomerId()) throw new DomainException("The customer must be the same customer who placed the order");

        List<TrackingModel> trackingModelList;
        try {
            trackingModelList = trackingFeignClientPort.getHistoryOrder(id);
        }
        catch(FeignException e){
            throw new DomainException(e.getMessage());
        }
        return trackingModelList;
    }

    @Override
    public List<OrderModel> getOrderDuration(Integer page, Integer size) {
        Long ownerId = getIdFromSecurityContext();
        RestaurantModel restaurantModel = restaurantPersistencePort.findByOwnerId(ownerId);
        if(restaurantModel == null) throw new DataNotFoundException("Owner does not a restaurant");

        List<OrderModel> orderModelList = orderPersistencePort.getAllOrdersByRestaurantAndStatus(page, size, restaurantModel.getId(), StatusEnumModel.DELIVERED);
        if(orderModelList.isEmpty()) throw new DataNotFoundException("There are no orders delivered in the restaurant");
        return orderModelList;
    }

    private String createSecurityPin(OrderModel orderModel){
        return  orderModel.getRestaurant().getName()
                + orderModel.getId()
                + orderModel.getCustomerId()
                + orderModel.getCreatedAt().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
    }

    private void sendSMS(String message, String cellphone){
        try {
            messengerFeignClientPort.sendMessage(message, cellphone);
        }catch(FeignException e){
            throw new DomainException(e.getMessage());
        }
    }

    private RestaurantEmployeeModel validateEmployeeBelongsToRestaurant(OrderModel orderModel) {
        Long employeeId = getIdFromSecurityContext();
        RestaurantEmployeeModel restaurantEmployeeModel = restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(employeeId);
        if(restaurantEmployeeModel == null) throw new DataNotFoundException("Employee not found");
        if(restaurantEmployeeModel.getRestaurant().getId() != orderModel.getRestaurant().getId()) throw new DomainException("Employee does not work in the restaurant");
        return restaurantEmployeeModel;
    }

    private Long getIdFromSecurityContext() {
        return securityContextPort.getIdFromSecurityContext();
    }

    private OrderModel getOrderModel(Long id) {
        OrderModel orderModel = orderPersistencePort.findById(id);
        if(orderModel == null) throw new DataNotFoundException("Order not found");
        return orderModel;
    }

    private StatusEnumModel getStatusEnumModel(String status) {
        StatusEnumModel[] statuses = StatusEnumModel.values();
        StatusEnumModel statusEnumModel = null;
        int i = 0;
        while(i < statuses.length && statusEnumModel == null) {
            if (statuses[i].name().equalsIgnoreCase(status)) {
                statusEnumModel = statuses[i];
            }
            i++;
        }
        return statusEnumModel;
    }

    private void trackingOrder(OrderModel orderModel, String previousStatus){
        TrackingModel trackingModel = new TrackingModel();
        trackingModel.setCustomerId(orderModel.getCustomerId());
        trackingModel.setOrderId(orderModel.getId());
        trackingModel.setStatusPrevious(previousStatus);
        trackingModel.setStatus(orderModel.getStatus().name());
        trackingModel.setDatetime(LocalDateTime.now());
        if(orderModel.getRestaurantEmployee() == null) { //pending and canceled
            trackingModel.setEmployeeId(0L);
        }
        else {
            trackingModel.setEmployeeId(orderModel.getRestaurantEmployee().getId());
        }
        if(orderModel.getCustomerId().equals(getIdFromSecurityContext())){
            trackingModel.setCustomerEmail(securityContextPort.getEmailFromSecurityContext());
        }
        else {
            trackingModel.setEmployeeEmail(securityContextPort.getEmailFromSecurityContext());
        }

        try {
            trackingFeignClientPort.trackingOrder(trackingModel);
        }
        catch(FeignException e){
            throw new DomainException(e.getMessage());
        }
    }

}

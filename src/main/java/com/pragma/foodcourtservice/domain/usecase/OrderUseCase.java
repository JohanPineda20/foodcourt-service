package com.pragma.foodcourtservice.domain.usecase;

import com.pragma.foodcourtservice.domain.api.IOrderServicePort;
import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.exception.DomainException;
import com.pragma.foodcourtservice.domain.model.*;
import com.pragma.foodcourtservice.domain.spi.feignclient.IMessengerFeignClientPort;
import com.pragma.foodcourtservice.domain.spi.persistence.IDishPersistencePort;
import com.pragma.foodcourtservice.domain.spi.persistence.IOrderPersistencePort;
import com.pragma.foodcourtservice.domain.spi.persistence.IRestaurantPersistencePort;
import com.pragma.foodcourtservice.domain.spi.securitycontext.ISecurityContextPort;
import feign.FeignException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final ISecurityContextPort securityContextPort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IMessengerFeignClientPort messengerFeignClientPort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, ISecurityContextPort securityContextPort, IRestaurantPersistencePort restaurantPersistencePort, IDishPersistencePort dishPersistencePort, IMessengerFeignClientPort messengerFeignClientPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.securityContextPort = securityContextPort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.messengerFeignClientPort = messengerFeignClientPort;
    }

    @Override
    public void save(OrderModel orderModel) {
        Long customerId = securityContextPort.getIdFromSecurityContext();
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
    }

    @Override
    public List<OrderModel> getAllOrdersByRestaurantAndStatus(Integer page, Integer size, String status) {
        Long employeeId = securityContextPort.getIdFromSecurityContext();
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
            StatusEnumModel[] statuses = StatusEnumModel.values();
            StatusEnumModel statusEnumModel = null;
            int i = 0;
            while(i < statuses.length && statusEnumModel == null) {
                if (statuses[i].name().equalsIgnoreCase(status)) {
                    statusEnumModel = statuses[i];
                }
                i++;
            }
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
        OrderModel orderModel = orderPersistencePort.findById(id);
        if(orderModel == null) throw new DataNotFoundException("Order not found");

        Long employeeId = securityContextPort.getIdFromSecurityContext();
        RestaurantEmployeeModel restaurantEmployeeModel = restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(employeeId);
        if(restaurantEmployeeModel == null) throw new DataNotFoundException("Employee not found");
        if(restaurantEmployeeModel.getRestaurant().getId() != orderModel.getRestaurant().getId()) throw new DomainException("Employee does not work in the restaurant");

        if(!orderModel.getStatus().equals(StatusEnumModel.PENDING)
                || orderModel.getRestaurantEmployee() != null) throw new DomainException("Order cannot be taken");

        orderModel.setStatus(StatusEnumModel.IN_PREPARATION);
        orderModel.setRestaurantEmployee(restaurantEmployeeModel);
        orderPersistencePort.save(orderModel);
    }

    @Override
    public void readyOrder(Long id) {
        OrderModel orderModel = orderPersistencePort.findById(id);
        if(orderModel == null) throw new DataNotFoundException("Order not found");

        Long employeeId = securityContextPort.getIdFromSecurityContext();
        RestaurantEmployeeModel restaurantEmployeeModel = restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(employeeId);
        if(restaurantEmployeeModel == null) throw new DataNotFoundException("Employee not found");
        if(restaurantEmployeeModel.getRestaurant().getId() != orderModel.getRestaurant().getId()) throw new DomainException("Employee does not work in the restaurant");

        if(!orderModel.getStatus().equals(StatusEnumModel.IN_PREPARATION)
                || orderModel.getRestaurantEmployee() == null) throw new DomainException("Order cannot be ready");

        if(restaurantEmployeeModel.getId() != orderModel.getRestaurantEmployee().getId()) throw new DomainException("Employee cannot mark the order as ready because the order was taken by another employee");

        orderModel.setStatus(StatusEnumModel.READY);
        orderPersistencePort.save(orderModel);

        String securityPin = createSecurityPin(orderModel);
        sendSMS("Your order is ready to pickup. You can get it by showing the following security pin: " + securityPin, "+573115330169");
    }

    private String createSecurityPin(OrderModel orderModel){
        return  orderModel.getRestaurant().getName()
                + orderModel.getId() +
                + orderModel.getCustomerId()
                + orderModel.getCreatedAt().format(DateTimeFormatter.ofPattern("ddMMyyyy"))
                + orderModel.hashCode();
    }
    private void sendSMS(String message, String cellphone){
        try {
            messengerFeignClientPort.sendMessage(message, cellphone);
        }catch(FeignException e){
            throw new DomainException(e.getMessage());
        }
    }
}

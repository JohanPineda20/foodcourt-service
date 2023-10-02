package com.pragma.foodcourtservice.application.mapper;

import com.pragma.foodcourtservice.application.dto.request.OrderDishRequest;
import com.pragma.foodcourtservice.application.dto.request.OrderRequest;
import com.pragma.foodcourtservice.application.dto.response.OrderDishResponse;
import com.pragma.foodcourtservice.application.dto.response.OrderDurationResponse;
import com.pragma.foodcourtservice.application.dto.response.OrderResponse;
import com.pragma.foodcourtservice.application.dto.response.TrackingResponse;
import com.pragma.foodcourtservice.domain.model.CategoryModel;
import com.pragma.foodcourtservice.domain.model.DishModel;
import com.pragma.foodcourtservice.domain.model.OrderDishModel;
import com.pragma.foodcourtservice.domain.model.OrderModel;
import com.pragma.foodcourtservice.domain.model.RestaurantEmployeeModel;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import com.pragma.foodcourtservice.domain.model.StatusEnumModel;
import com.pragma.foodcourtservice.domain.model.TrackingModel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-02T10:40:20-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class IOrderDtoMapperImpl implements IOrderDtoMapper {

    @Override
    public OrderDishModel mapToOrderDishModel(OrderDishRequest orderDishRequest) {
        if ( orderDishRequest == null ) {
            return null;
        }

        OrderDishModel orderDishModel = new OrderDishModel();

        orderDishModel.setDish( orderDishRequestToDishModel( orderDishRequest ) );
        orderDishModel.setAmount( orderDishRequest.getAmount() );

        return orderDishModel;
    }

    @Override
    public OrderModel mapToOrderModel(OrderRequest orderRequest) {
        if ( orderRequest == null ) {
            return null;
        }

        OrderModel orderModel = new OrderModel();

        orderModel.setRestaurant( orderRequestToRestaurantModel( orderRequest ) );
        orderModel.setDishes( orderDishRequestListToOrderDishModelList( orderRequest.getDishes() ) );

        return orderModel;
    }

    @Override
    public OrderResponse mapToOrderResponse(OrderModel orderModel) {
        if ( orderModel == null ) {
            return null;
        }

        Long employeeId = null;
        String restaurantName = null;
        List<OrderDishResponse> dishes = null;
        Long id = null;
        Long customerId = null;
        LocalDateTime createdAt = null;
        StatusEnumModel status = null;

        employeeId = orderModelRestaurantEmployeeId( orderModel );
        restaurantName = orderModelRestaurantName( orderModel );
        dishes = orderDishModelListToOrderDishResponseList( orderModel.getDishes() );
        id = orderModel.getId();
        customerId = orderModel.getCustomerId();
        createdAt = orderModel.getCreatedAt();
        status = orderModel.getStatus();

        OrderResponse orderResponse = new OrderResponse( id, customerId, createdAt, status, employeeId, restaurantName, dishes );

        return orderResponse;
    }

    @Override
    public OrderDishResponse mapToOrderResponse(OrderDishModel orderModel) {
        if ( orderModel == null ) {
            return null;
        }

        String dishName = null;
        BigDecimal dishPrice = null;
        String dishCategoryName = null;
        Long amount = null;

        dishName = orderModelDishName( orderModel );
        dishPrice = orderModelDishPrice( orderModel );
        dishCategoryName = orderModelDishCategoryName( orderModel );
        amount = orderModel.getAmount();

        OrderDishResponse orderDishResponse = new OrderDishResponse( dishName, dishPrice, dishCategoryName, amount );

        return orderDishResponse;
    }

    @Override
    public List<OrderResponse> mapToOrderResponseList(List<OrderModel> orderModelList) {
        if ( orderModelList == null ) {
            return null;
        }

        List<OrderResponse> list = new ArrayList<OrderResponse>( orderModelList.size() );
        for ( OrderModel orderModel : orderModelList ) {
            list.add( mapToOrderResponse( orderModel ) );
        }

        return list;
    }

    @Override
    public List<TrackingResponse> mapToTrackingResponseList(List<TrackingModel> trackingModelList) {
        if ( trackingModelList == null ) {
            return null;
        }

        List<TrackingResponse> list = new ArrayList<TrackingResponse>( trackingModelList.size() );
        for ( TrackingModel trackingModel : trackingModelList ) {
            list.add( trackingModelToTrackingResponse( trackingModel ) );
        }

        return list;
    }

    @Override
    public List<OrderDurationResponse> mapToOrderDurationResponseList(List<OrderModel> orderModelList) {
        if ( orderModelList == null ) {
            return null;
        }

        List<OrderDurationResponse> list = new ArrayList<OrderDurationResponse>( orderModelList.size() );
        for ( OrderModel orderModel : orderModelList ) {
            list.add( orderModelToOrderDurationResponse( orderModel ) );
        }

        return list;
    }

    protected DishModel orderDishRequestToDishModel(OrderDishRequest orderDishRequest) {
        if ( orderDishRequest == null ) {
            return null;
        }

        DishModel dishModel = new DishModel();

        dishModel.setId( orderDishRequest.getDishId() );

        return dishModel;
    }

    protected RestaurantModel orderRequestToRestaurantModel(OrderRequest orderRequest) {
        if ( orderRequest == null ) {
            return null;
        }

        RestaurantModel restaurantModel = new RestaurantModel();

        restaurantModel.setId( orderRequest.getRestaurantId() );

        return restaurantModel;
    }

    protected List<OrderDishModel> orderDishRequestListToOrderDishModelList(List<OrderDishRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderDishModel> list1 = new ArrayList<OrderDishModel>( list.size() );
        for ( OrderDishRequest orderDishRequest : list ) {
            list1.add( mapToOrderDishModel( orderDishRequest ) );
        }

        return list1;
    }

    private Long orderModelRestaurantEmployeeId(OrderModel orderModel) {
        if ( orderModel == null ) {
            return null;
        }
        RestaurantEmployeeModel restaurantEmployee = orderModel.getRestaurantEmployee();
        if ( restaurantEmployee == null ) {
            return null;
        }
        Long id = restaurantEmployee.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String orderModelRestaurantName(OrderModel orderModel) {
        if ( orderModel == null ) {
            return null;
        }
        RestaurantModel restaurant = orderModel.getRestaurant();
        if ( restaurant == null ) {
            return null;
        }
        String name = restaurant.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected List<OrderDishResponse> orderDishModelListToOrderDishResponseList(List<OrderDishModel> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderDishResponse> list1 = new ArrayList<OrderDishResponse>( list.size() );
        for ( OrderDishModel orderDishModel : list ) {
            list1.add( mapToOrderResponse( orderDishModel ) );
        }

        return list1;
    }

    private String orderModelDishName(OrderDishModel orderDishModel) {
        if ( orderDishModel == null ) {
            return null;
        }
        DishModel dish = orderDishModel.getDish();
        if ( dish == null ) {
            return null;
        }
        String name = dish.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private BigDecimal orderModelDishPrice(OrderDishModel orderDishModel) {
        if ( orderDishModel == null ) {
            return null;
        }
        DishModel dish = orderDishModel.getDish();
        if ( dish == null ) {
            return null;
        }
        BigDecimal price = dish.getPrice();
        if ( price == null ) {
            return null;
        }
        return price;
    }

    private String orderModelDishCategoryName(OrderDishModel orderDishModel) {
        if ( orderDishModel == null ) {
            return null;
        }
        DishModel dish = orderDishModel.getDish();
        if ( dish == null ) {
            return null;
        }
        CategoryModel category = dish.getCategory();
        if ( category == null ) {
            return null;
        }
        String name = category.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected TrackingResponse trackingModelToTrackingResponse(TrackingModel trackingModel) {
        if ( trackingModel == null ) {
            return null;
        }

        Long orderId = null;
        Long customerId = null;
        String customerEmail = null;
        LocalDateTime datetime = null;
        String statusPrevious = null;
        String status = null;
        Long employeeId = null;
        String employeeEmail = null;

        orderId = trackingModel.getOrderId();
        customerId = trackingModel.getCustomerId();
        customerEmail = trackingModel.getCustomerEmail();
        datetime = trackingModel.getDatetime();
        statusPrevious = trackingModel.getStatusPrevious();
        status = trackingModel.getStatus();
        employeeId = trackingModel.getEmployeeId();
        employeeEmail = trackingModel.getEmployeeEmail();

        TrackingResponse trackingResponse = new TrackingResponse( orderId, customerId, customerEmail, datetime, statusPrevious, status, employeeId, employeeEmail );

        return trackingResponse;
    }

    protected OrderDurationResponse orderModelToOrderDurationResponse(OrderModel orderModel) {
        if ( orderModel == null ) {
            return null;
        }

        Long id = null;
        LocalDateTime createdAt = null;
        Long durationMinutes = null;

        id = orderModel.getId();
        createdAt = orderModel.getCreatedAt();
        durationMinutes = orderModel.getDurationMinutes();

        OrderDurationResponse orderDurationResponse = new OrderDurationResponse( id, createdAt, durationMinutes );

        return orderDurationResponse;
    }
}

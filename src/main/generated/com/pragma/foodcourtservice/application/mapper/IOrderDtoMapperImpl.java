package com.pragma.foodcourtservice.application.mapper;

import com.pragma.foodcourtservice.application.dto.request.OrderDishRequest;
import com.pragma.foodcourtservice.application.dto.request.OrderRequest;
import com.pragma.foodcourtservice.domain.model.DishModel;
import com.pragma.foodcourtservice.domain.model.OrderDishModel;
import com.pragma.foodcourtservice.domain.model.OrderModel;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-25T18:00:30-0500",
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
}

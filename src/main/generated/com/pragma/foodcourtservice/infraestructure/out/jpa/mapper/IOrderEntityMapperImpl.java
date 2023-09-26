package com.pragma.foodcourtservice.infraestructure.out.jpa.mapper;

import com.pragma.foodcourtservice.domain.model.OrderModel;
import com.pragma.foodcourtservice.domain.model.RestaurantEmployeeModel;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import com.pragma.foodcourtservice.domain.model.StatusEnumModel;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.OrderEntity;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.RestaurantEmployeeEntity;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.RestaurantEntity;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.StatusEnum;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-26T12:21:11-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class IOrderEntityMapperImpl implements IOrderEntityMapper {

    @Override
    public OrderModel mapToOrderModel(OrderEntity orderEntity) {
        if ( orderEntity == null ) {
            return null;
        }

        OrderModel orderModel = new OrderModel();

        orderModel.setId( orderEntity.getId() );
        orderModel.setCustomerId( orderEntity.getCustomerId() );
        orderModel.setCreatedAt( orderEntity.getCreatedAt() );
        orderModel.setStatus( statusEnumToStatusEnumModel( orderEntity.getStatus() ) );
        orderModel.setRestaurantEmployee( restaurantEmployeeEntityToRestaurantEmployeeModel( orderEntity.getRestaurantEmployee() ) );
        orderModel.setRestaurant( restaurantEntityToRestaurantModel( orderEntity.getRestaurant() ) );

        return orderModel;
    }

    @Override
    public OrderEntity mapToOrderEntity(OrderModel orderModel) {
        if ( orderModel == null ) {
            return null;
        }

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setId( orderModel.getId() );
        orderEntity.setCustomerId( orderModel.getCustomerId() );
        orderEntity.setCreatedAt( orderModel.getCreatedAt() );
        orderEntity.setStatus( mapToStatusEnum( orderModel.getStatus() ) );
        orderEntity.setRestaurant( restaurantModelToRestaurantEntity( orderModel.getRestaurant() ) );
        orderEntity.setRestaurantEmployee( restaurantEmployeeModelToRestaurantEmployeeEntity( orderModel.getRestaurantEmployee() ) );

        return orderEntity;
    }

    @Override
    public List<OrderModel> mapToOrderModelList(List<OrderEntity> orderEntityList) {
        if ( orderEntityList == null ) {
            return null;
        }

        List<OrderModel> list = new ArrayList<OrderModel>( orderEntityList.size() );
        for ( OrderEntity orderEntity : orderEntityList ) {
            list.add( mapToOrderModel( orderEntity ) );
        }

        return list;
    }

    @Override
    public StatusEnum mapToStatusEnum(StatusEnumModel statusEnumModel) {
        if ( statusEnumModel == null ) {
            return null;
        }

        StatusEnum statusEnum;

        switch ( statusEnumModel ) {
            case PENDING: statusEnum = StatusEnum.PENDING;
            break;
            case IN_PREPARATION: statusEnum = StatusEnum.IN_PREPARATION;
            break;
            case READY: statusEnum = StatusEnum.READY;
            break;
            case DELIVERED: statusEnum = StatusEnum.DELIVERED;
            break;
            case CANCELLED: statusEnum = StatusEnum.CANCELLED;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + statusEnumModel );
        }

        return statusEnum;
    }

    protected StatusEnumModel statusEnumToStatusEnumModel(StatusEnum statusEnum) {
        if ( statusEnum == null ) {
            return null;
        }

        StatusEnumModel statusEnumModel;

        switch ( statusEnum ) {
            case PENDING: statusEnumModel = StatusEnumModel.PENDING;
            break;
            case IN_PREPARATION: statusEnumModel = StatusEnumModel.IN_PREPARATION;
            break;
            case READY: statusEnumModel = StatusEnumModel.READY;
            break;
            case DELIVERED: statusEnumModel = StatusEnumModel.DELIVERED;
            break;
            case CANCELLED: statusEnumModel = StatusEnumModel.CANCELLED;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + statusEnum );
        }

        return statusEnumModel;
    }

    protected RestaurantModel restaurantEntityToRestaurantModel(RestaurantEntity restaurantEntity) {
        if ( restaurantEntity == null ) {
            return null;
        }

        RestaurantModel restaurantModel = new RestaurantModel();

        restaurantModel.setId( restaurantEntity.getId() );
        restaurantModel.setName( restaurantEntity.getName() );
        restaurantModel.setNit( restaurantEntity.getNit() );
        restaurantModel.setAddress( restaurantEntity.getAddress() );
        restaurantModel.setPhone( restaurantEntity.getPhone() );
        restaurantModel.setUrlLogo( restaurantEntity.getUrlLogo() );
        restaurantModel.setOwnerId( restaurantEntity.getOwnerId() );

        return restaurantModel;
    }

    protected RestaurantEmployeeModel restaurantEmployeeEntityToRestaurantEmployeeModel(RestaurantEmployeeEntity restaurantEmployeeEntity) {
        if ( restaurantEmployeeEntity == null ) {
            return null;
        }

        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();

        restaurantEmployeeModel.setId( restaurantEmployeeEntity.getId() );
        restaurantEmployeeModel.setEmployeeId( restaurantEmployeeEntity.getEmployeeId() );
        restaurantEmployeeModel.setRestaurant( restaurantEntityToRestaurantModel( restaurantEmployeeEntity.getRestaurant() ) );

        return restaurantEmployeeModel;
    }

    protected RestaurantEntity restaurantModelToRestaurantEntity(RestaurantModel restaurantModel) {
        if ( restaurantModel == null ) {
            return null;
        }

        RestaurantEntity restaurantEntity = new RestaurantEntity();

        restaurantEntity.setId( restaurantModel.getId() );
        restaurantEntity.setName( restaurantModel.getName() );
        restaurantEntity.setNit( restaurantModel.getNit() );
        restaurantEntity.setAddress( restaurantModel.getAddress() );
        restaurantEntity.setPhone( restaurantModel.getPhone() );
        restaurantEntity.setUrlLogo( restaurantModel.getUrlLogo() );
        restaurantEntity.setOwnerId( restaurantModel.getOwnerId() );

        return restaurantEntity;
    }

    protected RestaurantEmployeeEntity restaurantEmployeeModelToRestaurantEmployeeEntity(RestaurantEmployeeModel restaurantEmployeeModel) {
        if ( restaurantEmployeeModel == null ) {
            return null;
        }

        RestaurantEmployeeEntity restaurantEmployeeEntity = new RestaurantEmployeeEntity();

        restaurantEmployeeEntity.setId( restaurantEmployeeModel.getId() );
        restaurantEmployeeEntity.setEmployeeId( restaurantEmployeeModel.getEmployeeId() );
        restaurantEmployeeEntity.setRestaurant( restaurantModelToRestaurantEntity( restaurantEmployeeModel.getRestaurant() ) );

        return restaurantEmployeeEntity;
    }
}

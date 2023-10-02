package com.pragma.foodcourtservice.infraestructure.out.jpa.mapper;

import com.pragma.foodcourtservice.domain.model.CategoryModel;
import com.pragma.foodcourtservice.domain.model.DishModel;
import com.pragma.foodcourtservice.domain.model.OrderDishModel;
import com.pragma.foodcourtservice.domain.model.OrderModel;
import com.pragma.foodcourtservice.domain.model.RestaurantEmployeeModel;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import com.pragma.foodcourtservice.domain.model.StatusEnumModel;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.CategoryEntity;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.DishEntity;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.OrderDishEntity;
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
    date = "2023-10-02T10:40:20-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class IOrderDishEntityMapperImpl implements IOrderDishEntityMapper {

    @Override
    public List<OrderDishEntity> mapToOrderDishEntityList(List<OrderDishModel> orderDishEntityList) {
        if ( orderDishEntityList == null ) {
            return null;
        }

        List<OrderDishEntity> list = new ArrayList<OrderDishEntity>( orderDishEntityList.size() );
        for ( OrderDishModel orderDishModel : orderDishEntityList ) {
            list.add( orderDishModelToOrderDishEntity( orderDishModel ) );
        }

        return list;
    }

    @Override
    public List<OrderDishModel> mapToOrderDishModelList(List<OrderDishEntity> orderDishEntityList) {
        if ( orderDishEntityList == null ) {
            return null;
        }

        List<OrderDishModel> list = new ArrayList<OrderDishModel>( orderDishEntityList.size() );
        for ( OrderDishEntity orderDishEntity : orderDishEntityList ) {
            list.add( orderDishEntityToOrderDishModel( orderDishEntity ) );
        }

        return list;
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

    protected CategoryEntity categoryModelToCategoryEntity(CategoryModel categoryModel) {
        if ( categoryModel == null ) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setId( categoryModel.getId() );
        categoryEntity.setName( categoryModel.getName() );
        categoryEntity.setDescription( categoryModel.getDescription() );

        return categoryEntity;
    }

    protected DishEntity dishModelToDishEntity(DishModel dishModel) {
        if ( dishModel == null ) {
            return null;
        }

        DishEntity dishEntity = new DishEntity();

        dishEntity.setId( dishModel.getId() );
        dishEntity.setName( dishModel.getName() );
        dishEntity.setDescription( dishModel.getDescription() );
        dishEntity.setUrlImage( dishModel.getUrlImage() );
        dishEntity.setPrice( dishModel.getPrice() );
        dishEntity.setActive( dishModel.getActive() );
        dishEntity.setRestaurant( restaurantModelToRestaurantEntity( dishModel.getRestaurant() ) );
        dishEntity.setCategory( categoryModelToCategoryEntity( dishModel.getCategory() ) );

        return dishEntity;
    }

    protected StatusEnum statusEnumModelToStatusEnum(StatusEnumModel statusEnumModel) {
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

    protected OrderEntity orderModelToOrderEntity(OrderModel orderModel) {
        if ( orderModel == null ) {
            return null;
        }

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setId( orderModel.getId() );
        orderEntity.setCustomerId( orderModel.getCustomerId() );
        orderEntity.setCreatedAt( orderModel.getCreatedAt() );
        orderEntity.setStatus( statusEnumModelToStatusEnum( orderModel.getStatus() ) );
        orderEntity.setRestaurant( restaurantModelToRestaurantEntity( orderModel.getRestaurant() ) );
        orderEntity.setRestaurantEmployee( restaurantEmployeeModelToRestaurantEmployeeEntity( orderModel.getRestaurantEmployee() ) );
        orderEntity.setDurationMinutes( orderModel.getDurationMinutes() );

        return orderEntity;
    }

    protected OrderDishEntity orderDishModelToOrderDishEntity(OrderDishModel orderDishModel) {
        if ( orderDishModel == null ) {
            return null;
        }

        OrderDishEntity orderDishEntity = new OrderDishEntity();

        orderDishEntity.setId( orderDishModel.getId() );
        orderDishEntity.setDish( dishModelToDishEntity( orderDishModel.getDish() ) );
        orderDishEntity.setOrder( orderModelToOrderEntity( orderDishModel.getOrder() ) );
        orderDishEntity.setAmount( orderDishModel.getAmount() );

        return orderDishEntity;
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

    protected CategoryModel categoryEntityToCategoryModel(CategoryEntity categoryEntity) {
        if ( categoryEntity == null ) {
            return null;
        }

        CategoryModel categoryModel = new CategoryModel();

        categoryModel.setId( categoryEntity.getId() );
        categoryModel.setName( categoryEntity.getName() );
        categoryModel.setDescription( categoryEntity.getDescription() );

        return categoryModel;
    }

    protected DishModel dishEntityToDishModel(DishEntity dishEntity) {
        if ( dishEntity == null ) {
            return null;
        }

        DishModel dishModel = new DishModel();

        dishModel.setId( dishEntity.getId() );
        dishModel.setName( dishEntity.getName() );
        dishModel.setDescription( dishEntity.getDescription() );
        dishModel.setPrice( dishEntity.getPrice() );
        dishModel.setUrlImage( dishEntity.getUrlImage() );
        dishModel.setActive( dishEntity.getActive() );
        dishModel.setRestaurant( restaurantEntityToRestaurantModel( dishEntity.getRestaurant() ) );
        dishModel.setCategory( categoryEntityToCategoryModel( dishEntity.getCategory() ) );

        return dishModel;
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

    protected OrderModel orderEntityToOrderModel(OrderEntity orderEntity) {
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
        orderModel.setDurationMinutes( orderEntity.getDurationMinutes() );

        return orderModel;
    }

    protected OrderDishModel orderDishEntityToOrderDishModel(OrderDishEntity orderDishEntity) {
        if ( orderDishEntity == null ) {
            return null;
        }

        OrderDishModel orderDishModel = new OrderDishModel();

        orderDishModel.setId( orderDishEntity.getId() );
        orderDishModel.setDish( dishEntityToDishModel( orderDishEntity.getDish() ) );
        orderDishModel.setOrder( orderEntityToOrderModel( orderDishEntity.getOrder() ) );
        orderDishModel.setAmount( orderDishEntity.getAmount() );

        return orderDishModel;
    }
}

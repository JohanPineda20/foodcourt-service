package com.pragma.foodcourtservice.application.mapper;

import com.pragma.foodcourtservice.application.dto.request.DishRequest;
import com.pragma.foodcourtservice.application.dto.request.UpdateDishRequest;
import com.pragma.foodcourtservice.domain.model.CategoryModel;
import com.pragma.foodcourtservice.domain.model.DishModel;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-21T01:04:17-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class IDishDtoMapperImpl implements IDishDtoMapper {

    @Override
    public DishModel mapToDishModel(DishRequest dishRequest) {
        if ( dishRequest == null ) {
            return null;
        }

        DishModel dishModel = new DishModel();

        dishModel.setCategory( dishRequestToCategoryModel( dishRequest ) );
        dishModel.setRestaurant( dishRequestToRestaurantModel( dishRequest ) );
        dishModel.setName( dishRequest.getName() );
        dishModel.setDescription( dishRequest.getDescription() );
        dishModel.setPrice( dishRequest.getPrice() );
        dishModel.setUrlImage( dishRequest.getUrlImage() );

        return dishModel;
    }

    @Override
    public DishModel mapToDishModelFromUpdateDishRequest(UpdateDishRequest updateDishRequest) {
        if ( updateDishRequest == null ) {
            return null;
        }

        DishModel dishModel = new DishModel();

        dishModel.setDescription( updateDishRequest.getDescription() );
        dishModel.setPrice( updateDishRequest.getPrice() );

        return dishModel;
    }

    protected CategoryModel dishRequestToCategoryModel(DishRequest dishRequest) {
        if ( dishRequest == null ) {
            return null;
        }

        CategoryModel categoryModel = new CategoryModel();

        categoryModel.setId( dishRequest.getCategoryId() );

        return categoryModel;
    }

    protected RestaurantModel dishRequestToRestaurantModel(DishRequest dishRequest) {
        if ( dishRequest == null ) {
            return null;
        }

        RestaurantModel restaurantModel = new RestaurantModel();

        restaurantModel.setId( dishRequest.getRestaurantId() );

        return restaurantModel;
    }
}

package com.pragma.foodcourtservice.application.mapper;

import com.pragma.foodcourtservice.application.dto.request.DishRequest;
import com.pragma.foodcourtservice.application.dto.request.UpdateDishRequest;
import com.pragma.foodcourtservice.application.dto.response.DishResponse;
import com.pragma.foodcourtservice.domain.model.CategoryModel;
import com.pragma.foodcourtservice.domain.model.DishModel;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-24T22:15:12-0500",
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

    @Override
    public List<DishResponse> mapToDishResponseList(List<DishModel> dishModelList) {
        if ( dishModelList == null ) {
            return null;
        }

        List<DishResponse> list = new ArrayList<DishResponse>( dishModelList.size() );
        for ( DishModel dishModel : dishModelList ) {
            list.add( dishModelToDishResponse( dishModel ) );
        }

        return list;
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

    protected DishResponse dishModelToDishResponse(DishModel dishModel) {
        if ( dishModel == null ) {
            return null;
        }

        String name = null;
        String description = null;
        BigDecimal price = null;
        String urlImage = null;

        name = dishModel.getName();
        description = dishModel.getDescription();
        price = dishModel.getPrice();
        urlImage = dishModel.getUrlImage();

        DishResponse dishResponse = new DishResponse( name, description, price, urlImage );

        return dishResponse;
    }
}

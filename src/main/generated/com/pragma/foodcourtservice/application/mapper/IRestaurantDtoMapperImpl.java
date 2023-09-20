package com.pragma.foodcourtservice.application.mapper;

import com.pragma.foodcourtservice.application.dto.request.RestaurantRequest;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-19T20:41:51-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class IRestaurantDtoMapperImpl implements IRestaurantDtoMapper {

    @Override
    public RestaurantModel mapToRestaurantModel(RestaurantRequest restaurantRequest) {
        if ( restaurantRequest == null ) {
            return null;
        }

        RestaurantModel restaurantModel = new RestaurantModel();

        restaurantModel.setName( restaurantRequest.getName() );
        restaurantModel.setNit( restaurantRequest.getNit() );
        restaurantModel.setAddress( restaurantRequest.getAddress() );
        restaurantModel.setPhone( restaurantRequest.getPhone() );
        restaurantModel.setUrlLogo( restaurantRequest.getUrlLogo() );
        if ( restaurantRequest.getOwnerId() != null ) {
            restaurantModel.setOwnerId( Long.parseLong( restaurantRequest.getOwnerId() ) );
        }

        return restaurantModel;
    }
}

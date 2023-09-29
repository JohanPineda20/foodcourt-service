package com.pragma.foodcourtservice.application.mapper;

import com.pragma.foodcourtservice.application.dto.request.RestaurantRequest;
import com.pragma.foodcourtservice.application.dto.response.RestaurantResponse;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-29T00:39:48-0500",
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
        restaurantModel.setOwnerId( restaurantRequest.getOwnerId() );

        return restaurantModel;
    }

    @Override
    public List<RestaurantResponse> mapToRestaurantResponseList(List<RestaurantModel> restaurantModelList) {
        if ( restaurantModelList == null ) {
            return null;
        }

        List<RestaurantResponse> list = new ArrayList<RestaurantResponse>( restaurantModelList.size() );
        for ( RestaurantModel restaurantModel : restaurantModelList ) {
            list.add( restaurantModelToRestaurantResponse( restaurantModel ) );
        }

        return list;
    }

    protected RestaurantResponse restaurantModelToRestaurantResponse(RestaurantModel restaurantModel) {
        if ( restaurantModel == null ) {
            return null;
        }

        String name = null;
        String urlLogo = null;

        name = restaurantModel.getName();
        urlLogo = restaurantModel.getUrlLogo();

        RestaurantResponse restaurantResponse = new RestaurantResponse( name, urlLogo );

        return restaurantResponse;
    }
}

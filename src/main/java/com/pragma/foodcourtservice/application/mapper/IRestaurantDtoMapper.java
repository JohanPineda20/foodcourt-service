package com.pragma.foodcourtservice.application.mapper;

import com.pragma.foodcourtservice.application.dto.request.RestaurantRequest;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IRestaurantDtoMapper {

    RestaurantModel mapToRestaurantModel(RestaurantRequest restaurantRequest);

}

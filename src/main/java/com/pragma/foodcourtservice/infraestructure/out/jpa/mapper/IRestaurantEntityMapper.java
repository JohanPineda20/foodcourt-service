package com.pragma.foodcourtservice.infraestructure.out.jpa.mapper;

import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEntityMapper {

    RestaurantEntity mapToRestaurantEntity(RestaurantModel restaurantModel);
    RestaurantModel mapToRestaurantModel(RestaurantEntity restaurantEntity);
}

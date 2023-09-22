package com.pragma.foodcourtservice.infraestructure.out.jpa.mapper;

import com.pragma.foodcourtservice.domain.model.RestaurantEmployeeModel;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.RestaurantEmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEmployeeEntityMapper {

    RestaurantEmployeeEntity mapToRestaurantEmployeeEntity(RestaurantEmployeeModel restaurantEmployeeModel);
    RestaurantEmployeeModel mapToRestaurantEmployeeModel(RestaurantEmployeeEntity restaurantEmployeeEntity);
}

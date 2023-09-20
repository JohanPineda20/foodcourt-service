package com.pragma.foodcourtservice.application.mapper;

import com.pragma.foodcourtservice.application.dto.request.DishRequest;
import com.pragma.foodcourtservice.domain.model.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IDishDtoMapper {

    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "restaurant.id", source = "restaurantId")
    DishModel mapToDishModel(DishRequest dishRequest);

}

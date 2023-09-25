package com.pragma.foodcourtservice.application.mapper;

import com.pragma.foodcourtservice.application.dto.request.DishRequest;
import com.pragma.foodcourtservice.application.dto.request.UpdateDishRequest;
import com.pragma.foodcourtservice.application.dto.response.DishResponse;
import com.pragma.foodcourtservice.domain.model.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IDishDtoMapper {

    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "restaurant.id", source = "restaurantId")
    DishModel mapToDishModel(DishRequest dishRequest);

    DishModel mapToDishModelFromUpdateDishRequest(UpdateDishRequest updateDishRequest);

    List<DishResponse> mapToDishResponseList (List<DishModel> dishModelList);

}

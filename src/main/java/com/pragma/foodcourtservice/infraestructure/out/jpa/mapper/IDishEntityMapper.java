package com.pragma.foodcourtservice.infraestructure.out.jpa.mapper;

import com.pragma.foodcourtservice.domain.model.DishModel;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IDishEntityMapper {

    DishEntity mapToDishEntity(DishModel dishModel);

    DishModel mapToDishModel(DishEntity dishEntity);

    List<DishModel> mapToDishModelList(List<DishEntity> dishEntityList);

}

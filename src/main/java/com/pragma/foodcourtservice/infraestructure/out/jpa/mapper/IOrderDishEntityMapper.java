package com.pragma.foodcourtservice.infraestructure.out.jpa.mapper;

import com.pragma.foodcourtservice.domain.model.OrderDishModel;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.OrderDishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IOrderDishEntityMapper {

    List<OrderDishEntity> mapToOrderDishEntityList(List<OrderDishModel> orderDishEntityList);
    List<OrderDishModel> mapToOrderDishModelList(List<OrderDishEntity> orderDishEntityList);
}

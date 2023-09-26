package com.pragma.foodcourtservice.application.mapper;

import com.pragma.foodcourtservice.application.dto.request.OrderDishRequest;
import com.pragma.foodcourtservice.application.dto.request.OrderRequest;
import com.pragma.foodcourtservice.domain.model.OrderDishModel;
import com.pragma.foodcourtservice.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IOrderDtoMapper {

    @Mapping(target = "dish.id", source = "dishId")
    OrderDishModel mapToOrderDishModel(OrderDishRequest orderDishRequest);

    @Mapping(target = "restaurant.id", source = "restaurantId")
    OrderModel mapToOrderModel(OrderRequest orderRequest);
}

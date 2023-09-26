package com.pragma.foodcourtservice.application.mapper;

import com.pragma.foodcourtservice.application.dto.request.OrderDishRequest;
import com.pragma.foodcourtservice.application.dto.request.OrderRequest;
import com.pragma.foodcourtservice.application.dto.response.OrderDishResponse;
import com.pragma.foodcourtservice.application.dto.response.OrderResponse;
import com.pragma.foodcourtservice.domain.model.OrderDishModel;
import com.pragma.foodcourtservice.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IOrderDtoMapper {

    @Mapping(target = "dish.id", source = "dishId")
    OrderDishModel mapToOrderDishModel(OrderDishRequest orderDishRequest);

    @Mapping(target = "restaurant.id", source = "restaurantId")
    OrderModel mapToOrderModel(OrderRequest orderRequest);

    @Mapping(target = "employeeId",source = "restaurantEmployee.id")
    @Mapping(target = "restaurantName",source = "restaurant.name")
    OrderResponse mapToOrderResponse(OrderModel orderModel);
    @Mapping(target = "dishName",source = "dish.name")
    @Mapping(target = "dishPrice",source = "dish.price")
    @Mapping(target = "dishCategoryName",source = "dish.category.name")
    OrderDishResponse mapToOrderResponse(OrderDishModel orderModel);
    List<OrderResponse> mapToOrderResponseList(List<OrderModel> orderModelList);
}

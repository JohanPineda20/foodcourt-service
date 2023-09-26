package com.pragma.foodcourtservice.infraestructure.out.jpa.mapper;

import com.pragma.foodcourtservice.domain.model.OrderModel;
import com.pragma.foodcourtservice.domain.model.StatusEnumModel;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.OrderEntity;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.StatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {

    OrderModel mapToOrderModel(OrderEntity orderEntity);
    OrderEntity mapToOrderEntity(OrderModel orderModel);
    List<OrderModel> mapToOrderModelList(List<OrderEntity> orderEntityList);
    StatusEnum mapToStatusEnum(StatusEnumModel statusEnumModel);

}

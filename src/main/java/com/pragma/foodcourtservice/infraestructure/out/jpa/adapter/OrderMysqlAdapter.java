package com.pragma.foodcourtservice.infraestructure.out.jpa.adapter;

import com.pragma.foodcourtservice.domain.model.OrderDishModel;
import com.pragma.foodcourtservice.domain.model.OrderModel;
import com.pragma.foodcourtservice.domain.spi.IOrderPersistencePort;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.OrderDishEntity;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IOrderDishRepository;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderMysqlAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;

    @Override
    public List<OrderModel> findByCustomerId(Long customerId) {
        return orderEntityMapper.mapToOrderModelList(orderRepository.findByCustomerId(customerId));
    }

    @Override
    public OrderModel save(OrderModel orderModel) {
        return orderEntityMapper.mapToOrderModel(orderRepository.save(orderEntityMapper.mapToOrderEntity(orderModel)));
    }

    @Override
    public void saveOrderDish(List<OrderDishModel> dishes) {
        List<OrderDishEntity> orderDishEntityList = orderDishEntityMapper.mapToOrderDishEntityList(dishes);
        orderDishRepository.saveAll(orderDishEntityList);
    }
}

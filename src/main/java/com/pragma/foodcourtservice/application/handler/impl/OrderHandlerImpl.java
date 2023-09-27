package com.pragma.foodcourtservice.application.handler.impl;

import com.pragma.foodcourtservice.application.dto.request.OrderRequest;
import com.pragma.foodcourtservice.application.dto.response.OrderResponse;
import com.pragma.foodcourtservice.application.handler.IOrderHandler;
import com.pragma.foodcourtservice.application.mapper.IOrderDtoMapper;
import com.pragma.foodcourtservice.domain.api.IOrderServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderDtoMapper orderDtoMapper;

    @Override
    public void save(OrderRequest orderRequest) {
        orderServicePort.save(orderDtoMapper.mapToOrderModel(orderRequest));
    }

    @Override
    public List<OrderResponse> getAllOrdersByRestaurantAndStatus(Integer page, Integer size, String status) {
        return orderDtoMapper.mapToOrderResponseList(orderServicePort.getAllOrdersByRestaurantAndStatus(page, size, status));
    }

    @Override
    public void takeOrder(Long id) {
        orderServicePort.takeOrder(id);
    }
}

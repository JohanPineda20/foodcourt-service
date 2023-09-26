package com.pragma.foodcourtservice.application.handler.impl;

import com.pragma.foodcourtservice.application.dto.request.OrderRequest;
import com.pragma.foodcourtservice.application.handler.IOrderHandler;
import com.pragma.foodcourtservice.application.mapper.IOrderDtoMapper;
import com.pragma.foodcourtservice.domain.api.IOrderServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}

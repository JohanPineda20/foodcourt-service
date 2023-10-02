package com.pragma.foodcourtservice.application.handler.impl;

import com.pragma.foodcourtservice.application.dto.request.OrderRequest;
import com.pragma.foodcourtservice.application.dto.response.OrderDurationResponse;
import com.pragma.foodcourtservice.application.dto.response.OrderRankingResponse;
import com.pragma.foodcourtservice.application.dto.response.OrderResponse;
import com.pragma.foodcourtservice.application.dto.response.TrackingResponse;
import com.pragma.foodcourtservice.application.handler.IOrderHandler;
import com.pragma.foodcourtservice.application.mapper.IOrderDtoMapper;
import com.pragma.foodcourtservice.domain.api.IOrderServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    @Override
    public void readyOrder(Long id) {
        orderServicePort.readyOrder(id);
    }
    @Override
    public void deliverOrder(Long id, String pin) {
        orderServicePort.deliverOrder(id, pin);
    }

    @Override
    public void cancelOrder(Long id) {
        orderServicePort.cancelOrder(id);
    }

    @Override
    public List<TrackingResponse> getHistoryOrder(Long id) {
        return orderDtoMapper.mapToTrackingResponseList(orderServicePort.getHistoryOrder(id));
    }

    @Override
    public List<OrderDurationResponse> getOrderDuration(Integer page, Integer size) {
        return orderDtoMapper.mapToOrderDurationResponseList(orderServicePort.getOrderDuration(page, size));
    }

    @Override
    public List<OrderRankingResponse> getRankingEmployee(Integer page, Integer size) {
        List<Object[]> objectList = orderServicePort.getRankingEmployee(page, size);
        return objectList.stream()
                .map(fields -> new OrderRankingResponse((long)fields[0], (double)fields[1]))
                .toList();
    }
}

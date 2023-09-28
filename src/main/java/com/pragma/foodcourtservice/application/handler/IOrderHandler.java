package com.pragma.foodcourtservice.application.handler;

import com.pragma.foodcourtservice.application.dto.request.OrderRequest;
import com.pragma.foodcourtservice.application.dto.response.OrderResponse;

import java.util.List;

public interface IOrderHandler {
    void save(OrderRequest orderRequest);
    List<OrderResponse> getAllOrdersByRestaurantAndStatus(Integer page, Integer size, String status);
    void takeOrder(Long id);
    void readyOrder(Long id);
}

package com.pragma.foodcourtservice.application.handler;

import com.pragma.foodcourtservice.application.dto.request.OrderRequest;
import com.pragma.foodcourtservice.application.dto.response.OrderDurationResponse;
import com.pragma.foodcourtservice.application.dto.response.OrderRankingResponse;
import com.pragma.foodcourtservice.application.dto.response.OrderResponse;
import com.pragma.foodcourtservice.application.dto.response.TrackingResponse;

import java.util.List;

public interface IOrderHandler {
    void save(OrderRequest orderRequest);
    List<OrderResponse> getAllOrdersByRestaurantAndStatus(Integer page, Integer size, String status);
    void takeOrder(Long id);
    void readyOrder(Long id);
    void deliverOrder(Long id, String pin);
    void cancelOrder(Long id);
    List<TrackingResponse> getHistoryOrder(Long id);
    List<OrderDurationResponse> getOrderDuration(Integer page, Integer size);
    List<OrderRankingResponse> getRankingEmployee(Integer page, Integer size);
}

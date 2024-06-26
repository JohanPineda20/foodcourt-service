package com.pragma.foodcourtservice.domain.api;

import com.pragma.foodcourtservice.domain.model.OrderModel;
import com.pragma.foodcourtservice.domain.model.TrackingModel;

import java.util.List;

public interface IOrderServicePort {
    void save(OrderModel orderModel);
    List<OrderModel> getAllOrdersByRestaurantAndStatus(Integer page, Integer size, String status);
    void takeOrder(Long id);
    void readyOrder(Long id);
    void deliverOrder(Long id, String pin);
    void cancelOrder(Long id);
    List<TrackingModel> getHistoryOrder(Long id);
    List<OrderModel> getOrderDuration(Integer page, Integer size);
    List<Object[]> getRankingEmployee(Integer page, Integer size);
}

package com.pragma.foodcourtservice.domain.api;

import com.pragma.foodcourtservice.domain.model.OrderModel;

public interface IOrderServicePort {
    void save(OrderModel orderModel);
}

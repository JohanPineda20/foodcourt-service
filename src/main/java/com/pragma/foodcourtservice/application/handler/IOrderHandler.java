package com.pragma.foodcourtservice.application.handler;

import com.pragma.foodcourtservice.application.dto.request.OrderRequest;

public interface IOrderHandler {
    void save(OrderRequest orderRequest);
}

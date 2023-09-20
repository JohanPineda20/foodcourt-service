package com.pragma.foodcourtservice.application.handler;

import com.pragma.foodcourtservice.application.dto.request.DishRequest;

public interface IDishHandler {
    void save(DishRequest dishRequest);
}

package com.pragma.foodcourtservice.application.handler;

import com.pragma.foodcourtservice.application.dto.request.DishRequest;
import com.pragma.foodcourtservice.application.dto.request.UpdateDishRequest;

public interface IDishHandler {
    void save(DishRequest dishRequest);

    void update(Long id, UpdateDishRequest updateDishRequest);
}

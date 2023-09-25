package com.pragma.foodcourtservice.application.handler;

import com.pragma.foodcourtservice.application.dto.request.DishRequest;
import com.pragma.foodcourtservice.application.dto.request.UpdateDishRequest;
import com.pragma.foodcourtservice.application.dto.response.DishResponse;

import java.util.List;

public interface IDishHandler {
    void save(DishRequest dishRequest);

    void update(Long id, UpdateDishRequest updateDishRequest);

    void enableDisable(Long id);
    List<DishResponse> getAllDishesByRestaurantAndCategory(Integer page, Integer size, Long restaurantId, Long categoryId);
}

package com.pragma.foodcourtservice.domain.api;

import com.pragma.foodcourtservice.domain.model.DishModel;

import java.util.List;

public interface IDishServicePort {
    void save(DishModel dishModel);
    void update(Long id, DishModel dishModel);
    void enableDisable(Long id);
    List<DishModel> getAllDishesByRestaurantAndCategory(Integer page, Integer size, Long restaurantId, Long categoryId);
}

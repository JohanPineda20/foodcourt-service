package com.pragma.foodcourtservice.domain.spi;

import com.pragma.foodcourtservice.domain.model.DishModel;

public interface IDishPersistencePort {
    boolean existsByNameAndRestaurantId(String name, Long restaurantId);
    void save(DishModel dishModel);
}

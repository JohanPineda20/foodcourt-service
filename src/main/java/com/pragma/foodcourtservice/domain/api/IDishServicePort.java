package com.pragma.foodcourtservice.domain.api;

import com.pragma.foodcourtservice.domain.model.DishModel;

public interface IDishServicePort {
    void save(DishModel dishModel);

    void update(Long id, DishModel dishModel);

    void enableDisable(Long id);
}

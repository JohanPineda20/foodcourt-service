package com.pragma.foodcourtservice.domain.spi;

import com.pragma.foodcourtservice.domain.model.DishModel;

public interface IDishPersistencePort {
    boolean existsByName(String name);
    void save(DishModel dishModel);
}

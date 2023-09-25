package com.pragma.foodcourtservice.domain.spi;

import com.pragma.foodcourtservice.domain.model.DishModel;

import java.util.List;

public interface IDishPersistencePort {
    boolean existsByNameAndRestaurantId(String name, Long restaurantId);
    void save(DishModel dishModel);
    DishModel findById(Long id);
    List<DishModel> getAllDishesByRestaurant(Integer page, Integer size, Long restaurantId, boolean active);

    //List<DishModel> getAllDishesByRestaurantAndCategory(Integer page, Integer size, Long restaurantId, boolean active, Long categoryId);
}

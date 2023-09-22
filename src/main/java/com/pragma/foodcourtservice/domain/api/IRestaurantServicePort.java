package com.pragma.foodcourtservice.domain.api;

import com.pragma.foodcourtservice.domain.model.RestaurantModel;

public interface IRestaurantServicePort {
    void save(RestaurantModel restaurantModel);
    void saveRestaurantEmployee(Long ownerId, Long employeeId);
}

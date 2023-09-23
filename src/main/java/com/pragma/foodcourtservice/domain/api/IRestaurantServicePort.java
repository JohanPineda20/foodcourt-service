package com.pragma.foodcourtservice.domain.api;

import com.pragma.foodcourtservice.domain.model.RestaurantModel;

import java.util.List;

public interface IRestaurantServicePort {
    void save(RestaurantModel restaurantModel);
    void saveRestaurantEmployee(Long ownerId, Long employeeId);
    List<RestaurantModel> getAllRestaurants(Integer page, Integer size);
}

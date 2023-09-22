package com.pragma.foodcourtservice.application.handler;

import com.pragma.foodcourtservice.application.dto.request.RestaurantRequest;

public interface IRestaurantHandler {

    void save(RestaurantRequest restaurantRequest);

    void saveRestaurantEmployee(Long ownerId, Long employeeId);
}

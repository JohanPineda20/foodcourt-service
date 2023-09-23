package com.pragma.foodcourtservice.application.handler;

import com.pragma.foodcourtservice.application.dto.request.RestaurantRequest;
import com.pragma.foodcourtservice.application.dto.response.RestaurantResponse;

import java.util.List;

public interface IRestaurantHandler {

    void save(RestaurantRequest restaurantRequest);

    void saveRestaurantEmployee(Long ownerId, Long employeeId);

    List<RestaurantResponse> getAllRestaurants(Integer page, Integer size);
}

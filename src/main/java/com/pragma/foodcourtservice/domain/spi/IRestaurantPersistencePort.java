package com.pragma.foodcourtservice.domain.spi;

import com.pragma.foodcourtservice.domain.model.RestaurantEmployeeModel;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;

import java.util.List;

public interface IRestaurantPersistencePort {
    void save(RestaurantModel restaurantModel);
    boolean existsByName(String name);
    boolean existsByNit(String nit);
    RestaurantModel findByOwnerId(Long ownerId);
    RestaurantModel findById(Long id);
    boolean existsRestaurantEmployeeByEmployeeId(Long employeeId);
    void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel);
    List<RestaurantModel> getAllRestaurants(Integer page, Integer size);
    RestaurantEmployeeModel findRestaurantEmployeeByEmployeeId(Long employeeId);
}

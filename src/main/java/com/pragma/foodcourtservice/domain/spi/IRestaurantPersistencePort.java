package com.pragma.foodcourtservice.domain.spi;

import com.pragma.foodcourtservice.domain.model.RestaurantEmployeeModel;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;

public interface IRestaurantPersistencePort {

    //Restaurant methods
    void save(RestaurantModel restaurantModel);

    boolean existsByName(String name);

    boolean existsByNit(String nit);

    RestaurantModel findByOwnerId(Long ownerId);

    RestaurantModel findById(Long id);

    //RestaurantEmployee methods
    boolean existsRestaurantEmployeeByEmployeeId(Long employeeId);
    void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel);
}

package com.pragma.foodcourtservice.domain.spi;

import com.pragma.foodcourtservice.domain.model.RestaurantModel;

public interface IRestaurantPersistencePort {

    void save(RestaurantModel restaurantModel);

}

package com.pragma.foodcourtservice.infraestructure.out.jpa.adapter;

import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import com.pragma.foodcourtservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantMysqlAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;


    @Override
    public void save(RestaurantModel restaurantModel) {
        restaurantRepository.save(restaurantEntityMapper.mapToRestaurantEntity(restaurantModel));
    }
}

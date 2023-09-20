package com.pragma.foodcourtservice.infraestructure.out.jpa.adapter;

import com.pragma.foodcourtservice.domain.model.DishModel;
import com.pragma.foodcourtservice.domain.spi.IDishPersistencePort;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishMysqlAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    @Override
    public boolean existsByNameAndRestaurantId(String name, Long restaurantId) {
        return dishRepository.existsByNameAndRestaurantId(name, restaurantId);
    }
    @Override
    public void save(DishModel dishModel) {
        dishRepository.save(dishEntityMapper.mapToDishEntity(dishModel));
    }
    @Override
    public DishModel findById(Long id) {
        return dishEntityMapper.mapToDishModel(dishRepository.findById(id).orElse(null));
    }
}

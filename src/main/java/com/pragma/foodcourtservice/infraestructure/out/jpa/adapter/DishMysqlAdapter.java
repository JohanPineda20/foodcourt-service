package com.pragma.foodcourtservice.infraestructure.out.jpa.adapter;

import com.pragma.foodcourtservice.domain.model.DishModel;
import com.pragma.foodcourtservice.domain.spi.IDishPersistencePort;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

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
    @Override
    public List<DishModel> getAllDishesByRestaurant(Integer page, Integer size, Long restaurantId, boolean active) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        return dishEntityMapper.mapToDishModelList(dishRepository.findByRestaurantIdAndActive(pageable, restaurantId, active));
    }

   @Override
    public List<DishModel> getAllDishesByRestaurantAndCategory(Integer page, Integer size, Long restaurantId, boolean active, Long categoryId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        return dishEntityMapper.mapToDishModelList(dishRepository.findByRestaurantIdAndActiveAndCategoryId(pageable, restaurantId, active, categoryId));
    }
}

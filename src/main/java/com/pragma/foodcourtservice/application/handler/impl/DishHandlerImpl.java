package com.pragma.foodcourtservice.application.handler.impl;

import com.pragma.foodcourtservice.application.dto.request.DishRequest;
import com.pragma.foodcourtservice.application.dto.request.UpdateDishRequest;
import com.pragma.foodcourtservice.application.dto.response.DishResponse;
import com.pragma.foodcourtservice.application.handler.IDishHandler;
import com.pragma.foodcourtservice.application.mapper.IDishDtoMapper;
import com.pragma.foodcourtservice.domain.api.IDishServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DishHandlerImpl implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishDtoMapper dishDtoMapper;
    @Override
    public void save(DishRequest dishRequest) {
        dishServicePort.save(dishDtoMapper.mapToDishModel(dishRequest));
    }

    @Override
    public void update(Long id, UpdateDishRequest updateDishRequest) {
        dishServicePort.update(id, dishDtoMapper.mapToDishModelFromUpdateDishRequest(updateDishRequest));
    }
    @Override
    public void enableDisable(Long id) {
        dishServicePort.enableDisable(id);
    }
    @Override
    public List<DishResponse> getAllDishesByRestaurantAndCategory(Integer page, Integer size, Long restaurantId, Long categoryId) {
        return dishDtoMapper.mapToDishResponseList(dishServicePort.getAllDishesByRestaurantAndCategory(page, size, restaurantId, categoryId));
    }
}

package com.pragma.foodcourtservice.application.handler.impl;

import com.pragma.foodcourtservice.application.dto.request.RestaurantRequest;
import com.pragma.foodcourtservice.application.handler.IRestaurantHandler;
import com.pragma.foodcourtservice.application.mapper.IRestaurantDtoMapper;
import com.pragma.foodcourtservice.domain.api.IRestaurantServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantHandlerImpl implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantDtoMapper restaurantDtoMapper;

    @Override
    public void save(RestaurantRequest restaurantRequest) {
        restaurantServicePort.save(restaurantDtoMapper.mapToRestaurantModel(restaurantRequest));
    }

    @Override
    public void saveRestaurantEmployee(Long ownerId, Long employeeId) {
        restaurantServicePort.saveRestaurantEmployee(ownerId, employeeId);
    }
}

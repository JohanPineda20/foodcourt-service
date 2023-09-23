package com.pragma.foodcourtservice.application.handler.impl;

import com.pragma.foodcourtservice.application.dto.request.RestaurantRequest;
import com.pragma.foodcourtservice.application.dto.response.RestaurantResponse;
import com.pragma.foodcourtservice.application.handler.IRestaurantHandler;
import com.pragma.foodcourtservice.application.mapper.IRestaurantDtoMapper;
import com.pragma.foodcourtservice.domain.api.IRestaurantServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<RestaurantResponse> getAllRestaurants(Integer page, Integer size) {
        return restaurantDtoMapper.mapToRestaurantResponseList(restaurantServicePort.getAllRestaurants(page, size));
    }
}

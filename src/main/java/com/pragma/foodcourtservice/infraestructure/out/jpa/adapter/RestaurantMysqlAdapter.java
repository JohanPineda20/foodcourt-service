package com.pragma.foodcourtservice.infraestructure.out.jpa.adapter;

import com.pragma.foodcourtservice.domain.model.RestaurantEmployeeModel;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import com.pragma.foodcourtservice.domain.spi.persistence.IRestaurantPersistencePort;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.RestaurantEntity;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IRestaurantEmployeeEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IRestaurantEmployeeRepository;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class RestaurantMysqlAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;

    @Override
    public void save(RestaurantModel restaurantModel) {
        restaurantRepository.save(restaurantEntityMapper.mapToRestaurantEntity(restaurantModel));
    }

    @Override
    public boolean existsByName(String name) {
        return restaurantRepository.existsByName(name);
    }

    @Override
    public boolean existsByNit(String nit) {
        return restaurantRepository.existsByNit(nit);
    }

    @Override
    public RestaurantModel findByOwnerId(Long ownerId) {
        return restaurantEntityMapper.mapToRestaurantModel(restaurantRepository.findByOwnerId(ownerId).orElse(null));
    }

    @Override
    public RestaurantModel findById(Long id) {
        return restaurantEntityMapper.mapToRestaurantModel(restaurantRepository.findById(id).orElse(null));
    }

    @Override
    public boolean existsRestaurantEmployeeByEmployeeId(Long employeeId) {
        return restaurantEmployeeRepository.existsRestaurantEmployeeByEmployeeId(employeeId);
    }
    @Override
    public void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel) {
        restaurantEmployeeRepository.save(restaurantEmployeeEntityMapper.mapToRestaurantEmployeeEntity(restaurantEmployeeModel));
    }

    @Override
    public List<RestaurantModel> getAllRestaurants(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<RestaurantEntity> restaurantEntityPage = restaurantRepository.findAll(pageRequest);
        return restaurantEntityMapper.mapToRestaurantModelList(restaurantEntityPage.getContent());}
    @Override
    public RestaurantEmployeeModel findRestaurantEmployeeByEmployeeId(Long employeeId) {
        return restaurantEmployeeEntityMapper.mapToRestaurantEmployeeModel(restaurantEmployeeRepository.findByEmployeeId(employeeId).orElse(null));
    }
}

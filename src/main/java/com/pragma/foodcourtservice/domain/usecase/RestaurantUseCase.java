package com.pragma.foodcourtservice.domain.usecase;

import com.pragma.foodcourtservice.domain.api.IRestaurantServicePort;
import com.pragma.foodcourtservice.domain.exception.DataAlreadyExistsException;
import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.exception.DomainException;
import com.pragma.foodcourtservice.domain.model.RestaurantEmployeeModel;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import com.pragma.foodcourtservice.domain.model.UserModel;
import com.pragma.foodcourtservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.foodcourtservice.domain.spi.IUserFeignClientPort;
import feign.FeignException;

import java.util.List;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserFeignClientPort userFeignClientPort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserFeignClientPort userFeignClientPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userFeignClientPort = userFeignClientPort;
    }

    @Override
    public void save(RestaurantModel restaurantModel) {
        if(restaurantPersistencePort.existsByName(restaurantModel.getName())){
            throw new DataAlreadyExistsException("Restaurant with name " + restaurantModel.getName() + " already exists");
        }
        if(restaurantPersistencePort.existsByNit(restaurantModel.getNit())){
            throw new DataAlreadyExistsException("Restaurant with nit " + restaurantModel.getNit() + " already exists");
        }
        UserModel userModel;
        try{
            userModel = userFeignClientPort.findById(restaurantModel.getOwnerId());
        }catch(FeignException.NotFound e){
            throw new DataNotFoundException("User not found");
        }catch(FeignException e){
            throw new DomainException(e.getMessage());
        }

        if(!userModel.getRole().equals("OWNER")) throw new DomainException("User must be an owner");

        RestaurantModel restaurantModel1 = restaurantPersistencePort.findByOwnerId(restaurantModel.getOwnerId());
        if(restaurantModel1 != null) throw new DomainException("Owner must have only one restaurant");

        restaurantPersistencePort.save(restaurantModel);
    }

    @Override
    public void saveRestaurantEmployee(Long ownerId, Long employeeId) {
        if(restaurantPersistencePort.existsRestaurantEmployeeByEmployeeId(employeeId)){
            throw new DataAlreadyExistsException("Employee already exists");
        }

        RestaurantModel restaurantModel = restaurantPersistencePort.findByOwnerId(ownerId);
        if(restaurantModel==null) throw new DataNotFoundException("Owner does not have a restaurant yet");
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setEmployeeId(employeeId);
        restaurantEmployeeModel.setRestaurant(restaurantModel);
        restaurantPersistencePort.saveRestaurantEmployee(restaurantEmployeeModel);
    }

    @Override
    public List<RestaurantModel> getAllRestaurants(Integer page, Integer size) {
        List<RestaurantModel> restaurantModelList = restaurantPersistencePort.getAllRestaurants(page, size);
        if(restaurantModelList.isEmpty()) throw new DataNotFoundException("There are not restaurants");
        return restaurantModelList;
    }
}

package com.pragma.foodcourtservice.domain.usecase;

import com.pragma.foodcourtservice.domain.exception.DataAlreadyExistsException;
import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.exception.DomainException;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import com.pragma.foodcourtservice.domain.model.UserModel;
import com.pragma.foodcourtservice.domain.spi.persistence.IRestaurantPersistencePort;
import com.pragma.foodcourtservice.domain.spi.feignclient.IUserFeignClientPort;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class RestaurantUseCaseTest {

    @InjectMocks
    RestaurantUseCase restaurantUseCase;
    @Mock
    IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    IUserFeignClientPort userFeignClientPort;

    @Test
    void save(){
        RestaurantModel restaurantModel = createExampleRestaurant();
        UserModel userModel = createExampleUser();
        when(userFeignClientPort.findById(restaurantModel.getOwnerId())).thenReturn(userModel);

        restaurantUseCase.save(restaurantModel);

        verify(restaurantPersistencePort, times(1)).save(restaurantModel);
    }

    @Test
    void saveFeignExceptionNotFound(){
        RestaurantModel restaurantModel = createExampleRestaurant();
        when(userFeignClientPort.findById(restaurantModel.getOwnerId())).thenThrow(FeignException.NotFound.class);

        assertThrows(DataNotFoundException.class, ()->restaurantUseCase.save(restaurantModel));
        verify(restaurantPersistencePort, never()).save(restaurantModel);
    }

    @Test
    void saveFeignException(){
        RestaurantModel restaurantModel = createExampleRestaurant();
        when(userFeignClientPort.findById(restaurantModel.getOwnerId())).thenThrow(FeignException.class);

        assertThrows(DomainException.class, ()->restaurantUseCase.save(restaurantModel));
        verify(restaurantPersistencePort, never()).save(restaurantModel);
    }

    @Test
    void saveUserIsNotOwner(){
        RestaurantModel restaurantModel = createExampleRestaurant();
        UserModel userModel = createExampleUser();
        userModel.setRole("CUSTOMER");
        when(userFeignClientPort.findById(restaurantModel.getOwnerId())).thenReturn(userModel);

        assertThrows(DomainException.class, ()->restaurantUseCase.save(restaurantModel));
        verify(restaurantPersistencePort, never()).save(restaurantModel);
    }

    @Test
    void saveUserAlreadyHaveARestaurant(){
        RestaurantModel restaurantModel = createExampleRestaurant();
        UserModel userModel = createExampleUser();
        when(userFeignClientPort.findById(restaurantModel.getOwnerId())).thenReturn(userModel);
        when(restaurantPersistencePort.findByOwnerId(restaurantModel.getOwnerId())).thenReturn(new RestaurantModel());

        assertThrows(DomainException.class, ()->restaurantUseCase.save(restaurantModel));
        verify(restaurantPersistencePort, never()).save(restaurantModel);
    }

    @Test
    void saveRestaurantAlreadyExistsByName(){
        RestaurantModel restaurantModel = createExampleRestaurant();
        when(restaurantPersistencePort.existsByName(restaurantModel.getName())).thenReturn(true);

        assertThrows(DataAlreadyExistsException.class, () -> restaurantUseCase.save(restaurantModel));
        verify(restaurantPersistencePort, never()).save(restaurantModel);

        when(restaurantPersistencePort.existsByNit(restaurantModel.getNit())).thenReturn(true);

        assertThrows(DataAlreadyExistsException.class, () -> restaurantUseCase.save(restaurantModel));
        verify(restaurantPersistencePort, never()).save(restaurantModel);
    }

    @Test
    void saveRestaurantAlreadyExistsByNit(){
        RestaurantModel restaurantModel = createExampleRestaurant();

        when(restaurantPersistencePort.existsByNit(restaurantModel.getNit())).thenReturn(true);

        assertThrows(DataAlreadyExistsException.class, () -> restaurantUseCase.save(restaurantModel));
        verify(restaurantPersistencePort, never()).save(restaurantModel);
    }

    @Test
    void saveRestaurantEmployee(){
        Long ownerId = 1L;
        Long employeeId = 1L;
        RestaurantModel restaurantModel = createExampleRestaurant();
        when(restaurantPersistencePort.findByOwnerId(ownerId)).thenReturn(restaurantModel);

        restaurantUseCase.saveRestaurantEmployee(ownerId, employeeId);

        verify(restaurantPersistencePort, times(1)).saveRestaurantEmployee(any());
    }

    @Test
    void saveRestaurantEmployeeAlreadyExists(){
        Long ownerId = 1L;
        Long employeeId = 1L;
        when(restaurantPersistencePort.existsRestaurantEmployeeByEmployeeId(employeeId)).thenReturn(true);

        assertThrows(DataAlreadyExistsException.class, () -> restaurantUseCase.saveRestaurantEmployee(ownerId, employeeId));
        verify(restaurantPersistencePort, never()).saveRestaurantEmployee(any());
    }

    @Test
    void saveRestaurantEmployeeRetaurantNotFound(){
        Long ownerId = 1L;
        Long employeeId = 1L;
        when(restaurantPersistencePort.findByOwnerId(ownerId)).thenReturn(null);
        assertThrows(DataNotFoundException.class, () -> restaurantUseCase.saveRestaurantEmployee(ownerId, employeeId));
        verify(restaurantPersistencePort, never()).saveRestaurantEmployee(any());
    }
    @Test
    void getAllRestaurants(){
        Integer page = 1;
        Integer size= 10;
        List<RestaurantModel> restaurantModelList = List.of(createExampleRestaurant());
        when(restaurantPersistencePort.getAllRestaurants(page,size)).thenReturn(restaurantModelList);

        List<RestaurantModel> restaurantModelList1 = restaurantUseCase.getAllRestaurants(page,size);

        assertEquals(restaurantModelList, restaurantModelList1);
    }

    @Test
    void getAllRestaurantsNotFound(){
        Integer page = 1;
        Integer size= 10;
        when(restaurantPersistencePort.getAllRestaurants(page,size)).thenReturn(new ArrayList<>());

        assertThrows(DataNotFoundException.class, () -> restaurantUseCase.getAllRestaurants(page,size));
    }
    @Test
    void getAllRestaurantsIllegalArgumentException(){
        Integer page = -1;
        Integer size= -10;
        when(restaurantPersistencePort.getAllRestaurants(page,size)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> restaurantUseCase.getAllRestaurants(page,size));
    }
    private RestaurantModel createExampleRestaurant(){
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setName("rodizio");
        restaurantModel.setNit("123456789");
        restaurantModel.setAddress("malecon");
        restaurantModel.setPhone("311433232");
        restaurantModel.setUrlLogo("image.jpg");
        restaurantModel.setOwnerId(1L);
        return restaurantModel;
    }
    private UserModel createExampleUser(){
        UserModel userModel = new UserModel();
        //los demas atributos no hacen falta
        userModel.setRole("OWNER");
        return userModel;
    }

}
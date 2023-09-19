package com.pragma.foodcourtservice.domain.usecase;

import com.pragma.foodcourtservice.domain.exception.DataAlreadyExistsException;
import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.exception.DomainException;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import com.pragma.foodcourtservice.domain.model.UserModel;
import com.pragma.foodcourtservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.foodcourtservice.domain.spi.IUserFeignClientPort;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
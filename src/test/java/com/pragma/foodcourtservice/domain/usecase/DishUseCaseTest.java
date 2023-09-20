package com.pragma.foodcourtservice.domain.usecase;

import com.pragma.foodcourtservice.domain.exception.DataAlreadyExistsException;
import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.exception.DomainException;
import com.pragma.foodcourtservice.domain.model.CategoryModel;
import com.pragma.foodcourtservice.domain.model.DishModel;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import com.pragma.foodcourtservice.domain.spi.ICategoryPersistencePort;
import com.pragma.foodcourtservice.domain.spi.IDishPersistencePort;
import com.pragma.foodcourtservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @InjectMocks
    DishUseCase dishUseCase;

    @Mock
    ICategoryPersistencePort categoryPersistencePort;
    @Mock
    IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    IDishPersistencePort dishPersistencePort;

    @Test
    void save() {
        DishModel dishModel = createExampleDish();
        CategoryModel categoryModel = createExampleCategory();
        RestaurantModel restaurantModel = createExampleRestaurant();
        when(categoryPersistencePort.findById(dishModel.getCategory().getId())).thenReturn(categoryModel);
        when(restaurantPersistencePort.findById(dishModel.getRestaurant().getId())).thenReturn(restaurantModel);

        dishUseCase.save(dishModel);

        verify(dishPersistencePort, times(1)).save(dishModel);
        assertTrue(dishModel.getActive());
        assertEquals(categoryModel, dishModel.getCategory());
        assertEquals(restaurantModel, dishModel.getRestaurant());
    }
    @Test
    void saveDishAlreadyExists() {
        DishModel dishModel = createExampleDish();
        CategoryModel categoryModel = createExampleCategory();
        RestaurantModel restaurantModel = createExampleRestaurant();
        when(categoryPersistencePort.findById(dishModel.getCategory().getId())).thenReturn(categoryModel);
        when(restaurantPersistencePort.findById(dishModel.getRestaurant().getId())).thenReturn(restaurantModel);
        when(dishPersistencePort.existsByNameAndRestaurantId(dishModel.getName(), restaurantModel.getId())).thenReturn(true);

        assertThrows(DataAlreadyExistsException.class, () -> dishUseCase.save(dishModel));
        verify(dishPersistencePort, never()).save(dishModel);
        assertNull(dishModel.getActive());
        assertEquals(1L, dishModel.getCategory().getId());
        assertEquals(1L, dishModel.getRestaurant().getId());
    }

    @Test
    void saveCategoryNotFound() {
        DishModel dishModel = createExampleDish();
        when(categoryPersistencePort.findById(dishModel.getCategory().getId())).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> dishUseCase.save(dishModel));
        verify(dishPersistencePort, never()).save(dishModel);
        assertNull(dishModel.getActive());
        assertEquals(1L, dishModel.getCategory().getId());
        assertEquals(1L, dishModel.getRestaurant().getId());
    }

    @Test
    void saveRestaurantNotFound() {
        DishModel dishModel = createExampleDish();
        CategoryModel categoryModel = createExampleCategory();
        when(categoryPersistencePort.findById(dishModel.getCategory().getId())).thenReturn(categoryModel);
        when(restaurantPersistencePort.findById(dishModel.getRestaurant().getId())).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> dishUseCase.save(dishModel));
        verify(dishPersistencePort, never()).save(dishModel);
        assertNull(dishModel.getActive());
        assertEquals(1L, dishModel.getCategory().getId());
        assertEquals(1L, dishModel.getRestaurant().getId());
    }
    @Test
    void update() {
        DishModel dishModel = createExampleDish();
        Long id =  1L;
        DishModel updateDish = new DishModel();
        updateDish.setPrice(BigDecimal.valueOf(0.5));
        updateDish.setDescription("delicious");
        when(dishPersistencePort.findById(id)).thenReturn(dishModel);

        dishUseCase.update(id, updateDish);

        verify(dishPersistencePort, times(1)).save(dishModel);
        assertEquals(updateDish.getPrice(), dishModel.getPrice());
        assertEquals(updateDish.getDescription(), dishModel.getDescription());
    }
    @Test
    void updateDishNotFound() {
        Long id =  1L;
        DishModel updateDish = new DishModel();
        updateDish.setPrice(BigDecimal.valueOf(0.5));
        updateDish.setDescription("delicious");
        when(dishPersistencePort.findById(id)).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> dishUseCase.update(id, updateDish));
        verify(dishPersistencePort, never()).save(any());
    }
    @Test
    void updatePriceAndDescriptionAreNull() {
        DishModel dishModel = createExampleDish();
        Long id =  1L;
        DishModel updateDish = new DishModel();
        when(dishPersistencePort.findById(id)).thenReturn(dishModel);

        assertThrows(DomainException.class, () -> dishUseCase.update(id, updateDish));
        verify(dishPersistencePort, never()).save(dishModel);
    }

    private DishModel createExampleDish(){
        DishModel dishModel = new DishModel();
        dishModel.setName("terraza");
        dishModel.setCategory(createExampleCategory());
        dishModel.setRestaurant(createExampleRestaurant());
        return dishModel;
    }
    private CategoryModel createExampleCategory(){
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(1L);
        return categoryModel;
    }
    private RestaurantModel createExampleRestaurant(){
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        return restaurantModel;
    }


}
package com.pragma.foodcourtservice.domain.usecase;

import com.pragma.foodcourtservice.domain.api.IDishServicePort;
import com.pragma.foodcourtservice.domain.exception.DataAlreadyExistsException;
import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.model.CategoryModel;
import com.pragma.foodcourtservice.domain.model.DishModel;
import com.pragma.foodcourtservice.domain.spi.ICategoryPersistencePort;
import com.pragma.foodcourtservice.domain.spi.IDishPersistencePort;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, ICategoryPersistencePort categoryPersistencePort){
        this.dishPersistencePort = dishPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }
    @Override
    public void save(DishModel dishModel) {
        if(dishPersistencePort.existsByName(dishModel.getName())) {
            throw new DataAlreadyExistsException("Dish with name " + dishModel.getName() + " already exists");
        }
        CategoryModel categoryModel = categoryPersistencePort.findById(dishModel.getCategory().getId());
        if(categoryModel == null) throw new DataNotFoundException("Category not found");

        //validar restaurante
        //validar q sea propietario del restaurante

        dishModel.setCategory(categoryModel);
        //dishModel.setRestaurant(restaurantModel);
        dishModel.setActive(true);
        dishPersistencePort.save(dishModel);
    }
}

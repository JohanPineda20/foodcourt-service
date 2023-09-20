package com.pragma.foodcourtservice.domain.usecase;

import com.pragma.foodcourtservice.domain.api.IDishServicePort;
import com.pragma.foodcourtservice.domain.exception.DataAlreadyExistsException;
import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.exception.DomainException;
import com.pragma.foodcourtservice.domain.model.CategoryModel;
import com.pragma.foodcourtservice.domain.model.DishModel;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import com.pragma.foodcourtservice.domain.spi.ICategoryPersistencePort;
import com.pragma.foodcourtservice.domain.spi.IDishPersistencePort;
import com.pragma.foodcourtservice.domain.spi.IRestaurantPersistencePort;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, ICategoryPersistencePort categoryPersistencePort, IRestaurantPersistencePort restaurantPersistencePort){
        this.dishPersistencePort = dishPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }
    @Override
    public void save(DishModel dishModel) {
        CategoryModel categoryModel = categoryPersistencePort.findById(dishModel.getCategory().getId());
        if(categoryModel == null) throw new DataNotFoundException("Category not found");
        RestaurantModel restaurantModel = restaurantPersistencePort.findById(dishModel.getRestaurant().getId());
        if(restaurantModel == null) throw new DataNotFoundException("Restaurant not found");


        if(dishPersistencePort.existsByNameAndRestaurantId(dishModel.getName(), restaurantModel.getId())) {
            throw new DataAlreadyExistsException("Dish with name " + dishModel.getName() + " already exists");
        }

        dishModel.setCategory(categoryModel);
        dishModel.setRestaurant(restaurantModel);
        dishModel.setActive(true);
        dishPersistencePort.save(dishModel);
    }

    @Override
    public void update(Long id, DishModel dishModel) {
        DishModel dishModel1 = dishPersistencePort.findById(id);
        if(dishModel1 == null) throw new DataNotFoundException("Dish not found");



        if(dishModel.getPrice() == null && dishModel.getDescription() == null){
            throw new DomainException("Price or Description is required");
        }
        if(dishModel.getPrice() != null) dishModel1.setPrice(dishModel.getPrice());
        if(dishModel.getDescription() != null) dishModel1.setDescription(dishModel.getDescription());
        dishPersistencePort.save(dishModel1);
    }
}

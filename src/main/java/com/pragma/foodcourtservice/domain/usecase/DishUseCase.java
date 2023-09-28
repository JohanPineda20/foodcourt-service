package com.pragma.foodcourtservice.domain.usecase;

import com.pragma.foodcourtservice.domain.api.IDishServicePort;
import com.pragma.foodcourtservice.domain.exception.DataAlreadyExistsException;
import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.exception.DomainException;
import com.pragma.foodcourtservice.domain.model.CategoryModel;
import com.pragma.foodcourtservice.domain.model.DishModel;
import com.pragma.foodcourtservice.domain.model.RestaurantModel;
import com.pragma.foodcourtservice.domain.spi.persistence.ICategoryPersistencePort;
import com.pragma.foodcourtservice.domain.spi.persistence.IDishPersistencePort;
import com.pragma.foodcourtservice.domain.spi.persistence.IRestaurantPersistencePort;
import com.pragma.foodcourtservice.domain.spi.securitycontext.ISecurityContextPort;

import java.util.List;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ISecurityContextPort securityContextPort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, ICategoryPersistencePort categoryPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, ISecurityContextPort securityContextPort){
        this.dishPersistencePort = dishPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.securityContextPort = securityContextPort;
    }
    @Override
    public void save(DishModel dishModel) {
        CategoryModel categoryModel = categoryPersistencePort.findById(dishModel.getCategory().getId());
        if(categoryModel == null) throw new DataNotFoundException("Category not found");
        RestaurantModel restaurantModel = restaurantPersistencePort.findById(dishModel.getRestaurant().getId());
        if(restaurantModel == null) throw new DataNotFoundException("Restaurant not found");

        validateOwnerRestaurant(restaurantModel.getOwnerId());

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

        validateOwnerRestaurant(dishModel1.getRestaurant().getOwnerId());

        if(dishModel.getPrice() == null && dishModel.getDescription() == null) throw new DomainException("Price or Description is required");
        if(dishModel.getPrice() != null) dishModel1.setPrice(dishModel.getPrice());
        if(dishModel.getDescription() != null) dishModel1.setDescription(dishModel.getDescription());
        dishPersistencePort.save(dishModel1);
    }

    @Override
    public void enableDisable(Long id) {
        DishModel dishModel = dishPersistencePort.findById(id);
        if(dishModel == null) throw new DataNotFoundException("Dish not found");

        validateOwnerRestaurant(dishModel.getRestaurant().getOwnerId());

        dishModel.setActive(!dishModel.getActive());
        dishPersistencePort.save(dishModel);
    }

    @Override
    public List<DishModel> getAllDishesByRestaurantAndCategory(Integer page, Integer size, Long restaurantId, Long categoryId) {
        RestaurantModel restaurantModel = restaurantPersistencePort.findById(restaurantId);
        if(restaurantModel == null) throw new DataNotFoundException("Restaurant not found");

        //get all dishes of a restaurant
        List<DishModel> dishModelList = dishPersistencePort.getAllDishesByRestaurant(page, size, restaurantId, true);
        if(dishModelList.isEmpty()) throw new DataNotFoundException("There are no dishes available in the restaurant");

        //if categoryId is null, then return the dishModelList with all dishes of a restaurant. if categoryId is not null, then filter the dishModelList by the categoryId.
        if (categoryId != null) {
            dishModelList = dishPersistencePort.getAllDishesByRestaurantAndCategory(page, size, restaurantId,true, categoryId);
            if(dishModelList.isEmpty()) throw new DataNotFoundException("There are no dishes available in the restaurant with that category");
        }

        return dishModelList;
    }

    private void validateOwnerRestaurant(Long ownerIdRestaurant){
       Long ownerIdAuthenticated = securityContextPort.getIdFromSecurityContext();
       if(ownerIdAuthenticated != ownerIdRestaurant) throw new DomainException("Owner authenticated must be owner of the restaurant");
    }

}

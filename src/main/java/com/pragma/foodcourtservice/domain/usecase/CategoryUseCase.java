package com.pragma.foodcourtservice.domain.usecase;

import com.pragma.foodcourtservice.domain.api.ICategoryServicePort;
import com.pragma.foodcourtservice.domain.exception.DataAlreadyExistsException;
import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.model.CategoryModel;
import com.pragma.foodcourtservice.domain.spi.ICategoryPersistencePort;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;
    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort){
        this.categoryPersistencePort = categoryPersistencePort;
    }
    @Override
    public void save(CategoryModel categoryModel) {
        if(categoryPersistencePort.existsByName(categoryModel.getName())){
            throw new DataAlreadyExistsException("Category with name " + categoryModel.getName() + " already exists");
        }
        categoryPersistencePort.save(categoryModel);
    }

    @Override
    public List<CategoryModel> getAllCategories() {
        List<CategoryModel> categoryModelList = categoryPersistencePort.getAllCategories();
        if(categoryModelList.isEmpty()) throw new DataNotFoundException("There aren't categories");
        return categoryModelList;
    }
}

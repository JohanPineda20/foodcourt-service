package com.pragma.foodcourtservice.domain.spi;

import com.pragma.foodcourtservice.domain.model.CategoryModel;

import java.util.List;

public interface ICategoryPersistencePort {
    void save(CategoryModel categoryModel);

    boolean existsByName(String name);

    List<CategoryModel> getAllCategories();
}

package com.pragma.foodcourtservice.domain.api;

import com.pragma.foodcourtservice.domain.model.CategoryModel;

import java.util.List;

public interface ICategoryServicePort {
    void save(CategoryModel categoryModel);

    List<CategoryModel> getAllCategories();
}

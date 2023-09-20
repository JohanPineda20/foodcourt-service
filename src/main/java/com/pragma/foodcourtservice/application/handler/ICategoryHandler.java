package com.pragma.foodcourtservice.application.handler;

import com.pragma.foodcourtservice.application.dto.request.CategoryRequest;
import com.pragma.foodcourtservice.application.dto.response.CategoryResponse;

import java.util.List;

public interface ICategoryHandler {
    void save(CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategories();
}

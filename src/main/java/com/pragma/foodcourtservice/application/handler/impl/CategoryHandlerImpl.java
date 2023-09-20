package com.pragma.foodcourtservice.application.handler.impl;

import com.pragma.foodcourtservice.application.dto.request.CategoryRequest;
import com.pragma.foodcourtservice.application.dto.response.CategoryResponse;
import com.pragma.foodcourtservice.application.handler.ICategoryHandler;
import com.pragma.foodcourtservice.application.mapper.ICategoryDtoMapper;
import com.pragma.foodcourtservice.domain.api.ICategoryServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryHandlerImpl implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryDtoMapper categoryDtoMapper;
    @Override
    public void save(CategoryRequest categoryRequest) {
        categoryServicePort.save(categoryDtoMapper.mapToCategoryModel(categoryRequest));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryDtoMapper.mapToCategoryResponseList(categoryServicePort.getAllCategories());
    }
}

package com.pragma.foodcourtservice.infraestructure.out.jpa.adapter;

import com.pragma.foodcourtservice.domain.model.CategoryModel;
import com.pragma.foodcourtservice.domain.spi.ICategoryPersistencePort;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryMysqlAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    @Override
    public void save(CategoryModel categoryModel) {
        categoryRepository.save(categoryEntityMapper.mapToCategoryEntity(categoryModel));
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public List<CategoryModel> getAllCategories() {
        return categoryEntityMapper.mapToCategoryModelList(categoryRepository.findAll());
    }
    @Override
    public CategoryModel findById(Long id) {
        return categoryEntityMapper.mapToCategoryModel(categoryRepository.findById(id).orElse(null));
    }
}

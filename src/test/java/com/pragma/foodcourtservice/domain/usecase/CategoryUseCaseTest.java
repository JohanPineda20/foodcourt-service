package com.pragma.foodcourtservice.domain.usecase;

import com.pragma.foodcourtservice.domain.exception.DataAlreadyExistsException;
import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.model.CategoryModel;
import com.pragma.foodcourtservice.domain.spi.persistence.ICategoryPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {
    @InjectMocks
    CategoryUseCase categoryUseCase;
    @Mock
    ICategoryPersistencePort categoryPersistencePort;

    @Test
    void save() {
        CategoryModel categoryModel = createExampleCategory();

        categoryUseCase.save(categoryModel);

        verify(categoryPersistencePort, times(1)).save(categoryModel);
    }

    @Test
    void saveCategoryAlreadyExists() {
        CategoryModel categoryModel = createExampleCategory();
        when(categoryPersistencePort.existsByName(categoryModel.getName())).thenReturn(true);

        assertThrows(DataAlreadyExistsException.class, () -> categoryUseCase.save(categoryModel));
        verify(categoryPersistencePort, never()).save(categoryModel);
    }

    @Test
    void getAllCategories() {
        List<CategoryModel> categoryModelList = List.of(createExampleCategory());
        when(categoryPersistencePort.getAllCategories()).thenReturn(categoryModelList);

        List<CategoryModel> categoryModelList1 = categoryUseCase.getAllCategories();

        assertEquals(categoryModelList, categoryModelList1);
        assertFalse(categoryModelList1.isEmpty());
    }

    @Test
    void getAllCategoriesNotFound() {
        when(categoryPersistencePort.getAllCategories()).thenReturn(new ArrayList<>());

        assertThrows(DataNotFoundException.class, () -> categoryUseCase.getAllCategories());
    }

    private CategoryModel createExampleCategory(){
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName("dish");
        return categoryModel;
    }

}
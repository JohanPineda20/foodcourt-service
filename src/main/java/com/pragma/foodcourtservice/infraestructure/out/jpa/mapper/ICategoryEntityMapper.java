package com.pragma.foodcourtservice.infraestructure.out.jpa.mapper;

import com.pragma.foodcourtservice.domain.model.CategoryModel;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICategoryEntityMapper {

    CategoryEntity mapToCategoryEntity(CategoryModel categoryModel);

    List<CategoryModel> mapToCategoryModelList(List<CategoryEntity> categoryEntityList);

}

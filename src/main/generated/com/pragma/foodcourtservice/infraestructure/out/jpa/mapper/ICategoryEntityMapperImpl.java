package com.pragma.foodcourtservice.infraestructure.out.jpa.mapper;

import com.pragma.foodcourtservice.domain.model.CategoryModel;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.CategoryEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-29T00:39:48-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class ICategoryEntityMapperImpl implements ICategoryEntityMapper {

    @Override
    public CategoryEntity mapToCategoryEntity(CategoryModel categoryModel) {
        if ( categoryModel == null ) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setId( categoryModel.getId() );
        categoryEntity.setName( categoryModel.getName() );
        categoryEntity.setDescription( categoryModel.getDescription() );

        return categoryEntity;
    }

    @Override
    public CategoryModel mapToCategoryModel(CategoryEntity categoryEntity) {
        if ( categoryEntity == null ) {
            return null;
        }

        CategoryModel categoryModel = new CategoryModel();

        categoryModel.setId( categoryEntity.getId() );
        categoryModel.setName( categoryEntity.getName() );
        categoryModel.setDescription( categoryEntity.getDescription() );

        return categoryModel;
    }

    @Override
    public List<CategoryModel> mapToCategoryModelList(List<CategoryEntity> categoryEntityList) {
        if ( categoryEntityList == null ) {
            return null;
        }

        List<CategoryModel> list = new ArrayList<CategoryModel>( categoryEntityList.size() );
        for ( CategoryEntity categoryEntity : categoryEntityList ) {
            list.add( mapToCategoryModel( categoryEntity ) );
        }

        return list;
    }
}

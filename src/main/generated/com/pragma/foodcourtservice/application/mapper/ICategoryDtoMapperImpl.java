package com.pragma.foodcourtservice.application.mapper;

import com.pragma.foodcourtservice.application.dto.request.CategoryRequest;
import com.pragma.foodcourtservice.application.dto.response.CategoryResponse;
import com.pragma.foodcourtservice.domain.model.CategoryModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-19T21:26:13-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class ICategoryDtoMapperImpl implements ICategoryDtoMapper {

    @Override
    public CategoryModel mapToCategoryModel(CategoryRequest categoryRequest) {
        if ( categoryRequest == null ) {
            return null;
        }

        CategoryModel categoryModel = new CategoryModel();

        categoryModel.setName( categoryRequest.getName() );
        categoryModel.setDescription( categoryRequest.getDescription() );

        return categoryModel;
    }

    @Override
    public List<CategoryResponse> mapToCategoryResponseList(List<CategoryModel> categoryModelList) {
        if ( categoryModelList == null ) {
            return null;
        }

        List<CategoryResponse> list = new ArrayList<CategoryResponse>( categoryModelList.size() );
        for ( CategoryModel categoryModel : categoryModelList ) {
            list.add( categoryModelToCategoryResponse( categoryModel ) );
        }

        return list;
    }

    protected CategoryResponse categoryModelToCategoryResponse(CategoryModel categoryModel) {
        if ( categoryModel == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;

        id = categoryModel.getId();
        name = categoryModel.getName();
        description = categoryModel.getDescription();

        CategoryResponse categoryResponse = new CategoryResponse( id, name, description );

        return categoryResponse;
    }
}

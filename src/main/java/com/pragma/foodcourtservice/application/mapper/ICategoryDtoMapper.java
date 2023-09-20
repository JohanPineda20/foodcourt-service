package com.pragma.foodcourtservice.application.mapper;

import com.pragma.foodcourtservice.application.dto.request.CategoryRequest;
import com.pragma.foodcourtservice.application.dto.response.CategoryResponse;
import com.pragma.foodcourtservice.domain.model.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICategoryDtoMapper {

    CategoryModel mapToCategoryModel(CategoryRequest categoryRequest);

    List<CategoryResponse> mapToCategoryResponseList(List<CategoryModel> categoryModelList);

}

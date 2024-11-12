package org.example.apiflutter.mapper;

import org.example.apiflutter.dto.request.CategoryRequest;
import org.example.apiflutter.dto.response.CategoryResponse;
import org.example.apiflutter.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryRequest categoryRequest);

    CategoryRequest toCategoryRequest(Category category);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(@MappingTarget Category category, CategoryRequest categoryRequest);
}

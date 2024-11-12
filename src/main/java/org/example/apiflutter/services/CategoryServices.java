package org.example.apiflutter.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.request.CategoryRequest;
import org.example.apiflutter.dto.response.CategoryResponse;
import org.example.apiflutter.entity.Category;
import org.example.apiflutter.exception.AppException;
import org.example.apiflutter.exception.CategoryException;
import org.example.apiflutter.exception.ErrorCode;
import org.example.apiflutter.mapper.CategoryMapper;
import org.example.apiflutter.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryServices {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;


    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw  new CategoryException("Category Exist");
        }
        Category category = categoryMapper.toCategory(categoryRequest);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public List<CategoryResponse> getCategory() {

        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();

    }

    //
    public CategoryResponse getById(Long id) {


        return categoryMapper.toCategoryResponse(categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException("Category Not Found")));

    }

    //
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryException("Category Not Found"));

        categoryMapper.updateCategory(category, categoryRequest);

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));

    }

    //
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryException("Category Not Found");
        }

//        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        categoryRepository.deleteById(id);
    }


    public CategoryResponse getByName(String name) {
        return categoryMapper.toCategoryResponse(categoryRepository.findByName(name));
//

    }
}




package org.example.apiflutter.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.request.CategoryRequest;
import org.example.apiflutter.dto.response.ApiResponse;
import org.example.apiflutter.dto.response.CategoryResponse;
import org.example.apiflutter.entity.Category;
import org.example.apiflutter.services.CategoryServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryController {
    CategoryServices categoryServices;

    @PostMapping
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryServices.createCategory(categoryRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryServices.getCategory())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<CategoryResponse> getCategoryById(@PathVariable() Long id) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryServices.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<CategoryResponse> updateCategory(@PathVariable() Long id, @RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryServices.updateCategory(id, categoryRequest))
                .build();

    }

    @GetMapping("/{name}")
    ApiResponse<CategoryResponse> getByName(@PathVariable String name) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryServices.getByName(name))
                .build();
    }

    @DeleteMapping("/{id}")
    String deleteCategory(@PathVariable() Long id) {
        categoryServices.deleteCategory(id);
        return "Deleted";
    }
}

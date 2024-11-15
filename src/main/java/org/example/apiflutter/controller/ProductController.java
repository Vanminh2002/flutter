package org.example.apiflutter.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.request.ProductRequest;
import org.example.apiflutter.dto.response.ApiResponse;
import org.example.apiflutter.dto.response.ProductResponse;
import org.example.apiflutter.entity.Category;
import org.example.apiflutter.entity.Product;
import org.example.apiflutter.services.ProductServices;
import org.example.apiflutter.services.UploadFileServices;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductServices productServices;
    UploadFileServices uploadFileServices;



    @PostMapping

    public ApiResponse<Product> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return ApiResponse.<Product>builder()
                .message("Success")
                .result(productServices.addProduct(productRequest))
                .build();
    }

    //
    @GetMapping
    ApiResponse<List<ProductResponse>> getProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .message("Success")
                .result(productServices.getAll())
                .build();
    }

    //
    @GetMapping("/{id}")
    ApiResponse<ProductResponse> getById(@PathVariable Long id) {

        return ApiResponse.<ProductResponse>builder()
                .message("Success")
                .result(productServices.getProductResponseById(id))
                .build();
    }

    //
    @PutMapping("/{id}")
    ApiResponse<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) throws IOException {

        return ApiResponse.<Product>builder()
                .result(productServices.updateProduct(request, id))
                .build();
    }


    @DeleteMapping("/{id}")
    String deleteProduct(@PathVariable Long id) {

        productServices.delete(id);

        return "Product has been Deleted";
    }

    @GetMapping("/product-by-brand-and-name")
    ApiResponse<List<Product>> getByBrandNameAndName(@RequestParam String brandName, @RequestParam String name) {
        return ApiResponse.<List<Product>>builder()
                .result(productServices.getProductByBrandAndName(brandName, name))
                .build();
    }

    @GetMapping("/by-name")
    ApiResponse<List<Product>> getProductByName(@RequestParam String name) {
        return ApiResponse.<List<Product>>builder()
                .result(productServices.getProductByName(name))
                .build();
    }

    @GetMapping("/product-by-category-and-brand")
    ApiResponse<List<Product>> getProductByCategoryAndBrand(@RequestParam String brandName, @RequestParam String category) {
        return ApiResponse.<List<Product>>builder()
                .result(productServices.getProductByCategoryAndBrand(brandName, category))
                .build();
    }


@GetMapping("/by-brand")
public ApiResponse<List<Product>> getProductByBrand(@RequestParam String brand) {
    List<Product> products = productServices.getProductsByBrand(brand);
    System.out.println("Brand received: " + brand);
    return ApiResponse.<List<Product>>builder()
            .result(products)
            .build();
}

    @GetMapping("/by-category")
    ApiResponse<List<Product>> getProductByCategory(@RequestParam String category) {
        return ApiResponse.<List<Product>>builder()
                .result(productServices.getProductByCategory(category))
                .build();
    }

}

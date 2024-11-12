package org.example.apiflutter.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.example.apiflutter.dto.request.ProductRequest;
import org.example.apiflutter.dto.response.ImageResponse;
import org.example.apiflutter.dto.response.ProductResponse;
import org.example.apiflutter.entity.Category;
import org.example.apiflutter.entity.Product;
import org.example.apiflutter.exception.AppException;
import org.example.apiflutter.exception.ErrorCode;
import org.example.apiflutter.exception.ProductException;
import org.example.apiflutter.mapper.ImageMapper;
import org.example.apiflutter.mapper.ProductMapper;
import org.example.apiflutter.repository.CategoryRepository;
import org.example.apiflutter.repository.ImageRepository;
import org.example.apiflutter.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServices {
    ProductRepository productRepository;
    ProductMapper productMapper;
    UploadFileServices uploadFileServices;
    ImageRepository imageRepository;
    CategoryRepository categoryRepository;
    private final ImageMapper imageMapper;

    public Product addProduct(ProductRequest request) {
        // check if the category is found in the DB
        // If Yes, set it as the new product category
        // If No, the save it as a new category
        // The set as the new product category.
        if (productRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Product product = productMapper.toProduct(request);
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        product.setCategory(category);
        return productRepository.save(product);

//        return productRepository.save(createProduct(request));
    }


    public Product updateProduct(ProductRequest request, Long id) {
        return productRepository.findById(id)
                .map(existProduct -> updateExistingProduct(existProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductException("Product Not Found"));
    }


    private Product updateExistingProduct(Product existProduct, ProductRequest request) {
        existProduct.setName(request.getName());
        existProduct.setBrand(request.getBrand());
        existProduct.setPrice(request.getPrice());
        existProduct.setInventory(request.getInventory());
        existProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existProduct.setCategory(category);
        return existProduct;


    }

    //    public List<ProductResponse> getAll() {
//        return productRepository.findAll().stream().map(productMapper::toProductResponse).toList();
//    }
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(product -> {
                    ProductResponse productResponse = productMapper.toProductResponse(product);
                    // Chuyển đổi danh sách images thành danh sách ImageResponse
                    List<ImageResponse> imageResponses = product.getImages().stream()
                            .map(imageMapper::toImageResponse).toList();

                    productResponse.setImages(imageResponses); // Gán lại danh sách hình ảnh
                    return productResponse;
                })
                .toList();
    }

    //    public ProductResponse getById(Long id) {
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductException("Product Not Found"));
//        return productMapper.toProductResponse(productRepository.findById(id).orElseThrow(() -> new ProductException("Product Not Found")));
    }

    public List<Product> getProductByCategory(String category) {


        return productRepository.findByCategoryName(category);
    }


    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);

    }


    public List<Product> getProductByCategoryAndBrand(String category, String brand) {


        return productRepository.findByCategoryNameAndBrand(category, brand);
    }


    public List<Product> getProductByName(String name) {


        return productRepository.findByName(name);
    }


    public List<Product> getProductByBrandAndName(String brand, String name) {


        return productRepository.findByBrandAndName(brand, name);
    }


    public Long countProductByBrandAndName(String brand, String name) {

        return productRepository.countByBrandAndName(brand, name);
    }

    public void delete(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, () -> {
                    throw new ProductException("Product Not Found");
                });
    }


}

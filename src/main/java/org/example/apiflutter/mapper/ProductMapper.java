package org.example.apiflutter.mapper;

import org.example.apiflutter.dto.request.ProductRequest;
import org.example.apiflutter.dto.response.ProductResponse;
import org.example.apiflutter.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductRequest productRequest);

    ProductRequest toProductRequest(Product product);

//    @Mapping(target = "image",source = "file")
    ProductResponse toProductResponse(Product product);

    void updateProduct(@MappingTarget Product product, ProductRequest productRequest);
}

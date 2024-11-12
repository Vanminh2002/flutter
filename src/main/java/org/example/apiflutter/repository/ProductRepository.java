package org.example.apiflutter.repository;

import org.example.apiflutter.dto.response.ProductResponse;
import org.example.apiflutter.entity.Category;
import org.example.apiflutter.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    List<Product> findByCategoryName(String category);

    @Query("SELECT p FROM Product p WHERE p.brand = :brand")
    List<Product> findByBrand(@Param("brand") String brand);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);


}

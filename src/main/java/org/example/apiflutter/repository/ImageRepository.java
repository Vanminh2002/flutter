package org.example.apiflutter.repository;

import org.example.apiflutter.dto.response.ImageResponse;
import org.example.apiflutter.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Images,Long> {
    List<Images> findByProductsId(Long id);
}

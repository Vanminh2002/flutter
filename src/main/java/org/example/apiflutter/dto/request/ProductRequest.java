package org.example.apiflutter.dto.request;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.entity.Category;
import org.example.apiflutter.entity.Images;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String name;
    String brand;
    BigDecimal price;
    int inventory;
    String description;
    Category category;
    List<ImageRequest> images;

}

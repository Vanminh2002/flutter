package org.example.apiflutter.dto.response;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.entity.Category;
import org.example.apiflutter.entity.Images;

import java.math.BigDecimal;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    Long id;
    String name;
    String brand;
    BigDecimal price;
    int inventory;
    String description;

    Category category;


    List<ImageResponse> images;
}

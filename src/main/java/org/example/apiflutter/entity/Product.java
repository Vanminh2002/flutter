package org.example.apiflutter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String brand;
    BigDecimal price;
    int inventory;
    String description;



//        @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")

    Category category;

    @OneToMany(mappedBy = "products",fetch = FetchType.EAGER, orphanRemoval = true)
    List<Images> images;


}

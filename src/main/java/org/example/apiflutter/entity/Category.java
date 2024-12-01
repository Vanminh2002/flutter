package org.example.apiflutter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@FieldDefaults(level = AccessLevel.PRIVATE)
//@Builder
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;


    @JsonIgnore
    @OneToMany(mappedBy = "category")
    Set<Product> products;


    public Category(String name) {
        this.name = name;
    }
}

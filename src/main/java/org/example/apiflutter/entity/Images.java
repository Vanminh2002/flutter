package org.example.apiflutter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Blob;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String fileName;
    String filePath;
    String fileType;


    @Lob
    Blob image;

    String downloadUrl;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product products;

}

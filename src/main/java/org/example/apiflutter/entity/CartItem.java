package org.example.apiflutter.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    int quantity;
    BigDecimal unitPrice;
    BigDecimal totalPrice;


    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    Cart cart;


    public void setTotalPrice() {
        this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));
    }
}

package org.example.apiflutter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    BigDecimal totalAmount = BigDecimal.ZERO;
    @OneToMany(mappedBy = "cart")
    Set<CartItem> cartItems = new HashSet<>();

    public void addItem(CartItem item) {
        this.cartItems.add(item);
        item.setCart(this);
        updateTotalAmount();
    }

    public void removeItem(CartItem item) {
        this.cartItems.remove(item);
        item.setCart(null);
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        this.totalAmount = cartItems.stream().map(item -> {
            BigDecimal unitPrice = item.getUnitPrice();
            if (unitPrice == null) {
                return BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
////    Set<CartItem> cartItems;
//    List<CartItem> cartItems = new ArrayList<>();
     /* 16-11-2024
    @JsonIgnore
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    Set<CartItem> cartItems;
    public Cart() {
        this.cartItems = new HashSet<>();  // Khởi tạo cartItems như một tập rỗng
    }

    // Getter for cartItems
    public Set<CartItem> getCartItems() {
        if (this.cartItems == null) {
            this.cartItems = new HashSet<>();  // Nếu chưa khởi tạo, khởi tạo mới
        }
        return cartItems;
    }

    public void addItem(CartItem item) {
        this.cartItems.add(item);
        item.setCart(this);
        updateTotalAmount();
    }

    public void removeCart(CartItem item) {
        this.cartItems.remove(item);
        item.setCart(null);
        updateTotalAmount();
    }

    public void updateTotalAmount() {
        this.totalAmount = cartItems.stream().map(item -> {
            BigDecimal unitPrice = item.getUnitPrice();
            if (unitPrice == null) {
                return BigDecimal.ZERO;

            }
            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

      */

}

package org.example.apiflutter.repository;

import org.example.apiflutter.entity.Cart;
import org.example.apiflutter.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteAllByCartId(Long id);
}
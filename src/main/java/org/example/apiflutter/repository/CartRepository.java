package org.example.apiflutter.repository;

import org.example.apiflutter.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    void deleteAllById(Long id);
}

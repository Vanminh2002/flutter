package org.example.apiflutter.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.entity.Cart;
import org.example.apiflutter.exception.AppException;
import org.example.apiflutter.exception.ErrorCode;
import org.example.apiflutter.repository.CartItemRepository;
import org.example.apiflutter.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {

    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    AtomicLong cartIdGeneral = new AtomicLong(0);

    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    public Cart clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
        return cart;
    }

    public BigDecimal getTotalPrice(Long id) {

        Cart cart = getCart(id);
        return cart.getTotalAmount();

    }

    public Long initializeNewCart() {
        Cart cart = new Cart();
        Long newCartId = cartIdGeneral.incrementAndGet();
        cart.setId(newCartId);
        return  cartRepository.save(cart).getId();
    }
}

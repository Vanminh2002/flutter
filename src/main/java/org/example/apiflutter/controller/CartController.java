package org.example.apiflutter.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.response.ApiResponse;
import org.example.apiflutter.dto.response.ResApi;
import org.example.apiflutter.entity.Cart;
import org.example.apiflutter.exception.AppException;
import org.example.apiflutter.exception.ErrorCode;
import org.example.apiflutter.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {

    CartService cartService;

    @GetMapping("/{id}")
    ApiResponse<Cart> getCart(@PathVariable Long id) {

        try {
            return ApiResponse.<Cart>builder()
                    .message("Success")
                    .result(cartService.getCart(id))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Cart>builder()
                    .message(e.getMessage())
                    .build();
        }
    }

    @PutMapping("/clear-cart/{id}")
    ResponseEntity<ResApi> clearCart(@PathVariable Long id) {
        cartService.clearCart(id);
        return ResponseEntity.ok(new ResApi("clear success", null));
    }

    @GetMapping("/{cartId}/cart-total")
    ResponseEntity<ResApi> getTotalAmount(@PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getCartTotal(cartId);
            return ResponseEntity.ok(new ResApi("total price", totalPrice));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
 /* 16-11-2024
    @GetMapping("/{id}")
    ApiResponse<Cart> getCart(@PathVariable Long id) {
        return ApiResponse.<Cart>builder()
                .result(cartService.getCart(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Cart> clearCart(@PathVariable Long id) {
        return ApiResponse.<Cart>builder()
                .message("Success")
                .result(cartService.clearCart(id))
                .build();
    }

    @GetMapping("/{id}/total-price")
    ApiResponse<BigDecimal> getTotalAmount(@PathVariable Long id) {
        try {
            return ApiResponse.<BigDecimal>builder()
                    .result(cartService.getTotalPrice(id))
                    .build();
        } catch (Exception e) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
    }

  */
}

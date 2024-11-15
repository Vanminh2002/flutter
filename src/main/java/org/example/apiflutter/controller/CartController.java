package org.example.apiflutter.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.response.ApiResponse;
import org.example.apiflutter.entity.Cart;
import org.example.apiflutter.exception.AppException;
import org.example.apiflutter.exception.ErrorCode;
import org.example.apiflutter.services.CartService;
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
}

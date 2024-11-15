package org.example.apiflutter.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.response.ApiResponse;
import org.example.apiflutter.entity.Cart;
import org.example.apiflutter.entity.CartItem;
import org.example.apiflutter.services.CartItemService;
import org.example.apiflutter.services.CartService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart-items")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemController {

    CartItemService cartItemService;
    private final CartService cartService;


    @PostMapping("/add")
    public ApiResponse<CartItem> addItemToCart(
            @RequestParam(required = false) Long cartId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        try {
            if (cartId == null) {
                cartId = cartService.initializeNewCart();
            }
            // Gọi phương thức service để thêm sản phẩm vào giỏ hàng
            ApiResponse<CartItem> response = cartItemService.addItemToCart(cartId, productId, quantity);
            return response;
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return ApiResponse.error("Unable to add item to cart: " + e.getMessage());
        }
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ApiResponse<Void> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        return cartItemService.removeItemFromCart(cartId, productId);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ApiResponse<CartItem> updateQuantity(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam Integer quantity) {
        return cartItemService.updateQuantity(cartId, productId, quantity);
    }

}

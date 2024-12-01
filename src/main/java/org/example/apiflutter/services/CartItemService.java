package org.example.apiflutter.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.weaver.ast.Not;
import org.example.apiflutter.dto.response.ApiResponse;
import org.example.apiflutter.entity.Cart;
import org.example.apiflutter.entity.CartItem;
import org.example.apiflutter.entity.Product;
import org.example.apiflutter.exception.AppException;
import org.example.apiflutter.exception.ErrorCode;
import org.example.apiflutter.repository.CartItemRepository;
import org.example.apiflutter.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemService {

    CartItemRepository cartItemRepository;
    ProductServices productServices;
    CartService cartService;
    CartRepository cartRepository;

    public void addToCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartService.getCart(cartId);

        Product product = productServices.getById(productId);
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);


    }

    public void removeFromCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartService.getCart(cartId);
        CartItem removeItem = getCartItem(cartId,productId);
        cart.removeItem(removeItem);

        cartRepository.save(cart);
    }

    public void updateCartItem(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getCartItems()
                .stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));
    }
//    public void addItemToCart(Long cartId, Long productId, Integer quantity) {
//        Cart cart = cartService.getCart(cartId);
//        Product product = productServices.getById(productId);
//        CartItem cartItem = cart.getCartItems()
//                .stream()
//                .filter(item -> item.getProduct().getId().equals(productId))
//                .findFirst().orElse(new CartItem());
//        if (cartItem.getId() == null) {
//            cartItem.setCart(cart);
//            cartItem.setProduct(product);
//            cartItem.setQuantity(quantity);
//            cartItem.setUnitPrice(product.getPrice());
//        } else {
//            cartItem.setQuantity(cartItem.getQuantity() + quantity);
//        }
//        cartItem.setTotalPrice();
//        cart.addItem(cartItem);
//        cartItemRepository.save(cartItem);
//        cartRepository.save(cart);
//    }
//16-11
    /* 16-11-2024
    public ApiResponse<CartItem> addItemToCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartService.getCart(cartId);
        if (cart == null) {
            cart = createCart();  // Tạo giỏ hàng mới nếu cartId không tồn tại
        }
        Product product = productServices.getById(productId);
        if (product == null) {
            return ApiResponse.error("Product not found with id: " + productId);
        }
        // Kiểm tra nếu sản phẩm đã có trong giỏ
//        CartItem cartItem = cart.getCartItems()
//                .stream()
//                .filter(item -> item.getProduct().getId().equals(productId))
//                .findFirst()
//                .orElse(new CartItem());
//
//        if (cartItem.getId() == null) {
//            // Nếu chưa có CartItem, tạo mới
//
//            cartItem.setCart(cart);
//            cartItem.setProduct(product);
//            cartItem.setQuantity(quantity);
//            cartItem.setUnitPrice(product.getPrice());
//        } else {
//            // Nếu CartItem đã có, cập nhật số lượng
//            cartItem.setQuantity(cartItem.getQuantity() + quantity);
//        }
//
//        cartItem.setTotalPrice();  // Cập nhật giá trị tổng
//
//        cart.addItem(cartItem);    // Thêm vào giỏ hàng
//        cartItemRepository.save(cartItem);  // Lưu CartItem
//        cartRepository.save(cart);          // Lưu Cart

        // Trả về ApiResponse
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems == null) {
//            cartItems = new HashSet<>();  // Khởi tạo nếu chưa có
            cart.setCartItems(cartItems); // Lưu lại cartItems vào Cart nếu cần
        }

        // Kiểm tra nếu sản phẩm đã có trong giỏ
        CartItem cartItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());

        if (cartItem.getId() == null) {
            // Nếu chưa có CartItem, tạo mới
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            // Nếu CartItem đã có, cập nhật số lượng
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();  // Cập nhật giá trị tổng

        cart.addItem(cartItem);    // Thêm vào giỏ hàng
        cartItemRepository.save(cartItem);  // Lưu CartItem
        cartRepository.save(cart);
        return ApiResponse.success(cartItem);  // Trả về ApiResponse với CartItem mới
    }

    public ApiResponse<CartItem> removeItemFromCart(Long cartId, Long productId) {
        try {
            // Lấy giỏ hàng và sản phẩm cần xóa
            Cart cart = cartService.getCart(cartId);
            CartItem cartItemToRemove = getCartItem(cartId, productId);

            if (cartItemToRemove == null) {
                return ApiResponse.error("Item not found in the cart");
            }

            // Xóa CartItem khỏi giỏ hàng
            cart.removeCart(cartItemToRemove);

            // Lưu giỏ hàng sau khi xóa CartItem
            cartRepository.save(cart);

            return ApiResponse.success(null); // Trả về ApiResponse với mã thành công
        } catch (Exception e) {
            return ApiResponse.error("Error removing item from cart: " + e.getMessage());
        }
    }


    public ApiResponse<CartItem> updateQuantity(Long cartId, Long productId, Integer quantity) {
        try {
            // Lấy giỏ hàng và sản phẩm cần cập nhật
            Cart cart = cartService.getCart(cartId);
            CartItem cartItem = cart.getCartItems()
                    .stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (cartItem == null) {
                return ApiResponse.error("Item not found in the cart");
            }

            // Cập nhật số lượng và tính lại giá trị
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(cartItem.getProduct().getPrice());
            cartItem.setTotalPrice(); // Tính lại tổng giá trị

            // Cập nhật tổng số tiền giỏ hàng
            BigDecimal totalAmount = cart.getTotalAmount();
            cart.setTotalAmount(totalAmount);

            // Lưu giỏ hàng sau khi cập nhật
            cartRepository.save(cart);

            return ApiResponse.success(cartItem); // Trả về ApiResponse với CartItem đã cập nhật
        } catch (Exception e) {
            return ApiResponse.error("Error updating quantity: " + e.getMessage());
        }
    }

     */
//    public void removeItemFromCart(Long cartId, Long productId) {
//        Cart cart = cartService.getCart(cartId);
//        CartItem cartItemToRemove = getCartItem(cartId, productId);
//        cart.removeCart(cartItemToRemove);
//        cartRepository.save(cart);
//
//    }
//
//    public void updateQuantity(Long cartId, Long productId, Integer quantity) {
//        Cart cart = cartService.getCart(cartId);
//        cart.getCartItems()
//                .stream().filter(item -> item.getProduct().getId().equals(productId))
//                .findFirst()
//                .ifPresent(item -> {
//                    item.setQuantity(quantity);
//                    item.setUnitPrice(item.getProduct().getPrice());
//                    item.setTotalPrice();
//                });
//        BigDecimal totalAmount = cart.getTotalAmount();
//        cart.setTotalAmount(totalAmount);
//        cartRepository.save(cart);
//    }

 /* 16-11-2024
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getCartItems()
                .stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
    }
    private Cart createCart() {
        Cart newCart = new Cart();
        // Có thể thêm các thuộc tính khác nếu cần, ví dụ: User nếu liên kết với người dùng
        cartRepository.save(newCart);  // Lưu giỏ hàng vào cơ sở dữ liệu
        return newCart;  // Trả về giỏ hàng mới
    }

  */
}

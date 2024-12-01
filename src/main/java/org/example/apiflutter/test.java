package org.example.apiflutter;

import org.example.apiflutter.entity.Cart;

import java.math.BigDecimal;

public class test {
    private BigDecimal totalAmount;

    // Getter và Setter
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.setTotalAmount(new BigDecimal("100.00"));  // Giả sử giỏ hàng có tổng là 100

        BigDecimal total = new BigDecimal("150.00");  // Tổng mới

        // Cập nhật lại tổng giỏ hàng
        cart.setTotalAmount(total.subtract(cart.getTotalAmount()));  // Chênh lệch là 150 - 100 = 50

        System.out.println("Total amount in cart: " + cart.getTotalAmount());  // In ra "50.00"
    }
}

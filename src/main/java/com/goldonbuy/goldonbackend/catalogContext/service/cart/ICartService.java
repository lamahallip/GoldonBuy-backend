package com.goldonbuy.goldonbackend.catalogContext.service.cart;

import com.goldonbuy.goldonbackend.catalogContext.entity.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();

    Cart getCartByUserId(Long userId);
}

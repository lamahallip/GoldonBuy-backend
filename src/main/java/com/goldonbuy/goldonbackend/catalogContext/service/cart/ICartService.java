package com.goldonbuy.goldonbackend.catalogContext.service.cart;

import com.goldonbuy.goldonbackend.catalogContext.entity.Cart;
import com.goldonbuy.goldonbackend.userContext.entity.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}

package com.goldonbuy.goldonbackend.catalogContext.service.cart;

import com.goldonbuy.goldonbackend.catalogContext.entity.Cart;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.ResourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.repository.CartItemRepository;
import com.goldonbuy.goldonbackend.catalogContext.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        Cart cart = this.cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found !"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Long newCartId = this.cartIdGenerator.get();
        if(getCartId(newCartId)){
            return newCartId;
        }
        Cart newCart = new Cart();
        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();
    }
    Boolean getCartId(Long id) {
        return this.cartRepository.existsById(id);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}

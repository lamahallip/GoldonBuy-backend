package com.goldonbuy.goldonbackend.catalogContext.repository;

import com.goldonbuy.goldonbackend.catalogContext.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}

package com.goldonbuy.goldonbackend.catalogContext.repository;

import com.goldonbuy.goldonbackend.catalogContext.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}

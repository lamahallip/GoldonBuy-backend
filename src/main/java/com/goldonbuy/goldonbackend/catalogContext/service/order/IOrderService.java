package com.goldonbuy.goldonbackend.catalogContext.service.order;

import com.goldonbuy.goldonbackend.catalogContext.entity.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}

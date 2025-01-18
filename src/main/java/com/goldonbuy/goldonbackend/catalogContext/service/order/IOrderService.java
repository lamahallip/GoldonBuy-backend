package com.goldonbuy.goldonbackend.catalogContext.service.order;

import com.goldonbuy.goldonbackend.catalogContext.dto.OrderDTO;
import com.goldonbuy.goldonbackend.catalogContext.entity.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);
    OrderDTO getOrder(Long orderId);
    List<OrderDTO> getUserOrders(Long userId);

    OrderDTO convertToDTO(Order order);
}

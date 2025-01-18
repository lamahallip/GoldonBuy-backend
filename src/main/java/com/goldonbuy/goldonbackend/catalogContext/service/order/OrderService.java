package com.goldonbuy.goldonbackend.catalogContext.service.order;

import com.goldonbuy.goldonbackend.catalogContext.dto.OrderDTO;
import com.goldonbuy.goldonbackend.catalogContext.entity.*;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.ResourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.repository.OrderRepository;
import com.goldonbuy.goldonbackend.catalogContext.repository.ProductRepository;
import com.goldonbuy.goldonbackend.catalogContext.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return savedOrder;
    }
    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());

        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(
                cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice()
                    );
                }
        ).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(
                item -> item.getPrice().multiply(new BigDecimal(item.getQuantity()))
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        return this.orderRepository.findById(orderId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found !"));
    }

    @Override
    public List<OrderDTO> getUserOrders(Long userId) {
        List<Order> orders = this.orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDTO).toList();
    }

    @Override
    public OrderDTO convertToDTO(Order order) {
        return this.modelMapper.map(order, OrderDTO.class);
    }

}

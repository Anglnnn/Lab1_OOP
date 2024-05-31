package com.example.lab1_servlet.service;


import com.example.lab1_servlet.converter.CartConverter;
import com.example.lab1_servlet.converter.OrderConverter;
import com.example.lab1_servlet.dto.OrderDTO;
import com.example.lab1_servlet.entity.Cart;
import com.example.lab1_servlet.entity.Order;
import com.example.lab1_servlet.service.data.CartService;
import com.example.lab1_servlet.service.data.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderControllerService {
    private final OrderService orderService;
    private final OrderConverter orderConverter;
    private final CartConverter cartConverter;
    private final CartService cartService;

    public OrderControllerService(OrderService orderService,
                                  OrderConverter orderConverter,
                                  CartConverter cartConverter,
                                  CartService cartService) {
        this.orderService = orderService;
        this.orderConverter = orderConverter;
        this.cartConverter = cartConverter;
        this.cartService = cartService;
    }

    public OrderDTO createOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = orderService.createOrder(userId, cart);
        return orderConverter.convertToOrderDto(order);
    }
}

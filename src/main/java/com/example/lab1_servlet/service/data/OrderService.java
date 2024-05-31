package com.example.lab1_servlet.service.data;


import com.example.lab1_servlet.entity.Cart;
import com.example.lab1_servlet.entity.Order;
import com.example.lab1_servlet.entity.User;
import com.example.lab1_servlet.exception.UserInBlacklistException;
import com.example.lab1_servlet.exception.UserNotFoundException;
import com.example.lab1_servlet.repository.OrderRepository;
import com.example.lab1_servlet.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Order createOrder(Long userId, Cart cart) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException());
        if (user.getBlacklist() != false){
            throw new UserInBlacklistException("User is in blacklist and cannot make orders.");
        }
        Order order = new Order();
        order.setUser(user);
        order.setCart(cart);
        order.setOrderDate(LocalDateTime.now());

        orderRepository.addOrder(order);
        return order;
    }

}

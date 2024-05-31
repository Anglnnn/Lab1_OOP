package com.example.lab1_servlet.converter;

import com.example.lab1_servlet.dto.CartDTO;
import com.example.lab1_servlet.dto.OrderDTO;
import com.example.lab1_servlet.entity.Cart;
import com.example.lab1_servlet.entity.Order;
import com.example.lab1_servlet.entity.User;
import com.example.lab1_servlet.exception.UserNotFoundException;
import com.example.lab1_servlet.repository.UserRepository;

import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class OrderConverter {
    private final UserRepository userRepository;
    private final CartConverter cartConverter;

    public OrderConverter(CartConverter cartConverter,
                          UserRepository userRepository) {
        this.cartConverter = cartConverter;
        this.userRepository = userRepository;
    }

    public OrderDTO convertToOrderDto(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUser().getId());
        CartDTO cartDTO  = cartConverter.convertToCartDto(order.getCart());
        orderDTO.setOrderCart(cartDTO);
        return orderDTO;
    }

    public Order convertToOrderEntity(OrderDTO orderDTO) throws SQLException {
        Order order = new Order();
        order.setId(orderDTO.getId());
        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(()-> new UserNotFoundException());
        order.setUser(user);
        Cart cart = cartConverter.convertToCartEntity(orderDTO.getOrderCart());
        order.setCart(cart);
        return order;
    }
}

package com.example.lab1_servlet.converter;


import com.example.lab1_servlet.dto.CartDTO;
import com.example.lab1_servlet.dto.CartItemDTO;
import com.example.lab1_servlet.entity.Cart;
import com.example.lab1_servlet.entity.Product;
import com.example.lab1_servlet.entity.User;
import com.example.lab1_servlet.exception.ProductNotFoundException;
import com.example.lab1_servlet.exception.UserNotFoundException;
import com.example.lab1_servlet.repository.ProductRepository;
import com.example.lab1_servlet.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CartConverter {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartConverter(UserRepository userRepository,
                         ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public CartDTO convertToCartDto(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUserId(cart.getUser().getId());
        cartDTO.setTotalPrice(cart.getTotalPrice());

        List<CartItemDTO> cartItems = cart.getItems().entrySet().stream()
                .map(entry -> {
                    CartItemDTO item = new CartItemDTO();
                    item.setProductId(entry.getKey().getId());
                    item.setQuantity(entry.getValue());
                    return item;
                })
                .collect(Collectors.toList());

        cartDTO.setCartItems(cartItems);
        return cartDTO;
    }

    public Cart convertToCartEntity(CartDTO cartDTO) throws SQLException {
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        User user = userRepository.findById(cartDTO.getUserId()).orElseThrow(()-> new UserNotFoundException());
        cart.setUser(user);
        cart.setTotalPrice(cartDTO.getTotalPrice());

        Map<Product, Integer> items = new HashMap<>();
        for (CartItemDTO item : cartDTO.getCartItems()) {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(()-> new ProductNotFoundException());
            items.put(product, item.getQuantity());
        }
        cart.setItems(items);
        return cart;
    }
}

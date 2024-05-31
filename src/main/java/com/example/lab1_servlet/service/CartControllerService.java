package com.example.lab1_servlet.service;

import com.example.lab1_servlet.converter.CartConverter;
import com.example.lab1_servlet.dto.CartDTO;
import com.example.lab1_servlet.entity.Cart;
import com.example.lab1_servlet.service.data.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartControllerService {
    private final CartService cartService;
    private final CartConverter cartConverter;

    public CartControllerService(CartService cartService, CartConverter cartConverter) {
        this.cartService = cartService;
        this.cartConverter = cartConverter;
    }

    public CartDTO addProductToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.addProductToCart(cartId, productId, quantity);
        return cartConverter.convertToCartDto(cart);
    }

    public CartDTO removeProductFromCart(Long cartId, Long productId) {
        Cart cart = cartService.removeProductFromCart(cartId, productId);
        return cartConverter.convertToCartDto(cart);
    }

    public CartDTO increaseProductQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.increaseProductQuantity(cartId, productId, quantity);
        return cartConverter.convertToCartDto(cart);
    }

    public CartDTO getCart(Long cartId) {
        Cart cart = cartService.getCart(cartId);
        return cartConverter.convertToCartDto(cart);
    }
}

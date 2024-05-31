package com.example.lab1_servlet.service.data;

import com.example.lab1_servlet.entity.Cart;
import com.example.lab1_servlet.entity.Product;
import com.example.lab1_servlet.entity.User;
import com.example.lab1_servlet.exception.*;
import com.example.lab1_servlet.exception.CartNotFoundException;
import com.example.lab1_servlet.exception.ProductNotFoundException;
import com.example.lab1_servlet.repository.CartRepository;
import com.example.lab1_servlet.repository.ProductRepository;
import com.example.lab1_servlet.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CartService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartService(final ProductRepository productRepository,
                       final CartRepository cartRepository,
                       final UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public Cart createCartForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Cart cart = new Cart(); //!!!!!
        cart.setUser(user);
        cart.setTotalPrice(0.0);
        cartRepository.addCart(cart);
        return cart;
    }

    public Cart addProductToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        Map<Product, Integer> items = cart.getItems();
        int existingQuantity = items.getOrDefault(product, 0);
        items.put(product, existingQuantity + quantity);

        cart.setTotalPrice(cart.getTotalPrice() + product.getPrice() * quantity);
        cartRepository.updateCart(cart);

        return cart;
    }

    public Cart removeProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);

        Product productInCart = cart.getItems().keySet().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(ProductNotInCartException::new);

        int quantity = cart.getItems().get(productInCart);
        cart.setTotalPrice(cart.getTotalPrice() - productInCart.getPrice() * quantity);
        cart.getItems().remove(productInCart);

        cartRepository.updateCart(cart);
        return cart;
    }

    public Cart increaseProductQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);

        Product product = cart.getItems().keySet().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(ProductNotInCartException::new);

        Map<Product, Integer> items = cart.getItems();
        int existingQuantity = items.getOrDefault(product, 0);

        // Відніміть вартість продукту, який був в кошику до зміни
        cart.setTotalPrice(cart.getTotalPrice() - product.getPrice() * existingQuantity);

        // Замініть кількість продукту на нову кількість
        items.put(product, quantity);

        // Додайте вартість продукту після зміни
        cart.setTotalPrice(cart.getTotalPrice() + product.getPrice() * quantity);


        cartRepository.updateCart(cart);
        return cart;
    }

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
    }

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findCartByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException());
    }
}


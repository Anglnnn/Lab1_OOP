package com.example.lab1_servlet.controller;


import com.example.lab1_servlet.dto.CartDTO;
import com.example.lab1_servlet.service.CartControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CartController {
    private final CartControllerService cartControllerService;

    @Autowired
    public CartController(final CartControllerService cartControllerService) {
        this.cartControllerService = cartControllerService;
    }

    @PostMapping(value = "/cart/{cartId}/product/{productId}")
    public CartDTO addProduct(@PathVariable Long cartId,
                              @PathVariable Long productId,
                              @RequestParam int quantity) {
        return cartControllerService.addProductToCart(cartId, productId, quantity);
    }

    @PutMapping(value = "/cart/{cartId}/product/{productId}")
    public CartDTO increaseProductQuantity(@PathVariable Long cartId,
                                           @PathVariable Long productId,
                                           @RequestParam int quantity) {
        return cartControllerService.increaseProductQuantity(cartId, productId, quantity);
    }

    @DeleteMapping(value = "/cart/{cartId}/product/{productId}")
    public CartDTO removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        return cartControllerService.removeProductFromCart(cartId, productId);
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long cartId) {
        CartDTO cartDTO = cartControllerService.getCart(cartId);
        return ResponseEntity.ok(cartDTO);
    }
}

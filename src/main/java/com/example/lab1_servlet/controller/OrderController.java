package com.example.lab1_servlet.controller;



import com.example.lab1_servlet.dto.OrderDTO;
import com.example.lab1_servlet.service.OrderControllerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class OrderController {
    private OrderControllerService orderControllerService;

    public OrderController (OrderControllerService orderControllerService) {
        this.orderControllerService = orderControllerService;
    }

    @PostMapping(value = "/orders/{userId}")
    public OrderDTO createOrder(@PathVariable Long userId) {
        return orderControllerService.createOrder(userId);
    }
}

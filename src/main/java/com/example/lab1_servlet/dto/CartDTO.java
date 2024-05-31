package com.example.lab1_servlet.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDTO {

    private Long id;

    private Long userId;

    private Double totalPrice;

    private List<CartItemDTO> cartItems;
}

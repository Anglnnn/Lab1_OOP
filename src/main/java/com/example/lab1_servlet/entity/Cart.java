package com.example.lab1_servlet.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter

public class Cart implements Serializable {

    private Long id;

    private User user;

    private Map<Product, Integer> items = new HashMap<>();

    private Double totalPrice;
}

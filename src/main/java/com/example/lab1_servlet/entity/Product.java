package com.example.lab1_servlet.entity;


import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@Setter
@Getter
public class Product implements Serializable {
    private Long id;

    private String name;

    private String description;

    private Double price;

    private int quantity;

    private String imageUrl;
}

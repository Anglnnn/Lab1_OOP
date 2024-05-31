package com.example.lab1_servlet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;


@Setter
@Getter
public class Order implements Serializable {

    private Long id;

    private User user;

    private Cart cart;

    private LocalDateTime orderDate;
}

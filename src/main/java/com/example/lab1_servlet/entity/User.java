package com.example.lab1_servlet.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class User implements Serializable {
    private Long id;

    private String name;

    private String login;

    private Boolean blacklist;

    private Long cartId;
}

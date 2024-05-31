package com.example.lab1_servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }, scanBasePackages = {"com.example" })
public class Lab1ServletApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab1ServletApplication.class, args);
    }

}


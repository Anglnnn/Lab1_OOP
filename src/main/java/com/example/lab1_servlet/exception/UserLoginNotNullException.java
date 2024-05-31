package com.example.lab1_servlet.exception;

public class UserLoginNotNullException extends RuntimeException {
    public UserLoginNotNullException(String message){
        super(message);
    }
}

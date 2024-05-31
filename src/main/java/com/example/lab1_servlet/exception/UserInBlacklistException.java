package com.example.lab1_servlet.exception;

public class UserInBlacklistException extends  RuntimeException{
    public UserInBlacklistException(String message){
        super(message);
    }
}

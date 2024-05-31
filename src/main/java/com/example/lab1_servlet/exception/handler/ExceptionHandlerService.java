package com.example.lab1_servlet.exception.handler;

import com.example.lab1_servlet.dto.ErrorInfo;
import com.example.lab1_servlet.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerService {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserNotFoundException.class,
            UserAlreadyExistsException.class,
            UserAlreadyInBlacklistException.class,
            UserNotInBlacklistException.class,
            UserLoginNotNullException.class,
            CartNotFoundException.class,
            ProductNotFoundException.class,
            ProductNotFoundException.class,
            UserInBlacklistException.class})

    @ResponseBody
    public ErrorInfo exceptionHandler(Exception ex){
        return new ErrorInfo().setTimestamp(System.currentTimeMillis())
                .setMessage(ex.getMessage())
                .setDeveloperMessage(ex.toString());
    }
}

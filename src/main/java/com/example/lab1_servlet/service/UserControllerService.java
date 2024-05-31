package com.example.lab1_servlet.service;



import com.example.lab1_servlet.converter.UserConverter;
import com.example.lab1_servlet.dto.UserDTO;
import com.example.lab1_servlet.exception.UserLoginNotNullException;
import com.example.lab1_servlet.service.data.UserService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserControllerService {

    private final UserService userService;
    private final UserConverter userConverter;

    public UserControllerService(UserService userService, UserConverter userConverter){
        this.userService = userService;
        this.userConverter = userConverter;
    }

    public List<UserDTO> findAll() throws SQLException {
        return userConverter.convertToListDto(userService.findAll());
    }

    public UserDTO inBlacklistUser(String login) throws SQLException {
        if (login == null){
            throw new UserLoginNotNullException("User login is required.");
        }
        return userConverter.convertToDto(userService.blacklistUser(login));
    }

    public UserDTO unBlacklistUser(String login) throws SQLException {
        if (login == null){
            throw new UserLoginNotNullException("User login is required.");
        }
        return userConverter.convertToDto(userService.unBlacklistUser(login));
    }
}

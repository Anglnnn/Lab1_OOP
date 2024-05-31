package com.example.lab1_servlet.controller;



import com.example.lab1_servlet.dto.UserDTO;
import com.example.lab1_servlet.service.UserControllerService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    private final UserControllerService userControllerService;

    public UserController (UserControllerService userControllerService){
        this.userControllerService = userControllerService;
    }

    @GetMapping(value = "/users")
    public List<UserDTO> findUser() throws SQLException {
        return userControllerService.findAll();
    }

    @PostMapping(value = "/blacklist/{login}")
    public UserDTO inBlacklist(@PathVariable("login") String login) throws SQLException {
        return userControllerService.inBlacklistUser(login);
    }

    @DeleteMapping(value = "/blacklist/{login}")
    public UserDTO unBlacklist(@PathVariable("login") String login) throws SQLException {
        return userControllerService.unBlacklistUser(login);
    }
}

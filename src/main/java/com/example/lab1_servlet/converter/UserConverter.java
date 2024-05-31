package com.example.lab1_servlet.converter;



import com.example.lab1_servlet.dto.UserDTO;
import com.example.lab1_servlet.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public UserDTO convertToDto(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setLogin(user.getLogin());
        userDTO.setIsBlacklist(user.getBlacklist());

        return userDTO;
    }

    public User convertToEntity(UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setLogin(userDTO.getLogin());

        return user;
    }

    public List<UserDTO> convertToListDto(List<User> users){
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}

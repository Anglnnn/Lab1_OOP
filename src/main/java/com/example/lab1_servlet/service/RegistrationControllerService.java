package com.example.lab1_servlet.service;

import com.example.lab1_servlet.converter.UserConverter;
import com.example.lab1_servlet.dto.UserDTO;
import com.example.lab1_servlet.entity.Cart;
import com.example.lab1_servlet.entity.User;
import com.example.lab1_servlet.service.data.CartService;
import com.example.lab1_servlet.service.data.RegistrationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


@Service
public class RegistrationControllerService {
    private final UserConverter userConverter;
    private final RegistrationService registrationService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CartService cartService;

    public RegistrationControllerService(UserConverter userConverter,
                                         RegistrationService registrationService,
                                         ApplicationEventPublisher applicationEventPublisher,
                                         CartService cartService){
        this.userConverter = userConverter;
        this.registrationService = registrationService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.cartService = cartService;
    }

    public UserDTO save(UserDTO userDTO){
        User currentUser = userConverter.convertToEntity(userDTO);
        UserDTO savedUserDto = userConverter.convertToDto(registrationService.save(currentUser));
        savedUserDto.setPassword(userDTO.getPassword());

        Cart newCart = cartService.createCartForUser(currentUser.getId());

        applicationEventPublisher.publishEvent(savedUserDto);

        return savedUserDto;
    }
}

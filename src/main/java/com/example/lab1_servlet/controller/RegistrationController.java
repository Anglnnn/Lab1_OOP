package com.example.lab1_servlet.controller;


import com.example.lab1_servlet.dto.UserDTO;
import com.example.lab1_servlet.service.RegistrationControllerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class RegistrationController {
    private final RegistrationControllerService registrationControllerService;

    public RegistrationController(RegistrationControllerService registrationControllerService){
        this.registrationControllerService = registrationControllerService;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity registration(@Valid @RequestBody UserDTO userDTO){
        registrationControllerService.save(userDTO);
        return ResponseEntity.accepted().build();
    }
}

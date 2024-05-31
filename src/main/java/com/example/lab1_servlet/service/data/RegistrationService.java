package com.example.lab1_servlet.service.data;



import com.example.lab1_servlet.entity.User;
import com.example.lab1_servlet.exception.UserAlreadyExistsException;
import com.example.lab1_servlet.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class RegistrationService {

    private final UserRepository userRepository;

    public RegistrationService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public User save(User currentUser){
        Optional<User> oldUser = userRepository.findByLogin(currentUser.getLogin());
        oldUser.ifPresent(entity->{throw new UserAlreadyExistsException("User already exists.");});
        return userRepository.addUser(currentUser);
    }
}

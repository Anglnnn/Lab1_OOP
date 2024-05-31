package com.example.lab1_servlet.service.data;

import com.example.lab1_servlet.entity.Blacklist;
import com.example.lab1_servlet.entity.User;
import com.example.lab1_servlet.repository.BlacklistRepository;
import com.example.lab1_servlet.repository.UserRepository;
import com.example.lab1_servlet.exception.*;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    private final UserRepository userRepository;
    private final BlacklistRepository blacklistRepository;

    public UserService(UserRepository userRepository, BlacklistRepository blacklistRepository) {
        this.userRepository = userRepository;
        this.blacklistRepository = blacklistRepository;
    }

    public List<User> findAll() throws SQLException {
        return userRepository.findAll();
    }

    public User blacklistUser(String login) throws SQLException {
        Optional<User> user = userRepository.findByLogin(login);
        User u = user.orElseThrow(UserNotFoundException::new);

        Boolean isBlacklisted = u.getBlacklist();
        if (isBlacklisted != null && isBlacklisted) {
            throw new UserAlreadyInBlacklistException();
        }

        Blacklist blacklist = new Blacklist();
        blacklist.setUser(u);

        blacklistRepository.addBlacklist(blacklist);

        u.setBlacklist(true);
        userRepository.updateUser(u);
        return userRepository.findByLogin(u.getLogin())
                .orElseThrow(UserNotFoundException::new);
    }

    public User unBlacklistUser(String login) throws SQLException {
        Optional<User> user = userRepository.findByLogin(login);
        User u = user.orElseThrow(UserNotFoundException::new);

        if (!u.getBlacklist()) {
            throw new UserNotInBlacklistException();
        }

        blacklistRepository.deleteBlacklist(u.getId());
        u.setBlacklist(false);
        userRepository.updateUser(u);

        return userRepository.findByLogin(u.getLogin())
                .orElseThrow(UserNotFoundException::new);
    }

    public Optional<User> findUserByLogin(String login) throws SQLException {
        return userRepository.findByLogin(login);
    }
}


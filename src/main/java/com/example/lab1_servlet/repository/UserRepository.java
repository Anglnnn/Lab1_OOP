package com.example.lab1_servlet.repository;

import com.example.lab1_servlet.db.DbContext;
import com.example.lab1_servlet.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_ALL_USERS = "SELECT u.*, CASE WHEN b.user_id IS NULL THEN false ELSE true END as isBlacklist FROM shop_users u LEFT JOIN blacklists b ON u.id = b.user_id";
    private static final String SELECT_USER_BY_ID = "SELECT u.*, CASE WHEN b.user_id IS NULL THEN false ELSE true END as isBlacklist FROM shop_users u LEFT JOIN blacklists b ON u.id = b.user_id WHERE u.id = ?";
    private static final String SELECT_USER_BY_LOGIN = "SELECT u.*, CASE WHEN b.user_id IS NULL THEN false ELSE true END as isBlacklist FROM shop_users u LEFT JOIN blacklists b ON u.id = b.user_id WHERE u.login = ?";
    private static final String UPDATE_USER = "UPDATE shop_users SET blacklist = ? WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO shop_users (name, login) VALUES (?, ?)";

    public List<User> findAll() {
        return jdbcTemplate.query(SELECT_ALL_USERS, (resultSet, i) -> {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setLogin(resultSet.getString("login"));
            user.setBlacklist(resultSet.getBoolean("isBlacklist"));
            return user;
        });
    }

    public Optional<User> findById(Long id) {
        return jdbcTemplate.query(SELECT_USER_BY_ID, new Object[]{id}, (resultSet, i) -> {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setLogin(resultSet.getString("login"));
            user.setBlacklist(resultSet.getBoolean("isBlacklist"));
            return user;
        }).stream().findFirst();
    }

    public Optional<User> findByLogin(String login) {
        return jdbcTemplate.query(SELECT_USER_BY_LOGIN, new Object[]{login}, (resultSet, i) -> {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setLogin(resultSet.getString("login"));
            user.setBlacklist(resultSet.getBoolean("isBlacklist"));
            return user;
        }).stream().findFirst();
    }

    public User addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null) {
            user.setId(((Number) keys.get("id")).longValue());
        }
        return user;
    }

    public void updateUser(User user) {
        jdbcTemplate.update(UPDATE_USER, user.getBlacklist(), user.getId());
    }
}

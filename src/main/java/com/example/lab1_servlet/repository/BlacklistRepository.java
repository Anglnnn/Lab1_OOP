package com.example.lab1_servlet.repository;

import com.example.lab1_servlet.db.DbContext;
import com.example.lab1_servlet.entity.Blacklist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;


@Repository
public class BlacklistRepository {

    private JdbcTemplate jdbcTemplate;

    public BlacklistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_BLACKLIST = "INSERT INTO blacklists (user_id) VALUES (?)";
    private static final String DELETE_BLACKLIST = "DELETE FROM blacklists WHERE user_id = ?";


    public void addBlacklist(Blacklist blacklist) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_BLACKLIST, new String[]{"id"});
            ps.setLong(1, blacklist.getUser().getId());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        blacklist.setId(key.longValue());
    }

    public void deleteBlacklist(Long id) {
        jdbcTemplate.update(DELETE_BLACKLIST, id);
    }
}


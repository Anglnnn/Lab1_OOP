package com.example.lab1_servlet.repository;

import com.example.lab1_servlet.db.DbContext;
import com.example.lab1_servlet.entity.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class OrderRepository {

    private JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String ADD_NEW_ORDER = "INSERT INTO orders (user_id, cart_id, order_date) VALUES (?, ?, ?)";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?";

    public Optional<Order> findById(Long id) {
        return jdbcTemplate.query(SELECT_ORDER_BY_ID, new Object[]{id}, (resultSet, i) -> {
            Order order = new Order();
            order.setId(resultSet.getLong("id"));
            // Заповніть інші поля замовлення
            return order;
        }).stream().findFirst();
    }

    public void addOrder(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_NEW_ORDER, new String[]{"id"});
            ps.setLong(1, order.getUser().getId());
            ps.setLong(2, order.getCart().getId());
            ps.setTimestamp(3, Timestamp.valueOf(order.getOrderDate()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        order.setId(key.longValue());
    }
}


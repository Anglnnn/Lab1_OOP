package com.example.lab1_servlet.repository;

import com.example.lab1_servlet.db.DbContext;
import com.example.lab1_servlet.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String ADD_NEW_PRODUCT = "INSERT INTO products (name, description, price, quantity, image_url) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM products WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE products SET name = ?, description = ?, price = ?, quantity = ?, image_url = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id = ?";

    public void addProduct(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_NEW_PRODUCT, new String[]{"id"});
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getImageUrl());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        product.setId(key.longValue());
    }

    public List<Product> findAll() {
        return jdbcTemplate.query(SELECT_ALL_PRODUCTS, (resultSet, i) -> {
            Product product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setName(resultSet.getString("name"));
            product.setDescription(resultSet.getString("description"));
            product.setPrice(resultSet.getDouble("price"));
            product.setQuantity(resultSet.getInt("quantity"));
            product.setImageUrl(resultSet.getString("image_url"));
            return product;
        });
    }

    public Optional<Product> findById(Long id) {
        return jdbcTemplate.query(SELECT_PRODUCT_BY_ID, new Object[]{id}, (resultSet, i) -> {
            Product product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setName(resultSet.getString("name"));
            product.setDescription(resultSet.getString("description"));
            product.setPrice(resultSet.getDouble("price"));
            product.setQuantity(resultSet.getInt("quantity"));
            product.setImageUrl(resultSet.getString("image_url"));
            return product;
        }).stream().findFirst();
    }

    public void updateProduct(Product product) {
        jdbcTemplate.update(UPDATE_PRODUCT, product.getName(), product.getDescription(), product.getPrice(), product.getQuantity(), product.getImageUrl(), product.getId());
    }

    public void deleteProduct(Long id) {
        jdbcTemplate.update(DELETE_PRODUCT, id);
    }
}


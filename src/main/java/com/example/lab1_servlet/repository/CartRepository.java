package com.example.lab1_servlet.repository;

import com.example.lab1_servlet.db.DbContext;
import com.example.lab1_servlet.entity.Cart;
import com.example.lab1_servlet.entity.Product;
import com.example.lab1_servlet.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CartRepository {

    private JdbcTemplate jdbcTemplate;

    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String ADD_NEW_CART = "INSERT INTO carts (user_id, total_price) VALUES (?, ?)";
    private static final String SELECT_CART_BY_ID = "SELECT * FROM carts WHERE id = ?";
    private static final String SELECT_CART_ITEMS_BY_CART_ID = "SELECT ci.*, p.name, p.price, p.description, p.image_url FROM cart_items ci JOIN products p ON ci.product_id = p.id WHERE ci.cart_id = ?";
    private static final String ADD_CART_ITEM = "INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)";
    private static final String UPDATE_CART_ITEM = "UPDATE cart_items SET quantity = ? WHERE cart_id = ? AND product_id = ?";
    private static final String UPDATE_CART_TOTAL_PRICE = "UPDATE carts SET total_price = ? WHERE id = ?";
    private static final String DELETE_CART_ITEM = "DELETE FROM cart_items WHERE cart_id = ? AND product_id = ?";
    private static final String SELECT_PRODUCT_IDS_IN_CART = "SELECT product_id FROM cart_items WHERE cart_id = ?";

    public Optional<Cart> findById(Long id) {
        Optional<Cart> cartOptional = jdbcTemplate.query(SELECT_CART_BY_ID, new Object[]{id}, (resultSet, i) -> {
            Cart cart = new Cart();
            cart.setId(resultSet.getLong("id"));
            cart.setTotalPrice(resultSet.getDouble("total_price"));

            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            cart.setUser(user);

            return cart;
        }).stream().findFirst();

        cartOptional.ifPresent(cart -> {
            Map<Product, Integer> items = jdbcTemplate.query(SELECT_CART_ITEMS_BY_CART_ID, new Object[]{cart.getId()}, (resultSet, i) -> {
                Product product = new Product();
                product.setId(resultSet.getLong("product_id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setImageUrl(resultSet.getString("image_url"));
                Integer quantity = resultSet.getInt("quantity");
                return new AbstractMap.SimpleEntry<>(product, quantity);
            }).stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            cart.setItems(items);
        });

        return cartOptional;
    }

    public void addCart(Cart cart) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_NEW_CART, new String[]{"id"});
            ps.setLong(1, cart.getUser().getId());
            ps.setDouble(2, cart.getTotalPrice());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        cart.setId(key.longValue());

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            jdbcTemplate.update(ADD_CART_ITEM, cart.getId(), entry.getKey().getId(), entry.getValue());
        }
    }

    public void updateCart(Cart cart) {
        jdbcTemplate.update(UPDATE_CART_TOTAL_PRICE, cart.getTotalPrice(), cart.getId());
        updateCartItems(cart);
    }

    private void updateCartItems(Cart cart) {
        List<Long> currentProductIds = jdbcTemplate.queryForList(SELECT_PRODUCT_IDS_IN_CART, Long.class, cart.getId());

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            Integer newQuantity = entry.getValue();
            if (currentProductIds.contains(product.getId())) {
                jdbcTemplate.update(UPDATE_CART_ITEM, newQuantity, cart.getId(), product.getId());
            } else {
                jdbcTemplate.update(ADD_CART_ITEM, cart.getId(), product.getId(), newQuantity);
            }
        }

        // Видалення продуктів, яких більше немає у кошику
        currentProductIds.removeAll(cart.getItems().keySet().stream().map(Product::getId).collect(Collectors.toList()));
        currentProductIds.forEach(productId -> deleteCartItem(cart.getId(), productId));
    }

    public void deleteCartItem(Long cartId, Long productId) {
        jdbcTemplate.update(DELETE_CART_ITEM, cartId, productId);
    }


    public Optional<Cart> findCartByUserId(Long userId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT c.* FROM carts c WHERE c.user_id = ?",
                    new Object[]{userId},
                    (resultSet, rowNum) -> {
                        Cart cart = new Cart();
                        cart.setId(resultSet.getLong("id"));
                        cart.setTotalPrice(resultSet.getDouble("total_price"));

                        User user = new User();
                        user.setId(userId);
                        cart.setUser(user);

                        cart.setItems(loadCartItems(cart.getId()));
                        return cart;
                    }));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    private Map<Product, Integer> loadCartItems(Long cartId) {
        return jdbcTemplate.query(
                SELECT_CART_ITEMS_BY_CART_ID,
                new Object[]{cartId},
                (rs, rowNum) -> {
                    Product product = new Product();
                    product.setId(rs.getLong("product_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getDouble("price"));
                    product.setImageUrl(rs.getString("image_url"));
                    Integer quantity = rs.getInt("quantity");
                    return new AbstractMap.SimpleEntry<>(product, quantity);
                }).stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}


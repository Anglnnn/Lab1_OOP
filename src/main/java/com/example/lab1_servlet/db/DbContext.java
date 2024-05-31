package com.example.lab1_servlet.db;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class DbContext {
    public Connection getConnection(){

        String url = "jdbc:postgresql://localhost:5432/lab1";
        String user = "postgres";
        String password = "admin1";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            // Обробка помилок підключення
            throw new RuntimeException("Failed to connect to the database.", e);
        }
    }
}

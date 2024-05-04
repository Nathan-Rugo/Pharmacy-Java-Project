/*
 * This class provides a method to establish a connection to the database.
 * It uses JDBC to connect to a MySQL database.
 */

package com.githinjinathan.pharma;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/db.GithinjiNathan.Pharmacy";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "c0l@nd3r";

    // Method to establish a database connection
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Attempt to establish the connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            // Print any errors that occur during connection establishment
            e.printStackTrace();
        }
        return connection;
    }
}

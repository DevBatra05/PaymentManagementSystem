// package src;
import javax.swing.JButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    public static final String URL = "jdbc:mysql://localhost:3306/payment_db";  // Replace with your database name
    public static final String USER = "root";    // Replace with your MySQL username
    public static final String PASSWORD = "123456";   // Replace with your MySQL password

    // Method to establish a connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Main method to test the connection
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println("Database Connected Successfully!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
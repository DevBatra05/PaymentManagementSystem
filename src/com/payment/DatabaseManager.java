import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/smartpay_db";
    private static final String USER = "root";
    private static final String PASSWORD = "rishabh007";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Database Connected Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }

}
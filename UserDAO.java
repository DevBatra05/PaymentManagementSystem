import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
public class UserDAO {

    public void registerUser(String name, String email, String password, String role) {
        String query = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
    
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, role);
            stmt.executeUpdate();
    
            // Send confirmation email
            String subject = "Welcome to SmartPay";
            String message = "Hello " + name + ",\n\nYour account has been successfully created!\n\nThanks,\nSmartPay Team";
            
    
            System.out.println("User registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int validateUser(String email, String password) {
        int userId = -1;
        String query = "SELECT user_id, password FROM users WHERE email = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");

                System.out.println("Stored Password: " + storedPassword);
                System.out.println("Entered Password: " + password);

                if (storedPassword.equals(password)) {
                    userId = rs.getInt("user_id");
                } else {
                    System.out.println("Password mismatch!");
                }
            } else {
                System.out.println("Email not found in database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;  // If -1, login failed
    }


    public String resetPassword(String email) {
        // Implement logic here to generate and send a new password.
        // For simplicity, just return the email for now.
        return email;
    }
    
    }
    

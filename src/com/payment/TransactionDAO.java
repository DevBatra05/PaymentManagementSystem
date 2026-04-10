import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO {
    public void createTransaction(int userId, double amount, String status) {
        String query = "INSERT INTO transactions (user_id, amount, status) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setDouble(2, amount);
            stmt.setString(3, status);
            stmt.executeUpdate();

            System.out.println("Transaction recorded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
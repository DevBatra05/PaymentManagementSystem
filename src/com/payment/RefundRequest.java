import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RefundRequest extends JFrame {
    private int userId;
    private JComboBox<String> transactionDropdown;

    public RefundRequest(int userId) {
        this.userId = userId;
        setTitle("Request a Refund");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        transactionDropdown = new JComboBox<>();
        loadFailedTransactions();

        JButton refundButton = new JButton("Request Refund");
        refundButton.addActionListener(e -> requestRefund());

        add(new JLabel("Select Failed Transaction:"));
        add(transactionDropdown);
        add(refundButton);

        setVisible(true);
    }

    private void loadFailedTransactions() {
        String query = "SELECT id, description FROM transactions WHERE user_id = ? AND status = 'Pending'";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactionDropdown.addItem(rs.getInt("id") + " - " + rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestRefund() {
        if (transactionDropdown.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "No failed transactions found.");
            return;
        }

        int transactionId = Integer.parseInt(transactionDropdown.getSelectedItem().toString().split(" - ")[0]);

        String query = "UPDATE transactions SET status = 'Refund Requested' WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, transactionId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Refund Request Submitted!");
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
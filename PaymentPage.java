import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentPage extends JFrame {
    private int userId;
    private JTextField recipientField, amountField;

    public PaymentPage(int userId) {
        this.userId = userId;
        setTitle("Make a Payment");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Recipient Email:"));
        recipientField = new JTextField();
        add(recipientField);

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        JButton payButton = new JButton("💰 Send Payment");
        payButton.addActionListener(e -> processPayment());
        add(payButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void processPayment() {
        String recipientEmail = recipientField.getText();
        double amount = Double.parseDouble(amountField.getText());

        try (Connection conn = DatabaseManager.getConnection()) {
            // Find recipient ID
            String recipientQuery = "SELECT user_id FROM users WHERE email = ?";
            int recipientId = -1;
            try (PreparedStatement stmt = conn.prepareStatement(recipientQuery)) {
                stmt.setString(1, recipientEmail);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    recipientId = rs.getInt("user_id");
                } else {
                    JOptionPane.showMessageDialog(this, "Recipient not found!");
                    return;
                }
            }

            // Check sender's balance
            String checkBalanceQuery = "SELECT balance FROM users WHERE user_id = ?";
            double senderBalance = 0;
            try (PreparedStatement stmt = conn.prepareStatement(checkBalanceQuery)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    senderBalance = rs.getDouble("balance");
                }
            }

            if (senderBalance < amount) {
                JOptionPane.showMessageDialog(this, "Insufficient balance!");
                return;
            }

            // Deduct from sender
            String deductQuery = "UPDATE users SET balance = balance - ? WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deductQuery)) {
                stmt.setDouble(1, amount);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }

            // Add to recipient
            String addQuery = "UPDATE users SET balance = balance + ? WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(addQuery)) {
                stmt.setDouble(1, amount);
                stmt.setInt(2, recipientId);
                stmt.executeUpdate();
            }

            // Insert transaction
            // Insert transaction with 'Pending' status
String insertTransaction = "INSERT INTO transactions (user_id, amount, description, status) VALUES (?, ?, ?, ?)";
try (PreparedStatement stmt = conn.prepareStatement(insertTransaction)) {
    stmt.setInt(1, userId);
    stmt.setDouble(2, -amount);
    stmt.setString(3, "Payment to " + recipientEmail);
    stmt.setString(4, "Pending"); // Payment status
    stmt.executeUpdate();
}

            try (PreparedStatement stmt = conn.prepareStatement(insertTransaction)) {
                stmt.setInt(1, recipientId);
                stmt.setDouble(2, amount);
                stmt.setString(3, "Payment from user " + userId);
                stmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Payment Successful!");
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error processing payment.");
        }
    }
}
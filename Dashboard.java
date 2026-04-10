import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dashboard extends JFrame {
    private int userId;
    private JLabel nameLabel, emailLabel, balanceLabel;
    private JTextArea transactionsArea;

    public Dashboard(int userId) {
        this.userId = userId;
        setTitle("Dashboard - SmartPay");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        nameLabel = new JLabel("Welcome, ");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        emailLabel = new JLabel("Email: ");
        balanceLabel = new JLabel("Balance: Fetching...");

        topPanel.add(nameLabel);
        topPanel.add(emailLabel);
        topPanel.add(balanceLabel);
        add(topPanel, BorderLayout.NORTH);

        transactionsArea = new JTextArea(10, 40);
        transactionsArea.setEditable(false);
        add(new JScrollPane(transactionsArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton payButton = new JButton("💸 Make Payment");
        payButton.addActionListener(e -> new PaymentPage(userId));
        buttonPanel.add(payButton);

        JButton historyButton = new JButton("📜 View Transactions");
        historyButton.addActionListener(e -> new TransactionHistory(userId));
        buttonPanel.add(historyButton);

        JButton refundButton = new JButton("🔄 Request Refund");
        refundButton.addActionListener(e -> new RefundRequest(userId));
        buttonPanel.add(refundButton);

        JButton profileButton = new JButton("⚙️ Profile Settings");
        profileButton.addActionListener(e -> new ProfileSettings(userId));
        buttonPanel.add(profileButton);

        JButton logoutButton = new JButton("🚪 Logout");
        logoutButton.addActionListener(e -> {
            new LoginPage();
            dispose();
        });
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.SOUTH);

        fetchUserData();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchUserData() {
        String query = "SELECT name, email, balance FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nameLabel.setText("Welcome, " + rs.getString("name"));
                emailLabel.setText("Email: " + rs.getString("email"));
                balanceLabel.setText("Balance: $" + rs.getDouble("balance"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
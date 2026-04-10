import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class TransactionHistory extends JFrame {
    private int userId;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterType;
    private JTextField minAmountField, maxAmountField;

    public TransactionHistory(int userId) {
        this.userId = userId;
        setTitle("Transaction History");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table Setup
        String[] columns = {"Date", "Amount", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        transactionTable = new JTable(tableModel);
        add(new JScrollPane(transactionTable), BorderLayout.CENTER);

        // Filter Panel
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());

        filterType = new JComboBox<>(new String[]{"All", "Sent", "Received"});
        minAmountField = new JTextField(5);
        maxAmountField = new JTextField(5);
        JButton filterButton = new JButton("Apply Filter");

        filterButton.addActionListener(e -> fetchTransactions());

        filterPanel.add(new JLabel("Type:"));
        filterPanel.add(filterType);
        filterPanel.add(new JLabel("Min Amount:"));
        filterPanel.add(minAmountField);
        filterPanel.add(new JLabel("Max Amount:"));
        filterPanel.add(maxAmountField);
        filterPanel.add(filterButton);

        add(filterPanel, BorderLayout.NORTH);

        fetchTransactions();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchTransactions() {
        tableModel.setRowCount(0); // Clear table before adding new data

        String filter = (String) filterType.getSelectedItem();
        double minAmount = minAmountField.getText().isEmpty() ? 0 : Double.parseDouble(minAmountField.getText());
        double maxAmount = maxAmountField.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxAmountField.getText());

        String query = "SELECT date, amount, description FROM transactions WHERE user_id = ? AND amount BETWEEN ? AND ?";

        if (filter.equals("Sent")) {
            query += " AND amount < 0";  // Sent transactions are negative
        } else if (filter.equals("Received")) {
            query += " AND amount > 0";  // Received transactions are positive
        }

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setDouble(2, minAmount);
            stmt.setDouble(3, maxAmount);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("date"));
                row.add(rs.getDouble("amount"));
                row.add(rs.getString("description"));
                tableModel.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching transactions.");
        }
    }
}
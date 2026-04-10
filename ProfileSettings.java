import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfileSettings extends JFrame {
    private int userId;
    private JTextField nameField, emailField;
    private JPasswordField passwordField;

    public ProfileSettings(int userId) {
        this.userId = userId;
        setTitle("Profile Settings");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("New Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> updateProfile());
        add(saveButton);

        loadUserData();
        setVisible(true);
    }

    private void loadUserData() {
        String query = "SELECT name, email FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                emailField.setText(rs.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateProfile() {
        String newName = nameField.getText();
        String newEmail = emailField.getText();
        String newPassword = new String(passwordField.getPassword());

        if (newName.isEmpty() || newEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Email cannot be empty.");
            return;
        }

        String query = "UPDATE users SET name = ?, email = ?" + 
                       (newPassword.isEmpty() ? "" : ", password = ?") + 
                       " WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newName);
            stmt.setString(2, newEmail);
            if (!newPassword.isEmpty()) {
                stmt.setString(3, newPassword);
                stmt.setInt(4, userId);
            } else {
                stmt.setInt(3, userId);
            }

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
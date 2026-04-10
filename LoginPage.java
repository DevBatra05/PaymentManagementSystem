import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    static {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginPage() {
        setTitle("SmartPay - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel title = new JLabel("SmartPay System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(72, 133, 237)); // Google Blue
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(_ -> loginUser());

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(15, 157, 88)); // Green
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(e -> registerUser());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        panel.add(buttonPanel);

        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(null); // Center window on screen
        setVisible(true);
    }

    private void loginUser() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
    
        UserDAO userDAO = new UserDAO();
        int userId = userDAO.validateUser(email, password);
    
        if (userId != -1) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            new Dashboard(userId);  // Open the Dashboard
            dispose();  // Close the login window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Email or Password");
        }
    }

    private void registerUser() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
    
        Object[] fields = {
            "Enter Name:", nameField,
            "Enter Email:", emailField,
            "Enter Password:", passwordField
        };
    
        int option = JOptionPane.showConfirmDialog(this, fields, "Register", JOptionPane.OK_CANCEL_OPTION);
    
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
    
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }
    
            UserDAO userDAO = new UserDAO();
            userDAO.registerUser(name, email, password, "customer");
    
            JOptionPane.showMessageDialog(this, "Registration Successful! You can now log in.");
        }
    }
    public static void main(String[] args) {
        new LoginPage();
    }
}
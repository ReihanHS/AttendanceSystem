import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    
    public LoginPage() {
        setTitle("Login Page");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set up layout and components student123
        setLayout(new GridLayout(4, 2));
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);
        
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);
        
        add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[] {"Student", "Teacher", "Beadle"});
        add(roleComboBox);
        
        loginButton = new JButton("Login");
        add(loginButton);
        
        // Add action listener for loginvxcvxvxcvxcvdfsdffs
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                String role = roleComboBox.getSelectedItem().toString();
                
                // Example credentials, ideally these would be stored more securely
                if ("student".equals(username) && Arrays.equals(password, "student123".toCharArray())) {
                    JOptionPane.showMessageDialog(null, "Login successful as " + role);
                    new HomePage(LoginPage.this, role); // Proceed to HomePage based on role
                    LoginPage.this.setVisible(false); // Hide the LoginPage)
                    dispose(); // Close the LoginPage
                } else {
                    JOptionPane.showMessageDialog(LoginPage.this, "Invalid credentials.");
                } 
            }
        });
    }

    public static void main(String[] args) {
        new LoginPage().setVisible(true);
    }
    
}
 

 
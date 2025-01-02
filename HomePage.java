import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    public HomePage(JFrame parentFrame, String role) {
        super("Home Page");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        setTitle("Home Page");
        setLayout(new BorderLayout());

        // Form panel for inputs
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.add(new JLabel("Select Day:"));
        JComboBox<String> dayComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"});
        formPanel.add(dayComboBox);

        formPanel.add(new JLabel("Select Month:"));
        JComboBox<String> monthComboBox = new JComboBox<>(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        formPanel.add(monthComboBox);

        formPanel.add(new JLabel("Select Section:"));
        JComboBox<String> sectionComboBox = new JComboBox<>(new String[]{"Garate L", "Ferreres W", "Sales top"});
        formPanel.add(sectionComboBox);

        add(formPanel, BorderLayout.CENTER);

        // Button panel for Proceed and Back buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton proceedButton = new JButton("Proceed");
        JButton backButton = new JButton("Back");
        buttonPanel.add(proceedButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the user is a teacher
                if (role.equals("Teacher")) {
                    TeacherPanel teacherPanel = new TeacherPanel();
                    teacherPanel.setVisible(true);
                    HomePage.this.dispose();
                } else if (role.equals("Beadle")) {
                    BeadlePanel beadlePanel = new BeadlePanel(HomePage.this); // Pass current frame
                    beadlePanel.setVisible(true);
                    HomePage.this.dispose();
                }
            }
        });

        backButton.addActionListener(e -> {
            MainApp.LoginPage loginpage = new MainApp.LoginPage(); // Use MainApp.LoginPage here
            loginpage.setVisible(true); // Show LoginPage
            dispose(); // Close the current frame
        });


        setVisible(true);
    }
}

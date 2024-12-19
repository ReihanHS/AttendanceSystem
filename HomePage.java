import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private JFrame parentFrame;

    public HomePage(JFrame parentFrame, String role) {
        super("Home Page");
        this.parentFrame = parentFrame; // Store reference to the parent frame
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        setTitle("Home Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2)); // Adjusted layout for extra button
        

        // UI Elements
        add(new JLabel("Select Day:"));
        JComboBox<String> dayComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});
        add(dayComboBox);

        add(new JLabel("Select Month:"));
        JComboBox<String> monthComboBox = new JComboBox<>(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        add(monthComboBox);

        add(new JLabel("Select Section:"));
        JComboBox<String> sectionComboBox = new JComboBox<>(new String[]{"A", "B", "C"});
        add(sectionComboBox);

        JButton proceedButton = new JButton("Proceed");
        add(proceedButton);

        JButton backButton = new JButton("Back");
        add(backButton);

        // Action Listeners
        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the user is a teacher
                if (role.equals("Teacher")) {
                    // Proceed to the Teacher Panel
                    
                    TeacherPanel teacherPanel = new TeacherPanel();
                    teacherPanel.setVisible(true); // Show the Teacher Panel
                    HomePage.this.dispose(); // Close the current HomePage
                } else {
                    // Handle other roles or show an error
                    JOptionPane.showMessageDialog(HomePage.this, "Invalid role for this action.");
                }
            }
        });

         // Back button action
        backButton.addActionListener(e -> {
            parentFrame.setVisible(true); // Show parent frame
            dispose(); // Close the current frame
        });

            setVisible(true);
            
        }

    public static void main(String[] args) {
        // Create the parent frame (e.g., Main Menu)
        JFrame mainMenu = new JFrame("Main Menu");
        mainMenu.setSize(400, 300);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setLayout(new FlowLayout());

        JButton openHomePageButton = new JButton("Go to Home Page");
        mainMenu.add(openHomePageButton);

        // Action to open HomePage
        openHomePageButton.addActionListener(e -> {
            mainMenu.setVisible(false); // Hide main menu
            new HomePage(mainMenu, "SomeRole"); // Pass the parent frame and a role string
 // Pass the main menu as the parent frame
        });

        mainMenu.setVisible(true);
    }
}

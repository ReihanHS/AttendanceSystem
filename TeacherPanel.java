import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TeacherPanel extends JFrame {
    private JFrame parent;
    private JTextField studentNameField;
    private JButton addButton, backButton, sendNotificationsButton;
    private DefaultListModel<String> studentListModel;
    private JList<String> studentList;
    private JFrame homePage; // Reference to the HomePage
    private String selectedSection;
    private String selectedDay;
    private String selectedMonth;
    private String fileName;
    


    public TeacherPanel() {
        this(null, "", "", ""); // Call the other constructor with default values
    }

    // Constructor accepting HomePage reference
    public TeacherPanel(JFrame homePage, String selectedDay, String selectedMonth, String selectedSection) {
        this.homePage = homePage;
        this.selectedSection = selectedSection;
        this.selectedDay = selectedDay;
        this.selectedMonth = selectedMonth;
        this.fileName = this.selectedDay + "_" + this.selectedMonth + "_" + this.selectedSection + ".txt";
        // Frame setup
        setTitle("Teacher Panel");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        File sectionFile = new File(this.selectedSection + ".txt");
        File attendanceFile = new File(this.fileName);
        if (sectionFile.exists() && !attendanceFile.exists()) {
            System.out.println(this.selectedSection);
            try (BufferedReader reader = new BufferedReader(new FileReader(sectionFile)); //Ferreres.txt
                 BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName))) { //1_January_Ferreres.txt
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Top panel for input
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel("Enter Student Name:");
        studentNameField = new JTextField();
        topPanel.add(nameLabel, BorderLayout.NORTH);
        topPanel.add(studentNameField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Center panel for student list
        studentListModel = new DefaultListModel<>();
        studentList = new JList<>(studentListModel);
        JScrollPane scrollPane = new JScrollPane(studentList);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add Student");
        backButton = new JButton("Back");
        sendNotificationsButton = new JButton("Send Notifications");
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);
        buttonPanel.add(sendNotificationsButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load students from file
        loadStudentList();

        // Add button functionality
        addButton.addActionListener(e -> {
            String studentName = studentNameField.getText().trim();
            if (!studentName.isEmpty()) {
                studentListModel.addElement(studentName);
                studentNameField.setText("");
                saveStudentList();
            } else {
                JOptionPane.showMessageDialog(this, "Student name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Back button functionality
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an instance of the HomePage
                HomePage homePage = new HomePage(TeacherPanel.this, "Teacher");
                
                // Make the HomePage visible
                homePage.setVisible(true);
                
                // Dispose the current TeacherPanel window
                TeacherPanel.this.dispose();
            }
        });
        // Send Notifications button functionality
        sendNotificationsButton.addActionListener(e -> {
            sendAttendanceNotifications();
        });

        // Show frame
        setVisible(true);
    }

    private void saveStudentList() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName, false))) {
            System.out.println(this.fileName);
            for (int i = 0; i < studentListModel.size(); i++) {
                writer.write(studentListModel.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStudentList() {
        
        try (BufferedReader reader = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                studentListModel.addElement(line);
            }
        } catch (IOException e) {
            System.out.println("No saved students found. File might not exist yet.");
        }
    }
    private void sendAttendanceNotifications() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("* L") || line.contains("* A")) {
                    String studentName = line.replace("* L", "").replace("* A", "").trim();
                    String status = line.contains("* L") ? "late" : "absent";
                    System.out.println("\n----------------------------------------");
                    System.out.println("Subject: Attendance Notification for " + studentName);
                    System.out.println("\nDear Parent/Guardian of " + studentName + ",");
                    System.out.println("\nI hope this email finds you well. I am writing to inform you that");
                    System.out.println("your child, " + studentName + ", was marked as " + status);
                    System.out.println("for today's class in " + selectedSection + ".");
                    System.out.println("\nPlease ensure that your child maintains regular attendance and");
                    System.out.println("punctuality as it is crucial for their academic progress.");
                    System.out.println("\nIf you have any concerns, please don't hesitate to contact me.");
                    System.out.println("\nBest regards,");
                    System.out.println("Class Teacher");
                    System.out.println("Section " + selectedSection);
                    System.out.println("----------------------------------------");                    
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading attendance file: " + e.getMessage());
        }    }
        
        
            
        
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Replace "null" and "Teacher" with the actual arguments your HomePage constructor needs
            JFrame homePage = new HomePage(null, "Teacher"); // Pass required arguments
            homePage.setVisible(true);
    
            TeacherPanel teacherPanel = new TeacherPanel(homePage, "", "", "");          
            teacherPanel.setVisible(true);
            homePage.setVisible(false); // Hide the HomePage while TeacherPanel is open
        });
    }
}

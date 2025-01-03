import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class BeadlePanel extends JFrame {
    private JList<String> studentList;
    private JComboBox<String> attendanceComboBox;
    private JButton markButton, backButton;

    public BeadlePanel(JFrame parent) {
        setTitle("Beadle Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Load students from file
        DefaultListModel<String> listModel = new DefaultListModel<>();
        loadStudentsFromFile("students.txt", listModel);
        studentList = new JList<>(listModel);
        add(new JScrollPane(studentList), BorderLayout.CENTER);

        attendanceComboBox = new JComboBox<>(new String[] {"Present", "Absent", "Late"});
        add(attendanceComboBox, BorderLayout.NORTH);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        markButton = new JButton("Mark Attendance");
        backButton = new JButton("Back");
        buttonPanel.add(markButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        markButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStudent = studentList.getSelectedValue();
                if (selectedStudent != null) {
                    String attendanceStatus = attendanceComboBox.getSelectedItem().toString();
                    markAttendance(selectedStudent, attendanceStatus);
                } else {
                    JOptionPane.showMessageDialog(BeadlePanel.this,
                        "Please select a student to mark attendance.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (parent != null) {
                    parent.setVisible(true); // Show the parent frame
                }
                dispose(); // Close this frame
            }
        });


        setVisible(true);
    }

    private void loadStudentsFromFile(String fileName, DefaultListModel<String> listModel) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listModel.addElement(line.trim()); // Add each line as a student name
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error loading students from file: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void markAttendance(String student, String status) {
        // Simulate saving attendance to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("attendance.txt", true))) {
            writer.write("Attendance for " + student + ": " + status);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error saving attendance: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("Attendance for " + student + " marked as: " + status);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame homePage = new JFrame(); // Placeholder for parent frame
            new BeadlePanel(homePage);
        });
    }
}

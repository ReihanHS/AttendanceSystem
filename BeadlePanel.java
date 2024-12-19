import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BeadlePanel extends JFrame {
    private JList<String> studentList;
    private JComboBox<String> attendanceComboBox;
    private JButton markButton, backButton;
    
    public BeadlePanel() {
        setTitle("Beadle Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout()); // Use BorderLayout
        
        // Example student list (this would come from the Teacher Panel in a real app)
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("John Doe");
        listModel.addElement("Jane Smith");
        studentList = new JList<>(listModel);
        add(new JScrollPane(studentList), BorderLayout.CENTER); // Add the list in the center
        
        attendanceComboBox = new JComboBox<>(new String[] {"Present", "Absent"});
        add(attendanceComboBox, BorderLayout.NORTH); // Place attendance combo box at the top
        
        markButton = new JButton("Mark Attendance");
        add(markButton, BorderLayout.SOUTH); // Place mark button at the bottom
        
        // Back button at the bottom
        JPanel backPanel = new JPanel();
        backButton = new JButton("Back");
        backPanel.add(backButton);
        add(backPanel, BorderLayout.SOUTH); // Add back button to the south panel
        
        // Add action listeners
        markButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStudent = studentList.getSelectedValue();
                String attendanceStatus = attendanceComboBox.getSelectedItem().toString();
                markAttendance(selectedStudent, attendanceStatus);
            }
        });

        // Action for back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage("Beadle");
                dispose(); // Close the current frame
            }
        });
        
        // Display the frame
        setVisible(true);
    }
    
    private void markAttendance(String student, String status) {
        // Save attendance to file (for simplicity, just printing it for now)
        System.out.println("Attendance for " + student + " marked as: " + status);
    }
}

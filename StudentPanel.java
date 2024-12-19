import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class StudentPanel extends JFrame {
    private JList<String> attendanceList;
    private DefaultListModel<String> attendanceListModel;
    private JButton backButton;


    public StudentPanel() {
        setTitle("Student Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    

        setLayout(new BorderLayout());

        // Initialize the attendance list model and load data
        attendanceListModel = new DefaultListModel<>();
        loadAttendanceData(); // Load attendance data from file

        attendanceList = new JList<>(attendanceListModel);
        add(new JScrollPane(attendanceList), BorderLayout.CENTER);

        // Back button at the bottom
        JPanel backPanel = new JPanel();
        backButton = new JButton("Back");
        backPanel.add(backButton);
        add(backPanel, BorderLayout.SOUTH);

        // Action for back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage("Student");
                dispose(); // Close the current frame
            }
        });
        
        //loadAttendanceData();

        setVisible(true);
    }

    private void loadAttendanceData() {
        File file = new File("attendance.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("No attendance data available yet.\n");
                }
                System.out.println("Attendance file created at: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    
        // Load attendance data from the file
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                attendanceListModel.addElement(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
    public static void main(String[] args) {
        new StudentPanel();
    }
}

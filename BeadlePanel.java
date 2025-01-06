import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class BeadlePanel extends JFrame {
    private JFrame parent;
    private JComboBox<String> attendanceComboBox;
    private DefaultListModel<String> studentListModel;
    private JList<String> studentList;
    private JButton  presentButton, lateButton, absentButton, backButton;
    private String selectedDay;
    private String selectedMonth;
    private String selectedSection;
    private String fileName;
    private Map<String, String> attendanceStatus; // Store attendance status for each student


    public BeadlePanel(JFrame parent, String selectedDay, String selectedMonth, String selectedSection) {
        this.parent = parent;
        this.selectedDay = selectedDay;
        this.selectedMonth = selectedMonth;
        this.selectedSection = selectedSection;
        this.fileName = this.selectedDay + "_" + this.selectedMonth + "_" + this.selectedSection + ".txt";
        this.attendanceStatus = new HashMap<>(); // Initialize the attendanceStatus map
        setTitle("Beadle Panel");
        setSize(400, 300);
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
        studentListModel = new DefaultListModel<>();
        studentList = new JList<>(studentListModel);
        studentList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String student = (String) value;
                String status = attendanceStatus.get(student);
                String displayText = student + (status != null ? " (" + status + ")" : "");
                return super.getListCellRendererComponent(list, displayText, index, isSelected, cellHasFocus);
            }
        });

        JScrollPane scrollPane = new JScrollPane(studentList);
        add(scrollPane, BorderLayout.CENTER);

        // Load students from file
        DefaultListModel<String> listModel = new DefaultListModel<>();

        loadStudentsFromFile(this.fileName,  listModel);
        studentList = new JList<>(listModel);
        add(new JScrollPane(studentList), BorderLayout.CENTER);

        

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        lateButton = new JButton("Late");
        absentButton = new JButton("Absent");
        presentButton = new JButton("Present");

        backButton = new JButton("Back");

        // buttonPanel.add(presentButton);
        buttonPanel.add(lateButton);
        buttonPanel.add(absentButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        lateButton.addActionListener(e -> markAttendance("L"));
        absentButton.addActionListener(e -> markAttendance("A"));
        // presentButton.addActionListener(e -> markAttendance("P"));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomePage homePage = new HomePage(BeadlePanel.this, "Beadle");
                homePage.setVisible(true);
                BeadlePanel.this.dispose();
            }
        });
        // Load students from file
        loadStudentsFromFile(this.fileName,  studentListModel);
        
            


        setVisible(true);
    }

    private void loadStudentsFromFile(String fileName, DefaultListModel<String> listModel) {
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String studentName = line.trim();
                if (!studentName.isEmpty()) {
                    listModel.addElement(studentName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading student list: " + e.getMessage());
        }
    }

    private void markAttendance(String status) {
        String selectedStudent = studentList.getSelectedValue();
        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(this, "Please select a student first");
            return;
        }

        // Get the base student name without any status
        String baseStudentName = selectedStudent.split(" - ")[0];

        // Status is already a single letter (P, A, or L)
        char statusLetter = status.charAt(0);

        try {
            // Read all lines
            java.util.List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Get the base name of the current line without status
                    String currentBaseName = line.split(" - ")[0];
                    
                    // If this is the selected student's line, update their status
                    if (currentBaseName.equals(baseStudentName)) {
                        
                        lines.add(baseStudentName + " - " + statusLetter);
                    } else {
                        
                        lines.add(line);
                    }
                }
            }

            // Write all lines back to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            // Update the list model to show the new status
            DefaultListModel<String> model = (DefaultListModel<String>) studentList.getModel();
            int index = studentList.getSelectedIndex();
            if (index != -1) {
                model.setElementAt(baseStudentName + " - " + statusLetter, index);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error updating attendance: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame homePage = new HomePage(null, "Beadle"); // Pass required arguments
            homePage.setVisible(true);
    
            BeadlePanel beadlePanel = new BeadlePanel(homePage, "", "", "");          
            beadlePanel.setVisible(true);
            homePage.setVisible(false);
        });
    }
}

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class EmployeeSearchFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtDatabase;
    private JList<String> lstDepartment;
    private DefaultListModel<String> department = new DefaultListModel<String>();
    private JList<String> lstProject;
    private DefaultListModel<String> project = new DefaultListModel<String>();
    private JTextArea textAreaEmployee;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EmployeeSearchFrame frame = new EmployeeSearchFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public EmployeeSearchFrame() {
        setTitle("Search Employee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 500, 450, 347);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Database:");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
        lblNewLabel.setBounds(21, 23, 59, 14);
        contentPane.add(lblNewLabel);

        txtDatabase = new JTextField();
        txtDatabase.setBounds(90, 20, 193, 20);
        contentPane.add(txtDatabase);
        txtDatabase.setColumns(10);

        JButton btnDBFill = new JButton("Fill");
        btnDBFill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                department.clear();
                project.clear();

                String databaseName = txtDatabase.getText().trim();
                try (FileReader reader = new FileReader("database.props")) {
                    Properties p = new Properties();
                    p.load(reader);

                    String dbdriver = p.getProperty("db.driver");
                    String dbuser = p.getProperty("db.user");
                    String dbpassword = p.getProperty("db.password");
                    String dburl = p.getProperty("db.url");
                    String dbURL = String.format(dburl, databaseName);

                    try {
                        Class.forName(dbdriver);
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    try (Connection connection = DriverManager.getConnection(dbURL, dbuser, dbpassword);
                         Statement stmt = connection.createStatement()) {
                        ResultSet rsDept = stmt.executeQuery("SELECT Dname FROM DEPARTMENT");
                        while (rsDept.next()) {
                            department.addElement(rsDept.getString("Dname"));
                        }
                        rsDept.close();

                        ResultSet rsProj = stmt.executeQuery("SELECT Pname FROM PROJECT");
                        while (rsProj.next()) {
                            project.addElement(rsProj.getString("Pname"));
                        }
                        rsProj.close();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        btnDBFill.setFont(new Font("Times New Roman", Font.BOLD, 12));
        btnDBFill.setBounds(307, 19, 68, 23);
        contentPane.add(btnDBFill);

        JLabel lblDepartment = new JLabel("Department");
        lblDepartment.setFont(new Font("Times New Roman", Font.BOLD, 12));
        lblDepartment.setBounds(52, 63, 89, 14);
        contentPane.add(lblDepartment);

        JLabel lblProject = new JLabel("Project");
        lblProject.setFont(new Font("Times New Roman", Font.BOLD, 12));
        lblProject.setBounds(255, 63, 47, 14);
        contentPane.add(lblProject);

        JScrollPane projectScrollPane = new JScrollPane();
        projectScrollPane.setBounds(225, 84, 150, 50);
        contentPane.add(projectScrollPane);

        lstProject = new JList<String>(new DefaultListModel<String>());
        lstProject.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lstProject.setModel(project);
        lstProject.setBounds(225, 84, 150, 42);
        contentPane.add(lstProject);
        projectScrollPane.setViewportView(lstProject);

        JCheckBox chckbxNotDept = new JCheckBox("Not");
        chckbxNotDept.setBounds(71, 133, 59, 23);
        contentPane.add(chckbxNotDept);

        JCheckBox chckbxNotProject = new JCheckBox("Not");
        chckbxNotProject.setBounds(270, 133, 59, 23);
        contentPane.add(chckbxNotProject);

        JScrollPane departmentScrollPane = new JScrollPane();
        departmentScrollPane.setBounds(36, 84, 172, 50);
        contentPane.add(departmentScrollPane);

        lstDepartment = new JList<String>(new DefaultListModel<String>());
        lstDepartment.setBounds(36, 84, 172, 40);
        contentPane.add(lstDepartment);
        lstDepartment.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lstDepartment.setModel(department);
        departmentScrollPane.setViewportView(lstDepartment);

        JLabel lblEmployee = new JLabel("Employee");
        lblEmployee.setFont(new Font("Times New Roman", Font.BOLD, 12));
        lblEmployee.setBounds(52, 179, 89, 14);
        contentPane.add(lblEmployee);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String databaseName = txtDatabase.getText().trim();

                try (FileReader reader = new FileReader("database.props")) {
                    Properties p = new Properties();
                    p.load(reader);

                    String dbdriver = p.getProperty("db.driver");
                    String dbuser = p.getProperty("db.user");
                    String dbpassword = p.getProperty("db.password");
                    String dburl = p.getProperty("db.url");
                    String dbURL = String.format(dburl, databaseName);
                    Class.forName(dbdriver);

                    try (Connection conn = DriverManager.getConnection(dbURL, dbuser, dbpassword);
                         Statement statementPrint = conn.createStatement()) {

                        textAreaEmployee.setText("");
                        List<String> selectedProjects = lstProject.getSelectedValuesList();
                        List<String> selectedDepartment = lstDepartment.getSelectedValuesList();
                        String projectName = "";
                        String departmentName = "";
                        String notDepartment = chckbxNotDept.isSelected() ? "NOT" : "";
                        String notProject = chckbxNotProject.isSelected() ? "NOT" : "";

                        if (!selectedProjects.isEmpty()) {
                            projectName = String.join("','", selectedProjects);
                        }

                        if (!selectedDepartment.isEmpty()) {
                            departmentName = String.join("','", selectedDepartment);
                        }

                        String queryString = "";
                        if (selectedProjects.isEmpty() && selectedDepartment.isEmpty()) {
                            queryString = "SELECT Fname, Minit, Lname FROM EMPLOYEE";
                        } else if (selectedDepartment.isEmpty()) {
                            queryString = "SELECT DISTINCT E.Fname, E.Minit, E.Lname FROM EMPLOYEE E LEFT JOIN WORKS_ON WO ON E.Ssn = WO.Essn LEFT JOIN PROJECT P ON WO.Pno = P.Pnumber WHERE E.Ssn " +
                                    notProject +
                                    " IN (SELECT Essn FROM WORKS_ON LEFT JOIN PROJECT ON Pno = Pnumber WHERE Pname IN ('" +
                                    projectName +
                                    "'));";
                        } else if (selectedProjects.isEmpty()) {
                            queryString = "SELECT DISTINCT Fname, Minit,  Lname FROM EMPLOYEE, DEPARTMENT WHERE DEPARTMENT.Dnumber = EMPLOYEE.Dno AND DEPARTMENT.Dname " +
                                    notDepartment +
                                    " IN ('" +
                                    departmentName +
                                    "');";
                        } else {
                            queryString = "SELECT DISTINCT E.Fname, E.Minit, E.Lname FROM EMPLOYEE E INNER JOIN DEPARTMENT D ON D.Dnumber = E.Dno INNER JOIN WORKS_ON W ON E.Ssn = W.Essn WHERE D.Dname " +
                                    notDepartment +
                                    " IN ('" +
                                    departmentName +
                                    "') AND E.Ssn " +
                                    notProject +
                                    " IN (SELECT Essn FROM WORKS_ON LEFT JOIN PROJECT ON Pno = Pnumber WHERE Pname IN ('" +
                                    projectName +
                                    "'));";
                        }

                        ResultSet employees = statementPrint.executeQuery(queryString);
                        while (employees.next()) {
                            String employeeName =
                                    employees.getString("Fname") +
                                            " " +
                                            employees.getString("Minit") +
                                            " " +
                                            employees.getString("Lname");
                            textAreaEmployee.append(employeeName + "\n");
                        }
                        employees.close();
                    } catch (Exception exep) {
                        exep.printStackTrace();
                    }
                } catch (ClassNotFoundException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        btnSearch.setBounds(80, 276, 89, 23);
        contentPane.add(btnSearch);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textAreaEmployee.setText("");
                lstDepartment.clearSelection();
                lstProject.clearSelection();
                chckbxNotDept.setSelected(false);
                chckbxNotProject.setSelected(false);
            }
        });

        btnClear.setBounds(236, 276, 89, 23);
        contentPane.add(btnClear);

        textAreaEmployee = new JTextArea();
        textAreaEmployee.setBounds(36, 197, 339, 68);
        contentPane.add(textAreaEmployee);

        JScrollPane employeeList = new JScrollPane();
        employeeList.setBounds(36, 197, 339, 70);
        contentPane.add(employeeList);
        employeeList.setViewportView(textAreaEmployee);
    }
}

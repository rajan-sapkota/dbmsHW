/**
 * Author: Lon Smith, Ph.D.
 * Description: This is the framework for the database program. Additional requirements and functionality
 *    are to be built by you and your group.
 */

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	/**
	 * Launch the application.
	 */
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
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				FileReader reader = new FileReader("database.props");
            Properties props = new Properties();
            props.load(reader);

            String dbUrl = props.getProperty("db.url");
            String dbUser = props.getProperty("db.user");
            String dbPassword = props.getProperty("db.password");
            String dbDriver = props.getProperty("db.driver");

            Class.forName(dbDriver);
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			} 
			catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("The database configuration file was not found.");
			}
			
			catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("this is the catch of connection meaning the class is working but problem in conection");
				System.out.println(e);
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("this is the catch of class meaning error aleready in first class");
			//System.out.println(e);
			e.printStackTrace();
		}
		catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the database configuration file.");}
		
		
	}

	/**
	 * Create the frame.
	 */
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
		/**
		 * The btnDBFill should fill the department and project JList with the 
		 * departments and projects from your entered database name.
		 */
		btnDBFill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				department.clear(); // Clear existing department list
				project.clear();    // Clear existing project list
		
				String databaseName = txtDatabase.getText().trim();
		
				try (Connection connection = DriverManager.getConnection(
						"jdbc:mysql://cis-lonsmith-student2.ccr8ibhqw8qf.us-east-2.rds.amazonaws.com/" + databaseName + "?useSSL=false",
						"sapkotara",
						"abc123");
					 Statement stmt = connection.createStatement()) {
		
					// Fetch department names
					ResultSet rsDept = stmt.executeQuery("SELECT Dname FROM DEPARTMENT");
					while (rsDept.next()) {
						department.addElement(rsDept.getString("Dname"));
					}
					rsDept.close();
		
					// Fetch project names
					ResultSet rsProj = stmt.executeQuery("SELECT Pname FROM PROJECT");
					while (rsProj.next()) {
						project.addElement(rsProj.getString("Pname"));
					}
					rsProj.close();
		
				} catch (SQLException ex) {
					ex.printStackTrace();
					// Ideally, implement better exception handling
				}
			}
		});
		
		
		
		btnDBFill.setFont(new Font("Times New Roman", Font.BOLD, 12));// the fill button
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
		

		//initializing the scroll for the projectList
		JScrollPane projectScrollPane = new JScrollPane();
		projectScrollPane.setBounds(225, 84, 150, 50);   //(x, y, width, height)
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
		
		//initializing the scroll for the projectList
		JScrollPane departmentScrollPane = new JScrollPane();
		departmentScrollPane.setBounds(36, 84, 172, 50);   //(x, y, width, height)
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
				String selectedDept = lstDepartment.getSelectedValue(); // Get the selected department
				String selectedProj = lstProject.getSelectedValue();    // Get the selected project
		
				if (selectedDept == null || selectedProj == null) {
					System.out.println("Please select both a department and a project.");
					return;
				}
		
				String sql = "SELECT DISTINCT E.Fname, E.Minit, E.Lname " +
							 "FROM EMPLOYEE E, DEPARTMENT D, PROJECT P, WORKS_ON W " +
							 "WHERE E.Dno = D.Dnumber " +
							 "AND W.Pno = P.Pnumber " +
							 "AND W.Essn = E.Ssn " +
							 "AND D.Dname = ? " +
							 "AND P.Pname = ?";
		
				try (Connection connection = DriverManager.getConnection(
						"jdbc:mysql://cis-lonsmith-student2.ccr8ibhqw8qf.us-east-2.rds.amazonaws.com/" + databaseName + "?useSSL=false",
						"sapkotara",
						"abc123");
					 PreparedStatement pstmt = connection.prepareStatement(sql)) {
		
					pstmt.setString(1, selectedDept);
					pstmt.setString(2, selectedProj);
		
					System.out.println("Executing query: " + pstmt);
					ResultSet rs = pstmt.executeQuery();
					StringBuilder employeeResults = new StringBuilder();
					while (rs.next()) {
						employeeResults.append(rs.getString("Fname"))
									   .append(' ')
									   .append(rs.getString("Minit"))
									   .append(". ")
									   .append(rs.getString("Lname"))
									   .append("\n");
					}
					if (employeeResults.length() == 0) {
						System.out.println("No results found.");
						textAreaEmployee.setText("No results found.");
					} else {
						textAreaEmployee.setText(employeeResults.toString());
					}
					rs.close();
		
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println("SQL Exception: " + ex.getMessage());
				}
			}
		});
		

		btnSearch.setBounds(80, 276, 89, 23);
		contentPane.add(btnSearch);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaEmployee.setText("");
			}
		});
		btnClear.setBounds(236, 276, 89, 23);
		contentPane.add(btnClear);
		
		textAreaEmployee = new JTextArea();
		textAreaEmployee.setBounds(36, 197, 339, 68);
		contentPane.add(textAreaEmployee);
		JScrollPane employeeList = new JScrollPane();
		employeeList.setBounds(36, 197, 339, 40);
		contentPane.add(employeeList);
		employeeList.setViewportView(textAreaEmployee);
	}
}

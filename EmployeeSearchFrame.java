/**
 * Author: Lon Smith, Ph.D.
 * Description: This is the framework for the database program. Additional requirements and functionality
 *    are to be built by you and your group.
 */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ConnectionBuilder;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
				Connection connection = DriverManager.getConnection("jdbc:mysql://cis-lonsmith-student2.ccr8ibhqw8qf.us-east-2.rds.amazonaws.com/COMPANY?useSSL=false", "sapkotara", "abc123");
			} catch (SQLException e) {
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
				String selectedDept = (String) lstDepartment.getSelectedValue();
				String selectedProj = (String) lstProject.getSelectedValue();
		
				// You'll need to adjust the query if multiple selections are allowed
				String sql = "SELECT EMPLOYEE.Fname, EMPLOYEE.Minit, EMPLOYEE.Lname " +
							 "FROM EMPLOYEE, DEPARTMENT, PROJECT, WORKS_ON " +
							 "WHERE EMPLOYEE.Dno = DEPARTMENT.Dnumber " +
							 "AND WORKS_ON.Pno = PROJECT.Pnumber " +
							 "AND WORKS_ON.Essn = EMPLOYEE.Ssn " +
							 "AND DEPARTMENT.Dname = ? " +
							 "AND PROJECT.Pname = ?";
		
				try (Connection connection = DriverManager.getConnection(
						"jdbc:mysql://cis-lonsmith-student2.ccr8ibhqw8qf.us-east-2.rds.amazonaws.com/" + databaseName + "?useSSL=false",
						"sapkotara",
						"abc123");
					 PreparedStatement pstmt = connection.prepareStatement(sql)) {
		
					pstmt.setString(1, selectedDept);
					pstmt.setString(2, selectedProj);
		
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
					textAreaEmployee.setText(employeeResults.toString());
					rs.close();
		
				} catch (SQLException ex) {
					ex.printStackTrace();
					// Implement better exception handling
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

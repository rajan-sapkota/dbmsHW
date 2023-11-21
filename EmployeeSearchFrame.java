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
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.util.Properties;
import java.io.FileReader;
import java.sql.*;

public class EmployeeSearchFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDatabase;
	private JList<String> lstDepartment;
	private DefaultListModel<String> department = new DefaultListModel<String>();
	private JList<String> lstProject;
	private DefaultListModel<String> project = new DefaultListModel<String>();
	private JTextArea textAreaEmployee;
	static Connection con= null;
	static String employees="";
	private static String database;

	/**
	 * Launch the application.
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
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
		public static String loadDatabase() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Your entered:"+database+"-Nospace\n");
			String connectorString= "jdbc:mysql://cis-lonsmith-student2.ccr8ibhqw8qf.us-east-2.rds.amazonaws.com/"+database+"?useSSL=false&connectTimeout=5000";
			System.out.println(connectorString);	
			con = DriverManager.getConnection(connectorString, "sapkotara", "abc123");
									
				if(!con.isClosed())
				{
				System.out.println("connected");

				Statement st= con.createStatement();
				String query= "Select * from EMPLOYEE";
				ResultSet result =st.executeQuery(query);

				System.out.println("+------------------------+-------------------+");
		System.out.println("|      Name              |       Hours       |");
		System.out.println("+------------------------+-------------------+");
String fullName="";
		while (result.next()) {
		    String firstName = result.getString("Fname");
			System.out.println(firstName);
		    String lastName = result.getString("Lname");
		    String mInit= result.getString("Minit");
		    //String hours= result.getString("Hours");
		   fullName = firstName+" "+mInit+" "+ lastName;
			System.out.println(fullName);
			employees += fullName+"\n";
		    System.out.println(employees);
		    return fullName;
		}


				}
				
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employees;
		

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
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					database= txtDatabase.getText();
					loadDatabase();
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("\n!!!!!!!!!!!!!!INVALID DATABASE NAME!!!!!!!!!!!!!!");
				}
				System.out.println("\n\n YOu clicked fill button\n\n");
				
				String[] dept = {"Headquarters", "Reorganization", "ULM"};	
				for(int i = 0; i < dept.length; i++) {
					department.addElement(dept[i]);
				}
				String[] prj = {"A", "B", "C", "D","E", "F", "G", "H"};
				for(int j = 0; j < prj.length; j++) {
					project.addElement(prj[j]);
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
				textAreaEmployee.setText(employees); //this is employee output
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
	
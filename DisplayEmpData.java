package Lab6;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;  

public class DisplayEmpData extends JFrame{
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	String[] columnNames = {"Employee Number", "Birth Date", "First Name", "Last Name", "Gender", "Hire Date"};
	String[][] tableData = new String[250][6];
	JTable table;
	JPanel panel1;
	JPanel Panel2;
	JPanel tPane;
	JScrollPane tablePane;
	ListSelectionModel listSelectionModel;
	int numberOfRow = 0;
	int employeeNumber = 0;
	
	DisplayEmpData(){
		//Connect database
		try {
			//Register the driver class
			Class.forName("com.mysql.cj.jdbc.Driver");
			//establish connection with the database
			conn = DriverManager.getConnection("jdbc:mysql://localhost/employeesdb", "root", "");
			System.out.println("connecting to the database......");
		}catch( ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("The database is not connected!!!");
		}
		if(conn != null) {
			System.out.println("The connection is success");
		}
		
		//GUI components for homepage
		panel1 = new JPanel();
		JButton savebtn = new JButton("add");
		savebtn.setBounds(10, 10, 75, 20);
		savebtn.addActionListener(new ActionListener(){	
			public void actionPerformed(ActionEvent e) {
			panel1.setVisible(false);
			tablePane.setVisible(false);
			Panel2.setVisible(true);
		}
		});
		
		JTextField tfsearch = new JTextField(12);
		tfsearch.setBounds(90, 10, 120, 20);
		
		JButton searchbtn = new JButton("search");
		searchbtn.setBounds(220, 10, 75, 20);
		searchbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					refresh();
					stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * from employees where emp_no like \"%" + tfsearch.getText() + "%\" ;");
					int i = 0;
					if (rs != null) {
						while (rs.next()) {
							tableData[i][0] = rs.getString(1);
							tableData[i][1] = rs.getString(2);
							tableData[i][2] = rs.getString(3);
							tableData[i][3] = rs.getString(4);
							tableData[i][4] = rs.getString(5);
							tableData[i][5] = rs.getString(6);
							i++;
						}
					}
					rs.close();
					stmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton editbtn = new JButton("edit");
		editbtn.setBounds(300, 10, 75, 20);
		editbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame f = new JFrame();
				String empNo = JOptionPane.showInputDialog(f,"Enter the emp_no");
				String lastName = JOptionPane.showInputDialog(f,"Edit the last name");
				try {
					stmt = conn.createStatement();
					stmt.executeUpdate("Update employees set last_name = '" + lastName + "' where emp_no = " + empNo + ";");
				} catch(SQLException e3) {
					e3.printStackTrace();
				}
				
			}
		});
		
		JButton removebtn = new JButton("remove");
		removebtn.setBounds(376, 10, 85, 20);
		removebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame f = new JFrame();
				String empNo = JOptionPane.showInputDialog(f,"Enter the emp_no");
				int a = JOptionPane.showConfirmDialog(f,"Are you sure?"); 
				if(a==JOptionPane.YES_OPTION){
					try {
						stmt = conn.createStatement();
						stmt.executeUpdate("Delete from employees where emp_no = " + empNo + ";");
					}catch (SQLException e4) {
						e4.printStackTrace();
					}
				}
			}
		});
		
		setSize(500,500);
		panel1.setLayout(new FlowLayout());
		panel1.add(savebtn);
		panel1.add(tfsearch);
		panel1.add(searchbtn);
		panel1.add(editbtn);
		panel1.add(removebtn);
		setLayout(new BorderLayout());
		add(panel1, BorderLayout.NORTH);
		showTableData();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//GUI component for add page
		JTextField textField;
		JTextField textField_1;
		JTextField textField_2;
		JTextField textField_3;
		JTextField textField_4;
		JTextField textField_5;
		
//		setContentPane(Panel2);
		Panel2 = new JPanel();
		add(Panel2);
		Panel2.setLayout(null);
		setBounds(100, 100, 500, 500);
		Panel2.setVisible(false);

		JLabel lblNewLabel = new JLabel("Save new employee");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(100, 10, 259, 33);
		Panel2.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("emp_no");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(30, 60, 60, 30);
		Panel2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("birth_date");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(30, 100, 60, 30);
		Panel2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("first_name");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(30, 140, 60, 30);
		Panel2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("last_name");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(30, 180, 60, 30);
		Panel2.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("gender");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(30, 220, 60, 30);
		Panel2.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("hire_date");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_6.setBounds(30, 260, 60, 30);
		Panel2.add(lblNewLabel_6);
		
		textField = new JTextField();
		textField.setBounds(130, 60, 300, 30);
		Panel2.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(130, 100, 300, 30);
		Panel2.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(130, 140, 300, 30);
		Panel2.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(130, 180, 300, 30);
		Panel2.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(130, 220, 300, 30);
		Panel2.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(130, 260, 300, 30);
		Panel2.add(textField_5);
		textField_5.setColumns(10);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setBounds(200, 400, 85, 25);
		Panel2.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					stmt = conn.createStatement();
					stmt.executeUpdate("INSERT into employees values('"+ textField.getText() + "','" + textField_1.getText() + "','" + textField_2.getText() + "','" + textField_3.getText() + "','" + textField_4.getText() + "','" + textField_5.getText() + "')");
					stmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		JButton btnNewButton_1 = new JButton("<");
		btnNewButton_1.setBounds(25, 15, 50, 40);
		Panel2.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener(){	
			public void actionPerformed(ActionEvent e) {
			panel1.setVisible(true);
			tablePane.setVisible(true);
			Panel2.setVisible(false);
		}
		});
		

	}
	
	public void showTableData() {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * from employees");
			int i = 0;
			if (rs != null) {
				while (rs.next()) {
					tableData[i][0] = rs.getString(1);
					tableData[i][1] = rs.getString(2);
					tableData[i][2] = rs.getString(3);
					tableData[i][3] = rs.getString(4);
					tableData[i][4] = rs.getString(5);
					tableData[i][5] = rs.getString(6);
					i++;
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table = new JTable(tableData, columnNames);
		tablePane = new JScrollPane(table);
		tPane = new JPanel();
		add(tPane,BorderLayout.SOUTH);
		tPane.add(tablePane);
		
	}
	
	public void refresh() {
		tPane.removeAll();
		tPane.setVisible(false);
		table = new JTable(tableData, columnNames);
		tablePane = new JScrollPane(table);
		tPane.add(tablePane);
		this.add(tPane, BorderLayout.SOUTH);
		tPane.setVisible(true);
	}
	
	public static void main(String args[]){
		new DisplayEmpData();
	}
}
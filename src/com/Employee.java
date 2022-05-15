package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Employee { // A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertEmployee(String name, String address, String phone, String gender, String age, String email) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into employee (`employeeId`,`name`,`address`,`phone`,`gender`, `age`, `email`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, address);
			preparedStmt.setString(4, phone);
			preparedStmt.setString(5, gender);
			preparedStmt.setString(6, age);
			preparedStmt.setString(7, email);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newEmployee = readEmployee();
			output = "{\"status\":\"success\", \"data\": \"" + newEmployee + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the employee.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readEmployee() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<div class='w-100'><table class='table table-dark table-hover table-bordered'><tr><th>Name</th>"
					+ "<th>Address</th><th>Phone</th>" + "<th>Gender</th>" + "<th>Age</th>" + "<th>Email</th>"
					+ "<th>Update</th><th>Remove</th></tr></div>";
			String query = "select * from employee";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String employeeId = Integer.toString(rs.getInt("employeeId"));
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String gender = rs.getString("gender");
				String age = rs.getString("age");
				String Email = rs.getString("email");

				// Add into the html table
				output += "<tr><td>" + name + "</td>";
				output += "<td>" + address + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + gender + "</td>";
				output += "<td>" + age + "</td>";
				output += "<td>" + Email + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-primary badge-pill' data-itemid='" + employeeId + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger badge-pill' data-itemid='" + employeeId + "'></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		}

		catch (Exception e) {
			output = "Error while reading the employee.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateEmployee(String employeeId, String name, String address, String phone, String gender,
			String age, String email)

	{
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE employee SET name=?,address=?,phone=?,gender=?,age=?,email=? WHERE employeeId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, address);
			preparedStmt.setString(3, phone);
			preparedStmt.setString(4, gender);
			preparedStmt.setString(5, age);
			preparedStmt.setString(6, email);
			preparedStmt.setInt(7, Integer.parseInt(employeeId));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newEmployee = readEmployee();
			output = "{\"status\":\"success\", \"data\": \"" + newEmployee + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the employee.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteEmployee(String employeeId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from employee where employeeId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(employeeId));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newEmployee = readEmployee();
			output = "{\"status\":\"success\", \"data\": \"" + newEmployee + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the employee.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}

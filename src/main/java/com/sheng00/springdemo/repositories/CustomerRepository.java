package com.sheng00.springdemo.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sheng00.springdemo.models.Customer;

@Service
public class CustomerRepository {

	static String className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static String url = "jdbc:sqlserver://10.35.63.10:1433;DatabaseName=springdemo";
	static String user = "sa";
	static String password = "p@ssw0rd";

	public List<Customer> getAll() {
		List<Customer> customers = new ArrayList<Customer>();
		try {
			Class.forName(className);
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from Customer");
			while (rs.next()) {
				UUID id = UUID.fromString(rs.getString("id"));
				String name = rs.getString("name");
				Customer customer = new Customer(id, name);
				customers.add(customer);
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customers;
	}

	public void addOne(Customer customer) {
		try {
			Class.forName(className);
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) conn.createStatement();
			String sql = "INSERT INTO Customer (name) VALUES (' " + customer.getName() + "')";
			stmt.execute(sql);
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Customer getOne(String id) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from Customer where id = '" + id + "'");
			if (rs.next()) {
				UUID id1 = UUID.fromString(rs.getString("id"));
				String name = rs.getString("name");
				Customer customer = new Customer(id1, name);
				return customer;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

}

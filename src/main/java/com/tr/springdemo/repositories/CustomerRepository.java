package com.tr.springdemo.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tr.springdemo.models.Customer;

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
	
	public boolean deleteOne(String id) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			stmt = (Statement) conn.createStatement();
			String sql1 = "delete from CustomerProduct where cid='" + id + "'";
			String sql2 = "delete FROM Customer where id='" + id + "'";
			
			stmt.addBatch(sql1);
			stmt.addBatch(sql2);
			int[] result = stmt.executeBatch();
            return result[1] != 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
	
	public boolean addProduct(String cid,String pid){
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			stmt = (Statement) conn.createStatement();
			String sql = "INSERT INTO CustomerProduct(cid,pid) VALUES ('" + cid + "','" + pid + "')";
			int result = stmt.executeUpdate(sql);
            return result != 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
	
	public boolean deleteProduct(String cid, String pid){
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			stmt = (Statement) conn.createStatement();
			String sql = "delete from CustomerProduct where cid='" + cid + "' and pid = '" + pid + "'";
			int result = stmt.executeUpdate(sql);
            return result != 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
	
	public List<Customer> getByProduct(String pid) {
		List<Customer> customers = new ArrayList<Customer>();
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Customer "
					+ "where id in "
					+ "(select cid from CustomerProduct "
					+ "where pid = '" + pid + "')");
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
		}finally {
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
		return customers;
	}
	

}

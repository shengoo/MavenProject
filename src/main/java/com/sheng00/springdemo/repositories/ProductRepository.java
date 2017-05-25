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

import com.sheng00.springdemo.models.Product;

@Service
public class ProductRepository {

	static String className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static String url = "jdbc:sqlserver://10.35.63.10:1433;DatabaseName=springdemo";
	static String user = "sa";
	static String password = "p@ssw0rd";

	public List<Product> getAll() {
		List<Product> customers = new ArrayList<Product>();
		try {
			Class.forName(className);
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from Product");
			while (rs.next()) {
				UUID id = UUID.fromString(rs.getString("id"));
				String name = rs.getString("name");
				Product customer = new Product(id, name);
				customers.add(customer);
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customers;
	}
	

	public List<Product> getByCustomer(String cid) {
		List<Product> products = new ArrayList<Product>();
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Product "
					+ "where id in "
					+ "(select pid from CustomerProduct "
					+ "where cid = '" + cid + "')");
			while (rs.next()) {
				UUID id = UUID.fromString(rs.getString("id"));
				String name = rs.getString("name");
				Product product = new Product(id, name);
				products.add(product);
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
		return products;
	}

	public void addOne(Product customer) {
		try {
			Class.forName(className);
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) conn.createStatement();
			String sql = "INSERT INTO Product (name) VALUES (' " + customer.getName() + "')";
			stmt.execute(sql);
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Product getOne(String id) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from Product where id = '" + id + "'");
			if (rs.next()) {
				UUID id1 = UUID.fromString(rs.getString("id"));
				String name = rs.getString("name");
				Product customer = new Product(id1, name);
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
			String sql1 = "delete from CustomerProduct where pid='" + id + "'";
			String sql2 = "delete FROM Product where id='" + id + "'";
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

}

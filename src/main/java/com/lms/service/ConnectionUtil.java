package com.lms.service;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/library";
	private String user = "root";
	private String pass = "5883";

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn =  DriverManager.getConnection(url, user, pass);
		conn.setAutoCommit(false);
		return conn;
	}
}

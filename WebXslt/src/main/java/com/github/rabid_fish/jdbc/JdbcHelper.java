package com.github.rabid_fish.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcHelper {

	public void checkDriver(String databaseDriverClass) {
		
		try {
			Class.forName(databaseDriverClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public Connection getConnection() throws SQLException {
		
		String url = "jdbc:h2:~/test";
		if (System.getProperty("env") != null && System.getProperty("env").equals("test")) {
			url = "jdbc:h2:mem:test";
		}
		
		Connection connection = DriverManager.getConnection(url, "sa", "");
		
		return connection;
	}

	public void executeStatement(Connection connection, String sql) {

		Statement statement = null;
		Exception exception = null;
		
		try {
			statement = connection.createStatement();
			statement.execute(sql);
			
		} catch (SQLException sqle) {
			exception = sqle;
			
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					if (exception == null) {
						exception = sqle;
					}
				}
			}
		}
		
		if (exception != null) {
			throw new RuntimeException(exception);
		}
	}
	
}

package com.github.rabid_fish;

import java.sql.Connection;
import java.sql.SQLException;

import com.github.rabid_fish.jdbc.JdbcHelper;

public class ContactDatabaseSetup {

	public static final String DATABASE_DRIVER_CLASS = "org.h2.Driver";
	
	private static final JdbcHelper helper = new JdbcHelper();
	private static final ContactDatabaseSetup setup = new ContactDatabaseSetup();
	
	private Connection connection = null;
	
	private ContactDatabaseSetup() {
		// only allow one instance
	}
	
	public static Connection getConnection() {
		
		synchronized (setup) {
			if (setup.connection == null) {
				setup.initConnection();
				setup.prepDatabaseForTest();
			}
		}
		
		return setup.connection;
	}
	
	public static void closeConnection() {

		try {
			synchronized (setup) {
				setup.connection.close();
				setup.connection = null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void initConnection() {
		
		helper.checkDriver(DATABASE_DRIVER_CLASS);

		try {
			System.setProperty("env", "test");
			connection = helper.getConnection();
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}
	
	public void prepDatabaseForTest() {

		try {
			helper.executeStatement(connection, "CREATE TABLE contact ( firstName VARCHAR(32), lastName VARCHAR(32), phoneNumber int)");
		} catch (RuntimeException re) {
			System.out.println("Table 'contact' has already been created");
		}
		
		helper.executeStatement(connection, "DELETE FROM contact");
		helper.executeStatement(connection, "INSERT INTO contact (firstName, lastName, phoneNumber) VALUES ('John', 'Deer', 123456)");
		helper.executeStatement(connection, "INSERT INTO contact (firstName, lastName, phoneNumber) VALUES ('Jane', 'Doe', 654321)");
		helper.executeStatement(connection, "INSERT INTO contact (firstName, lastName, phoneNumber) VALUES ('John', 'Smith', 555555)");
	}
}

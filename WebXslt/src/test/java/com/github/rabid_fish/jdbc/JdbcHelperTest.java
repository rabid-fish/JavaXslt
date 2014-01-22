package com.github.rabid_fish.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Test;

import com.github.rabid_fish.ContactDatabaseSetup;

public class JdbcHelperTest {

	JdbcHelper helper = new JdbcHelper();
	
	@AfterClass
	public static void after() {
		ContactDatabaseSetup.closeConnection();
	}
	
	@Test
	public void checkDriver() {
		helper.checkDriver(ContactDatabaseSetup.DATABASE_DRIVER_CLASS);
	}
	
	@Test(expected = RuntimeException.class)
	public void checkDriverWithBadString() {
		helper.checkDriver("gibberish");
	}
	
	@Test
	public void executeStatement() throws SQLException {
		Connection connection = ContactDatabaseSetup.getConnection();
		helper.executeStatement(connection, "INSERT INTO contact (firstName, lastName, phoneNumber) VALUES ('test', 'test', 0)");
		helper.executeStatement(connection, "DELETE FROM contact WHERE phoneNumber = 0");
	}

	@Test(expected = RuntimeException.class)
	public void executeStatementThrowsRuntimeException() throws SQLException {
		Connection connection = ContactDatabaseSetup.getConnection();
		helper.executeStatement(connection, "Gibberish");
	}
}

package com.github.rabid_fish;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.Test;

import com.github.rabid_fish.jdbc.JdbcResultSetTranslator;

public class ContactResultSetTranslatorTest {

	private ContactRowMapper mapper = new ContactRowMapper();
	private JdbcResultSetTranslator jdbcResultSetMapperExample = new JdbcResultSetTranslator();

	@AfterClass
	public static void after() {
		ContactDatabaseSetup.closeConnection();
	}
	
	@Test
	public void testWriteResultsToXml() {

		String sql = "SELECT firstName, lastName, phoneNumber FROM contact";
		Connection connection = ContactDatabaseSetup.getConnection();
		StringWriter writer = new StringWriter();
		jdbcResultSetMapperExample.writeResultsToXml(connection, writer , mapper, sql);
		
		assertTrue(writer.toString().length() > 0);
	}
	
	@Test(expected = RuntimeException.class)
	public void testWriteResultsToXmlThrowsRuntimeException() {

		String sql = "Gibberish";
		Connection connection = ContactDatabaseSetup.getConnection();
		StringWriter writer = new StringWriter();
		jdbcResultSetMapperExample.writeResultsToXml(connection, writer , mapper, sql);
	}

}

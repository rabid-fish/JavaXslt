package com.github.rabid_fish.sqltoxml;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Test;

public class JdbcResultSetTranslatorExampleTest {

	public class ContactMapper implements JdbcResultSetTranslatorExample.ResultSetRowMapper {

		@Override
		public void map(ResultSet resultSet, Writer writer) throws IOException, SQLException {
			writer.write("\t<contact>\n");
			writer.write("\t\t<firstName>" + resultSet.getString(1) + "</firstName>\n");
			writer.write("\t\t<lastName>" + resultSet.getString(2) + "</lastName>\n");
			writer.write("\t\t<phoneNumber>" + String.valueOf(resultSet.getLong(3)) + "</phoneNumber>\n");
			writer.write("\t</contact>\n");
		}

	}

	private ContactMapper mapper = new ContactMapper();
	private JdbcResultSetTranslatorExample jdbcResultSetMapperExample = new JdbcResultSetTranslatorExample();

	@AfterClass
	public static void after() {
		ContactDatabaseSetup.closeConnection();
	}
	
	@Test
	public void test() {

		String sql = "SELECT firstName, lastName, phoneNumber FROM contact";
		Connection connection = ContactDatabaseSetup.getConnection();
		StringWriter writer = new StringWriter();
		jdbcResultSetMapperExample.writeResultsToXml(connection, writer , mapper, sql);
		
		assertTrue(writer.toString().length() > 0);
	}

}

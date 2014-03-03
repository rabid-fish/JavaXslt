package com.github.rabid_fish.sqltoxml;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;
import org.junit.AfterClass;
import org.junit.Test;
import org.xml.sax.SAXException;

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
	public void testWriteResultsToXml() throws SAXException, IOException {

		String sql = "SELECT firstName, lastName, phoneNumber FROM contact";
		Connection connection = ContactDatabaseSetup.getConnection();
		StringWriter writer = new StringWriter();
		jdbcResultSetMapperExample.writeResultsToXml(connection, writer , mapper, sql);
		
		String result = writer.toString();
		assertTrue("Returned an empty string", result.length() > 0);
		
		InputStream xmlSkeletonFile = this.getClass().getResourceAsStream("/results/JdbcResultSetTranslatorResult.xml");
		String expected = IOUtils.toString(xmlSkeletonFile, "UTF-8");
		
		DifferenceListener listener = new IgnoreTextAndAttributeValuesDifferenceListener();
		Diff diff = new Diff(expected, result);
		diff.overrideDifferenceListener(listener);
		assertTrue("Returned xml does not have a skeleton structure that is expected", diff.similar());
	}

}

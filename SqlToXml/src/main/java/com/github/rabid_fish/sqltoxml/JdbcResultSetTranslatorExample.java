package com.github.rabid_fish.sqltoxml;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcResultSetTranslatorExample {

	public interface ResultSetRowMapper {
		public void map(ResultSet resultSet, Writer writer) throws IOException, SQLException;
	}
	
	public void writeResultsToXml(Connection connection, Writer writer, ResultSetRowMapper mapper, String sql) {
		
		try {
			writeResultsToXmlThrowsIOException(connection, writer, mapper, sql);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void writeResultsToXmlThrowsIOException(Connection connection, Writer writer, ResultSetRowMapper mapper, String sql) throws IOException {
		
		Exception exception = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		writer.write("<?xml version=\"1.0\"?>\n");
		writer.write("<root>\n");
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()) {
				mapper.map(resultSet, writer);
			}
			
		} catch (SQLException sqle) {
			exception = sqle;
			
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException sqle) {
					if (exception != null) {
						exception = sqle;
					}
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					if (exception != null) {
						exception = sqle;
					}
				}
			}
		}
		
		if (exception != null) {
			throw new RuntimeException(exception);
		}
		
		writer.write("</root>\n");
	}

}

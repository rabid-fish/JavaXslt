package com.github.rabid_fish.jdbc;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcResultSetTranslator {

	public interface ResultSetRowMapper {
		public void map(ResultSet resultSet, Writer writer) throws IOException, SQLException;
	}
	
	public void writeResultsToXml(Connection connection, Writer writer, ResultSetRowMapper rowMapper, String sql) {
		
		try {
			writeResultsToXmlThrowsIOException(connection, writer, rowMapper, sql);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void writeResultsToXmlThrowsIOException(Connection connection, Writer writer, ResultSetRowMapper rowMapper, String sql) throws IOException {
		
		Exception exception = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		writer.write("<?xml version=\"1.0\"?>\n");
		writer.write("<root>\n");
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()) {
				rowMapper.map(resultSet, writer);
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

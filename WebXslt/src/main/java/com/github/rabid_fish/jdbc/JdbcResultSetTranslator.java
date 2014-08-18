package com.github.rabid_fish.jdbc;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import com.github.rabid_fish.jaxb.JaxbListWrapper;
import com.github.rabid_fish.jaxb.JaxbUtil;

public class JdbcResultSetTranslator {

	public interface ResultSetRowMapper<T> {
		public T map(ResultSet resultSet) throws SQLException;
	}
	
	public <T> void writeResultsToXml(Connection connection, Writer writer, ResultSetRowMapper<T> rowMapper, String sql) {
		
		try {
			List<T> listOfElements = getListOfElementsViaJdbc(connection, rowMapper, sql);
			writeListToOutput(listOfElements, writer);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private <T> List<T> getListOfElementsViaJdbc(Connection connection, ResultSetRowMapper<T> rowMapper, String sql) throws IOException {
		
		List<T> list = new ArrayList<T>();
		
		Exception exception = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()) {
				T element = rowMapper.map(resultSet);
				list.add(element);
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
		
		return list;
	}

	private <T> void writeListToOutput(List<T> listOfElements, Writer writer) throws IOException, PropertyException, JAXBException {
		
		JaxbListWrapper listWrapper = new JaxbListWrapper(listOfElements);
		Marshaller marshaller = JaxbUtil.createMarshaller();
		
		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(listWrapper, stringWriter);
		System.out.println(stringWriter.toString());

		marshaller.marshal(listWrapper, writer);
	}
}

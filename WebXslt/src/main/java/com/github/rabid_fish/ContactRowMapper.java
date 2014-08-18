package com.github.rabid_fish;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.rabid_fish.jdbc.JdbcResultSetTranslator;

public class ContactRowMapper implements JdbcResultSetTranslator.ResultSetRowMapper<Contact> {

	@Override
	public Contact map(ResultSet resultSet) throws SQLException {
		
		Contact contact = new Contact(
			resultSet.getLong("id")
			, resultSet.getString("firstName")
			, resultSet.getString("lastName")
			, resultSet.getString("phoneNumber")
		);
		
		return contact;
	}

}

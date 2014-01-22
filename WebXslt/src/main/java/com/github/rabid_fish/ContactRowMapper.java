package com.github.rabid_fish;

import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.rabid_fish.jdbc.JdbcResultSetTranslator;

public class ContactRowMapper implements JdbcResultSetTranslator.ResultSetRowMapper {

	@Override
	public void map(ResultSet resultSet, Writer writer) throws IOException, SQLException {
		writer.write("\t<contact>\n");
		writer.write("\t\t<firstName>" + resultSet.getString(1) + "</firstName>\n");
		writer.write("\t\t<lastName>" + resultSet.getString(2) + "</lastName>\n");
		writer.write("\t\t<phoneNumber>" + String.valueOf(resultSet.getLong(3)) + "</phoneNumber>\n");
		writer.write("\t</contact>\n");
	}

}

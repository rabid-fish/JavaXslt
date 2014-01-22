package com.github.rabid_fish;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.rabid_fish.jdbc.JdbcResultSetTranslator;
import com.github.rabid_fish.xslt.XsltProcessor;

public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String STYLE_SHEET_PATH = "contact.xsl";

	private XsltProcessor xslt = new XsltProcessor();
	private JdbcResultSetTranslator translator = new JdbcResultSetTranslator();
	
	static {
		ContactDatabaseSetup.getConnection();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandleRequest(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandleRequest(request, response);
	}
	
	void doHandleRequest(HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/html");
		
		PrintWriter htmlOutput;
		try {
			htmlOutput = response.getWriter();
			
			StringWriter databaseXml = getDataFromDatabaseInXmlFormat();
			xslt.transformXml(STYLE_SHEET_PATH, databaseXml, htmlOutput);
			
			htmlOutput.close();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	private StringWriter getDataFromDatabaseInXmlFormat() {
		
		StringWriter xmlSourceWriter = new StringWriter();
		Connection connection = ContactDatabaseSetup.getConnection();
		ContactRowMapper rowMapper = new ContactRowMapper();
		String sql = "SELECT * FROM contact";
		translator.writeResultsToXml(connection, xmlSourceWriter, rowMapper, sql);
		
		return xmlSourceWriter;
	}
	
}

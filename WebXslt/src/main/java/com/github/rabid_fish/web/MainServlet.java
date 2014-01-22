package com.github.rabid_fish.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.rabid_fish.ContactDatabaseSetup;
import com.github.rabid_fish.jdbc.JdbcResultSetTranslator;
import com.github.rabid_fish.jdbc.JdbcResultSetTranslator.ResultSetRowMapper;
import com.github.rabid_fish.xslt.XsltProcessor;

public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String STYLE_SHEET_PATH = "example1/contact-list.xsl";

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

	private void doHandleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		RequestParametersCheck check = new RequestParametersCheck(request);
		if (check.requestParametersAreInvalid()) {
			response.sendRedirect("404.jsp");
			return;
		}
		
		doSendResponse(response, check.styleSheetPath, check.sql, check.rowMapper);
	}

	private void doSendResponse(HttpServletResponse response, String styleSheetPath, String sql, ResultSetRowMapper rowMapper) {
		
		try {
			response.setContentType("text/html");
			PrintWriter htmlOutput = response.getWriter();

			StringWriter databaseXml = getDataFromDatabaseInXmlFormat(rowMapper, sql);
			xslt.transformXml(styleSheetPath, databaseXml, htmlOutput);

			htmlOutput.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private StringWriter getDataFromDatabaseInXmlFormat(ResultSetRowMapper rowMapper, String sql) {

		StringWriter xmlSourceWriter = new StringWriter();
		Connection connection = ContactDatabaseSetup.getConnection();
		translator.writeResultsToXml(connection, xmlSourceWriter, rowMapper, sql);

		return xmlSourceWriter;
	}

}

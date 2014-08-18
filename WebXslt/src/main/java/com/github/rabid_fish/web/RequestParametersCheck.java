package com.github.rabid_fish.web;

import javax.servlet.http.HttpServletRequest;

import com.github.rabid_fish.Contact;
import com.github.rabid_fish.ContactRowMapper;
import com.github.rabid_fish.jdbc.JdbcResultSetTranslator.ResultSetRowMapper;

public class RequestParametersCheck {

	public ResultSetRowMapper<Contact> rowMapper;
	public String sql;
	public String styleSheetPath;
	
	public RequestParametersCheck(HttpServletRequest request) {
		
		String exampleString = request.getParameter("example");
		String actionString = request.getParameter("action");
		init(exampleString, actionString);
	}
	
	public RequestParametersCheck(String exampleString, String actionString) {
		
		init(exampleString, actionString);
	}
	
	private void init(String exampleString, String actionString) {

		ServletAction action = ServletAction.translateFromString(actionString);
		if (action == null) {
			return;
		}

		setStylesheetPath(action, exampleString);
		setSqlForAction(action);
		setRowMapperForAction(action);
	}
	
	public boolean requestParametersAreInvalid() {
		return (styleSheetPath == null || sql == null || rowMapper == null);
	}
	
	void setRowMapperForAction(ServletAction action) {

		switch (action) {
		case LIST:
		case UPDATE:
			rowMapper = new ContactRowMapper();
			break;
		default:
			rowMapper = null;
		}
	}

	void setSqlForAction(ServletAction action) {

		switch (action) {
		case LIST:
			sql = "SELECT * FROM contact";
			break;
		case UPDATE:
			sql = "SELECT * FROM contact WHERE firstName = 'John' AND lastName = 'Deer'";
			break;
		default:
			sql = null;
		}
	}

	void setStylesheetPath(ServletAction action, String example) {
		
		if ("1".equals(example)) {
			styleSheetPath = "view/contact-" + action.name().toLowerCase() + ".xsl";
		} else {
			styleSheetPath = null;
		}
	}


}

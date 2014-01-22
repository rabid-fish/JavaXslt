package com.github.rabid_fish.web;

import javax.servlet.http.HttpServletRequest;

import com.github.rabid_fish.ContactRowMapper;
import com.github.rabid_fish.jdbc.JdbcResultSetTranslator.ResultSetRowMapper;

public class RequestParametersCheck {

	public static final String STYLE_SHEET_PATH = "example1/contact-list.xsl";
	
	public ServletAction action;
	public ResultSetRowMapper rowMapper;
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

		setStylesheetPath(exampleString);
		setSqlForAction(action);
		setRowMapperForAction(action);
	}
	
	public boolean requestParametersAreInvalid() {
		return (styleSheetPath == null || sql == null || rowMapper == null);
	}
	
	void setRowMapperForAction(ServletAction action) {

		switch (action) {
		case LIST:
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
		default:
			sql = null;
		}
	}

	void setStylesheetPath(String example) {
		
		if ("1".equals(example)) {
			styleSheetPath = STYLE_SHEET_PATH;
		} else {
			styleSheetPath = null;
		}
	}


}

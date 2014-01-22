package com.github.rabid_fish.sqltoxml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.sql.SQLException;

import javax.sql.rowset.WebRowSet;

public class WebRowSetExample {

	public void writeResultsToXml(OutputStream out, String url, String sql) {

		/*
		 * DO NOT ANNOTATE THE LINE BELOW TO IGNORE THE WARNING.
		 * 
		 * Note that WebRowSetImpl is a proprietary Sun Microsystems provided
		 * class not available in other JRE's. If you are deploying to OpenJDK,
		 * you will need to use a different implementation of WebRowSetImpl.
		 */
		try {
			WebRowSet rowSet = new com.sun.rowset.WebRowSetImpl();
			rowSet.setUrl(url);
			rowSet.setCommand(sql);
			rowSet.execute();
			rowSet.writeXml(out);

		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
			
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public void writeResultsToXml(Writer writer, String url, String sql) {

		/*
		 * DO NOT ANNOTATE THE LINE BELOW TO IGNORE THE WARNING.
		 * 
		 * Note that WebRowSetImpl is a proprietary Sun Microsystems provided
		 * class not available in other JRE's. If you are deploying to OpenJDK,
		 * you will need to use a different implementation of WebRowSetImpl.
		 */

		try {
			WebRowSet rowSet = new com.sun.rowset.WebRowSetImpl();
			rowSet.setUrl(url);
			rowSet.setCommand(sql);
			rowSet.execute();
			rowSet.writeXml(writer);

		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}
}

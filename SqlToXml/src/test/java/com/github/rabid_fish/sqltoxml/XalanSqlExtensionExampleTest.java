package com.github.rabid_fish.sqltoxml;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;
import org.junit.Test;
import org.xml.sax.SAXException;

public class XalanSqlExtensionExampleTest {

	private static final String XSL_FILE_PATH = "src/test/resources/contact-sql.xsl";
	
	private XalanSqlExtensionExample xalanSqlExtensionExample = new XalanSqlExtensionExample();
	
	@Test
	public void testWriteResultsToXml() throws IOException, SAXException {
		
		OutputStream out = new ByteArrayOutputStream();
		xalanSqlExtensionExample.writeResultsToXml(out, XSL_FILE_PATH);
		
		String result = out.toString();
		assertTrue(result.length() > 0);
		assertTrue(result.contains("John"));
		assertTrue(result.contains("Deer"));
		
		InputStream xmlSkeletonFile = this.getClass().getResourceAsStream("/results/XalanSqlExtensionResult.xml");
		String expected = IOUtils.toString(xmlSkeletonFile, "UTF-8");
		
		DifferenceListener listener = new IgnoreTextAndAttributeValuesDifferenceListener();
		Diff diff = new Diff(expected, result);
		diff.overrideDifferenceListener(listener);
		assertTrue("Returned xml does not have a skeleton structure that is expected", diff.similar());
		
		System.out.println("***************");
		System.out.println("\nSample output of XalanSqlExtensionExample");
		System.out.println("***************");
		System.out.println(result);
		System.out.println("***************\n");
	}
}

package com.github.rabid_fish.sqltoxml;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;
import org.junit.Test;
import org.xml.sax.SAXException;

public class WebRowSetExampleTest {

	public static final String SAMPLE_URL = "jdbc:h2:~/test;USER=sa";
	public static final String SAMPLE_SQL = "SELECT * FROM contact";

	private WebRowSetExample webRowSetExample = new WebRowSetExample();
	
	@Test
	public void testWriteResultsToXmlWithWriter() throws IOException, SAXException {
		
		StringWriter writer = new StringWriter();
		webRowSetExample.writeResultsToXml(writer, SAMPLE_URL, SAMPLE_SQL);
		String result = writer.toString();
		assertTrue(result.length() > 0);
		
		InputStream xmlSkeletonFile = this.getClass().getResourceAsStream("/results/WebRowSetResult.xml");
		String expected = IOUtils.toString(xmlSkeletonFile, "UTF-8");
		
		DifferenceListener listener = new IgnoreTextAndAttributeValuesDifferenceListener();
		Diff diff = new Diff(expected, result);
		diff.overrideDifferenceListener(listener);
		assertTrue("Returned xml does not have a skeleton structure that is expected", diff.similar());
	}
	
	@Test
	public void testWriteResultsToXmlWithOutputStream() {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		webRowSetExample.writeResultsToXml(out, SAMPLE_URL, SAMPLE_SQL);
		assertTrue(out.toString().length() > 0);
		assertTrue(out.toString().contains("John"));
		assertTrue(out.toString().contains("Deer"));
		
		System.out.println("***************");
		System.out.println("\nSample output of WebRowSetExample");
		System.out.println("***************");
		System.out.println(out.toString());
		System.out.println("***************\n");
	}
}

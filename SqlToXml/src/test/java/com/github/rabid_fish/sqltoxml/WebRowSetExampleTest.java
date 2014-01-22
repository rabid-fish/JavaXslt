package com.github.rabid_fish.sqltoxml;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

import org.junit.Test;

public class WebRowSetExampleTest {

	public static final String SAMPLE_URL = "jdbc:h2:~/test;USER=sa";
	public static final String SAMPLE_SQL = "SELECT * FROM contact";

	private WebRowSetExample webRowSetExample = new WebRowSetExample();
	
	@Test
	public void testWriteResultsToXmlWithWriter() {
		
		StringWriter writer = new StringWriter();
		webRowSetExample.writeResultsToXml(writer, SAMPLE_URL, SAMPLE_SQL);
		assertTrue(writer.toString().length() > 0);
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

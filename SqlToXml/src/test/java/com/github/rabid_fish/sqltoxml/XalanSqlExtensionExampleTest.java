package com.github.rabid_fish.sqltoxml;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.Test;

public class XalanSqlExtensionExampleTest {

	private static final String XSL_FILE_PATH = "src/test/resources/contact-sql.xsl";
	
	private XalanSqlExtensionExample xalanSqlExtensionExample = new XalanSqlExtensionExample();
	
	@Test
	public void testWriteResultsToXml() {
		
		OutputStream out = new ByteArrayOutputStream();
		xalanSqlExtensionExample.writeResultsToXml(out, XSL_FILE_PATH);
		
		assertTrue(out.toString().length() > 0);
		assertTrue(out.toString().contains("John"));
		assertTrue(out.toString().contains("Deer"));
		
		System.out.println("***************");
		System.out.println("\nSample output of XalanSqlExtensionExample");
		System.out.println("***************");
		System.out.println(out.toString());
		System.out.println("***************\n");
	}
}

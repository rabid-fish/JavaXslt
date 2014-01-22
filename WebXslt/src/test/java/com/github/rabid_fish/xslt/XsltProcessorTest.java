package com.github.rabid_fish.xslt;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.Transformer;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import com.github.rabid_fish.MainServlet;

public class XsltProcessorTest {

	public static final String DATA_FILE_PATH = "contact.xml";
	
	XsltProcessor contactXslt;
	StringWriter xmlInputWriter;
	InputStream xmlInputStream;
	InputStream styleSheetStream;

	@Before
	public void setUp() throws IOException {
		
		contactXslt = new XsltProcessor();
		xmlInputWriter = new StringWriter();
		xmlInputStream = this.getClass().getClassLoader().getResourceAsStream(DATA_FILE_PATH);
		styleSheetStream = this.getClass().getClassLoader().getResourceAsStream(MainServlet.STYLE_SHEET_PATH);
		
		InputStream buffer = this.getClass().getClassLoader().getResourceAsStream(DATA_FILE_PATH);
		IOUtils.copy(buffer, xmlInputWriter);
	}
	
	@Test
	public void testTransformXmlWithXmlInputWriterAndHtmlOutputStream() {
		
		ByteArrayOutputStream htmlOutputStream = new ByteArrayOutputStream();
		contactXslt.transformXml(MainServlet.STYLE_SHEET_PATH, xmlInputWriter, htmlOutputStream);
		assertTrue(htmlOutputStream.toString().length() > 0);
	}

	@Test
	public void testTransformXmlWithXmlInputWriterAndHtmlOutputWriter() {
		
		StringWriter htmlOutputWriter = new StringWriter();
		contactXslt.transformXml(MainServlet.STYLE_SHEET_PATH, xmlInputWriter, htmlOutputWriter);
		assertTrue(htmlOutputWriter.toString().length() > 0);
	}
	
	@Test
	public void testTransformXmlWithXmlInputStreamAndHtmlOutputStream() {
		
		ByteArrayOutputStream htmlOutputStream = new ByteArrayOutputStream();
		contactXslt.transformXml(MainServlet.STYLE_SHEET_PATH, xmlInputStream, htmlOutputStream);
		assertTrue(htmlOutputStream.toString().length() > 0);
	}
	
	@Test
	public void testTransformXmlWithXmlInputStreamAndHtmlOutputWriter() {
		
		StringWriter htmlOutputWriter = new StringWriter();
		contactXslt.transformXml(MainServlet.STYLE_SHEET_PATH, xmlInputStream, htmlOutputWriter);
		assertTrue(htmlOutputWriter.toString().length() > 0);
	}
	
	@Test
	public void testCreateTransformer() {
		
		Transformer transformer = contactXslt.createTransformer(styleSheetStream);
		assertNotNull(transformer);
	}
	
	@Test
	public void testBuildDocumentFromInputStream() {
		
		Document document = contactXslt.buildDocumentFromInputStream(xmlInputStream);
		assertTrue(document.getChildNodes().getLength() > 0);
	}

}

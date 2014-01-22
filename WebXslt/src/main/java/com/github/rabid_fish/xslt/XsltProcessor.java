package com.github.rabid_fish.xslt;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

public class XsltProcessor {

	public void transformXml(String styleSheetPath, Writer xmlInputWriter, OutputStream xmlOutputStream) {

		InputStream styleSheetStream = this.getClass().getClassLoader().getResourceAsStream(styleSheetPath);
		InputStream xmlInputStream = writerToInputStream(xmlInputWriter);
		transformXmlUsingXslt(styleSheetStream, xmlInputStream, xmlOutputStream);
	}

	public void transformXml(String styleSheetPath, Writer xmlInputWriter, Writer xmlOutputWriter) {

		InputStream styleSheetStream = this.getClass().getClassLoader().getResourceAsStream(styleSheetPath);
		InputStream xmlInputStream = writerToInputStream(xmlInputWriter);
		transformXmlUsingXslt(styleSheetStream, xmlInputStream, xmlOutputWriter);
	}

	public void transformXml(String styleSheetPath, InputStream xmlInputStream, OutputStream xmlOutputStream) {

		InputStream styleSheetStream = this.getClass().getClassLoader().getResourceAsStream(styleSheetPath);
		transformXmlUsingXslt(styleSheetStream, xmlInputStream, xmlOutputStream);
	}

	public void transformXml(String styleSheetPath, InputStream xmlInputStream, Writer xmlOutputWriter) {

		InputStream styleSheetStream = this.getClass().getClassLoader().getResourceAsStream(styleSheetPath);
		transformXmlUsingXslt(styleSheetStream, xmlInputStream, xmlOutputWriter);
	}

	InputStream writerToInputStream(Writer xmlInputWriter) {
		
		byte[] xmlInputBytes = xmlInputWriter.toString().getBytes();
		InputStream xmlInputStream = new ByteArrayInputStream(xmlInputBytes);
		
		return xmlInputStream;
	}

	void transformXmlUsingXslt(InputStream styleSheetStream, InputStream xmlInputStream, OutputStream xmlOutputStream) {

		try {
			Document dataDocument = buildDocumentFromInputStream(xmlInputStream);
			DOMSource dataDomSource = new DOMSource(dataDocument);

			Transformer transformer = createTransformer(styleSheetStream);

			StreamResult result = new StreamResult(xmlOutputStream);
			transformer.transform(dataDomSource, result);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	void transformXmlUsingXslt(InputStream styleSheetStream, InputStream xmlInputStream, Writer xmlOutputWriter) {

		try {
			Document dataDocument = buildDocumentFromInputStream(xmlInputStream);
			DOMSource dataDomSource = new DOMSource(dataDocument);

			Transformer transformer = createTransformer(styleSheetStream);

			StreamResult result = new StreamResult(xmlOutputWriter);
			transformer.transform(dataDomSource, result);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	Transformer createTransformer(InputStream styleSheetStream) {

		Transformer transformer = null;

		try {
			StreamSource stylesource = new StreamSource(styleSheetStream);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer(stylesource);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return transformer;
	}

	Document buildDocumentFromInputStream(InputStream xmlInputStream) {

		Document dataDocument = null;

		try {
			DocumentBuilderFactory dataDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			dataDocumentBuilderFactory.setNamespaceAware(true);
			dataDocumentBuilderFactory.setValidating(true);

			DocumentBuilder dataDocumentBuilder = dataDocumentBuilderFactory.newDocumentBuilder();
			dataDocument = dataDocumentBuilder.parse(xmlInputStream);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return dataDocument;
	}

}

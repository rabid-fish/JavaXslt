package com.github.rabid_fish.sqltoxml;

import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.xml.utils.DefaultErrorHandler;

/**
 * This class is named after the xalan sql extension that is pulled in by the
 * file contact-sql.xsl. That file contaains database connection info and table
 * query sql, and xalan pulls data based on that, then pushes the data through a
 * generic jdbc xslt processor.
 */
public class XalanSqlExtensionExample {

	public void writeResultsToXml(OutputStream out, String xslFilePath) {

		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			DefaultErrorHandler errorHandler = new DefaultErrorHandler(false);
			transformerFactory.setErrorListener(errorHandler);

			StreamSource xslFileStream = new StreamSource(xslFilePath);
			Templates xslTemplate = transformerFactory.newTemplates(xslFileStream);

			Transformer transformer = xslTemplate.newTransformer();
			transformer.setErrorListener(errorHandler);

			org.apache.xalan.transformer.TransformerImpl tranformerImpl = (org.apache.xalan.transformer.TransformerImpl) transformer;
			tranformerImpl.setQuietConflictWarnings(false);

			StringReader emptyXmlReader = new StringReader("<?xml version=\"1.0\"?> <doc/>");
			StreamSource emptyXmlStream = new StreamSource(emptyXmlReader);

			StreamResult streamResult = new StreamResult(out);
			transformer.transform(emptyXmlStream, streamResult);

		} catch (TransformerException te) {
			throw new RuntimeException(te);
		}
	}

}

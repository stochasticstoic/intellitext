package com.tecacet.text.search;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;

/**
 * A DocumentFactory implementation that delegates responsibility to appropriate
 * DocumentHandler implementation, based on a file extension.
 */
public class ExtensionDocumentFactory implements DocumentFactory {

	private final ExtensionDocumentHandlerFactory handlerFactory;

	public ExtensionDocumentFactory(String propertiesFile) throws IOException,
			DocumentHandlerException {
		handlerFactory = new ExtensionDocumentHandlerFactory(propertiesFile);
	}
	
	@SuppressWarnings("rawtypes")
	public ExtensionDocumentFactory(Map properties) throws DocumentHandlerException {
		handlerFactory = new ExtensionDocumentHandlerFactory(properties);
	}
	
	public Document getDocument(File file) throws DocumentHandlerException {

		// make a new, empty document
		Document document = new Document();

		// Add the path of the file as a field named "path". Use a field that is
		// indexed but don't tokenize the field into words.
		Field pathField = new StringField(IndexConstants.PATH_FIELD, file.getPath(),
				Field.Store.YES);
		document.add(pathField);

		// Add the last modified date of the file a field named "modified". Use
		// a field that is indexed but don't tokenize the field into words.
		document.add(new LongField(IndexConstants.MODIFIED_FIELD, file.lastModified(),
				Field.Store.NO));

		DocumentHandler handler = handlerFactory.getHandlerForFile(file);
		handler.index(document, file);
		return document;
	}

}

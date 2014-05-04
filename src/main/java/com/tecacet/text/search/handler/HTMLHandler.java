package com.tecacet.text.search.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import com.tecacet.text.AbstractHTMLTextExtractor;
import com.tecacet.text.JTidyHTMLTextExtractor;
import com.tecacet.text.search.DocumentHandler;
import com.tecacet.text.search.DocumentHandlerException;
import com.tecacet.text.search.IndexConstants;

public class HTMLHandler implements DocumentHandler {

	private final AbstractHTMLTextExtractor textExtractor;

	public HTMLHandler(AbstractHTMLTextExtractor textExtractor) {
		super();
		this.textExtractor = textExtractor;
	}

	public HTMLHandler() {
		this(new JTidyHTMLTextExtractor());
	}

	public void index(Document doc, File file) throws DocumentHandlerException {

		try {
			FileInputStream fis = new FileInputStream(file);
			String body = textExtractor.getText(fis);
			String title = textExtractor.getTitle();
			if ((title != null) && (!title.equals(""))) {
				doc.add(new TextField("title", title, Field.Store.YES));
			}
			if ((body != null) && (!body.equals(""))) {
				doc.add(new TextField(IndexConstants.CONTENTS_FIELD, body,
						Field.Store.NO));
			}
			fis.close();
		} catch (IOException ioe) {
			throw new DocumentHandlerException(ioe);
		}
	}
}
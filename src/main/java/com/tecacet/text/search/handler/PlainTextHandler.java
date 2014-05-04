package com.tecacet.text.search.handler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;

import com.tecacet.text.search.DocumentHandler;
import com.tecacet.text.search.DocumentHandlerException;
import com.tecacet.text.search.IndexConstants;

/** 
 * A utility for making Lucene Documents from a plain text File. 
 */
public class PlainTextHandler implements DocumentHandler {

	/**
	 * Makes a document for a File.
	 * <p>
	 * The document has three fields:
	 * <ul>
	 * <li><code>path</code>--containing the pathname of the file, as a stored,
	 * untokenized field;
	 * <li><code>modified</code>--containing the last modified date of the file
	 * as a field as created by <a
	 * href="lucene.document.DateTools.html">DateTools</a>; and
	 * <li><code>contents</code>--containing the full contents of the file, as a
	 * Reader field;
	 */
	public void index(Document doc, File f) throws DocumentHandlerException {

		// Add the contents of the file to a field named "contents". Specify a
		// Reader,
		// so that the text of the file is tokenized and indexed, but not
		// stored.
		// Note that FileReader expects the file to be in the system's default
		// encoding.
		// If that's not the case searching for special characters will fail.
		try {
			FileReader fileReader = new FileReader(f);
			doc.add(new TextField(IndexConstants.CONTENTS_FIELD, fileReader));
		} catch (IOException e) {
			throw new DocumentHandlerException(e);
		}
	}

}

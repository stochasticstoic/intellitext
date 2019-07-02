package com.tecacet.text.search.handler;

import com.tecacet.text.TextExtractor;
import com.tecacet.text.search.DocumentHandler;
import com.tecacet.text.search.DocumentHandlerException;
import com.tecacet.text.search.IndexConstants;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import java.io.File;
import java.io.FileInputStream;

public class BaseDocumentHandler implements DocumentHandler {

    protected final TextExtractor textExtractor;

    public BaseDocumentHandler(TextExtractor textExtractor) {
        super();
        this.textExtractor = textExtractor;
    }

    @Override
    public void index(Document doc, File file) throws DocumentHandlerException {
        try {
            FileInputStream fis = new FileInputStream(file);
            String text = textExtractor.getText(fis);
            if (text.length() > 0) {
                doc.add(new TextField(IndexConstants.CONTENTS_FIELD, text, Field.Store.NO));
            }
            fis.close();
        } catch (Exception e) {
            throw new DocumentHandlerException(e);
        }
    }
}

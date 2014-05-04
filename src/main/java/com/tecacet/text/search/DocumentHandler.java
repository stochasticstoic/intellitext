package com.tecacet.text.search;

import java.io.File;

import org.apache.lucene.document.Document;

public interface DocumentHandler {

    /**
     * Creates a Lucene Document from an InputStream. This method can return <code>null</code>.
     * 
     * @param document
     * @param file
     */
    void index(Document document, File file) throws DocumentHandlerException;
}

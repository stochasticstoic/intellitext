package com.tecacet.text.search.handler;

import com.tecacet.text.search.DocumentHandler;
import com.tecacet.text.search.DocumentHandlerException;
import com.tecacet.text.search.IndexConstants;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

//TODO use TextExtractor
public class PDFBoxPDFHandler implements DocumentHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void index(Document doc, File file) throws DocumentHandlerException {

        COSDocument cosDoc = null;
        try {
            cosDoc = parseDocument(new FileInputStream(file));
        } catch (IOException e) {
            closeCOSDocument(cosDoc);
            throw new DocumentHandlerException("Cannot parse PDF document", e);
        }

        // extract PDF document's textual content
        String docText = null;
        try {
            PDFTextStripper stripper = new PDFTextStripper();
            docText = stripper.getText(new PDDocument(cosDoc));
        } catch (IOException e) {
            throw new DocumentHandlerException("Cannot parse PDF document", e);
        }

        if (docText != null) {
            doc.add(new TextField(IndexConstants.CONTENTS_FIELD, docText, Field.Store.YES));
        }

        // extract PDF document's meta-data
        indexMetadata(cosDoc, doc);

        closeCOSDocument(cosDoc);

    }

    private void indexMetadata(COSDocument cosDoc, Document doc) {

        PDDocument pdDoc = null;
        try {
            pdDoc = new PDDocument(cosDoc);
            PDDocumentInformation docInfo = pdDoc.getDocumentInformation();
            String author = docInfo.getAuthor();
            String title = docInfo.getTitle();
            String keywords = docInfo.getKeywords();
            String summary = docInfo.getSubject();
            // docInfo.
            if ((author != null) && (!author.equals(""))) {
                doc.add(new TextField("author", author, Field.Store.YES));
            }
            if ((title != null) && (!title.equals(""))) {
                doc.add(new TextField("title", title, Field.Store.YES));
            }
            if ((keywords != null) && (!keywords.equals(""))) {
                doc.add(new TextField("keywords", keywords, Field.Store.YES));
            }
            if ((summary != null) && (!summary.equals(""))) {
                doc.add(new TextField("summary", summary, Field.Store.YES));
            }
        } catch (Exception e) {
            closeCOSDocument(cosDoc);
            closePDDocument(pdDoc);
            System.err.println("Cannot get PDF document meta-data: " + e.getMessage());
        }
    }

    private static COSDocument parseDocument(InputStream is) throws IOException {
        PDFParser parser = new PDFParser(is);
        parser.parse();
        return parser.getDocument();
    }

    private void closeCOSDocument(COSDocument cosDoc) {
        if (cosDoc != null) {
            try {
                cosDoc.close();
            } catch (IOException e) {
                // eat it, what else can we do?
                logger.warn("Failed to close pdf document.", e);
            }
        }
    }

    private void closePDDocument(PDDocument pdDoc) {
        if (pdDoc != null) {
            try {
                pdDoc.close();
            } catch (IOException e) {
                // eat it, what else can we do?
            }
        }
    }

}

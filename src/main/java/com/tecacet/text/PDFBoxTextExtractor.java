package com.tecacet.text;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Parses PDF using PDFBox
 *
 * @author dimitri
 */
public class PDFBoxTextExtractor implements TextExtractor {

    @Override
    public String getText(InputStream is) throws IOException {
        COSDocument cosDoc = parseDocument(is);
        try {
            PDFTextStripper stripper = new PDFTextStripper();
            PDDocument pdfDocument = new PDDocument(cosDoc);
            return stripper.getText(pdfDocument);

        } finally {
            cosDoc.close();
        }
    }

    private static COSDocument parseDocument(InputStream is) throws IOException {
        PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(is));
        parser.parse();
        return parser.getDocument();
    }

}

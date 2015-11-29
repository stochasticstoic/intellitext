package com.tecacet.text;

import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * Parses PDF using PDFBox
 * 
 * @author dimitri
 *
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
		PDFParser parser = new PDFParser(is);
		parser.parse();
		return parser.getDocument();
	}

}

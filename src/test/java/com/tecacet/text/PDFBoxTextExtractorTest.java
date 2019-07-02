package com.tecacet.text;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class PDFBoxTextExtractorTest {

    @Test
    public void testGetText() throws IOException {

        String[] words = new String[] {"network", "probability", "classifier", "PCA"};
        PDFBoxTextExtractor textExtractor = new PDFBoxTextExtractor();
        FileInputStream fis = new FileInputStream("testfiles/pdf/IntroMLBook.pdf");
        String text = textExtractor.getText(fis);
        for (String word : words) {
            assertTrue(word, text.contains(word));
        }
        fis.close();
    }

}

package com.tecacet.text;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

public class JTidyHTMLTextExtractorTest {

    @Test
    public void testGetText() throws IOException {
        String[] words = new String[] { "craigslist", "bolivia", "greece", "classified", "household", "jobs", "fraud" };

        JTidyHTMLTextExtractor textExtractor = new JTidyHTMLTextExtractor();
        FileInputStream fis = new FileInputStream("testfiles/html/sfbay.craigslist.org.htm");
        String text = textExtractor.getText(fis);
        
        fis.close();

        for (String word : words) {
            assertTrue(text.contains(word));
        }
    }

}

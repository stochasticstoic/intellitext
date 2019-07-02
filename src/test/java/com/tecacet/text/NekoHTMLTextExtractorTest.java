package com.tecacet.text;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class NekoHTMLTextExtractorTest {

    @Test
    public void testGetText() throws IOException {
        String[] words = new String[] {"craigslist", "bolivia", "greece", "classified", "household", "work", "fraud"};

        NekoHTMLTextExtractor textExtractor = new NekoHTMLTextExtractor();
        FileInputStream fis = new FileInputStream("testfiles/html/sfbay.craigslist.org.htm");
        String text = textExtractor.getText(fis);

        fis.close();

        for (String word : words) {
            assertTrue(text.contains(word));
        }
    }

}

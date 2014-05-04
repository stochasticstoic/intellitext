package com.tecacet.text;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

public class JavaBuiltInRTFTextExtractorTest {

    @Test
    public void testGetText() throws IOException {
        TextExtractor textExtractor = new JavaBuiltInRTFTextExtractor();
        FileInputStream fis = new FileInputStream("testfiles/document.rtf");
        String text = textExtractor.getText(fis);
        assertEquals("There is TEXT and then there is text, and it has ALL sorts of styles.", text.trim());
        fis.close();
    }

}

package com.tecacet.text.lucene;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.tecacet.text.CountingTokenizerCallback;
import com.tecacet.text.TextTokenizer;


public class LuceneTextTokenizerTest {

    @Test
    public void testTokenize() throws IOException {
        String text = "We have mahi-mahi, yellowfin tuna, and Atlantic Trout!";
        TextTokenizer textTokenizer = new LuceneTextTokenizer();
        List<String> tokens = textTokenizer.tokenize(text);
        assertEquals("[we, have, mahi, mahi, yellowfin, tuna, atlantic, trout]", tokens.toString());
    }
    
    @Test
    public void testCountingTokenizer() throws IOException {
        String text = "Row, row, row your boat, merilly down the stream. Merilly, merilly, merilly...";
        CountingTokenizerCallback callback = new CountingTokenizerCallback();
        TextTokenizer textTokenizer = new LuceneTextTokenizer(callback);
       
        List<String> tokens = textTokenizer.tokenize(text);
        assertEquals(11, tokens.size());
        assertEquals("{stream=1, merilly=4, your=1, down=1, boat=1, row=3}",
                callback.getWordCount().toString());
        
    }
    
}

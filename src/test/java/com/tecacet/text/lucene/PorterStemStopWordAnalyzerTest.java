package com.tecacet.text.lucene;

import static org.junit.Assert.assertEquals;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class PorterStemStopWordAnalyzerTest {

    @Test
    public void testPorterStemStopWordAnalyzer() throws IOException {
        String[] expected = {"collect", "intellig", "smart", "action", "web", "2.0"};

        List<String> words = Files.asCharSource(new File("testfiles/stopwords.txt"), Charsets.UTF_8).readLines();
        PorterStemStopWordAnalyzer analyzer = new PorterStemStopWordAnalyzer(words);
        analyzer.addSynonym("intelligence", "smartness");
        analyzer.addSynonym("collective intelligence", "ci");

        String text = "Collective Intelligence in Action and Web 2.0";
        Reader reader = new StringReader(text);

        TokenStream tokenStream = analyzer.tokenStream(null, reader);

        CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
        OffsetAttribute offsetAttribute = tokenStream.getAttribute(OffsetAttribute.class);

        int i = 0;
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            String term = termAttribute.toString();
            int startOffset = offsetAttribute.startOffset();
            int endOffset = offsetAttribute.endOffset();
            System.out.println(text.substring(startOffset, endOffset));
            assertEquals(expected[i++], term);
        }
        tokenStream.end();
        tokenStream.close();
        analyzer.close();
    }

}

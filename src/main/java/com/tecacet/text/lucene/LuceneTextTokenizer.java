package com.tecacet.text.lucene;

import com.tecacet.text.TextTokenizer;
import com.tecacet.text.TokenizerCallback;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class LuceneTextTokenizer implements TextTokenizer, LuceneVersion {
    private final Analyzer analyzer;
    private TokenizerCallback callback = null;

    public LuceneTextTokenizer() {
        this(new StandardAnalyzer(LUCENE_VERSION));
    }

    public LuceneTextTokenizer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public LuceneTextTokenizer(TokenizerCallback callback) {
        this();
        this.callback = callback;
    }

    @Override
    public List<String> tokenize(String text) throws IOException {
        List<String> tokens = new ArrayList<String>();
        Reader reader = new StringReader(text);
        TokenStream tokenStream = analyzer.tokenStream(null, reader);

        try {
            for (tokenStream.reset(); tokenStream.incrementToken(); ) {
                CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
                tokens.add(token.toString());
                if (callback != null) {
                    callback.addToken(token.toString());
                }
            }
            tokenStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e); //TODO
        }
        return tokens;
    }

}

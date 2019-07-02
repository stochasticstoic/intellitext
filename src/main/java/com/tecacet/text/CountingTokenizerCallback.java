package com.tecacet.text;

import java.util.Map;
import java.util.TreeMap;

public class CountingTokenizerCallback implements TokenizerCallback {

    private final Map<String, Integer> wordCount = new TreeMap<>();

    @Override
    public void addToken(String token) {
        Integer count = wordCount.get(token);
        if (count == null) {
            count = 0;
        }
        wordCount.put(token, ++count);
    }

    public Map<String, Integer> getWordCount() {
        return wordCount;
    }

}

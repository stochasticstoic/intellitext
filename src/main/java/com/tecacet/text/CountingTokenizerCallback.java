package com.tecacet.text;

import java.util.HashMap;
import java.util.Map;

public class CountingTokenizerCallback implements TokenizerCallback {

	 private final Map<String, Integer> wordCount = new HashMap<String, Integer>();
	
	@Override
	public void addToken(String token) {
		Integer count = wordCount.get(token);
        if (count == null) {
            count = new Integer(0);
        }
        wordCount.put(token, ++count);
	}

    public Map<String, Integer> getWordCount() {
        return wordCount;
    }
	
}

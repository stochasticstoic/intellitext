package com.tecacet.text;

import java.util.ArrayList;
import java.util.List;

public class SimpleTextTokenizer implements TextTokenizer {

    public List<String> tokenize(String text) {
        String[] words = text.split("\\W+");
        List<String> wordList = new ArrayList<>();
        for (String word : words) {
            wordList.add(word.toLowerCase());
        }
        return wordList;
    }
}

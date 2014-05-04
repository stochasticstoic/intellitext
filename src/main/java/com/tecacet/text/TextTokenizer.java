package com.tecacet.text;

import java.io.IOException;
import java.util.List;

public interface TextTokenizer {

    /**
     * Break a piece of text into tokens
     * 
     * @param text
     * @return
     * @throws IOException 
     */
    List<String> tokenize(String text) throws IOException;

}
package com.tecacet.text;

import java.io.IOException;
import java.io.InputStream;

/**
 * Converts an Input Stream to text
 *
 * @author dimitri
 */
public interface TextExtractor {

    String getText(InputStream is) throws IOException;
}

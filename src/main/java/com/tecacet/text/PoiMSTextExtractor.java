package com.tecacet.text;


import org.apache.poi.extractor.POITextExtractor;
import org.apache.poi.ooxml.extractor.ExtractorFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Parses all MS formats using POI
 *
 * @author dimitri
 */
public class PoiMSTextExtractor implements TextExtractor {

    @Override
    public String getText(InputStream is) throws IOException {
        POITextExtractor poiExtractor;
        try {
            poiExtractor = ExtractorFactory.createExtractor(is);
        } catch (Exception e) {
            throw new IOException(e);
        }
        return poiExtractor.getText();
    }

}

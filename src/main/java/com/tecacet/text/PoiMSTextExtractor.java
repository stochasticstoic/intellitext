package com.tecacet.text;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;

/**
 * Parses all MS formats using POI
 * 
 * @author dimitri
 * 
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

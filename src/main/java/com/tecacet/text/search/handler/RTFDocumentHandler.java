package com.tecacet.text.search.handler;

import com.tecacet.text.JavaBuiltInRTFTextExtractor;

public class RTFDocumentHandler extends BaseDocumentHandler {

	public RTFDocumentHandler() {
		super(new JavaBuiltInRTFTextExtractor());
	}

}

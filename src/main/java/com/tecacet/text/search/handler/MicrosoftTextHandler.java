package com.tecacet.text.search.handler;

import com.tecacet.text.PoiMSTextExtractor;

public class MicrosoftTextHandler extends BaseDocumentHandler {

	public MicrosoftTextHandler() {
		super(new PoiMSTextExtractor());
	}

}

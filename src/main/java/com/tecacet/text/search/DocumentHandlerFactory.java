package com.tecacet.text.search;

import java.io.File;

public interface DocumentHandlerFactory {

    /**
     * Factory to provide a DocumentHandler for a given file
     *
     * @param file
     * @return
     * @throws DocumentHandlerException
     */
    DocumentHandler getHandlerForFile(File file) throws DocumentHandlerException;

}

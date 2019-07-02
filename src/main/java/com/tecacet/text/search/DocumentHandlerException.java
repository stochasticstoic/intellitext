package com.tecacet.text.search;

public class DocumentHandlerException extends Exception {

    private static final long serialVersionUID = 5350734055104088603L;

    /**
     * Constructs with message.
     */
    public DocumentHandlerException(String message) {
        super(message);
    }

    /**
     * Constructs with chained exception.
     */
    public DocumentHandlerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs with message and exception.
     */
    public DocumentHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

}

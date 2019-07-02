package com.tecacet.text;

public abstract class AbstractHTMLTextExtractor implements TextExtractor {

    private String title;

    protected void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

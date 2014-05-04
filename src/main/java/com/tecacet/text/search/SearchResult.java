package com.tecacet.text.search;

public class SearchResult {

    private final String path;
    private final float score;

    public SearchResult(String path, float score) {
        super();
        this.path = path;
        this.score = score;
    }

    public String getPath() {
        return path;
    }

    public float getScore() {
        return score;
    }

}

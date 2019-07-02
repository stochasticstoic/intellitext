package com.tecacet.text.search;

import org.apache.lucene.search.Explanation;

public class LuceneUtil {

    private LuceneUtil() {

    }

    public static void navigateExplanation(Explanation explanation) {
        // System.out.println(explanation.getDescription() + ": " +
        // explanation.getValue());
        Explanation[] explanations = explanation.getDetails();
        if (explanations != null) {
            for (Explanation e : explanations) {
                navigateExplanation(e);
            }
        }
    }
}

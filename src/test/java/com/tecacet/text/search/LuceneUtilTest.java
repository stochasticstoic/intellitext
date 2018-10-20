package com.tecacet.text.search;

import com.tecacet.text.lucene.LuceneVersion;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

@Ignore
public class LuceneUtilTest {

    @Test
    public void testNavigateExplanation() throws Exception {
        File indexDir = new File("./index");
        String queryExpression = "Visitor";

        FSDirectory directory = FSDirectory.open(indexDir);
        QueryParser parser = new QueryParser(LuceneVersion.LUCENE_VERSION,
                IndexConstants.CONTENTS_FIELD, new SimpleAnalyzer(LuceneVersion.LUCENE_VERSION));
        Query query = parser.parse(queryExpression);

        System.out.println("Query: " + queryExpression);

        // the second argument denotes that this is a read-only searcher

        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs hits = searcher.search(query, null, 100);
        ScoreDoc[] scoreDocs = hits.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            Explanation explanation = searcher.explain(query, scoreDoc.doc);
            LuceneUtil.navigateExplanation(explanation);
        }
    }

}

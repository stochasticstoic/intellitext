package com.tecacet.text.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class LuceneSearcher {

    private static final Version LUCENE_VERSION = Version.LUCENE_47;

    private static final int DEFAULT_MAX_RESULTS = 100;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final Analyzer analyzer = new StandardAnalyzer(LUCENE_VERSION);

    private int maxResults = DEFAULT_MAX_RESULTS;

    private final File indexDirectory;

    public LuceneSearcher(File indexDirectory) {
        super();
        this.indexDirectory = indexDirectory;
    }

    public TopDocs getTopHits(String queryString) throws IOException, ParseException {
        FSDirectory fsDir = FSDirectory.open(indexDirectory);
        IndexReader reader = DirectoryReader.open(fsDir);
        IndexSearcher is = new IndexSearcher(reader); // read only searher
        TopDocs hits = search(queryString, is);

        reader.close();
        return hits;
    }

    public TopDocs search(String queryString, IndexSearcher is) throws IOException, ParseException {
        QueryParser parser = new QueryParser(LUCENE_VERSION, IndexConstants.CONTENTS_FIELD, analyzer);
        Query query = parser.parse(queryString);
        long start = System.currentTimeMillis();
        TopDocs hits = is.search(query, null, maxResults); // 100 results
        long time = System.currentTimeMillis() - start;
        log.info("Found " + hits.scoreDocs.length + " document(s) (in " + time + " milliseconds) that matched query '" + queryString + "':");
        return hits;
    }

    public Document[] search(String queryString) throws IOException, ParseException {
        Directory fsDir = FSDirectory.open(indexDirectory);
        IndexReader reader = DirectoryReader.open(fsDir);
        IndexSearcher is = new IndexSearcher(reader); // read only searher
        TopDocs hits = search(queryString, is);
        Document[] docs = new Document[hits.scoreDocs.length];
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            docs[i] = is.doc(hits.scoreDocs[i].doc);
            //            		is.doc(hits.scoreDocs[i].doc, new FieldSelector() {
            //
            //                @Override
            //                public FieldSelectorResult accept(String fieldName) {
            //                    if (IndexConstants.MODIFIED_FIELD.equals(fieldName) ||
            //                            IndexConstants.PATH_FIELD.equals(fieldName)) {
            //                        return FieldSelectorResult.LOAD;
            //                    } else {
            //                        return FieldSelectorResult.LAZY_LOAD;
            //                    }
            //
            //                }
            //            });
        }

        reader.close();
        return docs;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

}

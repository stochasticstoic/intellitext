package com.tecacet.text.search;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import org.apache.lucene.document.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.Resources;
import com.tecacet.util.io.ExtensionFileFilter;

public class LuceneSearcherTest {

    private File indexDir;

    @Before
    public void index() throws Exception {
        String parentDir = "./testfiles";
        indexDir = new File("index");
        indexDir.delete();
        indexDir.mkdir();
        indexDir.deleteOnExit(); // TODO does not work
        File dataDir = new File(parentDir);

        URL url = Resources.getResource("document.factory.properties");
        Properties properties = new Properties();
        InputStream is = url.openStream();
        properties.load(is);
        is.close();
        ExtensionFileFilter filter = new ExtensionFileFilter(properties.keySet());
        DocumentFactory documentFactory = new ExtensionDocumentFactory(properties);
        long start = new Date().getTime();
        LuceneIndexer indexer = new LuceneIndexer(indexDir, documentFactory, filter);
        int numIndexed = indexer.index(dataDir);
        long end = new Date().getTime();
        System.out.println("Indexing " + numIndexed + " files took " + (end - start) + " milliseconds");

    }

    @After
    public void cleanUp() {
    	//TODO does not work. Cannot delete a dir with files
        if (indexDir.exists()) {
            indexDir.delete();
        }
    }

    @Test
    public void testQueryField() throws Exception {
    	File indexDir = new File("index");
    	LuceneSearcher searcher = new LuceneSearcher(indexDir);
        Document[] docs = searcher.search("author:dimitri");
        for (Document doc : docs) {
            System.out.println(doc.get(IndexConstants.PATH_FIELD));
        }
    }
    
    @Test
    public void testSearch() throws Exception {

        File indexDir = new File("index");
        String q = "exception*";
		LuceneSearcher searcher = new LuceneSearcher(indexDir);
        Document[] docs = searcher.search(q);
        //TODO check expected results
        for (Document doc : docs) {
            System.out.println(doc.get(IndexConstants.PATH_FIELD));
        }
    }

}

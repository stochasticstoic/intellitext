package com.tecacet.text.search;

import com.google.common.io.Resources;
import com.tecacet.util.io.ExtensionFileFilter;

import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class LuceneIndexerTest {

    @Test
    public void testIndex() throws Exception {
        String parentDir = "./testfiles";
        File indexDir = new File("index");

        indexDir.mkdir();

        File dataDir = new File(parentDir);

        URL url = Resources.getResource("document.factory.properties");
        Properties properties = new Properties();
        InputStream is = url.openStream();
        properties.load(is);
        is.close();
        ExtensionFileFilter filter = new ExtensionFileFilter(properties.keySet());
        DocumentFactory documentFactory = new ExtensionDocumentFactory(properties);
        long start = System.currentTimeMillis();
        LuceneIndexer indexer = new LuceneIndexer(indexDir, documentFactory, filter);
        int numIndexed = indexer.index(dataDir);
        long end = System.currentTimeMillis();
        System.out.println("Indexing " + numIndexed + " files took " + (end - start) + " milliseconds");

        //TODO assert something

        //TODO how do delete index dir?
    }

}

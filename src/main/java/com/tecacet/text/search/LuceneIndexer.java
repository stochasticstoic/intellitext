package com.tecacet.text.search;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tecacet.util.io.DirectoryTraverser;
import com.tecacet.util.io.ExtensionFileFilter;
import com.tecacet.util.io.FileVisitor;

/**
 * A Lucene File Indexer
 */
public class LuceneIndexer {

    private static final Version LUCENE_VERSION = Version.LUCENE_47;

	private Logger log = LoggerFactory.getLogger(this.getClass());

    private final DocumentFactory documentFactory;

    private final Directory directory;
    private IndexWriter writer;
    private final Analyzer analyzer;

    private final FileFilter filter;
    
    private final FileVisitor visitor = new FileVisitor() {
        @Override
        public void visit(File f) throws IOException {
            try {
                indexFile(f);
            } catch (DocumentHandlerException e) {
                throw new IOException(e);
            }
        }
    };

    public LuceneIndexer(File parentDir) throws IOException, DocumentHandlerException {
        this(parentDir, new ExtensionDocumentFactory("document.factory.properties"),
                new ExtensionFileFilter(new String[] { "txt", "pdf" }));
    }
   

    public LuceneIndexer(File parentDir, DocumentFactory documentFactory, FileFilter filter) throws IOException {
        this.directory = FSDirectory.open(parentDir);
        this.documentFactory = documentFactory;
        this.filter = filter;
        this.analyzer = new StandardAnalyzer(LUCENE_VERSION);
    }

    public int index(File dataDir) throws IOException, DocumentHandlerException {
        if (!dataDir.exists() || !dataDir.isDirectory()) {
            throw new IOException(dataDir + " does not exist or is not a directory");
        }
        IndexWriterConfig config = new IndexWriterConfig(LUCENE_VERSION, analyzer);

        writer = new IndexWriter(directory, config);

        indexDirectory(dataDir);
        int numIndexed = writer.maxDoc();
        writer.close();
        return numIndexed;
    }

    private void indexDirectory(File dataDir) throws IOException, DocumentHandlerException {
        DirectoryTraverser traverser = new DirectoryTraverser(filter);
        traverser.traverse(visitor, dataDir);
    }

    private void indexFile(File f) throws IOException, DocumentHandlerException {
        if (f.isHidden() || !f.exists() || !f.canRead()) {
            log.info(String.format("Skipping hidden or unreadable file %s.", f.getName()));
            return;
        }
        log.info("Indexing " + f.getCanonicalPath());

        Document doc = documentFactory.getDocument(f);
        if (doc == null) {
            log.error("Failed to read document " + f.getName());
        }
        writer.addDocument(doc);
    }
}

package com.tecacet.util.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * Traverse a directory tree and apply a visitor to each non-directory file
 */
public class DirectoryTraverser {

    private FileFilter filter;

    /**
     * Initialize a directory traverser without a filter.
     * 
     */
    public DirectoryTraverser() {
        
    }
    
    /**
     * Initialize a directory traverser with a filter
     * 
     * @param filter
     *            only files that pass the filter will be visited
     */
    public DirectoryTraverser(FileFilter filter) {
        super();
        this.filter = filter;
    }

    public void traverse(final FileVisitor visitor, File dir) throws IOException {
        File[] files = filter == null ? dir.listFiles() : dir.listFiles(filter);
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                traverse(visitor, f); 
            } else {
                visitor.visit(f);
            }
        }
        
        
    }
}

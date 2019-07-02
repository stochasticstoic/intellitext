package com.tecacet.util.io;

import java.io.File;
import java.io.IOException;

/**
 * Visitor for a DirectoryTraverser
 */
public interface FileVisitor {

    void visit(File f) throws IOException;
}

package com.tecacet.util.io;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class DirectoryTraverserTest {

    @Test
    public void traverseNoFilter() throws IOException {
        DirectoryTraverser traverser = new DirectoryTraverser();
        File dir = new File("testfiles");
        final AtomicInteger count = new AtomicInteger();
        traverser.traverse(f -> {
            System.out.println("Visiting " + f.getCanonicalPath());
            count.incrementAndGet();
        }, dir);
        assertEquals(9, count.get());
    }

    @Test
    public void traverseWithFilter() throws IOException {
        ExtensionFileFilter fileFilter = new ExtensionFileFilter("doc", "docx", "txt");
        DirectoryTraverser traverser = new DirectoryTraverser(fileFilter);
        File dir = new File("testfiles");
        final AtomicInteger count = new AtomicInteger();
        traverser.traverse(new FileVisitor() {

            @Override
            public void visit(File f) throws IOException {
                System.out.println("Visiting " + f.getCanonicalPath());
                count.incrementAndGet();
            }
        }, dir);
        assertEquals(3, count.get());
    }

}

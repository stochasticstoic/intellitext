package com.tecacet.util.io;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Filter files by a list of extensions
 *
 * @author dimitri
 */
public class ExtensionFileFilter implements FileFilter {

    private boolean acceptDirectory = true;
    private boolean acceptExtensionless = false;
    private Set<String> extensions = new HashSet<>();

    public ExtensionFileFilter(String... ext) {
        extensions.addAll(Arrays.asList(ext));
    }

    public ExtensionFileFilter(Set extensions) {
        this.extensions = extensions;
    }

    public void addExtension(String ext) {
        extensions.add(ext);
    }

    public boolean removeExtension(String ext) {
        return extensions.remove(ext);
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return acceptDirectory;
        }
        String ext = getExtension(f);
        if (ext == null) {
            return acceptExtensionless;
        }
        return extensions.contains(ext);
    }

    public static String getExtension(File f) {
        String name = f.getName();
        int index = name.lastIndexOf(".");
        if (index < 0) {
            return null;
        }
        return name.substring(index + 1);
    }

    public boolean isAcceptDirectory() {
        return acceptDirectory;
    }

    public void setAcceptDirectory(boolean acceptDirectory) {
        this.acceptDirectory = acceptDirectory;
    }

    public boolean isAcceptExtensionless() {
        return acceptExtensionless;
    }

    public void setAcceptExtensionless(boolean acceptExtensionless) {
        this.acceptExtensionless = acceptExtensionless;
    }



}

package com.tecacet.text;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

public class JavaBuiltInRTFTextExtractor implements TextExtractor {

    @Override
    public String getText(InputStream is) throws IOException {

        DefaultStyledDocument styledDoc = new DefaultStyledDocument();
        try {
            new RTFEditorKit().read(is, styledDoc, 0);
            return styledDoc.getText(0, styledDoc.getLength());
        } catch (BadLocationException e) {
            throw new IOException(e);
        }
    }

}

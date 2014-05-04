package com.tecacet.text;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

public class PoiMSTextExtractorTest {

	private PoiMSTextExtractor textExtractor = new PoiMSTextExtractor();

	@Test
	public void testDOC() throws IOException {

		String[] words = new String[] { "exception", "coder", "novice", "class" };
		test("testfiles/msft/ExceptionalProgramming.doc", words);
	}

	@Test
	public void testDOCX() throws IOException {
		String[] words = new String[] { "compiler", "code", "memory", "thy" };
		test("testfiles/msft/Compiler Invocation.docx", words);
	}

	@Test
	public void testXLSX() throws IOException {
		String[] words = new String[] { "GENERAL", "NAVIGATOR", "SLEEPER",
				"LOVE" };
		test("testfiles/msft/funny_movies.xlsx", words);
	}

	@Test
	public void testPPT() throws IOException {
		String[] words = new String[] { "Swing", "Pattern", "interface", "GUI" };
		test("testfiles/msft/MVC.ppt", words);
	}

	// TODO test .xls and .vsf

	private void test(String document, String[] words) throws IOException {
		FileInputStream fis = new FileInputStream(document);
		String text = textExtractor.getText(fis);
		for (String word : words) {
			assertTrue(word, text.contains(word));
		}
		fis.close();
	}

}

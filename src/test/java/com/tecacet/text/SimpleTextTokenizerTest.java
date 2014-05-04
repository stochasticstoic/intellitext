package com.tecacet.text;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class SimpleTextTokenizerTest {

	@Test
	public void testTokenize() {
		SimpleTextTokenizer tokenizer = new SimpleTextTokenizer();
		List<String> words = tokenizer
				.tokenize("The quick borwn fox, jumped over. the lazy Dog");
		assertEquals("[the, quick, borwn, fox, jumped, over, the, lazy, dog]",
				words.toString());
	}

}

package com.tecacet.text.lucene;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.util.Version;

public final class PorterStemStopWordAnalyzer extends Analyzer {

	private static final Version LUCENE_VERSION = Version.LUCENE_47;

	private final Set<String> stopwords = new HashSet<String>();

	// TODO generalize to many-to-one
	private Map<String, String> synonyms = new HashMap<String, String>();

	public PorterStemStopWordAnalyzer() {
		super();
	}

	public PorterStemStopWordAnalyzer(Collection<String> stopWords)
			throws IOException {
		super();
		stopwords.addAll(stopWords);
	}

	private TokenFilter addSynomyms(TokenFilter stream) {
		if (synonyms.isEmpty()) {
			return stream;
		}
		SynonymMap.Builder builder = new SynonymMap.Builder(true);
		for (Map.Entry<String, String> entry : synonyms.entrySet()) {
			builder.add(new CharsRef(entry.getKey()),
					new CharsRef(entry.getValue()), true);
		}
		SynonymMap map;
		try {
			map = builder.build();
			return new SynonymFilter(stream, map, true);
		} catch (IOException e) {
			throw new RuntimeException(e); // TODO
		}

	}

	public void addSynonym(String word1, String word2) {
		synonyms.put(word1, word2);
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName,
			Reader reader) {
		Tokenizer tokenizer = new StandardTokenizer(LUCENE_VERSION, reader);
		TokenFilter lowerCaseFilter = new LowerCaseFilter(LUCENE_VERSION,
				tokenizer);
		TokenFilter synonymFilter = addSynomyms(lowerCaseFilter);
		CharArraySet set = new CharArraySet(LUCENE_VERSION, stopwords, true);
		TokenFilter stopFilter = new StopFilter(LUCENE_VERSION, synonymFilter,
				set);
		TokenFilter stemFilter = new PorterStemFilter(stopFilter);
		return new TokenStreamComponents(tokenizer, stemFilter);
	}

}

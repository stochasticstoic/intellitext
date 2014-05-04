package com.tecacet.text;

import java.io.IOException;
import java.io.InputStream;

import org.apache.html.dom.HTMLDocumentImpl;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class NekoHTMLTextExtractor extends AbstractHTMLTextExtractor {

	private final DOMFragmentParser parser = new DOMFragmentParser();

	@Override
	public String getText(InputStream is) throws IOException {

		DocumentFragment node = new HTMLDocumentImpl().createDocumentFragment();

		try {
			parser.parse(new InputSource(is), node);
		} catch (SAXException e) {
			throw new IOException(e);
		}

		StringBuffer sb = new StringBuffer();
		getText(sb, node, "title");
		setTitle(sb.toString());

		sb.setLength(0);
		getText(sb, node);
		String text = sb.toString();
		return text;

	}

	private void getText(StringBuffer sb, Node node) {
		if (node.getNodeType() == Node.TEXT_NODE) {
			sb.append(node.getNodeValue());
		}
		NodeList children = node.getChildNodes();
		if (children != null) {
			int len = children.getLength();
			for (int i = 0; i < len; i++) {
				getText(sb, children.item(i));
			}
		}
	}

	private boolean getText(StringBuffer sb, Node node, String element) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			if (element.equalsIgnoreCase(node.getNodeName())) {
				getText(sb, node);
				return true;
			}
		}
		NodeList children = node.getChildNodes();
		if (children != null) {
			int len = children.getLength();
			for (int i = 0; i < len; i++) {
				if (getText(sb, children.item(i), element)) {
					return true;
				}
			}
		}
		return false;
	}

}

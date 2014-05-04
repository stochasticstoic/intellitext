package com.tecacet.text;

import java.io.InputStream;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.tidy.Tidy;

public class JTidyHTMLTextExtractor extends AbstractHTMLTextExtractor {

	@Override
	public String getText(InputStream is) {
		Tidy tidy = new Tidy();
		tidy.setQuiet(true);
		tidy.setShowWarnings(false);
		org.w3c.dom.Document root = tidy.parseDOM(is, null);
		Element rawDoc = root.getDocumentElement();
		setTitle(getTitle(rawDoc));
		return getBody(rawDoc);
	}

	/**
	 * Gets the title text of the HTML document.
	 * 
	 * @param rawDoc
	 *            the DOM Element to extract title Node from
	 * @return the title text
	 */
	private String getTitle(Element rawDoc) {
		if (rawDoc == null) {
			return null;
		}

		String title = "";

		NodeList children = rawDoc.getElementsByTagName("title");
		if (children.getLength() > 0) {
			Element titleElement = ((Element) children.item(0));
			Text text = (Text) titleElement.getFirstChild();
			if (text != null) {
				title = text.getData();
			}
		}
		return title;
	}

	/**
	 * Gets the body text of the HTML document.
	 * 
	 * @param rawDoc
	 *            the DOM Element to extract body Node from
	 * @return the body text
	 */
	private String getBody(Element rawDoc) {
		if (rawDoc == null) {
			return null;
		}

		String body = "";
		NodeList children = rawDoc.getElementsByTagName("body");
		if (children.getLength() > 0) {
			body = getText(children.item(0));
		}
		return body;
	}

	/**
	 * Extracts text from the DOM node.
	 * 
	 * @param node
	 *            a DOM node
	 * @return the text value of the node
	 */
	private String getText(Node node) {
		NodeList children = node.getChildNodes();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			switch (child.getNodeType()) {
			case Node.ELEMENT_NODE:
				sb.append(getText(child));
				sb.append(" ");
				break;
			case Node.TEXT_NODE:
				sb.append(((Text) child).getData());
				break;
			}
		}
		return sb.toString();
	}

}

package com.tecacet.text.search;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.io.Resources;

/**
 * Factory that produces DocumentHandler instances based on the file's extensions.
 * extension-handler associations can be provided either via a configuraiton file
 * or explicitly registered with the addHanlder methods
 *
 */
public class ExtensionDocumentHandlerFactory implements DocumentHandlerFactory {

	private static final String DEFAULT_CONFIG_FILE = "document.factory.properties";
	private static final String CANNOT_CREATE_HANDLER_MESSAGE = "Cannot create instance of Document Handler %s.";

	private final Map<String, DocumentHandler> handlers = new HashMap<String, DocumentHandler>();

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public ExtensionDocumentHandlerFactory() throws IOException,
			DocumentHandlerException {
		this(DEFAULT_CONFIG_FILE);
	}

	public ExtensionDocumentHandlerFactory(String propertiesFile)
			throws IOException, DocumentHandlerException {
		URL url = Resources.getResource(propertiesFile);
		if (url == null) {
			throw new IOException("Resource does not exist: " + propertiesFile);
		}
		InputStream is = url.openStream();
		Properties props = new Properties();
		props.load(is);
		for (Object key : props.keySet()) {
			String ext = (String) key;
			String clazz = props.getProperty(ext);
			handlers.put(ext, instantiateHandler(clazz));
		}
		is.close();
	}

	@SuppressWarnings("rawtypes")
	public ExtensionDocumentHandlerFactory(Map props)
			throws DocumentHandlerException {
		for (Object key : props.keySet()) {
			String ext = (String) key;
			String clazz = (String) props.get(ext);
			handlers.put(ext, instantiateHandler(clazz));
		}
	}

	public void addHandler(String extension, String handlerClassName)
			throws DocumentHandlerException {
		addHandler(extension, instantiateHandler(handlerClassName));
	}

	public void addHandler(String extension, DocumentHandler handler) {
		handlers.put(extension, handler);
	}

	@Override
	public DocumentHandler getHandlerForFile(File file)
			throws DocumentHandlerException {
		String ext = Files.getFileExtension(file.getName());
		if (Strings.isNullOrEmpty(ext)) {
			log.warn("Cannot handle extensionless file {}.", file.getName());
			return null;
		}
		return getHandlerForExtension(ext);
	}

	
	public DocumentHandler getHandlerForExtension(String ext) {
		return handlers.get(ext);
	}

	private DocumentHandler instantiateHandler(String handlerClassName)
			throws DocumentHandlerException {
		try {
			Class<?> handlerClass = Class.forName(handlerClassName);
			DocumentHandler handler = (DocumentHandler) handlerClass
					.newInstance();
			return handler;
		} catch (ClassNotFoundException e) {
			throw new DocumentHandlerException(String.format(
					CANNOT_CREATE_HANDLER_MESSAGE, handlerClassName), e);
		} catch (InstantiationException e) {
			throw new DocumentHandlerException(String.format(
					CANNOT_CREATE_HANDLER_MESSAGE, handlerClassName), e);
		} catch (IllegalAccessException e) {
			throw new DocumentHandlerException(String.format(
					CANNOT_CREATE_HANDLER_MESSAGE, handlerClassName), e);
		}
	}
}

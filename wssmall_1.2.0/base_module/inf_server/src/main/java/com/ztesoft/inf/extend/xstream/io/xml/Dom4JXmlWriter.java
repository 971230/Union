/*
 * Copyright (C) 2004, 2005, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 07. March 2004 by Joe Walnes
 */
package com.ztesoft.inf.extend.xstream.io.xml;

import java.io.IOException;

import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.ztesoft.inf.extend.xstream.core.util.FastStack;
import com.ztesoft.inf.extend.xstream.io.StreamException;

public class Dom4JXmlWriter extends AbstractXmlWriter {

	private final XMLWriter writer;
	private final FastStack elementStack;
	private AttributesImpl attributes;
	private boolean started;
	private boolean children;
	private Element rootElement;

	public Dom4JXmlWriter(XMLWriter writer) {
		this(writer, new XmlFriendlyReplacer());
	}

	/**
	 * @since 1.2
	 */
	public Dom4JXmlWriter(XMLWriter writer, XmlFriendlyReplacer replacer) {
		super(replacer);
		this.writer = writer;
		this.elementStack = new FastStack(16);
		this.attributes = new AttributesImpl();
		try {
			writer.startDocument();
		} catch (SAXException e) {
			throw new StreamException(e);
		}
	}

	@Override
	public void startNode(String name) {
		if (elementStack.size() > 0) {
			try {
				startElement();
			} catch (SAXException e) {
				throw new StreamException(e);
			}
			started = false;
		}
		elementStack.push(escapeXmlName(name));
		children = false;
	}

	@Override
	public void setValue(String text) {
		char[] value = text.toCharArray();
		if (value.length > 0) {
			try {
				startElement();
				writer.characters(value, 0, value.length);
			} catch (SAXException e) {
				throw new StreamException(e);
			}
			children = true;
		}
	}

	@Override
	public void addAttribute(String key, String value) {
		attributes.addAttribute("", "", escapeXmlName(key), "string", value);
	}

	@Override
	public void endNode() {
		try {
			if (!children) {
				Element element = new DefaultElement(
						(String) elementStack.pop());
				for (int i = 0; i < attributes.getLength(); ++i) {
					element.addAttribute(attributes.getQName(i),
							attributes.getValue(i));
				}
				writer.write(element);
				attributes.clear();
				children = true; // node just closed is child of node on top of
									// stack
				started = true;
				rootElement = element;
			} else {
				startElement();
				writer.endElement("", "", (String) elementStack.pop());
			}
		} catch (SAXException e) {
			throw new StreamException(e);
		} catch (IOException e) {
			throw new StreamException(e);
		}
	}

	public Element getRootElement() {
		return rootElement;
	}

	public void setRootElement(Element rootElement) {
		this.rootElement = rootElement;
	}

	@Override
	public void flush() {
		// nothing to do
	}

	@Override
	public void close() {
		try {
			writer.endDocument();
		} catch (SAXException e) {
			throw new StreamException(e);
		}
	}

	private void startElement() throws SAXException {
		if (!started) {
			writer.startElement("", "", (String) elementStack.peek(),
					attributes);
			attributes.clear();
			started = true;
		}
	}
}

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

import com.ztesoft.inf.extend.xstream.converters.ErrorWriter;

import org.dom4j.Document;
import org.dom4j.Element;

public class Dom4JReader extends AbstractDocumentReader {

	private Element currentElement;

	public Dom4JReader(Element rootElement) {
		this(rootElement, new XmlFriendlyReplacer());
	}

	public Dom4JReader(Document document) {
		this(document.getRootElement());
	}

	/**
	 * @since 1.2
	 */
	public Dom4JReader(Element rootElement, XmlFriendlyReplacer replacer) {
		super(rootElement, replacer);
	}

	/**
	 * @since 1.2
	 */
	public Dom4JReader(Document document, XmlFriendlyReplacer replacer) {
		this(document.getRootElement(), replacer);
	}

	@Override
	public String getNodeName() {
		return unescapeXmlName(currentElement.getName());
	}

	@Override
	public String getValue() {
		return currentElement.getText();
	}

	@Override
	public String getAttribute(String name) {
		return currentElement.attributeValue(name);
	}

	@Override
	public String getAttribute(int index) {
		return currentElement.attribute(index).getValue();
	}

	@Override
	public int getAttributeCount() {
		return currentElement.attributeCount();
	}

	@Override
	public String getAttributeName(int index) {
		return unescapeXmlName(currentElement.attribute(index)
				.getQualifiedName());
	}

	@Override
	protected Object getParent() {
		return currentElement.getParent();
	}

	@Override
	protected Object getChild(int index) {
		return currentElement.elements().get(index);
	}

	@Override
	protected int getChildCount() {
		return currentElement.elements().size();
	}

	@Override
	protected void reassignCurrentElement(Object current) {
		currentElement = (Element) current;
	}

	@Override
	public void appendErrors(ErrorWriter errorWriter) {
		errorWriter.add("xpath", currentElement.getPath());
	}

}
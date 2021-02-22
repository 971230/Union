package com.ztesoft.inf.framework.utils;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

public class Dom4jUtils {

	public static String getValue(Node parent, String path) {
		Node node = parent.selectSingleNode(path);
		if (node != null)
			return node.getText();
		return null;
	}

	public static String getValues(Node parent, String path) {
		List lists = parent.selectNodes(path);
		String values = "";
		for (Iterator iterator = CollectionUtils.safe(lists).iterator(); iterator
				.hasNext();) {
			Node node = (Node) iterator.next();
			values += node.getText() + ",";
		}
		if (values.length() > 0) {
			return values.substring(0, values.length() - 1);
		}
		return null;
	}

	public static Document toDoc(String inXml) throws Exception {
		SAXReader reader = new SAXReader();
		return reader.read(new StringReader(inXml));
	}
}


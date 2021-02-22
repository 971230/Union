package com.ztesoft.inf.communication.client.util;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axis.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.inf.framework.commons.CodedException;

public class DomUtils {

	public static Document newDocument(String xmlStr, boolean nsaware) {
		return newDocument(xmlStr, nsaware, getXMLEncoding(xmlStr));
	}

	public static Document newDocument(String xmlStr, boolean nsaware, String encoding) {
		try {
			DocumentBuilderFactory xmlFact = DocumentBuilderFactory.newInstance();
			xmlFact.setNamespaceAware(nsaware);
			DocumentBuilder builder = xmlFact.newDocumentBuilder();
			return builder.parse(new ByteArrayInputStream(xmlStr
					.getBytes(encoding)));
		} catch (Exception e) {
			throw new CodedException("-1", "从字符串创建DOM对象失败", e);
		}
	}
	
	public static String ElementToString(Element element) {
		return XMLUtils.ElementToString(element);
	}
	public static String DocumentToString(Document doc) {
		return XMLUtils.DocumentToString(doc);
	}
	private static String getXMLEncoding(String xmlStr) {
		
		String defaultCode="UTF-8";
		String encode=StringUtils.toXmlAttr(xmlStr, "?xml", "", "encoding");
		if(StringUtils.isEmpty(encode)){
			encode =defaultCode;
		}
		
		return StringUtils.toUpperCase(encode);
	}
 
}

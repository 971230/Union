package com.ztesoft.net.util;

import java.io.IOException;
import java.io.StringReader;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class EcsXmlUtil {

	/**
	 * String 转为 XML
	 * @param str
	 * @return
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public static Document string2Doc(String xmlStr) throws JDOMException, IOException{
		 StringReader read = new StringReader(xmlStr);
		  // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
		  InputSource source = new InputSource(read);
		  // 创建一个新的SAXBuilder
		  SAXBuilder sb = new SAXBuilder();
		  return sb.build(source);
	}
}

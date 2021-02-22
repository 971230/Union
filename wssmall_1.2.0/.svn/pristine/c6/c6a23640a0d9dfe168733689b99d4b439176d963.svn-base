package com.powerise.ibss.util;

import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */


class XmlSerial {
	private static Logger logger = Logger.getLogger(XmlSerial.class);
  StringBuffer xmlStr=new StringBuffer();
  public XmlSerial() {
    super();
  }
  void startTag(String nameSpace,String tagName) {
    xmlStr.append("<"+tagName+">");
  }
  void endTag(String nameSpace,String tagName) {
    xmlStr.append("</"+tagName+">");
  }

  void startDocument(String charSet,Boolean boolValue) {
    xmlStr.append("<?xml version=\"1.0\" encoding=\""+charSet+"\"?>");
  }
  void endDocument() {
  }
  void text(String text) {
    xmlStr.append(text);
  }
  void attribute(String string,String attrName,String attrValue) {
    int len=xmlStr.length();
    xmlStr.deleteCharAt(len-1);
    xmlStr.append(" "+attrName+"= \""+attrValue+"\"");
    xmlStr.append(">");
  }
  String getXmlStr() {
    return xmlStr.toString();
  }
  void clear() {
    xmlStr=null;
  }


  public static void main(String argv[]) {
    XmlSerial test = new XmlSerial();
    test.startDocument("GBK",null);
    test.startTag("","DATA");
    test.attribute(null,"attr","1");
    test.text("value");
    test.endTag("","DATA");
    logger.info(test.getXmlStr());
  }

}

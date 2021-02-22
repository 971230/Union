package com.ztesoft.common.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import commons.CommonTools;

import params.ZteRequest;

public class XmlUtils {

	public static void main(String[] args) {
		//{goods_id:'201403251533131403',product_id:'',source_from:'LLKJ_WT'}
		String xml="<root><goods_id>123</goods_id><product_id>123</product_id><source_from>123</source_from><object><user><name>jack</name><age>12</age></user><user><name>asan</name><age>22</age></user></object></root>";
		Map map = xmlToMap(xml);
		System.out.println(map);
		String xml1 = mapToXml(map);
		System.out.println(xml1);
	}
	
	public static Map xmlToMap(String xml) {
		Map retMap = null;
		try {
			retMap = Dom2Map(DocumentHelper.parseText(xml));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return retMap;
	}
	

	@SuppressWarnings("unchecked")
	public static String zteRequestToXml(ZteRequest zteRequest) {
		Map map = new LinkedHashMap();
		try {
			BeanUtils.beanToMap(map, zteRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return XmlUtils.mapToXml(map);
	}
	@SuppressWarnings("unchecked")
	public static String zteRequestToJson(ZteRequest zteRequest) {
		Map map = new LinkedHashMap();
		try {
			BeanUtils.beanToMap(map, zteRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CommonTools.beanToJson(map);
	}
	
	public static String mapToXml(Map map) {
		Document document = DocumentHelper.createDocument();
		Element root = null;
		root = document.addElement("root");
		root = getXmlFromMap(root, map);
		String xmlStr = root.asXML();
		try {
			xmlStr = formatXML(xmlStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlStr;
	}
	//--songqi		对应EMS
	public static String mapToXml(Map map,String str) {
		Document document = DocumentHelper.createDocument();
		Element root = null;
		root = document.addElement(str);
		root = getXmlFromMap(root, map);
		String xmlStr = root.asXML();
		try {
			xmlStr = formatXML(xmlStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlStr;
	}

	public static Element getXmlFromMap(Element root, Map<String, Object> map) {
		for (Entry<String, Object> entry : map.entrySet()) {
			if (!(entry.getValue() instanceof HashMap || entry.getValue() instanceof ArrayList)) {
				Element elementOne = root.addElement(entry.getKey());
				Object val = entry.getValue();
				if(val!=null){
					elementOne.setText(String.valueOf(entry.getValue()));
				}
			} else if (entry.getValue() instanceof HashMap) {
				Element elementTwo = root.addElement(entry.getKey());
				getXmlFromMap(elementTwo, (Map<String, Object>) entry.getValue());
			} else {
				List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
				for (Map<String, Object> listMap : list) {
					Element elementThr = root.addElement(entry.getKey());
					getXmlFromMap(elementThr, listMap);
				}
			}
		}
		return root;
	}

	/*
	 * 输出去的xml格式化
	 */
	public static String formatXML(String str) throws Exception {
		SAXReader reader = new SAXReader();
		// 创建一个串的字符输入流
		StringReader in = new StringReader(str);
		Document doc = reader.read(in);
		// 创建输出格式
		OutputFormat formater = OutputFormat.createPrettyPrint();
		// 去掉xml文件的版本信息
		formater.setSuppressDeclaration(true);
		// 设置xml的输出编码
		formater.setEncoding("UTF-8");
		// 创建输出(目标)
		StringWriter out = new StringWriter();
		// 创建输出流
		XMLWriter writer = new XMLWriter(out, formater);
		// 输出格式化的串到目标中，执行后。格式化后的串保存在out中。
		writer.write(doc);
		writer.close();
		// 返回我们格式化后的结果
		return out.toString();
	}

	public static Map<String, Object> Dom2Map(Document doc) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), Dom2Map(e));
			} else{
				String text =e.getText();
				if(StringUtils.isEmpty(text))
					text = null;
				map.put(e.getName(),text);
			}
		}
		return map;
	}

	public static Map Dom2Map(Element e) {
		Map map = new HashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = Dom2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}
	
	public static String listToXml(List<Map<String,Object>> list) {
		Document document = DocumentHelper.createDocument();
		Element root = null;
		root = document.addElement("root");
		root = getXmlFromList(root, list);
		String xmlStr = root.asXML();
		try {
			xmlStr = formatXML(xmlStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlStr;
	}
	
	public static Element getXmlFromList(Element root, List<Map<String, Object>> srcList) {
		for(Map<String, Object> map:srcList) {
			getXmlFromMap(root, map);
		}
		return root;
	}
}

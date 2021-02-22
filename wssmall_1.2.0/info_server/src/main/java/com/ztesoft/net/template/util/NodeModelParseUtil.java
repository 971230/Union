package com.ztesoft.net.template.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.ztesoft.net.template.model.NodeModel;
import com.ztesoft.net.template.model.OrderTemplateNode;

public class NodeModelParseUtil {
	public static String mapToXml(Map<String, NodeModel> map) {
		Document document = DocumentHelper.createDocument();
		String xmlStr ="";
		try {
			for (Entry<String, NodeModel> entry : map.entrySet()) {
				String nodeCode=entry.getKey();
				NodeModel nodeModel=entry.getValue();
				Map<String, NodeModel> sub_map=nodeModel.getSub_node();
				OrderTemplateNode node=nodeModel.getNodeInfo();
				Element root = document.addElement(nodeCode);
				//addNodeInfo(node,root);
				if(sub_map!=null&&!sub_map.isEmpty()){
					getXmlFromMap(root, sub_map);
				}else{
					root.setText(" ");
				}
				xmlStr = root.asXML();
			}
			xmlStr = formatXML(xmlStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlStr;
	}
	
	public static void getXmlFromMap(Element root, Map<String, NodeModel> map) {
		for (Entry<String, NodeModel> entry : map.entrySet()) {
			String nodeCode=entry.getKey();
			NodeModel nodeModel=entry.getValue();
			Map<String, NodeModel> sub_map=nodeModel.getSub_node();
			OrderTemplateNode node=nodeModel.getNodeInfo();
			Long node_repeat=node.getNode_repeat();
			String node_repeat_desc=node.getNode_repeat_desc();
			Element childElement = DocumentHelper.createElement(nodeCode);
			//addNodeInfo(node,childElement);
			if(sub_map!=null&&!sub_map.isEmpty()){
				getXmlFromMap(childElement, sub_map);
			}else{
				childElement.setText(" ");
			}
			if(node_repeat!=null&&"1".equals(node_repeat.toString())&&!"".equals(node_repeat_desc)){
				Element repeatElement = DocumentHelper.createElement(node_repeat_desc).addAttribute("node_repeat", node_repeat.toString());
				repeatElement.add(childElement);
				root.add(repeatElement);
			}else{
				root.add(childElement);
			}
		}
	}
	
	public static void addNodeInfo(OrderTemplateNode node,Element root){
		String orderTemplateId=node.getOrder_template_id();
		String nodeId=node.getNode_id();
		String nodeName=node.getNode_name();
		String nodeCode=node.getNode_code();
		String nodeType=node.getNode_type();
		String superNodeId=node.getSuper_node_id()==null?"-0":node.getSuper_node_id();
		String nodePath=node.getNode_path()==null?"":node.getNode_path();
		String nodeTableCode=node.getNode_table_code()==null?"":node.getNode_table_code();
		String nodeTableFieldCode=node.getNode_table_field_code()==null?"":node.getNode_table_field_code();
		String nodeReadComments=node.getNode_read_comments()==null?"":node.getNode_read_comments();
		Long nodeRepeat=node.getNode_repeat();
		Long nodeDepth=node.getNode_depth()==null?1:node.getNode_depth();
		
		Element childElement1 = DocumentHelper.createElement("order_template_id");
		childElement1.setText(orderTemplateId);
		root.add(childElement1);
		
		Element childElement2 = DocumentHelper.createElement("node_id");
		childElement2.setText(nodeId);
		root.add(childElement2);
		
		Element childElement3 = DocumentHelper.createElement("node_code");
		childElement3.setText(nodeCode);
		root.add(childElement3);
		
		Element childElement4 = DocumentHelper.createElement("node_name");
		childElement4.setText(nodeName);
		root.add(childElement4);
		
	}
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
	
	public static String mapToJson(Map<String, NodeModel> map){
    	
		StringBuilder json = new StringBuilder();
		json.append("{");
		for (Entry<String, NodeModel> entry : map.entrySet()) {
			String nodeCode=entry.getKey();
			NodeModel nodeModel=entry.getValue();
			Map<String, NodeModel> sub_map=nodeModel.getSub_node();
			OrderTemplateNode node=nodeModel.getNodeInfo();
			if(sub_map!=null&&!sub_map.isEmpty()){
				json.append("'"+nodeCode+"':'',");
				getJsonByMap(sub_map,json);
			}else{
				json.append("'"+nodeCode+"':''");
			}
		}
		json.append("}");
		return json.toString();
    }
	
	public static void getJsonByMap(Map<String, NodeModel> map,StringBuilder json){
		for (Entry<String, NodeModel> entry : map.entrySet()) {
			String nodeCode=entry.getKey();
			NodeModel nodeModel=entry.getValue();
			Map<String, NodeModel> sub_map=nodeModel.getSub_node();
			OrderTemplateNode node=nodeModel.getNodeInfo();
			if(sub_map!=null&&!sub_map.isEmpty()){
				json.append("{'"+nodeCode+"':'',");
				getJsonByMap(sub_map,json);
				json.append("},");
			}else{
				json.append("{'"+nodeCode+"':''");
				json.append("}");
			}
		}
	}
}

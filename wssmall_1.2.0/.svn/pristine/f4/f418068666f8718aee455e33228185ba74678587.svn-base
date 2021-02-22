package com.ztesoft.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import zte.net.ecsord.params.sf.resp.NotifyOrderInfoSFResponse;

public class Xml2JsonUtil {
	private static Logger logger = Logger.getLogger(Xml2JsonUtil.class);
	public static  String xml2JSON(String xml) {
		JSONObject obj = new JSONObject();
		try {
			xml = xml.replace("&", "&amp;");
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement(root));
			return obj.getString(root.getName());
		} catch (Exception e) {
			return null;
		}
	}
	private static Map  iterateElement(Element element) {
		List jiedian = element.getChildren();
		Element et = null;
		Map obj = new HashMap();
		List list = null;
		for (int i = 0; i < jiedian.size(); i++) {
			list = new LinkedList();
			et = (Element) jiedian.get(i);
			if (et.getTextTrim().equals("")) {
				if (et.getChildren().size() == 0)
					continue;
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				list.add(iterateElement(et));
				obj.put(et.getName(), list);
			} else {
//				if (obj.containsKey(et.getName())) {
//					list = (List) obj.get(et.getName());
//				}
//				list.add(et.getTextTrim());
				obj.put(et.getName(), et.getTextTrim());
			}
		}
		return obj;
	}
	public static void main(String[] args) throws Exception {
		//String xml = "<Response><error_code>OK</error_code><routeResponseList><orderid>SZWYG201504299124143823</orderid><mailno>444004856621</mailno><routeList><accept_time>2015-05-04 17:43:40</accept_time><accept_address>深圳福田服务点</accept_address><remark>顺丰速运 已收取快件</remark><opcode>50</opcode></routeList><routeList><accept_time>2015-05-05 00:43:41</accept_time><accept_address>深圳福田服务点</accept_address><remark>快件派送不成功(已与收方客户约定新派送时间 ),待再次派送</remark><opcode>70</opcode></routeList><routeList><accept_time>2015-05-05 01:43:41</accept_time><accept_address>深圳福田服务点</accept_address><remark>已签收,感谢使用顺丰,期待再次为您服务</remark><opcode>80</opcode></routeList></routeResponseList></Response>";
		String xml = "<Response><errorCode>OK</errorCode><orderInfo><orderid>VIP201504019&396143&707</orderid><mailno>444004888363</mailno><origincode>020</origincode><destcode>02&0</destcode><filter_result>2</filter_result><return_tracking_no>477330017226</return_tracking_no></orderInfo></Response>";
		//String xml = "<Response><errorCode>OK</errorCode><orderInfo orderid=\"VIP201504019396143707\" mailno=\"444004888363\" origincode=\"020\" destcode=\"020\" filter_result=\"2\" return_tracking_no=\"477330017226\"></orderInfo></Response>";
		String json = xml2JSON(xml);
		logger.info(json);
		Class<?> clazz = Class.forName("zte.net.ecsord.params.sf.resp.NotifyOrderInfoSFResponse");
		NotifyOrderInfoSFResponse resp = (NotifyOrderInfoSFResponse) JsonUtil.fromJson(json, clazz);
		logger.info(resp);
	}
}

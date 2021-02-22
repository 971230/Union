package com.ztesoft.net.outter.inf.huasheng;

import java.io.ByteArrayInputStream;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ztesoft.common.util.JsonUtil;

public class HuaShengServiceClient {
	private static Logger logger = Logger.getLogger(HuaShengServiceClient.class);
	public static String callServers(String url , String reqXml){
		int timeout = 30;
		String respJson = "";
		try {
			logger.info("请求报文：" + reqXml);
			SOAPEnvelope req = new SOAPEnvelope(new ByteArrayInputStream(reqXml.getBytes("UTF-8")));
			SOAPEnvelope rsp;
			Call call = new Call(new Service());
			call.setUsername("soapcall");
			call.setPassword("soapcall");
			call.setTargetEndpointAddress(url);
			call.setTimeout(timeout * 1000);
			rsp = call.invoke(req);
			//获取返回结果
			if(null != rsp && null != rsp.getAsDocument()){
				Document resultDoc = rsp.getAsDocument();
				NodeList nodeList = resultDoc.getChildNodes();
				String respMsg = "";
				for(int i = 0 ; i < nodeList.getLength(); i++){
					Node n = nodeList.item(i);
					respMsg = n.getTextContent();
				}
				//结果转换为json
				respJson = respMsg;
			}
		} catch (AxisFault fault) {
			fault.printStackTrace();
			respJson = "";
		}catch(Exception ex){
			ex.printStackTrace();
			respJson = "";
		}
		//响应报文
		logger.info("响应报文：" + respJson);
		return respJson;
	}

	public static void main(String[] args) throws Exception {
		String reqXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:urn=\"urn:sap-com:document:sap:rfc:functions\"> <soapenv:Header/><soapenv:Body><urn:ZB2CDWL_001><INPUT><![CDATA[<?xml version=\"1.0\" encoding=\"utf-8\"?> <INPUT><ERSDS>20160701</ERSDS><ERSDE>20160725</ERSDE><MATNR></MATNR><RESERVE1></RESERVE1><RESERVE2></RESERVE2><RESERVE3></RESERVE3></INPUT>]]></INPUT></urn:ZB2CDWL_001></soapenv:Body></soapenv:Envelope>";		
		String url = "http://114.247.168.39:50000/XISOAPAdapter/MessageServlet?channel=:B2C:CC_B2C_DWL_TO_VSENS_ARTICLE_MASTER_SOAP_Out";
		String result = HuaShengServiceClient.callServers(url, reqXml);
		logger.info(result);
		logger.info(reqXml);
		
//		
//		String data_end_time = "2016-07-25 15:14:20";
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date d = dateFormat.parse(data_end_time);
//		d.setMinutes(d.getMinutes() - 3);
//		
//		String start_time = dateFormat.format(d);
//		logger.info(start_time);
		String json = "{\"MESSAGE\":\"接口成功，本次同步1条商品信息！\",\"ITEMS\":[{\"ITEM\":[{\"MATKL\":\"Z0010001\",\"ERSDA\":\"20160704\",\"PRDHANAME\":\"华为\",\"PRDHA\":\"10006\",\"CMMAG\":\"00\",\"MTART\":\"Z001\",\"MAKTX\":\"华华为P9全网通32G版（EVA-AL00）钛银灰\",\"MEINS\":\"PCS\",\"LAEDA\":\"20160725\",\"MATNR\":\"000000007402702334\"}]}],\"TYPE\":\"S\"}";

		HuaShengGoodsDto goodsDto = JsonUtil.fromJson(json, HuaShengGoodsDto.class);
		
		logger.info("ddddddddd");
		
	}
	
}

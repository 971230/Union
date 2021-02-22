package com.ztesoft.inf.communication.client;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.log4j.Logger;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.drools.core.util.StringUtils;
import org.w3c.dom.Document;

import params.ZteResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.Xml2JsonUtil;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.inf.communication.client.util.DomUtils;
import com.ztesoft.inf.extend.xstream.XStream;

@SuppressWarnings("unchecked")
public class HSInvoker extends Invoker {

	String cdataPath;
	Map dataNamespaces;
	Map cdataNamespaces;
	private static Logger logger = Logger.getLogger(HSInvoker.class);
	private static XStream xstream = XStream.instance();
	private static XPath xpath = XPathFactory.newInstance().newXPath();
	// private Service service = new Service();

	private final static String TEXT = "_EXCEPTION";

	@Override
	public Object invoke(InvokeContext _context) throws Exception {
		WsInvokeContext context = (WsInvokeContext) _context;
		context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		ZteResponse resp = new ZteResponse();
		SOAPEnvelope rsp = null;
		Call call = null;
		String respJson = "";
		try {
			rsp = new SOAPEnvelope();
			call = new Call(new Service());
			String reqString = generateRequestString(context);
			context.setRequestString(reqString);
			logger.info("请求报文(华盛)：" + reqString);
			SOAPEnvelope req = new SOAPEnvelope(new ByteArrayInputStream(reqString
					.trim().getBytes("UTF-8")));
			context.setRequestSOAP(req);
			call.setTargetEndpointAddress(endpoint);
			call.setUsername(reqUser.getUser_name());
			call.setPassword(reqUser.getUser_pwd());
			if (timeout != null) {
				call.setTimeout(timeout * 1000);
			}
			rsp = call.invoke(req);

			//获取返回结果
			if(null != rsp && null != rsp.getAsDocument()){
				Document resultDoc = rsp.getAsDocument();
				String resultStr = DomUtils.DocumentToString(resultDoc).replaceAll("&lt;", "<").replaceAll("&gt;", ">");
				logger.info("返回报文(华盛):" + resultStr);
				//结果转换为json
				respJson = Xml2JsonUtil.xml2JSON(resultStr).replaceAll(">", "");
				if(!StringUtils.isEmpty(cdataPath)){
					try{
						String[] paths = cdataPath.split("/");
						JSONObject jObj = JSON.parseObject(respJson);
						for(int i=0;i<paths.length;i++){
							jObj = (JSONObject) jObj.getJSONArray(paths[i]).get(0);
						}
						respJson = jObj.toString();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			context.setResponeString(respJson);
			Class<?> clazz = Class.forName(rspPath);
			resp = (ZteResponse)JsonUtil.fromJson(respJson, clazz);
		} catch (AxisFault fault) {
			fault.printStackTrace();
			context.setFailure(fault.dumpToString());
		}catch(Exception ex){
			ex.printStackTrace();
			context.setFailure(ex.getMessage());
			throw new Exception("接口异常" + ex.getMessage());
		} finally {
			context.setResponseTime(DateUtil.currentTime());
		}
		return resp;
	}

	@Override
	public Object invokeTest(InvokeContext _context) throws Exception {
		WsInvokeContext context = (WsInvokeContext) _context;
		context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		HashMap params = (HashMap)context.getParameters();
		String reqString = params.toString();
		context.setRequestString(reqString);
		logger.info(reqString);
		ZteResponse resp = new ZteResponse();
		Class<?> clazz = Class.forName(rspPath);
		resp = (ZteResponse) JsonUtil.fromJson("{\"errorCode\":\"0000\",\"errorMessage\":\"成功\"}", clazz);
		context.setResultString(transform);
		context.setResponeString(transform);
		context.setResponseTime(DateUtil.currentTime());
		return resp;
	}
	
	/**
	 * 用于异常转换模板 add by xiaof 111229
	 */
	public Document parseToDoc(String Content) throws Exception {
		StringWriter out = new StringWriter();
		Map content = new HashMap();
		Map rootMap = new HashMap();
		content.put(TEXT, Content);
		rootMap.put("doc", content);
		transformTemplate.process(rootMap, out);
		String resultStr = out.toString();
		out.close();
		return DomUtils.newDocument(resultStr, false);
	}

	/**
	 * 把xml字符串格式化一把，以便在控制台上输出更美观
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String format(String str) throws Exception {
		StringReader in = null;
		StringWriter out = null;
		try {
			SAXReader reader = new SAXReader();
			// 创建一个串的字符输入流
			in = new StringReader(str);
			org.dom4j.Document doc = reader.read(in);
			// 创建输出格式
			OutputFormat formate = OutputFormat.createPrettyPrint();
			// 创建输出
			out = new StringWriter();
			// 创建输出流
			XMLWriter writer = new XMLWriter(out, formate);
			// 输出格式化的串到目标中,格式化后的串保存在out中。
			writer.write(doc);
		} finally {
			// 关闭流
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
		return out.toString();
	}
	
	
	private Map subMap(Map map, String path){
		if(null == map)
			return map;
		String[] paths = path.split("\\.");
		for(int i = 0; i < paths.length; i++){
			map = (Map)map.get(paths[i]);
		}
		return map;
	}
}

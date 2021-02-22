package com.ztesoft.inf.communication.client;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPEnvelope;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Document;

import params.ZteResponse;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.inf.communication.client.util.DomUtils;
import com.ztesoft.inf.extend.freemarker.TemplateUtils;
import com.ztesoft.inf.extend.xstream.XStream;
import com.ztesoft.inf.framework.commons.CachedNamespaceContext;
import com.ztesoft.inf.framework.commons.CodedException;

import freemarker.ext.dom.NodeModel;

@SuppressWarnings("unchecked")
public class WsInvoker extends Invoker {

	String cdataPath;
	Map dataNamespaces;
	Map cdataNamespaces;

	private static XStream xstream = XStream.instance();
	private static XPath xpath = XPathFactory.newInstance().newXPath();
	// private Service service = new Service();

	private final static String TEXT = "_EXCEPTION";

	@Override
	public Object invoke(InvokeContext _context) throws Exception {

		WsInvokeContext context = (WsInvokeContext) _context;

		context.setEndpoint(endpoint);
		// context.setEndpoint("http://136.5.8.83:9100/CrmWeb/services/exchangeSOAP?wsdl");
		context.setRequestTime(DateUtil.currentTime());

		String reqString = generateRequestString(context);
//		reqString = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ord=\"http://orderService.service.protocol.cxf.linkage.com/\">	<soapenv:Header/>	<soapenv:Body>		<ord:actionRecv>			<actionRecvDREQ>				<GD_BSS_HEAD>					<CHANNEL_ID>F03XYL</CHANNEL_ID>					<EPARCHY_CODE>0020</EPARCHY_CODE>					<MSG_RECEIVER>actionRecv</MSG_RECEIVER>					<MSG_SENDER>OrderServiceD</MSG_SENDER>					<OPERATE_NAME>0F4F6</OPERATE_NAME>					<OPER_ID>ITFBK001</OPER_ID>					<PROCESS_TIME>20141128143900</PROCESS_TIME>					<RSP_CODE/>					<RSP_DESC/>					<SERVICE_NAME>OrderService</SERVICE_NAME>					<TEST_FLAG>0</TEST_FLAG>					<TRANS_IDO>GZ201411285735876750</TRANS_IDO>				</GD_BSS_HEAD>				<ACTION_CODE>141028</ACTION_CODE>				<ACTION_REASON>测试</ACTION_REASON>				<CHANNEL_COD>141028</CHANNEL_COD>				<ORDER_ID>2935222718</ORDER_ID>				<PAY_MONEY>240</PAY_MONEY>				<PAY_TYPE>141028</PAY_TYPE>				<SERIAL_NUMBER>18002282071</SERIAL_NUMBER>				<SERVICE_CALSS_CODE>G</SERVICE_CALSS_CODE>				<TAC_NO>520406</TAC_NO>			</actionRecvDREQ>		</ord:actionRecv>	</soapenv:Body></soapenv:Envelope>";
		context.setRequestString(reqString);
		System.out.println(reqString);
		SOAPEnvelope req = new SOAPEnvelope(new ByteArrayInputStream(reqString
				.trim().getBytes("UTF-8")));
		context.setRequestSOAP(req);

		SOAPEnvelope rsp;
		Call call = new Call(new Service());
		call.setTargetEndpointAddress(endpoint);
		// call.setTargetEndpointAddress("http://136.5.8.83:9100/CrmWeb/services/exchangeSOAP?wsdl");

		Map param = (Map) context.getParameters();
		if (StringUtils.equals("T", (String) param.get("ws_isNet"))) {
			call.setOperationName((String) param.get("ws_method"));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI((String) param.get("ws_namespace"));
		}

		if (timeout != null) {
			call.setTimeout(timeout * 1000);
		}
		try {
			rsp = call.invoke(req);
		} catch (AxisFault fault) {
			fault.printStackTrace();
			if (fault.detail instanceof ConnectException) {
				throw new CodedException("8001", "无法连接到操作["
						+ context.getOperationCode() + "]所请求的服务地址");
			}
			if (fault.detail == null) {
				throw new CodedException("8999", "WebService调用异常,"
						+ fault.getFaultString());
			}

			context.setFailure(fault.dumpToString());

			if (!transformFault) {
				throw fault;
			}

			Message msg = call.getResponseMessage();

			if (msg != null && msg.getSOAPEnvelope() != null) {
				rsp = msg.getSOAPEnvelope();
				context.setResponseSOAP(rsp);
			} else {
				throw new CodedException("8002", fault.getFaultString());
			}
		} finally {
			context.setResponseTime(DateUtil.currentTime());
		}

		Document resultDoc = rsp.getAsDocument();
		String resultStr = DomUtils.DocumentToString(resultDoc);
		context.setResponeString(resultStr);

		System.out.println("\nWMS返回正式报文：：："+resultStr);
		try {
			if (!StringUtils.isEmpty(cdataPath)) {
				CachedNamespaceContext nsCtx = new CachedNamespaceContext();
				nsCtx.putInCache(cdataNamespaces);
				xpath.setNamespaceContext(nsCtx);
				String cdataContent = xpath.evaluate(cdataPath, resultDoc);

				// 2011.10.13 如果cddata为空 ，则不进行解析了，否则报错
				if (StringUtils.isEmpty(cdataContent)) {
					return new HashMap();
				}
				try {
					resultDoc = DomUtils.newDocument(cdataContent, false);
				} catch (CodedException e) {
					// 转化失败做重解析 add by xiaof
					// resultDoc =parseToDoc(cdataContent);
					resultDoc = rsp.getAsDocument();
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			throw new CodedException("9002", "根据返回的SOAP报文取业务报文["
					+ context.getOperationCode() + "]时出错.", e);
		}

		Object result;
		StringWriter out = new StringWriter();
		try {
			if (transformTemplate != null) {
				Map rootMap = new HashMap();
				rootMap.put("doc", NodeModel.wrap(resultDoc));
				TemplateUtils.addUtils(rootMap);
				transformTemplate.process(rootMap, out);
				resultStr = out.toString();
				context.setResultString(resultStr);
			}
//			result = xstream.fromXML(resultStr, mapperContext);
			Class<?> clazz = Class.forName(rspPath);
			ZteResponse resp = (ZteResponse) JsonUtil.fromJson(resultStr, clazz);
			return resp;
		} catch (Exception e) {
			throw new CodedException("9003", "根据反馈的业务报文转为目标格式["
					+ context.getOperationCode() + "]时出错.", e);
		}
//		return result;
	}

	@Override
	public Object invokeTest(InvokeContext _context) throws Exception {
		WsInvokeContext context = (WsInvokeContext) _context;
		context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		HashMap params = (HashMap)context.getParameters();
		String reqString = params.toString();
		context.setRequestString(reqString);
		System.out.println(reqString);
		ZteResponse resp = new ZteResponse();
		Class<?> clazz = Class.forName(rspPath);
		resp = (ZteResponse) JsonUtil.fromJson("{\"RSP_CODE\":\"0000\",\"RSP_DESC\":\"成功\"}", clazz);
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

}

package com.ztesoft.inf.communication.client;

import java.io.ByteArrayInputStream;
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
import org.w3c.dom.Document;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.inf.communication.client.util.DomUtils;
import com.ztesoft.inf.extend.freemarker.TemplateUtils;
import com.ztesoft.inf.extend.xstream.XStream;
import com.ztesoft.inf.framework.commons.CachedNamespaceContext;
import com.ztesoft.inf.framework.commons.CodedException;

import freemarker.ext.dom.NodeModel;

@SuppressWarnings("unchecked")
public class StringObjectInvoker extends Invoker {

	protected String cdataPath;
	protected Map dataNamespaces;
	protected Map cdataNamespaces;

	private static XStream xstream = XStream.instance();
	private static XPath xpath = XPathFactory.newInstance().newXPath();
	private static Service service = new Service();

	private final static String TEXT = "_EXCEPTION";

	@Override
	public Object invoke(InvokeContext _context) throws Exception {
		// TODO Auto-generated method stub
		WsInvokeContext context = (WsInvokeContext) _context;

		context.setEndpoint(endpoint);
		// context.setEndpoint("http://136.5.8.83:9100/CrmWeb/services/exchangeSOAP?wsdl");
		context.setRequestTime(DateUtil.currentTime());

		String reqString = generateRequestString(context);
		context.setRequestString(reqString);

		SOAPEnvelope req = new SOAPEnvelope(new ByteArrayInputStream(reqString.trim().getBytes("UTF-8")));
		context.setRequestSOAP(req);

		SOAPEnvelope rsp;
		Call call = new Call(service);
		call.setTargetEndpointAddress(endpoint);
		// call.setTargetEndpointAddress("http://136.5.8.83:9100/CrmWeb/services/exchangeSOAP?wsdl");

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

		// logger.info(resultStr);
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
			result = xstream.fromXML(resultStr, mapperContext);
			
			
			if(result instanceof Map) {
				((Map) result).put("resultXml", resultStr);
			}
			
		} catch (Exception e) {
			throw new CodedException("9003", "根据反馈的业务报文转为目标格式["
					+ context.getOperationCode() + "]时出错.", e);
		}
		return result;
	}

	
}

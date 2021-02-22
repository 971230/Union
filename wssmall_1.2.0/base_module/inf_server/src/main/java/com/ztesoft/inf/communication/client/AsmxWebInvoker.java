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
import org.apache.log4j.Logger;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
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
public class AsmxWebInvoker extends Invoker {
	private static Logger logger = Logger.getLogger(AsmxWebInvoker.class);
	String cdataPath;
	Map dataNamespaces;
	Map cdataNamespaces;

	private static XStream xstream = XStream.instance();
	private static XPath xpath = XPathFactory.newInstance().newXPath();
	private static Service service = new Service();

	private final static String TEXT="_EXCEPTION";
	@Override
	public Object invoke(InvokeContext _context) throws Exception {

		AsmxWebInvokeContext context = (AsmxWebInvokeContext) _context;

		context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		String reqType = (String)((Map)context.getParameters()).get("TYPE");

		String reqString = generateRequestString(context);
		context.setRequestString(reqString);
		

		SOAPEnvelope req = new SOAPEnvelope(new ByteArrayInputStream(reqString.trim().getBytes("UTF-8")));
		context.setRequestSOAP(req);

		SOAPEnvelope rsp;
		Call call = new Call(service);
		call.setTargetEndpointAddress(endpoint);
		call.setSOAPActionURI("http://crm.jx.ct10000.com/"+reqType);

		if (timeout != null) {
			call.setTimeout(timeout * 1000);
		}

		try {
			rsp = call.invoke(req);
		} catch (AxisFault fault) {
			if (fault.detail instanceof ConnectException) {
				throw new CodedException("8001", "无法连接到操作[" + context.getOperationCode() + "]所请求的服务地址");
			}
			if (fault.detail == null) {
				throw new CodedException("8999", "WebService调用异常," + fault.getFaultString());
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

		logger.info("\n\n reqStr=\n" + reqString + "\n\n rspStr=\n");
		logger.info(format(resultStr.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"")
				.replace(">", ">\n")));
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
				try{
					resultDoc = DomUtils.newDocument(cdataContent, false);
				}catch (CodedException e) {
					//转化失败做重解析 add by xiaof 
					resultDoc =parseToDoc(cdataContent);
				}
			}
		} catch (Exception e) {
			throw new CodedException("9002", "根据返回的SOAP报文取业务报文[" + context.getOperationCode() + "]时出错.", e);
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
		} catch (Exception e) {
			throw new CodedException("9003", "根据反馈的业务报文转为目标格式[" + context.getOperationCode() + "]时出错.", e);
		}
		return result;
	}
	
	/**
	 * 用于异常转换模板 add by xiaof 111229
	 */
	public Document parseToDoc(String Content) throws  Exception {
		StringWriter out = new StringWriter();
		Map content=new HashMap();
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


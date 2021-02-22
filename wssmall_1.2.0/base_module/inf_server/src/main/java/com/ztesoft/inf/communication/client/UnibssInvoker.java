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

import params.ZteResponse;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.inf.communication.client.util.DomUtils;
import com.ztesoft.inf.extend.freemarker.TemplateUtils;
import com.ztesoft.inf.extend.xstream.XStream;
import com.ztesoft.inf.framework.commons.CodedException;
import commons.CommonTools;

import freemarker.ext.dom.NodeModel;
/**
 * 
 * @author sguo
 * 广东联通：针对统一业务平台webservice的invoker
 * 新增原因：WsInvoker对统一业务平台返回的soap报文，解析失败，要独立处理
 * <p><B>返回报文样例</B></p>
 * 
 * <PRE>
	&lt;?xml version=&quot;1.0&quot;?&gt;
	&lt;SOAP-ENV:Envelope xmlns:SOAP-ENV=&quot;http://schemas.xmlsoap.org/soap/envelope/&quot;&gt;
	    &lt;SOAP-ENV:Header /&gt;
	    &lt;SOAP-ENV:Body&gt;
	        &lt;m:AGENCY_ACCT_PAY_NOTIFY_OUTPUT xmlns:m=&quot;http://ws.chinaunicom.cn/AgencyAcctPaySer/unibssBody&quot;&gt;
	            &lt;h:UNI_BSS_HEAD xmlns:h=&quot;http://ws.chinaunicom.cn/unibssHead&quot;&gt;
	                &lt;h:ORIG_DOMAIN&gt;ULTE&lt;/h:ORIG_DOMAIN&gt;
	                &lt;h:SERVICE_NAME&gt;AgencyAcctPaySer&lt;/h:SERVICE_NAME&gt;
	                &lt;h:OPERATE_NAME&gt;agencyAcctPayNotify&lt;/h:OPERATE_NAME&gt;
	                &lt;h:ACTION_CODE&gt;1&lt;/h:ACTION_CODE&gt;
	                &lt;h:ACTION_RELATION&gt;0&lt;/h:ACTION_RELATION&gt;
	                &lt;h:ROUTING&gt;
	                    &lt;h:ROUTE_TYPE&gt;00&lt;/h:ROUTE_TYPE&gt;
	                    &lt;h:ROUTE_VALUE&gt;51&lt;/h:ROUTE_VALUE&gt;
	                &lt;/h:ROUTING&gt;
	                &lt;h:PROC_ID&gt;2014122903870850&lt;/h:PROC_ID&gt;
	                &lt;h:TRANS_IDO&gt;20141229015394670020&lt;/h:TRANS_IDO&gt;
	                &lt;h:TRANS_IDH /&gt;
	                &lt;h:PROCESS_TIME&gt;20141229090322&lt;/h:PROCESS_TIME&gt;
	                &lt;h:RESPONSE&gt;
	                    &lt;h:RSP_TYPE&gt;0&lt;/h:RSP_TYPE&gt;
	                    &lt;h:RSP_CODE&gt;0000&lt;/h:RSP_CODE&gt;
	                    &lt;h:RSP_DESC&gt;ok&lt;/h:RSP_DESC&gt;
	                &lt;/h:RESPONSE&gt;
	                &lt;h:COM_BUS_INFO&gt;
	                    &lt;h:OPER_ID&gt;GZYX0932&lt;/h:OPER_ID&gt;
	                    &lt;h:PROVINCE_CODE&gt;51&lt;/h:PROVINCE_CODE&gt;
	                    &lt;h:EPARCHY_CODE&gt;510&lt;/h:EPARCHY_CODE&gt;
	                    &lt;h:CITY_CODE&gt;512002&lt;/h:CITY_CODE&gt;
	                    &lt;h:CHANNEL_ID&gt;51b397g&lt;/h:CHANNEL_ID&gt;
	                    &lt;h:CHANNEL_TYPE&gt;2010200&lt;/h:CHANNEL_TYPE&gt;
	                    &lt;h:ACCESS_TYPE&gt;01&lt;/h:ACCESS_TYPE&gt;
	                    &lt;h:ORDER_TYPE&gt;00&lt;/h:ORDER_TYPE&gt;
	                &lt;/h:COM_BUS_INFO&gt;
	                &lt;h:SP_RESERVE&gt;
	                    &lt;h:TRANS_IDC&gt;201412290903222966009213308111&lt;/h:TRANS_IDC&gt;
	                    &lt;h:CUTOFFDAY&gt;20141229&lt;/h:CUTOFFDAY&gt;
	                    &lt;h:OSNDUNS&gt;9900&lt;/h:OSNDUNS&gt;
	                    &lt;h:HSNDUNS&gt;5100&lt;/h:HSNDUNS&gt;
	                    &lt;h:CONV_ID /&gt;
	                &lt;/h:SP_RESERVE&gt;
	                &lt;h:TEST_FLAG&gt;0&lt;/h:TEST_FLAG&gt;
	                &lt;h:MSG_SENDER&gt;0003&lt;/h:MSG_SENDER&gt;
	                &lt;h:MSG_RECEIVER&gt;0000&lt;/h:MSG_RECEIVER&gt;
	            &lt;/h:UNI_BSS_HEAD&gt;
	            &lt;m:UNI_BSS_BODY&gt;
	                &lt;n508423376:AGENCY_ACCT_PAY_NOTIFY_RSP xmlns:n508423376=&quot;http://ws.chinaunicom.cn/AgencyAcctPaySer/unibssBody/agencyAcctPayNotifyRsp&quot;&gt;
	                    &lt;n508423376:RESP_CODE&gt;0000&lt;/n508423376:RESP_CODE&gt;
	                    &lt;n508423376:RESP_DESC&gt;TradeOk&lt;/n508423376:RESP_DESC&gt;
	                    &lt;n508423376:RESP_INFO&gt;
	                        &lt;n508423376:ORDER_ID&gt;5114122945449727&lt;/n508423376:ORDER_ID&gt;
	                        &lt;n508423376:PROVINCE_ORDER_ID&gt;2014122972117968&lt;/n508423376:PROVINCE_ORDER_ID&gt;
	                        &lt;n508423376:CONTRACT_FLAG&gt;1&lt;/n508423376:CONTRACT_FLAG&gt;
	                    &lt;/n508423376:RESP_INFO&gt;
	                &lt;/n508423376:AGENCY_ACCT_PAY_NOTIFY_RSP&gt;
	            &lt;/m:UNI_BSS_BODY&gt;
	            &lt;a:UNI_BSS_ATTACHED xmlns:a=&quot;http://ws.chinaunicom.cn/unibssAttached&quot; /&gt;
	        &lt;/m:AGENCY_ACCT_PAY_NOTIFY_OUTPUT&gt;
	    &lt;/SOAP-ENV:Body&gt;
	&lt;/SOAP-ENV:Envelope&gt;
 * </PRE>
 */
@SuppressWarnings("unchecked")
public class UnibssInvoker extends Invoker {
	String cdataPath;
	Map dataNamespaces;
	Map cdataNamespaces;
	private static XStream xstream = XStream.instance();
	private static XPath xpath = XPathFactory.newInstance().newXPath();
	private final static String TEXT = "_EXCEPTION";

	@Override
	public Object invoke(InvokeContext _context) throws Exception {
		WsInvokeContext context = (WsInvokeContext) _context;
		context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		String reqString = generateRequestString(context);
		context.setRequestString(reqString);
		SOAPEnvelope req = new SOAPEnvelope(new ByteArrayInputStream(reqString
				.trim().getBytes("UTF-8")));
		context.setRequestSOAP(req);
		SOAPEnvelope rsp;
		Call call = new Call(new Service());
		call.setTargetEndpointAddress(endpoint);
		Map param = (Map) context.getParameters();
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
		resultStr = parseStr(resultStr);
		ByteArrayInputStream is = new ByteArrayInputStream(resultStr.getBytes("utf-8"));
		rsp = new SOAPEnvelope(is);
		resultDoc = rsp.getAsDocument();
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
			Class<?> clazz = Class.forName(rspPath);
			ZteResponse resp = (ZteResponse) CommonTools.jsonToBean(resultStr, clazz);
			return resp;
		} catch (Exception e) {
			throw new CodedException("9003", "根据反馈的业务报文转为目标格式["
					+ context.getOperationCode() + "]时出错.", e);
		}
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
		resp = (ZteResponse) JsonUtil.fromJson("{\"UNI_BSS_HEAD\":{\"RESPONSE\":{	\"RSP_TYPE\":\"0\",	\"RSP_CODE\":\"0000\",	\"RSP_DESC\":\"ok\"}	},\"AGENCY_ACCT_PAY_NOTIFY_RSP\":{\"RESP_CODE\":\"0000\",\"RESP_DESC\":\"TradeOk\"}}", clazz);
		context.setResultString(transform);
		context.setResponeString(transform);
		context.setResponseTime(DateUtil.currentTime());
		return resp;
	}
	
	
	private String parseStr(String src){
		String soap = src;
		soap = soap.replaceAll("SOAP-ENV:", "soap:");
		soap = soap.replaceAll("xmlns:SOAP-ENV", "xmlns:soap");
		soap = soap.replaceAll("h:", "");
		soap = soap.replaceAll("http://ws.chinaunicom.cn/AgencyAcctPaySer/unibssBody", "http://ws.chinaunicom.cn/AgencyAcctPaySer/unibssBody/");
		soap = soap.replaceAll("xmlns:h=\"http://ws.chinaunicom.cn/unibssHead\"", "");
		soap = soap.replaceAll("m:UNI_BSS_BODY", "UNI_BSS_BODY");
		soap = soap.replaceAll("n508423376:", "");
		soap = soap.replaceAll("xmlns:n508423376=\"http://ws.chinaunicom.cn/AgencyAcctPaySer/unibssBody//agencyAcctPayNotifyRsp\"", "");
		soap = soap.replaceAll("a:UNI_BSS_ATTACHED", "UNI_BSS_ATTACHED");
		soap = soap.replaceAll("xmlns:a=\"http://ws.chinaunicom.cn/unibssAttached\"", "");
		return soap;
	}
	
	public static void main(String[] args){
		String soap = "<?xml version=\"1.0\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">    <SOAP-ENV:Header />    <SOAP-ENV:Body>        <m:AGENCY_ACCT_PAY_NOTIFY_OUTPUT xmlns:m=\"http://ws.chinaunicom.cn/AgencyAcctPaySer/unibssBody\">            <h:UNI_BSS_HEAD xmlns:h=\"http://ws.chinaunicom.cn/unibssHead\">                <h:ORIG_DOMAIN>ULTE</h:ORIG_DOMAIN>                <h:SERVICE_NAME>AgencyAcctPaySer</h:SERVICE_NAME>                <h:OPERATE_NAME>agencyAcctPayNotify</h:OPERATE_NAME>                <h:ACTION_CODE>1</h:ACTION_CODE>                <h:ACTION_RELATION>0</h:ACTION_RELATION>                <h:ROUTING>                    <h:ROUTE_TYPE>00</h:ROUTE_TYPE>                    <h:ROUTE_VALUE>51</h:ROUTE_VALUE>                </h:ROUTING>                <h:PROC_ID>2014122903870850</h:PROC_ID>                <h:TRANS_IDO>20141229015394670020</h:TRANS_IDO>                <h:TRANS_IDH />                <h:PROCESS_TIME>20141229090322</h:PROCESS_TIME>                <h:RESPONSE>                    <h:RSP_TYPE>0</h:RSP_TYPE>                    <h:RSP_CODE>0000</h:RSP_CODE>                    <h:RSP_DESC>ok</h:RSP_DESC>                </h:RESPONSE>                <h:COM_BUS_INFO>                    <h:OPER_ID>GZYX0932</h:OPER_ID>                    <h:PROVINCE_CODE>51</h:PROVINCE_CODE>                    <h:EPARCHY_CODE>510</h:EPARCHY_CODE>                    <h:CITY_CODE>512002</h:CITY_CODE>                    <h:CHANNEL_ID>51b397g</h:CHANNEL_ID>                    <h:CHANNEL_TYPE>2010200</h:CHANNEL_TYPE>                    <h:ACCESS_TYPE>01</h:ACCESS_TYPE>                    <h:ORDER_TYPE>00</h:ORDER_TYPE>                </h:COM_BUS_INFO>                <h:SP_RESERVE>                    <h:TRANS_IDC>201412290903222966009213308111</h:TRANS_IDC>                    <h:CUTOFFDAY>20141229</h:CUTOFFDAY>                    <h:OSNDUNS>9900</h:OSNDUNS>                    <h:HSNDUNS>5100</h:HSNDUNS>                    <h:CONV_ID />                </h:SP_RESERVE>                <h:TEST_FLAG>0</h:TEST_FLAG>                <h:MSG_SENDER>0003</h:MSG_SENDER>                <h:MSG_RECEIVER>0000</h:MSG_RECEIVER>            </h:UNI_BSS_HEAD>            <m:UNI_BSS_BODY>                <n508423376:AGENCY_ACCT_PAY_NOTIFY_RSP xmlns:n508423376=\"http://ws.chinaunicom.cn/AgencyAcctPaySer/unibssBody/agencyAcctPayNotifyRsp\">                    <n508423376:RESP_CODE>0000</n508423376:RESP_CODE>                    <n508423376:RESP_DESC>TradeOk</n508423376:RESP_DESC>                    <n508423376:RESP_INFO>                        <n508423376:ORDER_ID>5114122945449727</n508423376:ORDER_ID>                        <n508423376:PROVINCE_ORDER_ID>2014122972117968</n508423376:PROVINCE_ORDER_ID>                        <n508423376:CONTRACT_FLAG>1</n508423376:CONTRACT_FLAG>                    </n508423376:RESP_INFO>                </n508423376:AGENCY_ACCT_PAY_NOTIFY_RSP>            </m:UNI_BSS_BODY>            <a:UNI_BSS_ATTACHED xmlns:a=\"http://ws.chinaunicom.cn/unibssAttached\" />        </m:AGENCY_ACCT_PAY_NOTIFY_OUTPUT>    </SOAP-ENV:Body></SOAP-ENV:Envelope>";
		soap = soap.replaceAll("SOAP-ENV:", "soap:");
		soap = soap.replaceAll("h:", "");
		soap = soap.replaceAll("xmlns:h=\"http://ws.chinaunicom.cn/unibssHead\"", "");
		soap = soap.replaceAll("m:UNI_BSS_BODY", "UNI_BSS_BODY");
		soap = soap.replaceAll("n508423376:", "");
		soap = soap.replaceAll("xmlns:n508423376=\"http://ws.chinaunicom.cn/AgencyAcctPaySer/unibssBody//agencyAcctPayNotifyRsp\"", "");
		soap = soap.replaceAll("<a:UNI_BSS_ATTACHED xmlns:a=\"http://ws.chinaunicom.cn/unibssAttached\" />", "");
		System.out.println(soap);
	}

}
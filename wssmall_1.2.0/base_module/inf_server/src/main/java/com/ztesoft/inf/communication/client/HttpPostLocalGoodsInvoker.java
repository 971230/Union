package com.ztesoft.inf.communication.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.resp.BSSAccountResponse;
import zte.net.ecsord.params.ecaop.resp.BSSCustomerInfoResponse;
import zte.net.ecsord.params.ecaop.resp.BSSGetCardMsgRsp;
import zte.net.ecsord.params.ecaop.resp.BSSOrderSubResponse;
import zte.net.ecsord.params.ecaop.resp.BSSWriteCardResultNoticeResp;
import zte.net.ecsord.params.ecaop.resp.UserCountCheckRsp;
import zte.net.ecsord.params.ecaop.resp.WriteCardPreRsp;

import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.Xml2JsonUtil;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.inf.communication.client.util.MapUtils;
import com.ztesoft.inf.extend.freemarker.TemplateUtils;
import com.ztesoft.inf.framework.commons.CodedException;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class HttpPostLocalGoodsInvoker extends Invoker {

	private static Logger logger = Logger.getLogger(HttpPostBssInvoker.class);
	private static String  CHARSET = "UTF-8";
	private static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static{
    	/** 
    	 * add by shusx 2016-08-10
    	 * 下面两个参数使用说明:
    	 * 如果该多线程连接池不对多个host使用时，MaxTotalConnections = MaxConnectionsPerHost
    	 * 如果该多线程连接池对多个host使用时，MaxTotalConnections = n(host) * MaxConnectionsPerHost
    	 */
        connectionManager.setMaxConnectionsPerHost(60);
        connectionManager.setMaxTotalConnections(60);
	}
	private static HttpClient client = new HttpClient(connectionManager);

	@Override
	public Object invoke(InvokeContext context) throws Exception {
		//连接超时
		client.getHttpConnectionManager().getParams().setConnectionTimeout(http_post_time_out);
		//读取超时
		client.getHttpConnectionManager().getParams().setSoTimeout(http_post_time_out);
		
		//根据配置模板生成请求xml报文
		String paramValue = generateRequestString(context);
		context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());

		// 创建参数队列z
        /*List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("xmlmsg",paramValue));
		context.setRequestString(list.toString());*/
		//设置消息头
		PostMethod postMethod = new PostMethod(endpoint);
		postMethod.setRequestHeader("Content-Type","text/xml");
		postMethod.setRequestHeader("charset","utf-8");
		
		ZteResponse resp = new ZteResponse();
		try{
			logger.info("BSS请求报文：" + paramValue);
			
			postMethod.setRequestEntity(new StringRequestEntity(paramValue,"text/xml","utf-8"));
        	/** 调用前先打印连接池使用情况  */
        	logger.info("HttpPostBssInvoker==pool,"+DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss")
        			+",connectionsInPool:"+connectionManager.getConnectionsInPool()
        			+",connectionsInUse:"+connectionManager.getConnectionsInUse()
        			+",maxConnectionsPerHost:"+connectionManager.getMaxConnectionsPerHost()
        			+",maxTotalConnections:"+connectionManager.getMaxTotalConnections());
			int statusCode = client.executeMethod(postMethod);
			logger.info("statusCode:"+statusCode);
			//获取BSS接口返回报文
			String respContent = getResponseContent(postMethod.getResponseBodyAsStream());
			logger.info("BSS返回报文：" + respContent);
			String respValue = respContent;
			Class<?> clazz = Class.forName(rspPath);	//返回结果转换为对象
			String jsonString = Xml2JsonUtil.xml2JSON(respContent);	//xml转换为json
			JSONObject jsonObj = JSONObject.fromObject(jsonString);
			
			JSONObject responseJson = new JSONObject();
			//PKG_BODY节点jsonArray
			String pkgBody = jsonObj.getString("PKG_BODY");
			JSONArray pkgBodyArr = JSONArray.fromObject(pkgBody);
			JSONObject pkgBodyNodeJson = JSONObject.fromObject(pkgBodyArr.get(0));
			if(pkgBodyNodeJson.containsKey("ORDER_CODE")){
				responseJson.put("ORDER_CODE", jsonObj.get("ORDER_CODE"));
			}
			
			String pkgHead = jsonObj.getString("PKG_HEAD");
			JSONArray pkgHeadArr = JSONArray.fromObject(pkgHead);
			JSONObject pkgHeadNodeJson = JSONObject.fromObject(pkgHeadArr.get(0));
			
			if(pkgHeadNodeJson.containsKey("ACTION_RES_TIME")){
				responseJson.put("ACTION_RES_TIME", pkgHeadNodeJson.getString("ACTION_RES_TIME"));
			}
			if(pkgHeadNodeJson.containsKey("ACTION_REQ_TIME")){
				responseJson.put("ACTION_REQ_TIME", pkgHeadNodeJson.get("ACTION_REQ_TIME"));
			}
			if(pkgHeadNodeJson.containsKey("ACTION_ID")){
				responseJson.put("ACTION_ID", pkgHeadNodeJson.get("ACTION_ID"));
			}
			resp = (ZteResponse) JsonUtil.fromJson(responseJson.toString(), clazz);	//json转换为对象

			//取模板报文外层的结果码
			resp.setError_code(pkgHeadNodeJson.getString("RES_CODE"));
			resp.setError_msg(pkgHeadNodeJson.getString("RES_MSG"));
			context.setResultString(respValue);
            context.setResponeString(respValue);
            context.setResponseTime(DateUtil.currentTime());
			if(EcsOrderConsts.INF_RESP_CODE_00000.equals(resp.getError_code())){
				logColMap.put("col2", "success");
			}else{
				logColMap.put("col2", "error");
			}
            return resp;
		}catch(HttpException ex){
			ex.printStackTrace();
			resp = setExceptionResponse(rspPath,"调BSS接口失败，原因：" + ex.getMessage());
		}catch(Exception ex){
			ex.printStackTrace();
			resp = setExceptionResponse(rspPath,"调BSS接口失败，原因：" + ex.getMessage());
		}finally{
			if(null != postMethod){
				postMethod.releaseConnection();
			}
		}
		return resp;
	}

	@Override
	public Object invokeTest(InvokeContext context) throws Exception {
		//return invoke(context);
		//连接超时
		client.getHttpConnectionManager().getParams().setConnectionTimeout(http_post_time_out);
		//读取超时
		client.getHttpConnectionManager().getParams().setSoTimeout(http_post_time_out);
		
		//根据配置模板生成请求xml报文
		String paramValue = generateRequestString(context);
		context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());

		// 创建参数队列z
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("xmlmsg",paramValue));
		context.setRequestString(list.toString());
		//设置消息头
		PostMethod postMethod = new PostMethod(endpoint);
		postMethod.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=" + CHARSET);
		postMethod.setRequestHeader("Accept-Language", "us");
		postMethod.setRequestHeader("CONN_type", "SSL");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,CHARSET);  

		ZteResponse resp = new ZteResponse();
		try{
			logger.info("BSS请求报文：" + paramValue);
			postMethod.addParameter("xmlmsg",paramValue);
        	//** 调用前先打印连接池使用情况  *//*
        	logger.info("HttpPostBssInvoker==pool,"+DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss")
        			+",connectionsInPool:"+connectionManager.getConnectionsInPool()
        			+",connectionsInUse:"+connectionManager.getConnectionsInUse()
        			+",maxConnectionsPerHost:"+connectionManager.getMaxConnectionsPerHost()
        			+",maxTotalConnections:"+connectionManager.getMaxTotalConnections());
			//获取BSS接口返回报文
			String respContent =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
									+"<MSGPKG>"
										+"<PKG_HEAD>"
											+"<ACTION_ID>M10001</ACTION_ID>"
											+"<ACTION_REQ_TIME>20161017124304</ACTION_REQ_TIME>"
											+"<ACTION_RES_TIME>20161017124304</ACTION_RES_TIME>"
											+"<RES_CODE>00000</RES_CODE>"
											+"<RES_MSG>操作成功</RES_MSG>"
										+"</PKG_HEAD>"
										+"<PKG_BODY>"
											+"<ORDER_CODE>M2016101617000467886</ORDER_CODE>"
										+"</PKG_BODY>"
									+"</MSGPKG>";
			logger.info("BSS返回报文：" + respContent);
			String respValue = respContent;
			Class<?> clazz = Class.forName(rspPath);	//返回结果转换为对象
			String jsonString = Xml2JsonUtil.xml2JSON(respContent);	//xml转换为json
			JSONObject jsonObj = JSONObject.fromObject(jsonString);
			
			JSONObject responseJson = new JSONObject();
			//PKG_BODY节点jsonArray
			String pkgBody = jsonObj.getString("PKG_BODY");
			JSONArray pkgBodyArr = JSONArray.fromObject(pkgBody);
			JSONObject pkgBodyNodeJson = JSONObject.fromObject(pkgBodyArr.get(0));
			if(pkgBodyNodeJson.containsKey("ORDER_CODE")){
				responseJson.put("ORDER_CODE", jsonObj.get("ORDER_CODE"));
			}
			
			String pkgHead = jsonObj.getString("PKG_HEAD");
			JSONArray pkgHeadArr = JSONArray.fromObject(pkgHead);
			JSONObject pkgHeadNodeJson = JSONObject.fromObject(pkgHeadArr.get(0));
			
			if(pkgHeadNodeJson.containsKey("ACTION_RES_TIME")){
				responseJson.put("ACTION_RES_TIME", pkgHeadNodeJson.getString("ACTION_RES_TIME"));
			}
			if(pkgHeadNodeJson.containsKey("ACTION_REQ_TIME")){
				responseJson.put("ACTION_REQ_TIME", pkgHeadNodeJson.get("ACTION_REQ_TIME"));
			}
			if(pkgHeadNodeJson.containsKey("ACTION_ID")){
				responseJson.put("ACTION_ID", pkgHeadNodeJson.get("ACTION_ID"));
			}
			resp = (ZteResponse) JsonUtil.fromJson(responseJson.toString(), clazz);	//json转换为对象

			//取模板报文外层的结果码
			resp.setError_code(pkgHeadNodeJson.getString("RES_CODE"));
			resp.setError_msg(pkgHeadNodeJson.getString("RES_MSG"));
			context.setResultString(respValue);
            context.setResponeString(respValue);
            context.setResponseTime(DateUtil.currentTime());
			if(EcsOrderConsts.INF_RESP_CODE_00000.equals(resp.getError_code())){
				logColMap.put("col2", "success");
			}else{
				logColMap.put("col2", "error");
			}
            return resp;
		}catch(Exception ex){
			ex.printStackTrace();
			resp = setExceptionResponse(rspPath,"调BSS接口失败，原因：" + ex.getMessage());
		}finally{
			if(null != postMethod){
				postMethod.releaseConnection();
			}
		}
		return resp;
	}
	
	@Override
	protected String generateRequestString(InvokeContext context) {
		Object params = context.getParameters();
		StringWriter out = new StringWriter();
		try {
			Map root = new HashMap();
			BeanUtils.copyProperties(root, params);
			if (globalVars != null) {
				root.put("_global", globalVars);
			}
			MapUtils.parseNull2EmptyInMap(root);
			TemplateUtils.addUtils(root);
			TemplateUtils.addInvokerInfo(root, this);
			requestTemplate.process(root, out);
		} catch (Exception e) {
			throw new CodedException("9001", "根据模板["
					+ context.getOperationCode() + "]组装请求报文出错.", e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		//替换模板中的空格、换行符为空
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");   
        Matcher m = p.matcher(out.toString());
        //为xml加上空格
        return m.replaceAll("").replaceAll("xmlversion=\"1.0\"encoding=\"UTF-8\"", "xml version=\"1.0\" encoding=\"UTF-8\"");
	}
	
	private String getResponseContent(final InputStream input) throws IOException {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(input,CHARSET));
		StringBuilder buffer = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String result = "";
		try{
			result = URLDecoder.decode(buffer.toString(),"UTF-8");
		}catch(Exception e){
			result = buffer.toString();
		}
		return result;
    }
	
	/**
	 * 设置接口异常时的错误
	 * @param clazz
	 * @param exceptionMsg
	 * @return
	 */
	public ZteResponse setExceptionResponse(String className , String exceptionMsg){
		if("zte.net.ecsord.params.ecaop.resp.BSSCustomerInfoResponse".equals(className)){
			BSSCustomerInfoResponse r = new zte.net.ecsord.params.ecaop.resp.BSSCustomerInfoResponse();
			r.setRespCode("-9999");
			r.setRespDesc(exceptionMsg);
			return r;
		}else if("zte.net.ecsord.params.ecaop.resp.BSSWriteCardResultNoticeResp".equals(className)){
			BSSWriteCardResultNoticeResp r = new zte.net.ecsord.params.ecaop.resp.BSSWriteCardResultNoticeResp();
			r.setRESP_CODE("-9999");
			r.setRESP_DESC(exceptionMsg);
			return r;
		}else if("zte.net.ecsord.params.ecaop.resp.BSSAccountResponse".equals(className)){
			BSSAccountResponse r = new zte.net.ecsord.params.ecaop.resp.BSSAccountResponse();
			r.setRespCode("-9999");
			r.setRespDesc(exceptionMsg);
			return r;
		}else if("zte.net.ecsord.params.ecaop.resp.BSSOrderSubResponse".equals(className)){
			BSSOrderSubResponse r = new zte.net.ecsord.params.ecaop.resp.BSSOrderSubResponse();
			r.setRespCode("-9999");
			r.setRespDesc(exceptionMsg);
			return r;
		}else if("zte.net.ecsord.params.ecaop.resp.UserCountCheckRsp".equals(className)){
			UserCountCheckRsp r = new zte.net.ecsord.params.ecaop.resp.UserCountCheckRsp();
			r.setRespCode("-9999");
			r.setRespDesc(exceptionMsg);
			return r;
		}else if("zte.net.ecsord.params.ecaop.resp.WriteCardPreRsp".equals(className)){
			WriteCardPreRsp r = new zte.net.ecsord.params.ecaop.resp.WriteCardPreRsp();
			r.setRespCode("-9999");
			r.setRespDesc(exceptionMsg);
			return r;
		}else if("zte.net.ecsord.params.ecaop.resp.BSSGetCardMsgRsp".equals(className)){
			BSSGetCardMsgRsp r = new zte.net.ecsord.params.ecaop.resp.BSSGetCardMsgRsp();
			r.setRESP_CODE("-9999");
			r.setRESP_DESC(exceptionMsg);
			return r;
		}else{
			ZteResponse resp = new ZteResponse();
			resp.setError_code("-9999");
			resp.setError_msg(exceptionMsg);
			return resp;
		}
	}

}

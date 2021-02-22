package com.ztesoft.inf.communication.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import params.ZteResponse;
import com.alibaba.fastjson.JSON;
import com.oreilly.servlet.Base64Decoder;
import com.oreilly.servlet.Base64Encoder;
import com.taobao.tair.json.JSONObject;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.XmlUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.inf.communication.client.exception.HttpTimeOutException;
import com.ztesoft.net.framework.util.StringUtil;
/**
 * 
 * @author zou.qh
 * <p>
 * 	EMS自助服务http接口
 * </p>
 * 
 */
public class HttpEmsSelfServiceInvoker extends Invoker {
	protected Logger logger = Logger.getLogger(HttpEmsSelfServiceInvoker.class);
    static MultiThreadedHttpConnectionManager connectionManager =
            new MultiThreadedHttpConnectionManager();
    static {
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
		ZteResponse resp = new ZteResponse();
		String reqStr = generateRequestString(context);
		String url = endpoint + "?method="+reqUser.getUser_param();//getBillNumBySys
//		String url = "http://10.123.98.57:7001/integrationweb/integration/ordersStatus.sync" + "?result=" + StringUtil.md5(md5Str);
		PostMethod postMethod = new PostMethod(url);
		try {
			//链接超时
			client.getHttpConnectionManager().getParams().setConnectionTimeout(http_post_time_out);  
			//httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, http_post_time_out); 
			//读取超时
			client.getHttpConnectionManager().getParams().setSoTimeout(http_read_time_out);
            postMethod.addParameter("xml", Base64Encoder.encode(reqStr));
    		context.setRequestString(reqStr);
    		context.setEndpoint(url);
    		context.setRequestTime(DateUtil.currentTime());
        	/** 调用前先打印连接池使用情况  */
        	logger.info("HttpEmsInvoker==pool,"+DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss")
        			+",connectionsInPool:"+connectionManager.getConnectionsInPool()
        			+",connectionsInUse:"+connectionManager.getConnectionsInUse()
        			+",maxConnectionsPerHost:"+connectionManager.getMaxConnectionsPerHost()
        			+",maxTotalConnections:"+connectionManager.getMaxTotalConnections());
            int statusCode = client.executeMethod(postMethod);
        	//int statusCode = 200;
            if(TIME_OUT_STATUS==statusCode)throw new HttpTimeOutException("超时异常");
            String resContent =  getResponseContent(postMethod.getResponseBodyAsStream());
            resContent = Base64Decoder.decode(resContent);
            resContent = new String(resContent.getBytes("ISO8859-1"),"UTF-8");
	        if(OK_STATUS_200!=statusCode && OK_STATUS_201!=statusCode && OK_STATUS_202!=statusCode && OK_STATUS_203!=statusCode && OK_STATUS_204!=statusCode && OK_STATUS_205!=statusCode && OK_STATUS_206!=statusCode)
				throw new HttpException("接口异常".concat(";HTTP响应状态:")
						.concat(String.valueOf(statusCode))
						.concat(";HTTP响应内容:").concat(resContent));
            if(resContent.contains("<errorCode>E000</errorCode>") || resContent.contains("<result>1</result>")){
    			logColMap.put("col2", "success");
    		}else{
    			logColMap.put("col2", "error");
    		}
            //xml转map
            Map xmlMap = XmlUtils.xmlToMap(resContent);
            Map newMap = new HashMap();
            if(!StringUtil.isEmpty(rspType)){
            	newMap = subMap(xmlMap, rspType);
            	if(newMap == null){
            		newMap = xmlMap;
            	}
            	newMap.put("result", Const.getStrValue(xmlMap, "result"));
            	String error_code = Const.getStrValue(xmlMap, "errorCode");
            	newMap.put("error_code", "E000".equals(error_code)?"0000":"9999");
            	newMap.put("error_msg", Const.getStrValue(xmlMap, "errorDesc")+"："+Const.getStrValue(newMap, "dataError"));
            	newMap.remove("dErrorCode");
            	newMap.remove("dataError");
            }
            //map转json
            String jsonStr = JSONObject.toJSONString(newMap);
            //返回参数对象
            Class<?> clazz = Class.forName(rspPath);
            //json转对象
            resp = (ZteResponse) JSON.parseObject(jsonStr, clazz);
            postMethod.releaseConnection();
            context.setResultString(resContent);
            context.setResponeString(resContent);
            context.setResponseTime(DateUtil.currentTime());
            return resp;
        }catch(HttpException ex){
			ex.printStackTrace();
			throw ex;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new IOException("接口异常");
		} finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
	}
	
	@Override
	public Object invokeTest(InvokeContext context) throws Exception {
		context.setRequestTime(DateUtil.currentTime());
		String md5Str = StringUtil.md5(generateRequestString(context));
		String url = endpoint + "?result=" + md5Str;
		context.setRequestString(url);
		context.setEndpoint(url);
		context.setRequestTime(DateUtil.currentTime());
		context.setRequestString(md5Str);
		ZteResponse resp = null;
		Class<?> clazz = null;
		try {
			//xml转map
            Map xmlMap = XmlUtils.xmlToMap(transform);
            Map newMap = new HashMap();
            if(!StringUtil.isEmpty(rspType)){
            	newMap = subMap(xmlMap, rspType);
            	if(newMap == null){
            		newMap = xmlMap;
            	}
            	newMap.put("result", Const.getStrValue(xmlMap, "result"));
            	String error_code = Const.getStrValue(xmlMap, "errorCode");
            	newMap.put("error_code", "E000".equals(error_code)?"0000":"9999");
            	newMap.put("error_msg", Const.getStrValue(xmlMap, "errorDesc")+"："+Const.getStrValue(newMap, "dataError"));
            	newMap.remove("dErrorCode");
            	newMap.remove("dataError");
            	newMap.remove("errorDesc");
            	newMap.remove("errorCode");
            }
            //map转json
            String jsonStr = JSONObject.toJSONString(newMap);
			clazz = Class.forName(rspPath);
			resp =  (ZteResponse)JsonUtil.fromJson(jsonStr, clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		context.setResultString(transform);
		context.setResponeString(transform);
		context.setResponseTime(DateUtil.currentTime());
		return resp;
	}
	
	@Override
	protected String generateRequestString(InvokeContext context) {
		String reqString = null;
		Object params = context.getParameters();
		if("ems.getLogisticsNumber".equals(context.getOperationCode()) || "ems.syncLogisticsInfo".equals(context.getOperationCode())){
			StringWriter out = new StringWriter();
			try {
				Map pMap = (Map) params;
				pMap.put("appKey", reqUser.getUser_code());
				pMap.put("sysAccount", reqUser.getUser_name());
				pMap.put("passWord", reqUser.getUser_pwd());
				requestTemplate.process(pMap, out);
				reqString = out.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return reqString;
		}
		return StringUtils.capitalized(JsonUtil.toJson(params));
	}
	
	private String getResponseContent(final InputStream input) throws IOException {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(input, "utf-8"));
		StringBuilder buffer = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
        return buffer.toString();
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

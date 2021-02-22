package com.ztesoft.inf.communication.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oreilly.servlet.Base64Encoder;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;
import com.ztesoft.common.util.DesEncrypt;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.inf.communication.client.exception.HttpTimeOutException;
import com.ztesoft.net.framework.util.StringUtil;
/**
 * 
 * @author sguo 
 * <p>
 * 	联通淘宝http接口
 * </p>
 * 
 */
public class HttpTBInvoker extends Invoker {
	private static Logger logger = Logger.getLogger(HttpTBInvoker.class);
	private static TaobaoClient client = null;
	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
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
	private static HttpClient httpClient = new HttpClient(connectionManager);
	
	@Override
	public Object invoke(InvokeContext context) throws Exception {
		String json = reqUser.getUser_param();
		json = StringUtils.isEmpty(json)?"{}":json;//user_param若无配置,默认空Json
		boolean isJushita = false;
		JSONObject app_json = null;
		try{
			app_json = JSON.parseObject(json);
			String isJushitaS = app_json.get("isJushita")==null?"":app_json.get("isJushita").toString();
			if("1".equals(isJushitaS)){
				isJushita = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		HashMap params = (HashMap)context.getParameters();
		String url = endpoint;
		TaobaoRequest request = (TaobaoRequest)params.get("request");
		if(!isJushita){//直接调淘宝api
			String appkey = reqUser.getUser_code();
			String appSecret = reqUser.getUser_name();
			String sessionKey1 = reqUser.getUser_pwd();
			client = new DefaultTaobaoClient(url, appkey, appSecret);
			context.setRequestTime(DateUtil.currentTime());
			TaobaoResponse tbResp = client.execute(request, sessionKey1);
			context.setResponseTime(DateUtil.currentTime());
			context.setEndpoint(endpoint);
			context.setRequestString(((HashMap)request.getTextParams()).toString());
			context.setResultString(tbResp.getBody().toString());
			context.setResponeString(tbResp.getBody().toString());
			return tbResp;
		}else{//调聚石塔
			TaobaoResponse resp = null;
			Class<?> clazz = null;
			try {
				clazz = request.getResponseClass();
				resp = (TaobaoResponse) clazz.newInstance();//这里要保证成功，否则可能返回不了正确的对象
			} catch (Exception e) {
				e.printStackTrace();
			}
			PostMethod postMethod = null;
			try {
				postMethod = new PostMethod(url);
				//请求报文
				DesEncrypt des = new DesEncrypt();
				
				String app_id_s = app_json.getString("jushitaAppId");
				String req_s = JSON.toJSONString(request);
				String md5_s = StringUtil.md5(app_id_s+json+req_s);
				
				JSONObject req_json = new JSONObject();
				req_json.put("APP_ID", des.encrypt(Base64Encoder.encode(md5_s)));//将APP_ID、APP_KEYS、REQ做md5，再做base64后放到APP_ID节点
				req_json.put("APP_KEYS", des.encrypt(json));
				req_json.put("REQ", req_s);
				req_json.put("REQ_CLASS", request.getClass().getName());
				req_json.put("RESP_CALSS", request.getResponseClass().getName());
				
				/*此段代码将请求Json转为请求对象，验证成功
				JSONObject obj = JSONObject.fromObject(req_json);
				JSONObject req = (JSONObject)obj.get("REQ");
				TaobaoRequest req1 = (TaobaoRequest) JSONObject.toBean(req,request.getClass());
				*/
				
				//链接超时
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(http_post_time_out);
				//httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, http_post_time_out); 
				//读取超时
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(http_read_time_out);
				//httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, http_read_time_out);
	            byte[] bytes = req_json.toString().getBytes("UTF-8");
	            ByteArrayRequestEntity bare = new ByteArrayRequestEntity(bytes);
	            postMethod.setRequestEntity(bare);
	    		context.setRequestString(req_json.toString());
	    		context.setEndpoint(url);
	    		context.setRequestTime(DateUtil.currentTime());
	        	/** 调用前先打印连接池使用情况  */
	        	logger.info("HttpTBInvoker==pool,"+DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss")
	        			+",connectionsInPool:"+connectionManager.getConnectionsInPool()
	        			+",connectionsInUse:"+connectionManager.getConnectionsInUse()
	        			+",maxConnectionsPerHost:"+connectionManager.getMaxConnectionsPerHost()
	        			+",maxTotalConnections:"+connectionManager.getMaxTotalConnections());
	            int statusCode = httpClient.executeMethod(postMethod);
	            if(TIME_OUT_STATUS==statusCode)throw new HttpTimeOutException("超时异常");
	            String resContent =  getResponseContent(postMethod.getResponseBodyAsStream());
	            context.setResultString(resContent);
	            context.setResponeString(resContent);
	            context.setResponseTime(DateUtil.currentTime());
				
				if (TIME_OUT_STATUS == statusCode)
					throw new Exception("超时异常".concat(";HTTP响应状态:")
							.concat(String.valueOf(statusCode))
							.concat(";HTTP响应内容:").concat(resContent));
				if (OK_STATUS_200 != statusCode && OK_STATUS_201 != statusCode && OK_STATUS_202 != statusCode
						&& OK_STATUS_203 != statusCode && OK_STATUS_204 != statusCode
						&& OK_STATUS_205 != statusCode && OK_STATUS_206 != statusCode)
					throw new Exception("接口异常".concat(";HTTP响应状态:")
							.concat(String.valueOf(statusCode))
							.concat(";HTTP响应内容:").concat(resContent));
				
				JSONObject resp_json = JSON.parseObject(resContent);
				if ("0".equals(resp_json.getString("ERR_CODE"))) {
					try{//以聚石塔返回的响应对象类名为准
						resp = (TaobaoResponse) JSON.parseObject(resp_json.getString("RESP"), Class.forName(resp_json.getString("RESP_CALSS")));
					}catch(Exception e){//转为聚石塔返回的对象类名失败，则尝试转为req类指定的返回类
						e.printStackTrace();
						resp = (TaobaoResponse) JSON.parseObject(resp_json.getString("RESP"), clazz);
					}
				} else {//聚石塔返回错误
					resp.setErrorCode("zte-error");
					resp.setMsg("调用聚石服务失败："+resp_json.getString("ERR_MESSGE"));
					resp.setSubCode("zte defined error");
					resp.setSubMsg("调用聚石服务失败："+resp_json.getString("ERR_MESSGE"));
				}
	        }catch(Exception e){//系统报错
	        	e.printStackTrace();
				resp.setErrorCode("zte-error");
				resp.setMsg("调用聚石服务失败："+e.getMessage());
				resp.setSubCode("zte defined error");
				resp.setSubMsg("调用聚石服务失败：");
	        }finally {
	            if (postMethod != null) {
	                postMethod.releaseConnection();
	            }
	        }
			return resp;
		}
	}
	
	@Override
	public Object invokeTest(InvokeContext context) throws Exception {
		context.setRequestTime(DateUtil.currentTime());
		OutputStream out = null;
		HashMap params = (HashMap)context.getParameters();
		context.setEndpoint(endpoint);
		context.setRequestString(JsonUtil.toJson(params));
		TaobaoResponse resp = null;
		Class<?> clazz = Class.forName(rspPath);
		resp = (TaobaoResponse) JsonUtil.fromJson(transform, clazz);
		context.setResultString(transform);
		context.setResponeString(transform);
		context.setResponseTime(DateUtil.currentTime());
		return resp;		
	}

	
	private static String getResponseContent(final InputStream input) throws IOException {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(input, "utf-8"));
		StringBuilder buffer = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
        return buffer.toString();
    }
}

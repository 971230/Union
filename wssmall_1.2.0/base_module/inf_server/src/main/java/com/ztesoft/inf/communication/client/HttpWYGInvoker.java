package com.ztesoft.inf.communication.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import params.ZteResponse;

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
 * 	联通沃云购http接口
 * </p>
 * 
 */
public class HttpWYGInvoker extends Invoker {
	protected Logger logger = Logger.getLogger(HttpWYGInvoker.class);
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
		String md5Str = generateRequestString(context);
		String url = endpoint + "?result=" + StringUtil.md5(md5Str);
//		String url = "http://10.123.98.57:7001/integrationweb/integration/ordersStatus.sync" + "?result=" + StringUtil.md5(md5Str);
		PostMethod postMethod = new PostMethod(url);
		try {
			//链接超时
			client.getHttpConnectionManager().getParams().setConnectionTimeout(http_post_time_out);  
			//httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, http_post_time_out); 
			//读取超时
			client.getHttpConnectionManager().getParams().setSoTimeout(http_read_time_out);
			//httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, http_read_time_out);
            byte[] bytes = generateRequestString(context).getBytes("UTF-8");
            ByteArrayRequestEntity bare = new ByteArrayRequestEntity(bytes);
            postMethod.setRequestEntity(bare);
    		context.setRequestString(md5Str);
    		context.setEndpoint(url);
    		context.setRequestTime(DateUtil.currentTime());
        	/** 调用前先打印连接池使用情况  */
        	logger.info("HttpWYGInvoker==pool,"+DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss")
        			+",connectionsInPool:"+connectionManager.getConnectionsInPool()
        			+",connectionsInUse:"+connectionManager.getConnectionsInUse()
        			+",maxConnectionsPerHost:"+connectionManager.getMaxConnectionsPerHost()
        			+",maxTotalConnections:"+connectionManager.getMaxTotalConnections());
            int statusCode = client.executeMethod(postMethod);
            if(TIME_OUT_STATUS==statusCode)throw new HttpTimeOutException("超时异常");
            String resContent =  getResponseContent(postMethod.getResponseBodyAsStream());
	        if(OK_STATUS_200!=statusCode && OK_STATUS_201!=statusCode && OK_STATUS_202!=statusCode && OK_STATUS_203!=statusCode && OK_STATUS_204!=statusCode && OK_STATUS_205!=statusCode && OK_STATUS_206!=statusCode)
				throw new HttpException("接口异常".concat(";HTTP响应状态:")
						.concat(String.valueOf(statusCode))
						.concat(";HTTP响应内容:").concat(resContent));
            if(resContent.contains("\"resp_code\":\"0000\"") || resContent.contains("\"resp_code\":\"0\"")){
    			logColMap.put("col2", "success");
    		}else{
    			logColMap.put("col2", "error");
    		}
            Class<?> clazz = Class.forName(rspPath);
    		resp = (ZteResponse) JsonUtil.fromJson(resContent, clazz);
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
			clazz = Class.forName(rspPath);
			resp =  (ZteResponse)JsonUtil.fromJson(transform, clazz);
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
		Object params = context.getParameters();
		if("WYG.numberResourceQuery".equals(context.getOperationCode())
				||"WYG.NumberChange".equals(context.getOperationCode())
				||"WYG.notifyOrderInfoWYG".equals(context.getOperationCode())
				||"WYG.Send3NetSms".equals(context.getOperationCode())){
			return JsonUtil.toJson(params);
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
}

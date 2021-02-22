package com.ztesoft.inf.communication.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import params.ZteResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
/**
 * 
 * @author zou.qh
 * <p>
 * 	EMS路由信息查询 http接口
 * </p>
 * 
 * 
 */
public class HttpEmsRouteQryInvoker extends Invoker {
	protected Logger logger = Logger.getLogger(HttpEmsRouteQryInvoker.class);
	public static final String DEFAULTCHARSET = "UTF-8";
	public static final String version = "0.1";
	public static final String format = "json";
	
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
    //private static HttpClient client = new HttpClient(connectionManager);
	
	@Override
	public Object invoke(InvokeContext context) throws Exception {
		net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(reqUser.getUser_param());
		String method = obj.getString("method");
		String version = obj.getString("version");
		String access_token = obj.getString("access_token");
	
//		ZteResponse resp = null;
		Map params = (Map) context.getParameters();
		HttpClient httpClient = HttpClientBuilder.create().build();
		//设置请求地址
		HttpPost request = new HttpPost(endpoint);
		logger.info(endpoint);
		int timeout = 30000;//超时时间30秒
		//设置超时
		if(timeout > 0){
			RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(timeout)
				.setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout)
				.setExpectContinueEnabled(false).build();
			request.setConfig(requestConfig);
		}

		//组装请求报文
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("access_token", access_token);
		param.put("method", method);
		param.put("version", version);

		//业务参数   
		Map<String, String> content = new HashMap<String, String>();
		content.put("mail_num", params.get("logi_no").toString());
		param.put("content", content);
		logger.info("请求参数："+param);
		HttpResponse response = null;
		try{
			//请求报文转换成json格式的字符串
			StringEntity entity = new StringEntity(JSON.toJSONString(param), Consts.UTF_8.name());
			entity.setContentEncoding(Consts.UTF_8.name());
			entity.setContentType(ContentType.APPLICATION_JSON.toString());
			request.setEntity(entity);
			
			context.setRequestString(JSON.toJSONString(param));
	        context.setEndpoint(endpoint);
			context.setRequestTime(DateUtil.currentTime());
			
			response = httpClient.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			//一定要先调用此方法，否则无法关闭连接
			String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8.name());
			if(statusCode == HttpStatus.SC_OK){
				//打印响应结果
				logger.info(result);
			}
			/** 调用前先打印连接池使用情况  */
        	logger.info("HttpEmsRouteQryInvoker==pool,"+DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss")
        			+",connectionsInPool:"+connectionManager.getConnectionsInPool()
        			+",connectionsInUse:"+connectionManager.getConnectionsInUse()
        			+",maxConnectionsPerHost:"+connectionManager.getMaxConnectionsPerHost()
        			+",maxTotalConnections:"+connectionManager.getMaxTotalConnections());
//            int statusCode = client.executeMethod(postMethod);
//            if(TIME_OUT_STATUS==statusCode) throw new HttpTimeOutException("超时异常");
//            String resContent =  getResponseContent(postMethod.getResponseBodyAsStream());
//            //请求报文打印
//            logger.info("EMS返回报文: "+resContent);
//            //异常提示信息，单引号处理
//            if(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200!=statusCode){
//            	resContent = resContent.replace("'", "\'");
//            	resContent = resContent.replace("\n", ",");//部分浏览器报错，临时解决方案
//            }
            JSONObject respJson = JSON.parseObject(result);
            JSONObject resultJson = respJson.getJSONObject("result");
            String resultString=resultJson.toString(); 
            logger.info(resultString);
            Class<?> clazz = Class.forName(rspPath);
			ZteResponse resp = (ZteResponse) JsonUtil.fromJson(resultString, clazz);
            if(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200!=statusCode){
            	net.sf.json.JSONObject results = net.sf.json.JSONObject.fromObject(result);
            	resp.setError_code("-1");
            	resp.setError_msg("接口异常："+result);
            	logColMap.put("col2", "error");
            } else{
            	resp.setError_code("0000");
            	resp.setError_msg("成功");
				logColMap.put("col2", "success");
            }
	        context.setResultString(result);
            context.setResponeString(result);
            context.setResponseTime(DateUtil.currentTime());
            return resp;
		}catch(HttpException ex){
			ex.printStackTrace();
			throw ex;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new IOException("接口异常");
		}
		finally{
			if(response != null){
				EntityUtils.consume(response.getEntity());//自动释放连接
			}
		}
		
	}
	
	@Override
	public Object invokeTest(InvokeContext context) throws Exception {
		return invoke(context);
//		//链接超时
//		client.getHttpConnectionManager().getParams().setConnectionTimeout(http_post_time_out);  
//		//读取超时
//		client.getHttpConnectionManager().getParams().setSoTimeout(http_read_time_out);
//		Object params = context.getParameters();
//		String param_value = JsonUtil.toJson(params);
//		//String apptx = returnStr();
//		//String param_value1 = param_value.replace("}", ",\"apptx\":\""+apptx+"\"}");
//		//context.setRequestString(param_value1);
//        context.setEndpoint(endpoint);
//		context.setRequestTime(DateUtil.currentTime());
//		//设置消息头
//		GetMethod getMethod = new GetMethod(endpoint);
//		getMethod.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
//		getMethod.setRequestHeader("Accept-Language", "us");
//		getMethod.setRequestHeader("CONN_type", "SSL");
////		getMethod.setRequestHeader("version", "");//版本号，待补充
////		getMethod.setRequestHeader("authenticate", "");//鉴权码，待补充
//		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8"); 
//        Class<?> clazz=null;
//        ZteResponse resp = null;
//        try {
//        	clazz = Class.forName(rspPath);
//        	resp = (ZteResponse) JsonUtil.fromJson(transform, clazz);
//        	logger.info(resp);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }  finally {
//            // 关闭连接,释放资源
//           // httpclient.getConnectionManager().shutdown();
//        }
//		logColMap.put("col2", "success");
//		context.setResponeString(transform);
//		context.setResultString(transform);
//		context.setResponseTime(DateUtil.currentTime());
//		return resp;
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
	
	private void removeNotNeedKey(Map params){
		params.remove("dyn_field");
		params.remove("mqTopic");
		params.remove("inf_log_id");
		params.remove("base_order_id");
		params.remove("base_co_id");
	}
}

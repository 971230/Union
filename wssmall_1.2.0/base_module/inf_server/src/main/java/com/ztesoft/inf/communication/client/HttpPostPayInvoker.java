package com.ztesoft.inf.communication.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import params.ZteResponse;

import com.alibaba.fastjson.JSON;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.inf.communication.client.exception.HttpTimeOutException;

public class HttpPostPayInvoker extends Invoker{
	protected Logger logger = Logger.getLogger(HttpPostAipBSSNewInvoker.class);
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
		//链接超时
		client.getHttpConnectionManager().getParams().setConnectionTimeout(http_post_time_out);  
		//读取超时
		client.getHttpConnectionManager().getParams().setSoTimeout(http_read_time_out);
		Map paramMap = (Map)context.getParameters();
		Map m = context.getExtMap();
		
		String apptx = returnStr();
        context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		//设置消息头
		PostMethod postMethod = new PostMethod(endpoint);
		postMethod.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.setRequestHeader("Accept-Language", "us");
		postMethod.setRequestHeader("CONN_type", "SSL");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");   
		
		
		//添加参数
        Map userParams = null;
		if(!StringUtils.isEmpty(reqUser.getUser_param())){
			try{
				userParams = JSON.parseObject(reqUser.getUser_param(), Map.class);
			}catch(Exception ex){
				throw new Exception("参数配置有误");
			}
		}
        String app_id = String.valueOf(userParams.get("app_id"));
        String access_token = String.valueOf(userParams.get("access_token"));
        String version = String.valueOf(userParams.get("version"));
        //String sign_method = String.valueOf(userParams.get("sign_method"));
		
        if (paramMap == null) {
        	paramMap = new HashMap<String, String>();
        } else {
	        Map	paramMap_temp = paramMap;
	        paramMap = new HashMap<String, String>();
        	Set<String> set=paramMap_temp.keySet();
    		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
    			String key = (String) iterator.next();
    			Object value = paramMap_temp.get(key) ;
    			if(value!=null){
    				paramMap.put(key, value);
    			}else if(!StringUtils.isEmpty(key)){
    				//paramMap.remove(key);
    			}
    		}
        }
        
		//String charset = operation.getCharset();//参数编码
		//String secret = String.valueOf(userParams.get("secret"));//秘钥
		//对业务参的json串进行数字签名
		String busiContent = JSON.toJSONString(paramMap);
		//根据加密方式获取签名
		//String sign = this.getSign(busiContent, secret, sign_method, charset);
		//请求map
		Map reqMap = new HashMap();
		//AIP 必要参数
		reqMap.put("app_id", app_id);
		reqMap.put("access_token", access_token);
		reqMap.put("method", context.getOperationCode());
		reqMap.put("version", version);
		reqMap.put("timestamp", DateFormatUtils.formatDate("yyyyMMddHHmmss"));
		reqMap.put("format", "json");
		reqMap.put("content", busiContent);
		//请求报文打印
        logger.info("AiPBSS请求报文: "+JSON.toJSONString(reqMap));	
		 //创建参数队列
		List<NameValuePair> list = new ArrayList<NameValuePair>();
	    
		Set<String> set = reqMap.keySet();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String value = String.valueOf(reqMap.get(key)) ;
			list.add(new BasicNameValuePair(key, value));
			postMethod.addParameter(key,value);
		}
		context.setRequestString(list.toString());
		try {
			
        	/** 调用前先打印连接池使用情况  */
        	logger.info("HttpPostAopInvoker==pool,"+DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss")
        			+",apptx:"+apptx
        			+",connectionsInPool:"+connectionManager.getConnectionsInPool()
        			+",connectionsInUse:"+connectionManager.getConnectionsInUse()
        			+",maxConnectionsPerHost:"+connectionManager.getMaxConnectionsPerHost()
        			+",maxTotalConnections:"+connectionManager.getMaxTotalConnections());
            int statusCode = client.executeMethod(postMethod);
            if(TIME_OUT_STATUS == statusCode) throw new HttpTimeOutException("超时异常");
            String resContent = getResponseContent(postMethod.getResponseBodyAsStream());
            String res = resContent;
           /* if(StringUtils.equals(resContent, "{\"code\":\"9999\"}")){
            	resContent = "{\"code\":\"9999\",\"detail\":\"未知(aop未返回错误描述)\"}";
            	res = "接口原报文:"+res+";处理之后的报文:"+resContent;
            }*/
            //请求报文打印
            logger.info("AiPBSS返回报文: "+resContent);	
            //异常提示信息，单引号处理
/*            if(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200!=statusCode){
//            	resContent = "{\"code\":\"9999\",\"detail\":\"未知(aop@!@#$%^&~:{}|?><<*()[]/.,';未返回错误描述)\"}";
            	resContent = resContent.replace("'", "\'");
            	resContent = resContent.replace("\n", ",");//部分浏览器报错，临时解决方案
            }*/
            Map resMap = JSON.parseObject(resContent);
            Map result = (Map) resMap.get("result");
            ZteResponse resp = null;
            if (result != null) {
            	resContent = JSON.toJSONString(result);
                //resp  节点处理
                resContent = resContent.replace("\"resp\":", "\"respJson\":");
                
                int bb = resContent.indexOf("respJson\":\"\"");
                if(bb!=-1){//对respJson结果为空的置为null
                	resContent = resContent.replace("respJson\":\"\"", "respJson\": null");
                }
    	        Class<?> clazz = Class.forName(rspPath);
    			resp = (ZteResponse) JsonUtil.fromJson(resContent, clazz);
    			if (resp == null) {
    				throw new Exception(resContent+"转为"+clazz+"失败");
    			}
    			resp.setError_msg("成功");
            } else {
            	throw new Exception("能开调用失败："+resContent);
            }
			logColMap.put("col2", "success");
			
	        context.setResultString(res);
            context.setResponeString(res);
            context.setResponseTime(DateUtil.currentTime());
			return resp;
        }catch(HttpException ex){
			ex.printStackTrace();
			throw ex;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new IOException("异常错误："+(ex.getMessage() == null? ex.toString():ex.getMessage()));
		} finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
	}
	
	@Override
	public Object invokeTest(InvokeContext context) throws Exception {
		//return invoke( context);
		//链接超时
		
		client.getHttpConnectionManager().getParams().setConnectionTimeout(http_post_time_out);  
		//读取超时
		client.getHttpConnectionManager().getParams().setSoTimeout(http_read_time_out);
		Object params = context.getParameters();
		String param_value = JsonUtil.toJson(params);
		String apptx=returnStr();
		//String param_value1 = param_value.replace("}", ",\"apptx\":\""+apptx+"\"}");
		context.setRequestString(param_value);
        context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		//设置消息头
		PostMethod postMethod = new PostMethod(context.getEndpoint());
		postMethod.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.setRequestHeader("Accept-Language", "us");
		postMethod.setRequestHeader("CONN_type", "SSL");
        Class<?> clazz=null;
        ZteResponse resp = null;
        try {
        	 clazz = Class.forName(rspPath);
        	 resp = (ZteResponse) JsonUtil.fromJson(transform, clazz);
        	 logger.info(resp);
          
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }  finally {
            // 关闭连接,释放资源
           // httpclient.getConnectionManager().shutdown();
        }
		logColMap.put("col2", "success");
		context.setResponeString(transform);
		context.setResultString(transform);
		context.setResponseTime(DateUtil.currentTime());
		return resp;
	}
	
	public String returnStr(){
	 	DateFormat tempDF = new SimpleDateFormat("yyyyMMddHHmmss");   
		String str=tempDF.format(new Date());
		String model = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";  
        char[] m = model.toCharArray(); 
		for(int j=0;j<6;j++){
			char c = m[(int)(Math.random()*62)];  
			str = str + c;  
		}
		return str;
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
	
	private String getSign(String busiContent,String secret,String sign_method, String charset) throws Exception{
		String sign = "";
		logger.info("busiContent: "+busiContent);
		logger.info("secret: "+secret);
		logger.info("charset: "+charset);
		//如果是MD5的加密方式
		if ("MD5".equalsIgnoreCase(sign_method)) {
			if (StringUtils.isEmpty(charset)) {
				sign = MD5Util.MD5(secret + busiContent + secret);
			} else {
				sign = MD5Util.MD5(secret + busiContent + secret, charset);
			}
		}
		logger.info("sign: "+sign);
		return sign;//密钥;
	}
	
}

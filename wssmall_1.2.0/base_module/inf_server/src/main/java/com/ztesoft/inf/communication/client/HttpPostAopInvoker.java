package com.ztesoft.inf.communication.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import params.ZteResponse;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.inf.communication.client.exception.HttpTimeOutException;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import net.sf.json.JSONObject;

public class HttpPostAopInvoker extends Invoker{
	protected Logger logger = Logger.getLogger(HttpPostAopInvoker.class);
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
		Object params = context.getParameters();
		Map m = context.getExtMap();
		String param_value = JsonUtil.toJson(params);
		String apptx=returnStr();
        context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		//设置消息头
//		endpoint = "http://127.0.0.1:10081/aop/aopservlet";
//		endpoint = "http://127.0.0.1:10080/aop/test";
		PostMethod postMethod = new PostMethod(endpoint);
		postMethod.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.setRequestHeader("Accept-Language", "us");
		postMethod.setRequestHeader("CONN_type", "SSL");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");   
		 // 创建参数队列
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("appkey", reqUser.getUser_code()));//
	    list.add(new BasicNameValuePair("method", context.getOperationCode()));//
	    list.add(new BasicNameValuePair("apptx", apptx));         
        if(m.get("bizkey")!=null){
        	list.add(new BasicNameValuePair("bizkey",m.get("bizkey").toString()));
        }  
        list.add(new BasicNameValuePair("timestamp", DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss")));
        list.add(new BasicNameValuePair("msg",param_value)); 
        //请求报文打印
        logger.info("AOP请求报文: "+list.toString());	
		context.setRequestString(list.toString());
		try {
        	postMethod.addParameter("appkey", reqUser.getUser_code());
        	postMethod.addParameter("method", context.getOperationCode());
        	postMethod.addParameter("apptx", apptx);
        	if(m.get("bizkey")!=null){
        		postMethod.addParameter("bizkey",m.get("bizkey").toString());
            }
        	postMethod.addParameter("timestamp", DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss"));
        	postMethod.addParameter("msg",param_value);
        	/** 调用前先打印连接池使用情况  */
        	logger.info("HttpPostAopInvoker==pool,"+DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss")
        			+",apptx:"+apptx
        			+",connectionsInPool:"+connectionManager.getConnectionsInPool()
        			+",connectionsInUse:"+connectionManager.getConnectionsInUse()
        			+",maxConnectionsPerHost:"+connectionManager.getMaxConnectionsPerHost()
        			+",maxTotalConnections:"+connectionManager.getMaxTotalConnections());
            int statusCode = client.executeMethod(postMethod);
            if(TIME_OUT_STATUS==statusCode)throw new HttpTimeOutException("超时异常");
            String resContent =  getResponseContent(postMethod.getResponseBodyAsStream());
            String res = resContent;
            if(StringUtils.equals(resContent, "{\"code\":\"9999\"}")){
            	resContent = "{\"code\":\"9999\",\"detail\":\"未知(aop未返回错误描述)\"}";
            	res = "接口原报文:"+res+";处理之后的报文:"+resContent;
            }
//            resContent = "{\"detail\":\"[INDETERMINATE]Utility.cpp:207,CRMException-300041: TCS_ChangeServiceReg执行异常:\n[WARNING]TradeCheckAfterTrade.cpp:2245,CRMException-300041: 业务登记后条件判断:退出产品校验TAG校验异常！\nCaused by: [WARNING]TradeCheckAfterTrade.cpp:4902,CRMException-300041: 产品：\\\"4G省内闲时流量包0元（广东）\\\"业务包：\\\"4G省内闲时流量包\\\"最多选择1个元素、业务包：\\\"4G省内闲时流量包0折优惠资费包(粤)\\\"最多选择1个元素，业务无法继续！调用流水TRANS_IDO是：AOP2026060302482598057979\",\"code\":\"1020\"}";
            //请求报文打印
            logger.info("AOP返回报文: "+resContent);	
            //异常提示信息，单引号处理
            if(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200!=statusCode){
//            	resContent = "{\"code\":\"9999\",\"detail\":\"未知(aop@!@#$%^&~:{}|?><<*()[]/.,';未返回错误描述)\"}";
            	resContent = resContent.replace("'", "\'");
            	resContent = resContent.replace("\n", ",");//部分浏览器报错，临时解决方案
            }
            //add zhangjun 能开返回的不是透传的，需要做截取处理
            String code  = "";//返回编码
            if(resContent.indexOf("返回的报文：")!=-1){
             	resContent=resContent.substring(resContent.indexOf("返回的报文：")+6, resContent.length()-2);
             	resContent=resContent.replace("\\\"", "\"");
             	if(StringUtils.equals(EcsOrderConsts.CERT_CHECK, context.getOperationCode())){
             		resContent=resContent.replace("detail", "error_msg");
             	}
             	statusCode=EcsOrderConsts.AOP_HTTP_STATUS_CODE_560;//临时处理，都当做业务异常
             	JSONObject json_res = JSONObject.fromObject(resContent); //国政通异常校验返回总部需要匹配编码
             	code =  json_res.getString("code");
             	logger.info("截取后的报文:"+resContent);
             }
            if(resContent.indexOf("Error 404：")!=-1){
            	resContent = "{\"code\":\"404\",\"detail\":\"请求的资源不可用\"}";
            	resContent=resContent.replace("\\\"", "\"");
            	statusCode = 404;
            	JSONObject json_res = JSONObject.fromObject(resContent);
            	code =  json_res.getString("code");
            }
	        Class<?> clazz = Class.forName(rspPath);
			ZteResponse resp = (ZteResponse) JsonUtil.fromJson(resContent, clazz);
			resp.setError_code(""+statusCode);
			String msg = "";
			if(!StringUtils.isEmpty(resp.getError_msg())){
				msg += ":"+resp.getError_msg()+"*"+code;
			}
	        switch (statusCode) {
				case EcsOrderConsts.AOP_HTTP_STATUS_CODE_200:
					logColMap.put("col2", "success");
					resp.setError_msg("成功");
					break;
				case EcsOrderConsts.AOP_HTTP_STATUS_CODE_560:
					resp.setError_msg("业务异常"+msg);
					logColMap.put("col2", "error");
					break;
				case EcsOrderConsts.AOP_HTTP_STATUS_CODE_600:
					resp.setError_msg("系统异常"+msg);
					logColMap.put("col2", "error");
					break;
				case EcsOrderConsts.AOP_HTTP_STATUS_CODE_440:
					resp.setError_msg("业务异常"+msg);
					logColMap.put("col2", "error");
					break;
				default:
					resp.setError_msg("网络异常"+msg);
					logColMap.put("col2", "error");
					break;
			}
	        context.setResultString(res);
            context.setResponeString(res);
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
		//链接超时
		client.getHttpConnectionManager().getParams().setConnectionTimeout(http_post_time_out);  
		//读取超时
		client.getHttpConnectionManager().getParams().setSoTimeout(http_read_time_out);
		Object params = context.getParameters();
		String param_value = JsonUtil.toJson(params);
		String apptx=returnStr();
		String param_value1 = param_value.replace("}", ",\"apptx\":\""+apptx+"\"}");
		context.setRequestString(param_value1);
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

}

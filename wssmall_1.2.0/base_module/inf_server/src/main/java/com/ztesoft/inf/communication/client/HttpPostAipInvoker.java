package com.ztesoft.inf.communication.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import zte.net.ecsord.params.ecaop.resp.AIPResponse;

import com.alibaba.fastjson.JSON;
import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.inf.communication.client.exception.HttpTimeOutException;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class HttpPostAipInvoker extends Invoker{
	protected Logger logger = Logger.getLogger(HttpPostAipInvoker.class);
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
		
		
		//添加参数
        Map userParams = null;
		if(!StringUtils.isEmpty(reqUser.getUser_param())){
			try{
				userParams = JSON.parseObject(reqUser.getUser_param(), Map.class);
			}catch(Exception ex){
				throw new Exception("参数配置有误");
			}
		}
        String v = String.valueOf(userParams.get("v"));
        String secret = String.valueOf(userParams.get("secret"));
        String sign=null;//获取sign
        String sign_method = String.valueOf(userParams.get("sign_method"));
        String session = String.valueOf(userParams.get("session"));
        
		
		
		
		Map<String,String> paramMap=new HashMap<String, String>();
		//AIP 必要参数
		paramMap.put("method", "ecaop.trades.aop.transfer.sub");
		paramMap.put("timestamp", DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss"));
		paramMap.put("format", "json");
		paramMap.put("app_key", reqUser.getUser_code());
		paramMap.put("v", v);
		
		paramMap.put("sign_method", sign_method);
		paramMap.put("session", session);
		
		
		
		//if(is_transmission){//AOP 本身的参数
		paramMap.put("msg", param_value);
		paramMap.put("bizkey", Const.getStrValue(m, "bizkey"));
		paramMap.put("api", context.getOperationCode());
		paramMap.put("apptx", apptx);
		sign=this.getSign(paramMap, secret);
		paramMap.put("sign", sign);
		 //创建参数队列
		List<NameValuePair> list = new ArrayList<NameValuePair>();
	    
		Set<String> set=paramMap.keySet();
		String str="";
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String value = paramMap.get(key);
			list.add(new BasicNameValuePair(key, value));//
			postMethod.addParameter(key,value);
			
			//str=str+key+"="+java.net.URLEncoder.encode(value,"UTF-8")+"&";
		}
        //请求报文打印
		 logger.info("str: "+str);	
		 logger.info("bizkey: "+Const.getStrValue(m, "bizkey"));	
		 logger.info("流水号: "+apptx);	
		 logger.info("AOP请求报文msg: "+param_value);
        logger.info("AOP请求报文: "+list.toString());	
		context.setRequestString(list.toString());
		try {
			
        	/** 调用前先打印连接池使用情况  */
        	/*logger.info("HttpPostAopInvoker==pool,"+DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss")
        			+",apptx:"+apptx
        			+",connectionsInPool:"+connectionManager.getConnectionsInPool()
        			+",connectionsInUse:"+connectionManager.getConnectionsInUse()
        			+",maxConnectionsPerHost:"+connectionManager.getMaxConnectionsPerHost()
        			+",maxTotalConnections:"+connectionManager.getMaxTotalConnections());*/
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
            
            AIPResponse aipResp = JsonUtil.fromJson(resContent, AIPResponse.class);
            String resContent_new=aipResp.getResp();
            String aipRespCode=aipResp.getCode();//code
            String msg=aipResp.getMsg();//msg
            
            
            logger.info("#############################################################################");
	        Class<?> clazz = Class.forName(rspPath);
			ZteResponse resp =null;
			resp=(ZteResponse) clazz.newInstance();
			
            if(EcsOrderConsts.AIP_STATUS_CODE_SUCC.equals(aipRespCode)){//成功
            	resp = (ZteResponse) JsonUtil.fromJson(resContent_new, clazz);
            	
            	 resp.setError_code(""+statusCode);//临时
            }else{//失败
            	resContent=resContent.replace("\"msg\"", "\"detail\"");
            	resContent=resContent.replace(",\"resp\":\"\"","" );
            	resp = (ZteResponse) JsonUtil.fromJson(resContent, clazz);
            	resp.setError_code("-1");
            	statusCode=EcsOrderConsts.AOP_HTTP_STATUS_CODE_560;//临时
            	resp.setError_msg(msg);
            }
           
			
	        switch (statusCode) {
				case EcsOrderConsts.AOP_HTTP_STATUS_CODE_200:
					logColMap.put("col2", "success");
					resp.setError_msg("成功");
					break;
				case EcsOrderConsts.AOP_HTTP_STATUS_CODE_560:
					resp.setError_msg("业务异常");
					logColMap.put("col2", "error");
					break;
				case EcsOrderConsts.AOP_HTTP_STATUS_CODE_600:
					resp.setError_msg("系统异常");
					logColMap.put("col2", "error");
					break;
				case EcsOrderConsts.AOP_HTTP_STATUS_CODE_440:
					resp.setError_msg("业务异常");
					logColMap.put("col2", "error");
					break;
				default:
					resp.setError_msg("网络异常");
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
	
	private String getSign(Map<String,String> paramMap,String secret ) throws Exception{
		String str="";//拼合的字符串
		List<String> arryList=new ArrayList<String>();
		Set<String> set=paramMap.keySet();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			arryList.add(key);//
			
		}
		Collections.sort(arryList);// 
		for (String key : arryList) {
			if("msg".equals(key)){
				str=str+key+java.net.URLDecoder.decode(paramMap.get(key),"UTF-8")+"&";
			}else{
				str=str+key+paramMap.get(key)+"&";
			}
		}
		str=secret+str.substring(0, str.length()-1)+secret;
		//加密
		//logger.info("sign: 字符串   "+str);
		str=MD5Util.MD5(str);
		//logger.info("sign: "+str);
		return str;//密钥;
	}

}

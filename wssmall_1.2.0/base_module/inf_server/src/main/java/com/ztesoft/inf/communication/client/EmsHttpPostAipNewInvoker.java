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

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import params.ZteResponse;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;
import com.alibaba.fastjson.JSON;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.XmlUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.inf.communication.client.exception.HttpTimeOutException;

/**
 * --sognqi
 */
public class EmsHttpPostAipNewInvoker extends Invoker{
	protected Logger logger = Logger.getLogger(EmsHttpPostAipNewInvoker.class);
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
		
		//同步物流公司时，拼装printData节点
		List printData = (List) MapUtils.getObject(paramMap, "printDatas");
		if(printData != null && printData.size() > 0 ) {
			Map newParamMap = new HashMap();
			
			newParamMap.put("printData", printData.get(0));
			
			paramMap.put("printDatas",newParamMap);
		}
		
		
		//拼装xml报文    节点XMLInfo不同
		String xml = XmlUtils.mapToXml(paramMap, "XMLInfo");
		
		xml = xml.replace("appKeySub", "appKey");
//		xml = xml.replace("<printDatas>", "<printDatas>"+"\n"+"   <printData>");
//		
//		xml = xml.replace("</printDatas>", " </printData>"+"\n"+"</printDatas>");
		
		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xml;
		logger.info("EMS-AiPBSS请求xml: "+xml);	
		//BASE64加密
		BASE64Encoder en = new BASE64Encoder();
		String ciphertext = en.encode(xml.getBytes());
		
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
        String sign_method = String.valueOf(userParams.get("sign_method"));
		
        
//        if (paramMap == null) {
//        	paramMap = new HashMap<String, String>();
//        } else {
//        	Map paramMap_temp = paramMap;
//        	paramMap = new HashMap<String, Map>();
//        	Set<String> set = paramMap_temp.keySet();
////        	Map map = new HashMap();
//        	for (Iterator iterator = set.iterator(); iterator.hasNext();) {
//        		String key = (String)iterator.next();
//        		Map valueMap = (Map) paramMap_temp.get(key);
//        		Map map = new HashMap();
//        		if(null != valueMap) {
//        			Set<String> vSet = valueMap.keySet();
//        			for (Iterator vIterator = vSet.iterator(); vIterator.hasNext();) {
//        				String vKey = (String) vIterator.next();
//        				String vValue = String.valueOf(valueMap.get(vKey));
//        				
//        				if(null != vValue) {
//        					map.put(vKey, vValue);
//        				}
//        			}
//        		}
//        		paramMap.put(key, map);
//        	}
//        }
		String charset = operation.getCharset();//参数编码
		String secret = String.valueOf(userParams.get("secret"));//秘钥
		//对业务参的json串进行数字签名
		Map xmlMap = new HashMap();
		xmlMap.put("xml", ciphertext);
		String busiContent = JSON.toJSONString(xmlMap);
		//根据加密方式获取签名
		String sign = this.getSign(busiContent, secret, sign_method, charset);
		//请求map
		Map reqMap = new HashMap();
		//AIP 必要参数
		reqMap.put("sign", sign);
		reqMap.put("app_id", app_id);
		reqMap.put("access_token", access_token);
		reqMap.put("method", context.getOperationCode());
		reqMap.put("version", version);
		reqMap.put("timestamp", DateFormatUtils.formatDate("yyyyMMddHHmmss"));
		reqMap.put("format", "json");
		reqMap.put("sign_method", sign_method);
		reqMap.put("content", busiContent);
		//请求报文打印
        logger.info("EMS-AiPBSS请求报文: "+JSON.toJSONString(reqMap));	
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
            logger.info("EMS-AiPBSS返回报文: "+resContent);	
            //异常提示信息，单引号处理
/*            if(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200!=statusCode){
//            	resContent = "{\"code\":\"9999\",\"detail\":\"未知(aop@!@#$%^&~:{}|?><<*()[]/.,';未返回错误描述)\"}";
            	resContent = resContent.replace("'", "\'");
            	resContent = resContent.replace("\n", ",");//部分浏览器报错，临时解决方案
            }*/
            Map resMap = JSON.parseObject(resContent);
            String result = (String) resMap.get("result");
         
            //解密
            BASE64Decoder de = new BASE64Decoder();
            byte[] decoder = de.decodeBuffer(result);
            String plaintext = new String(decoder, "UTF-8");
            logger.info("EMS-AiPBSS返回报文: "+plaintext);	
            ZteResponse resp = null;
            Map resultMap = XmlUtils.xmlToMap(plaintext);
            
            String resultDetail = JSON.toJSONString(resultMap);
            
            resultDetail = resultDetail.replace("result", "resultCode");
//            resultDetail = resultDetail.replace("\"resp\":", "\"respJson\":");
//            
////          //resp  节点处理
//            resultDetail = resultDetail.replace("\"resp\":", "\"respJson\":");
//          
//          int bb = resultDetail.indexOf("respJson\":\"\"");
//          if(bb!=-1){//对respJson结果为空的置为null
//        	  resultDetail = resultDetail.replace("respJson\":\"\"", "respJson\": null");
//          }
	        Class<?> clazz = Class.forName(rspPath);
			resp = (ZteResponse) JsonUtil.fromJson(resultDetail, clazz);
            
//            Class<?> clazz = Class.forName(rspPath);
//			resp = (ZteResponse) JsonUtil.fromJson(resultDetail, clazz);
			
			context.setResultString(resultDetail);
            context.setResponeString(resultDetail);
            context.setResponseTime(DateUtil.currentTime());
            
			return resp;
//            String errorCodeString = MapUtils.getString(resultMap, "result");
//            int errorCode = MapUtils.getIntValue(resultMap, "result");
////            boolean errorCodeBool = MapUtils.getBooleanValue(resultMap, "result");
//            
//            if(errorCode == 1) {
//            	resp.setError_code("0000");
//            	resp.setError_msg("成功");
//            	logColMap.put("col2", "success");
//            }else if(errorCode == 0 ){
//            	resp.setError_code("-9999");
//            	String errorMsg = MapUtils.getString(resultMap, "errorDesc");
////            	resp.setError_msg(MapUtils.getString(resultMap, "errorDesc"));
//            	if(MapUtils.getString(resultMap, "errorCode").equals("E028")) {
//            		errorMsg = errorMsg + (String) MapUtils.getMap(resultMap, "errorDetail").get("dataError");
//            	}
//            	logColMap.put("col2", "error");
//            }
//            
//            return resp;
//           int errorCode1 = MapUtils.geti
//            DesEncrypt wclwww = new DesEncrypt();
//            String wwwwwwww  =  wclwww.decrypt(result);
           
            //对返回内容进行处理
            
            
//            if (result != null) {
//            	resContent = JSON.toJSONString(result);
//                //resp  节点处理
//                resContent = resContent.replace("\"resp\":", "\"respJson\":");
//                
//                int bb = resContent.indexOf("respJson\":\"\"");
//                if(bb!=-1){//对respJson结果为空的置为null
//                	resContent = resContent.replace("respJson\":\"\"", "respJson\": null");
//                }
//    	        Class<?> clazz = Class.forName(rspPath);
//    			resp = (ZteResponse) JsonUtil.fromJson(resContent, clazz);
//    			if (resp == null) {
//    				throw new Exception(resContent+"转为"+clazz+"失败");
//    			}
//    			resp.setError_msg("成功");
//            } else {
//            	throw new Exception("能开调用失败："+resContent);
//            }
			
			
	        
//			return resp;
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
	
	public static void main(String[] args) {
//		Map map = new HashMap();
//		map.put("sysAccount", "123456789w");
//		map.put("passWord", "01234556789z");
//		map.put("appKe", "test");
//		
//		List<Map> printDatas = new ArrayList<Map>();
//		Map printData = new HashMap();
//		printData.put("billno","977192833");
//		printData.put("dataType","1");//时间类型
//		
//		printDatas.add(printData);
//		
//		map.put("printDatas", printDatas);
//		
//		
//		String xml = XmlUtils.mapToXml(map,"XMLInfo");
//		
//		logger.info();
		
		
		String temp = "123890sadjasdak";
		
		StringUtils.replace(temp, "123", "wwwwwww");;
		temp = temp.replace("123", "www");
		//logger.info();
	}
}

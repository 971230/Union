package com.ztesoft.inf.communication.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.powerise.ibss.util.Base64;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.inf.communication.client.exception.HttpTimeOutException;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
/**
 * 
 * @author zou.qh
 * <p>
 * 	EMS http接口
 * </p>
 * 
 */
public class HttpEmsInvoker extends Invoker {
	protected Logger logger = Logger.getLogger(HttpEmsInvoker.class);
	public static final String DEFAULTCHARSET = "UTF-8";
	public static final String version = "V0.1";
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
    private static HttpClient client = new HttpClient(connectionManager);
	
	@Override
	public Object invoke(InvokeContext context) throws Exception {
		net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(reqUser.getUser_param());
		String secret = obj.getString("secret");
		String version = obj.getString("version");
		String format = obj.getString("format");
		//链接超时
		client.getHttpConnectionManager().getParams().setConnectionTimeout(http_post_time_out);  
		//读取超时
		client.getHttpConnectionManager().getParams().setSoTimeout(http_read_time_out);
		Map params = (Map) context.getParameters();
		Map m = context.getExtMap();
		this.removeNotNeedKey(params);
		
        context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		//设置消息头
		PostMethod postMethod = new PostMethod(endpoint);
		postMethod.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.setRequestHeader("Accept-Language", "us");
		postMethod.setRequestHeader("CONN_type", "SSL");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");  
		/*postMethod.addParameter("timestamp", DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss"));
		postMethod.addParameter("version", version);
    	postMethod.addParameter("method", context.getOperationCode());
    	postMethod.addParameter("format", format);
    	postMethod.addParameter("app_key", reqUser.getUser_code());
    	postMethod.addParameter("charset", DEFAULTCHARSET);
    	postMethod.addParameter("authorization", reqUser.getUser_pwd());*/
		try {
			//计算sign签名
			params.put("timestamp", DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss"));
			params.put("version", version);
			params.put("method", context.getOperationCode());
			params.put("format", format);
			params.put("app_key", reqUser.getUser_code());
			params.put("charset", DEFAULTCHARSET);
			params.put("authorization", reqUser.getUser_pwd());
	    	
	    	String signStr = this.getSortParams(params)+secret;
	    	logger.info("sign签名参数："+signStr);
	    	String sign = this.sign(signStr, DEFAULTCHARSET);
	    	params.put("sign", sign);
	    	
	    	List<NameValuePair> list = new ArrayList<NameValuePair>();
			Set<String> set=params.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				String value = String.valueOf(params.get(key));
				list.add(new BasicNameValuePair(key, value));//
				postMethod.addParameter(key,value);
			}
	    	
        	/** 调用前先打印连接池使用情况  */
        	logger.info("HttpEmsInvoker==pool,"+DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss")
        			+",sign:"+sign
        			+",connectionsInPool:"+connectionManager.getConnectionsInPool()
        			+",connectionsInUse:"+connectionManager.getConnectionsInUse()
        			+",maxConnectionsPerHost:"+connectionManager.getMaxConnectionsPerHost()
        			+",maxTotalConnections:"+connectionManager.getMaxTotalConnections());
            int statusCode = client.executeMethod(postMethod);
            if(TIME_OUT_STATUS==statusCode) throw new HttpTimeOutException("超时异常");
            String resContent =  getResponseContent(postMethod.getResponseBodyAsStream());
            //String resContent = "{\"requestno\":\"061603181223138041\",\"count\":1,\"mailnums\":\"9700106486900\",\"success\":\"T\"}";
            //请求报文打印
            logger.info("EMS返回报文: "+resContent);	
            //异常提示信息，单引号处理
            if(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200!=statusCode){
            	resContent = resContent.replace("'", "\'");
            	resContent = resContent.replace("\n", ",");//部分浏览器报错，临时解决方案
            }
            Class<?> clazz = Class.forName(rspPath);
			ZteResponse resp = (ZteResponse) JsonUtil.fromJson(resContent, clazz);
            if(resContent.contains("\"success\":\"F\"") || (resContent.contains("\"success\":\"T\"")&&resContent.contains("\"count\":0"))){
            	net.sf.json.JSONObject result = net.sf.json.JSONObject.fromObject(resContent);
            	resp.setError_code("-1");
            	resp.setError_msg("获取物流单号失败");
            	logColMap.put("col2", "error");
            }
            else{
            	resp.setError_code("0000");
				resp.setError_msg("执行成功");
				logColMap.put("col2", "success");
            }
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
		//链接超时
		client.getHttpConnectionManager().getParams().setConnectionTimeout(http_post_time_out);  
		//读取超时
		client.getHttpConnectionManager().getParams().setSoTimeout(http_read_time_out);
		Object params = context.getParameters();
		String param_value = JsonUtil.toJson(params);
		//String apptx = returnStr();
		//String param_value1 = param_value.replace("}", ",\"apptx\":\""+apptx+"\"}");
		//context.setRequestString(param_value1);
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
	
	private String getResponseContent(final InputStream input) throws IOException {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(input, "utf-8"));
		StringBuilder buffer = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
        return buffer.toString();
    }
	
	private String sign(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			charset = DEFAULTCHARSET;
		}
		String sign = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			sign = Base64.encode(md5.digest(content.getBytes(charset)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sign;
	}

	private String getSortParams(Map<String, String> params) {
		// 删掉sign参数
		params.remove("sign");
		String content = "";
		Set<String> keySet = params.keySet();
		List<String> keyList = new ArrayList<String>();
		for (String key : keySet) {
			Object obj = params.get(key);
			if(obj != null){
				keyList.add(key);
			}
			//将值为空的参数排除
			//if (!StringUtil.isEmpty(value)) {
				//keyList.add(key);
			//}
		}
		Collections.sort(keyList, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int length = Math.min(o1.length(), o2.length());
				for (int i = 0; i < length; i++) {
					char c1 = o1.charAt(i);
					char c2 = o2.charAt(i);
					int r = c1 - c2;
					if (r != 0) {
						// char值小的排前边
						return r;
					}
				}
				// 2个字符串关系是str1.startsWith(str2)==true
				// 取str2排前边
				return o1.length() - o2.length();
			}
		});
		//将参数和参数值按照排序顺序拼装成字符串
		for (int i = 0; i < keyList.size(); i++) {
			String key = keyList.get(i);
			content += key + String.valueOf(params.get(key));
		}
		return content;
	}
	
	private void removeNotNeedKey(Map params){
		params.remove("dyn_field");
		params.remove("mqTopic");
		params.remove("inf_log_id");
		params.remove("base_order_id");
		params.remove("base_co_id");
	}
}

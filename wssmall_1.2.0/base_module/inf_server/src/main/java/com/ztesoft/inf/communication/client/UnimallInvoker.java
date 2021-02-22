package com.ztesoft.inf.communication.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.params.CoreConnectionPNames;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.resp.CheckIDECAOPResponse;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.inf.communication.client.exception.HttpTimeOutException;
/**
 * 
 * @author sguo
 * <p>
 * 		通过从新接口机调用其他接口,暂时仅支持通过接口机调用国政通接口
 * </p>
 * 
 */
public class UnimallInvoker extends Invoker {
	private final static int CONNECT_TIMEOUT = 15000;
	private final static int RESPONSE_TIMEOUT = 600000;
	
    /**
     * 假冒的IE
     */
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
    
    static{
        HttpClientParams params =client.getParams();
        params.setHttpElementCharset("UTF-8");
        params.setContentCharset("UTF-8");
        //设置连接超时,单位为毫秒
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, UnimallInvoker.CONNECT_TIMEOUT); 
        //设置请求超时
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, UnimallInvoker.RESPONSE_TIMEOUT);
    }
	
    
	@Override
	public Object invoke(InvokeContext context) throws Exception {
		String serverUrl = endpoint;
		context.setEndpoint(endpoint);
		Object params = context.getParameters();
		String param_value = JsonUtil.toJson(params);
		context.setRequestTime(DateUtil.currentTime());
		context.setRequestString(param_value);
//		System.out.println(param_value+"=====================================UnimallInvoker invoke(InvokeContext context)");
		String responeString = postStream2(serverUrl,param_value);
		context.setResponeString(responeString);
		context.setResultString(responeString);
		context.setResponseTime(DateUtil.currentTime());
		return setCardInfo(responeString) ;
	}

	
	@Override
	public Object invokeTest(InvokeContext context) throws Exception {
		context.setEndpoint(endpoint);
		Object params = context.getParameters();
		String param_value = JsonUtil.toJson(params);
		param_value = StringUtils.capitalized(param_value);
		context.setRequestString(param_value);
		// 准备平台参数参数
		Map<String, Object> inputMap = new HashMap<String, Object>();
		//jsonString 为业务参数，请求时传入
		inputMap.put("msg", param_value);
		inputMap.put("apptx", getApptx());
		inputMap.put("timestamp", DateUtil.formatDate(new Date()));
		// 设置超时时间
		// 执行调用
		ZteResponse resp = null;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(rspPath);
			context.setRequestTime(DateUtil.currentTime());
			resp = (ZteResponse) JsonUtil.fromJson(transform, clazz);
			context.setResponseTime(DateUtil.currentTime());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		context.setResultString(transform);
		context.setResponeString(transform);
		return resp;
	}
	
	//获得请求流水号
	public static String getApptx(){
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH); 
		int date = c.get(Calendar.DATE); 
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
		int second = c.get(Calendar.SECOND);
		return year  + "" +  month + ""+  date+ "" + hour+ "" + minute + ""+ second+Math.round(Math.random()*8999+1000);
	}
	
    /**
     * 将数据提交到指定url，不增加任何额外参数
     * @param url
     * @param content
     * @return
     * @throws IOException
     */
    private String postStream2(String url, String content) throws Exception {
        PostMethod postMethod = new PostMethod(url);
        try {
            byte[] bytes = content.getBytes("UTF-8");
            ByteArrayRequestEntity bare = new ByteArrayRequestEntity(bytes);
            postMethod.setRequestEntity(bare);
        	/** 调用前先打印连接池使用情况  */
        	System.out.println("UnimallInvoker==pool,"+DateFormatUtils.formatDate("yyyy-MM-dd HH:mm:ss")
        			+",connectionsInPool:"+connectionManager.getConnectionsInPool()
        			+",connectionsInUse:"+connectionManager.getConnectionsInUse()
        			+",maxConnectionsPerHost:"+connectionManager.getMaxConnectionsPerHost()
        			+",maxTotalConnections:"+connectionManager.getMaxTotalConnections());
            int statusCode = client.executeMethod(postMethod);
            if(TIME_OUT_STATUS==statusCode)throw new HttpTimeOutException("超时异常");
	        if(OK_STATUS_200!=statusCode && OK_STATUS_201!=statusCode && OK_STATUS_202!=statusCode && OK_STATUS_203!=statusCode && OK_STATUS_204!=statusCode && OK_STATUS_205!=statusCode && OK_STATUS_206!=statusCode)
	        	throw new HttpException("接口异常");
            String resContent =  getResponseContent(postMethod.getResponseBodyAsStream());
            postMethod.releaseConnection();
            return resContent;
        } catch (Throwable e) {
        	e.printStackTrace();
            throw new Exception("接口异常");
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
    }
    
    //读取数据流，为字附串
    private String getResponseContent(final InputStream input) throws IOException {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(input, "utf-8"));
		StringBuilder buffer = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
        return buffer.toString();
    }
    
	/**
	 * 解析验证响应报文，返回CardInfo对象
	 * @param obj
	 * @param cardInfo
	 * @return
	 */
	private static CheckIDECAOPResponse setCardInfo(String json){
		CheckIDECAOPResponse resp = new CheckIDECAOPResponse();
		System.out.println("json："+json);	
		JSONObject obj = JSONObject.fromObject(json);
		int statusCode = Integer.parseInt(obj.getString("statusCode"));
		if(statusCode == 200){
			JSONObject outObj = obj.getJSONObject("out");
			if (outObj.containsKey("certId")) {
				resp.setCertId(outObj.getString("certId"));
			}
			if (outObj.containsKey("certName")) {
				resp.setCertName(outObj.getString("certName"));
			}
			if (outObj.containsKey("photo")) {
				resp.setPhoto(null !=outObj.get("photo")?outObj.getString("photo"):"");
			}
			if (outObj.containsKey("sex")) {
				resp.setSex(outObj.getString("sex"));
			}
			if (outObj.containsKey("birthday")) {
				resp.setBirthday(outObj.getString("birthday"));
			}
			if (outObj.containsKey("nation")) {
				resp.setNation(outObj.getString("nation"));
			}
			if (outObj.containsKey("addr")) {
				resp.setAddr(outObj.getString("addr"));
			}
			if (outObj.containsKey("exp")) {
				resp.setExp(stringToDateFormat(outObj.getString("exp")));
			}
			resp.setError_code("0000");
			resp.setError_msg("调用成功");
		}else{
			String response = obj.getString("response");
			JSONObject o=JSONObject.fromObject(response);
			resp.setError_code(o.getString("code"));
			resp.setError_msg(o.getString("detail"));
		}
		return resp;
	}
	
	private static String stringToDateFormat(String exp){
		return (null!=exp&&exp.length()>=8)?(exp.substring(0, 4)+"-"+exp.substring(4, 6)+"-"+exp.substring(6,8)+" 23:59:59"):"";
	}
}

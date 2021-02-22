package com.ztesoft.inf.communication.client;

import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.log4j.Logger;

import params.ZteResponse;

import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import commons.CommonTools;
/**
 * 
 * @author sguo 
 * <p>
 * 	联通森瑞http接口，仅封装url参数
 * </p>
 * 
 */
public class HttpInvoker extends Invoker {

	private static DefaultHttpClient httpclient = null;

	protected Logger logger = Logger.getLogger(HttpInvoker.class);
	
	@Override
	public Object invoke(InvokeContext context) throws Exception {
		context.setRequestTime(DateUtil.currentTime());
		Object result = null;
		DefaultHttpClient httpClient = getHttpClientDefault();
		HttpResponse httpResponse = null;
		HttpRequestBase uriRequest = null;
		String url = generateRequestString(context);
		uriRequest = new HttpGet(endpoint+url);
		context.setEndpoint(endpoint);
		context.setRequestString(url + "-------" + JsonUtil.toJson(context.getParameters()));
		try {
			httpResponse = httpClient.execute(uriRequest);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK
					&& statusCode != HttpStatus.SC_NO_CONTENT) {
				throw new Exception("HttpStatusCode:" + statusCode);
			} else if (statusCode == HttpStatus.SC_REQUEST_TIMEOUT) {
				throw new Exception("网络连接超时");
			}

			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				String rspStr = StringUtils.convertStreamToString(instream);
				logger.debug("rspStr====>" + rspStr);
				logger.info("rspStr====>" + URLDecoder.decode(rspStr));
				context.setResponeString(rspStr);
				result = dealResult(context);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			uriRequest.releaseConnection();
		}		
        context.setResultString(result.toString());
        context.setResponeString(CommonTools.beanToJson(result));
        context.setResponseTime(DateUtil.currentTime());
		return result;
	}
	
	@Override
	public Object invokeTest(InvokeContext context) throws Exception {
		context.setRequestTime(DateUtil.currentTime());
		String url = generateRequestString(context);
		context.setEndpoint(endpoint);
		context.setRequestString(url);
		ZteResponse resp = null;
		// 抽取总部返回的报文直接生成返回对象

		Class<?> clazz = null;
		try {
			clazz = Class.forName(rspPath);
			resp = (ZteResponse) JsonUtil.fromJson(transform, clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
        context.setResultString(transform.toString());
        context.setResponeString(transform.toString());
        context.setResponseTime(DateUtil.currentTime());
		return resp;
	}	
	
	@Override
	protected String generateRequestString(InvokeContext context) {
		Object params = context.getParameters();
		StringBuffer requestURL = null;
		//（浙江）数据库配置的接口地址是能开地址http://132.151.42.124:7028/api/http/wssmall.user.request.writecardstatus/2.0?access_token=ODgxM2RiYjM1Zjg5N2ZkZDRhZTM1MzczYzYwMjQyMjc=
		if(StringUtils.isNotEmpty(endpoint) && endpoint.indexOf("access_token")!=-1){
			requestURL = new StringBuffer("&");
		}else{
			requestURL = new StringBuffer("?");
		}
		logger.info("===============接口地址："+endpoint);
		try {
			requestURL.append("sequence=" + new Date().getTime() + "&");
			Map hm = JSONObject.fromObject(params);
			String dataMapStr = Const.getStrValue(hm, "data");
			Map dataMap = JSONObject.fromObject(dataMapStr);
			Iterator ite = dataMap.entrySet().iterator();	
			dataMapStr = "{";
			if(!"sr.rgqueWriMachStaInBatch".equals(context.getOperationCode())){
				while(ite.hasNext()){			
					Map.Entry<Object, Object> entry = (Entry<Object, Object>) ite.next();	
					dataMapStr += "'" + entry.getKey() + "':";
					dataMapStr += "'" + entry.getValue() + "',";
				}
			}else{//针对sr.rgqueWriMachStaInBatch接口特殊处理，此处理方式暂无法通用
				while(ite.hasNext()){			
					Map.Entry<Object, Object> entry = (Entry<Object, Object>) ite.next();	
					dataMapStr += "'" + entry.getKey() + "':";
					dataMapStr += entry.getValue() + ",";
				}
			}
			dataMapStr = dataMapStr.substring(0, dataMapStr.length() - 1);
			dataMapStr += "}";
			String data = URLEncoder.encode(dataMapStr, "UTF-8");
			requestURL.append("data=" + data + "&");
			requestURL.append("key=" + Const.getStrValue(hm, "KEY") + "&");		
			requestURL.append("request=" + hm.get("request") + "&");
			requestURL.append("sessionId=");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestURL.toString();
	}
	

	protected DefaultHttpClient getHttpClientDefault() {
		if (null != httpclient) {
			return httpclient;
		}
		int timeoutConnection = http_post_time_out;
		int timeoutSocket = http_read_time_out;
		int BUFFER_SIZE = 8192 * 20;

		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		HttpConnectionParams.setSocketBufferSize(httpParameters, BUFFER_SIZE);
		
		PoolingClientConnectionManager pcm = new PoolingClientConnectionManager();  
		//MaxTotal 值不应该太大  
		pcm.setMaxTotal(200);  
		//DefaultMaxPerRoute 是路由的默认最大连接（该值默认为2），限制数量实际使用DefaultMaxPerRoute并非MaxTotal。  
		//设置过小无法支持大并发(ConnectionPoolTimeoutException: Timeout waiting for connection from pool)，路由是对maxTotal的细分。  
		pcm.setDefaultMaxPerRoute(pcm.getMaxTotal());//

		
		HttpClientParams.setRedirecting(httpParameters, true);
		HttpClientParams.setCookiePolicy(httpParameters,
				CookiePolicy.BROWSER_COMPATIBILITY);
		HttpProtocolParams.setUserAgent(httpParameters, "apache.http.client");
		httpclient = new DefaultHttpClient(pcm,
				httpParameters);
		return httpclient;
	}
	
	@Override
	protected Object dealResult(InvokeContext context){
		ZteResponse result = null;
		try {
			String resp = URLDecoder.decode(context.getResponeString(), "UTF-8");
			resp = converShit(resp);
			resp = "{\"sr_response\":" + resp + "}";
			Class<?> clazz = Class.forName(rspPath);
			//原来的resp.contains("code")&&resp.contains("\"code\":\"0\"")
			if(resp.contains("code") && resp.contains("\"code\":0")){
				logColMap.put("col2", "success");
			}else{
				logColMap.put("col2", "error");
			}
			result = (ZteResponse)JsonUtil.fromJson(resp, clazz);
			//logger.info("=============>>>>>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String converShit(String shit){
		Map shitMap = JSONObject.fromObject(shit);
		String shits = shitMap.get("body").toString();
		shits = shits.replaceAll("'", "\"");
		Map shitsMap = JSONObject.fromObject(shits);
		shitMap.put("body", shitsMap);
		return CommonTools.beanToJsonNCls(shitMap);
	}
}

package com.ztesoft.net.eop.processor.core;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;



/**
 * @author kingapex
 * @version 1.0
 * @created 09-十月-2009 22:22:30
 */
public class RemoteRequest implements Request {
	private static Logger logger = Logger.getLogger(RemoteRequest.class);
	public HttpClient httpClient;
	private Map<String ,String> params;
	
	@Override
	public void setExecuteParams(Map<String,String> params){
		this.params = params;
	}

	private HttpClient getHttpClient(HttpServletRequest httpRequest){
		HttpSession session = httpRequest.getSession();
		if(session.getAttribute("httpClient")==null){
			
			HttpClient httpclient = new DefaultHttpClient();
			session.setAttribute("httpClient", httpclient);
			
		}
		
		return (HttpClient)session.getAttribute("httpClient");
		
		
	}

	/**
	 * 
	 * @param uri
	 * @param httpResponse
	 * @param httpRequest
	 */
	@Override
	public Response execute(String uri, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) {
		String method = httpRequest.getMethod();
		
		method = method.toUpperCase();
		
		HttpClient httpclient = getHttpClient(httpRequest);
		HttpUriRequest httpUriRequest =null;
		
//		if(method.equals("POST")){
			HttpPost httppost = new HttpPost(uri);
			HttpEntity entity = HttpEntityFactory.buildEntity(httpRequest,this.params);
			httppost.setEntity(entity);
			httpUriRequest = httppost;
//		}
//		
//		if(method.equals("GET")){
//			HttpGet httpget = new HttpGet(uri);
//			httpUriRequest =  httpget;
//		}
//		
		
		try{
			
			HttpResponse httpresponse = httpclient.execute(httpUriRequest);
			HttpEntity rentity = httpresponse.getEntity();
			
//			String content = EntityUtils.toString(rentity,"utf-8");
			Response response  = new InputStreamResponse(rentity.getContent());
			logger.info(  rentity.getContentType().getValue() ); 
//			response.setContentType();
			
//			response.getStatusCode()
//			response.setContent(content);
			return response;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	

	/**
	 * 
	 * @param uri
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Override
	public Response execute(String uri)  {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(uri);
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity,"utf-8");
			Response eopresponse  = new StringResponse();
			eopresponse.setContent(content);
			return eopresponse;
			
		} catch (ClientProtocolException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		return null;
	}

}
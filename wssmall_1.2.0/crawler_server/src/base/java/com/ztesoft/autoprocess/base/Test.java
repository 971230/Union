package com.ztesoft.autoprocess.base;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.ztesoft.autoprocess.base.exception.BusinessException;
import com.ztesoft.autoprocess.base.exception.SystemException;

public class Test {
	private static Logger logger = Logger.getLogger(Test.class);
	HttpClient httpClient=new DefaultHttpClient();
	
	public static void main(String[] args) throws SystemException, BusinessException {
		Test t=new Test();
		String s=t.get("http://localhost:8080/web");
		logger.info(s);
		
	}
	
	public String get(String url) throws SystemException,BusinessException{
		String responseStr = null;
		// 创建httpget.
		HttpGet httpget = new HttpGet(url);
		// 执行get请求.
		HttpResponse response = null;
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,60000); 


		try {
			response = httpClient.execute(httpget);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 获取响应实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			try {
				responseStr = EntityUtils.toString(entity);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseStr;
	}
}

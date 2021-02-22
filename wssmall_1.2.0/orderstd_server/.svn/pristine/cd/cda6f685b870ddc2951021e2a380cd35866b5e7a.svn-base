package com.ztesoft.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.ArrayUtils;


public class HttpClientUtil {
	
	public static String getResult(String requestUrl , String inParam, String...charsets) throws Exception{
		String result = null;
		String charset ="UTF-8"; 
		if (ArrayUtils.isNotEmpty(charsets))
			charset = charsets[0];
		inParam=inParam.replaceAll("＝", "=");//xml标签属性的"="从前到后台不一样替换掉
		HttpURLConnection httpUrlConnection = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			URL url = new URL(requestUrl.toString());
		
			httpUrlConnection = (HttpURLConnection) url.openConnection();
			
			//2、设置连接参数
			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setInstanceFollowRedirects(false);
			httpUrlConnection.setRequestProperty("Accept", "application/json");
			httpUrlConnection.setRequestProperty("Content-Type", "application/json;" + charset);
			
			outputStream = httpUrlConnection.getOutputStream(); 
//			logger.info("inParam:"+inParam);
			outputStream.write(inParam.getBytes(charset));
			
			outputStream.flush();
			
			inputStream = httpUrlConnection.getInputStream();
			
			result = getStreamAsString(inputStream, charset);
          //  logger.info(result); 
           
		}finally{
			try{
				if(outputStream!= null){
					outputStream.close();
				}
				
				if(inputStream!= null){
					inputStream.close();
				}
				
				if(httpUrlConnection!= null){
					httpUrlConnection.disconnect();
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	
	public static String getStreamAsString(InputStream stream, String charset) throws Exception {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
			StringWriter writer = new StringWriter();

			char[] chars = new char[256];
			int count = 0;
			while ((count = reader.read(chars)) > 0) {
				writer.write(chars, 0, count);
			}

			return writer.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}
	
}

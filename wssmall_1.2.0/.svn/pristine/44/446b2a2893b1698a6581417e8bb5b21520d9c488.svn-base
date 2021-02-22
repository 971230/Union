package com.ztesoft.common.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.directwebremoting.util.Logger;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-14
 * Time: 上午9:26
 * To change this template use File | Settings | File Templates.
 */
//用于发送http请求
public class HttpUtils {
    private static String encode="UTF-8";
    private static Logger log= Logger.getLogger(HttpUtils.class);

    public static void sendPost(){

    }

    public static boolean sendGet(String url){
        boolean rval=false;
        HttpClient httpClient=null;
        GetMethod method=null;
        try {
             httpClient = new HttpClient();
             method = new GetMethod(url);
             int status=httpClient.executeMethod(method);
             if(status==HttpStatus.SC_OK){
                String val=method.getResponseBodyAsString();
               log.debug("返回值!====>"+val);
                rval=true;
             }
        } catch (IOException e) {
            log.debug("发送请求失败!url===>"+url);
            rval=false;
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
           if(null!=method)method.releaseConnection();
        }
        return rval;
    }
	public static String getContentByUrl(String url,Integer timeout) throws Exception {
		HttpClient client = new HttpClient();
		HttpMethod method = null;
		String content = "";
		try {
			HttpConnectionManagerParams managerParams = 
				client.getHttpConnectionManager().getParams(); 
			if (timeout!=null) {
				// 设置连接超时时间(单位毫秒) 
				managerParams.setConnectionTimeout(timeout.intValue() * 1000);				
				// 设置读数据超时时间(单位毫秒) 
				managerParams.setSoTimeout(timeout.intValue() * 1000); 
			}
			method = new GetMethod(url);
			client.executeMethod(method);
			content = method.getResponseBodyAsString();
		} catch (Exception e) {
			log.debug("获取请求URL失败：url==>{"+url+"},message==>{"+e.getMessage()+"}");
			throw e;
		} finally {
			try {
				if (method != null)
					method.releaseConnection();
				client.getHttpConnectionManager().closeIdleConnections(0);
			} catch (Exception e) {
			}
		}
		log.debug("获取请求URL返回数据：url==>{"+url+"},content = {"+content+"}");
		return content;
	}
}

package com.ztesoft.inf.framework.utils;


import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-09-11 14:20
 * To change this template use File | Settings | File Templates.
 * <p/>
 * 用于发送http请求
 */
public class HttpSendUtils {
    private static Logger logger = Logger.getLogger(HttpSendUtils.class);

    //post数据
    public static String post(String url,String data,int timeout) {
        HttpClient client = null;
        PostMethod method = null;
        String rval=null;
        try {
            client = new HttpClient();
            method = new PostMethod(url);
            //设置成了默认的恢复策略，在发生异常时候将自动重试3次
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            //设置请求超时时间
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, new Integer(timeout));
            client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
            //设置内容
            method.setRequestEntity(new StringRequestEntity(data, "text/html", "utf-8"));
            method.setRequestHeader("Content-type", "text/xml;charset=utf-8");
            int status = client.executeMethod(method);
            logger.debug("请求地址:"+url);
            if (status == HttpStatus.SC_OK) {
                rval=new String(method.getResponseBody());
            }
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            if(null!=client)method.releaseConnection();
        }
        return rval;
    }

}

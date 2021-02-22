package com.ztesoft.common.util.web;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.Logger;

import com.ztesoft.common.util.StringUtils;


public class HttpUtils {
	private static Logger logger = Logger.getLogger(HttpUtils.class);
	public static String getContentByUrl(String url, Integer timeout)
			throws Exception {

		HttpClient client = new HttpClient();
		HttpMethod method = null;
		String content = "";

		try {
			HttpConnectionManagerParams managerParams = client
					.getHttpConnectionManager().getParams();
			if (timeout != null) {
				// 设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(timeout * 1000);
				// 设置读数据超时时间(单位毫秒)
				managerParams.setSoTimeout(timeout * 1000);
			}
			method = new GetMethod(url);
			client.executeMethod(method);
			content = method.getResponseBodyAsString();

		} catch (Exception e) {
			StringUtils.printInfo("获取请求URL失败：" + url + "\n" + e.getMessage());
			throw e;
		} finally {
			try {
				if (method != null)
					method.releaseConnection();
				client.getHttpConnectionManager().closeIdleConnections(0);
			} catch (Exception e) {
			}
		}

		StringUtils.printInfo("获取请求URL返回数据：" + url + "\n content = " + content);
		return content;
	}

	public static void main(String[] args) throws Exception {
		logger.info(HttpUtils.getContentByUrl(
				"http://127.0.0.1:8081/nexus/index.html", 10));
	}
}

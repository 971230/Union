package com.ztesoft.dict.config;

public class ServerConfig {

	public static String serverIp = "wapjx.189.cn";// 接口IP
	public static int serverPort = 80;// 接口端口
	public static final String WEB_CONTEXT = "";// 上下文路径
	public static final String COMMON_REQ_URL = "/BackAgentServlet";// 接口服务路径
	public static final String QUERY_REQ_URL = "";// 查询路径

	// http://ip:port
	public static String getServerPath() {
		String url = "http://" + serverIp.trim();
		if (!"".equals(serverPort)) {
			url += ":" + serverPort;
		}

		return url;
	}

	// http://ip:port/webcontext
	public static String getServerUrl() {
		String url = "http://" + serverIp.trim();
		if (!"".equals(serverPort)) {
			url += ":" + serverPort;
		}
		url += WEB_CONTEXT;
		return url;
	}

}

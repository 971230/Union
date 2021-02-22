package com.ztesoft.rop.client;

import org.springframework.web.client.RestTemplate;

import java.util.Locale;

public class DefaultRopClient implements RopClient {

	// 应用密钥
	private String appSecret;

	// 报文格式
	private String messageFormat = "xml";

	private Locale locale = Locale.SIMPLIFIED_CHINESE;

	private RestTemplate restTemplate = null;
	// 服务地址
	private String serverUrl;

	// 应用键
	private String appKey;

	public DefaultRopClient() {
		initRest();
	}

	private void initRest() {
		restTemplate = new RestTemplate();
	}

	public DefaultRopClient(String serverUrl, String appKey, String appSecret) {
		this.serverUrl = serverUrl;
		this.appKey = appKey;
		this.appSecret = appSecret;
		initRest();
	}

	public DefaultRopClient(String serverUrl, String appKey, String appSecret,
			String messageFormat) {
		this.serverUrl = serverUrl;
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.messageFormat = messageFormat;
		initRest();
	}

	public DefaultRopClient(String serverUrl, String appKey, String appSecret,
			String messageFormat, Locale locale) {
		this.serverUrl = serverUrl;
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.messageFormat = messageFormat;
		this.locale = locale;
		initRest();
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	@Override
	public String getMessageFormat() {
		return messageFormat;
	}

	@Override
	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public ClientRequest buildClientRequest() {
		// TODO Auto-generated method stub

		DefaultClientRequest clientRequest = new DefaultClientRequest(this,
				appKey, messageFormat, locale, appSecret, serverUrl);

		return clientRequest;
	}

}

package com.ztesoft.rop.route.impl;

import com.ztesoft.rop.annotation.HttpAction;
import com.ztesoft.rop.common.*;
import com.ztesoft.rop.response.MainError;
import com.ztesoft.rop.session.Session;
import com.ztesoft.rop.utils.RopUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author 陈雄华
 * @version 1.0
 */
public class SimpleRopRequestContext implements RopRequestContext {

	public static final String SPRING_VALIDATE_ERROR_ATTRNAME = "$SPRING_VALIDATE_ERROR_ATTRNAME";

	private RopContext ropContext;

	private String method;

	private String version;

	private String appKey;

	private Locale locale;

	private String format;

	public static ThreadLocal<String> messageFormat = new ThreadLocal<String>();

	private String sign;

	private Map<String, Object> attributes = new HashMap<String, Object>();

	private ServiceMethodHandler serviceMethodHandler;

	private MainError mainError;

	private Object ropResponse;

	private RopRequest ropRequest;

	private long serviceBeginTime = -1;

	private long serviceEndTime = -1;

	private String ip;

	private HttpAction httpAction;

	private Object rawRequestObject;

	private Map<String, String> allParams;

	private Object otherObject = null;

	private String requestId = RopUtils.getUUID();

	@Override
	public long getServiceBeginTime() {
		return this.serviceBeginTime;
	}

	@Override
	public long getServiceEndTime() {
		return this.serviceEndTime;
	}

	@Override
	public void setServiceBeginTime(long serviceBeginTime) {
		this.serviceBeginTime = serviceBeginTime;
	}

	@Override
	public void setServiceEndTime(long serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	@Override
	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public Object getRawRequestObject() {
		return this.rawRequestObject;
	}

	public void setRawRequestObject(Object rawRequestObject) {
		this.rawRequestObject = rawRequestObject;
	}

	public SimpleRopRequestContext(RopContext ropContext) {
		this.ropContext = ropContext;
		this.serviceBeginTime = System.currentTimeMillis();
	}

	@Override
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public RopContext getRopContext() {
		return ropContext;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public Session getSession(String sessionId) {
		if (ropContext != null && ropContext.getSessionManager() != null
				&& sessionId != null) {
			return ropContext.getSessionManager().getSession(sessionId);
		} else {
			return null;
		}
	}
	
	

	@Override
	public void addSession(String sessionId, Session session) {
		if (ropContext != null && ropContext.getSessionManager() != null) {
			ropContext.getSessionManager().addSession(sessionId, session);
		}
	}

	@Override
	public void removeSession(String sessionId) {
		if (ropContext != null && ropContext.getSessionManager() != null) {
			ropContext.getSessionManager().removeSession(sessionId);
		}
	}

	@Override
	public Locale getLocale() {
		return this.locale;
	}

	@Override
	public ServiceMethodHandler getServiceMethodHandler() {
		return this.serviceMethodHandler;
	}

	@Override
	public String getMessageFormat() {
		return messageFormat.get();
	}

	@Override
	public Object getRopResponse() {
		return this.ropResponse;
	}

	@Override
	public RopRequest getRopRequest() {
		return this.ropRequest;
	}

	@Override
	public void setRopRequest(RopRequest ropRequest) {
		this.ropRequest = ropRequest;
	}

	@Override
	public String getAppKey() {
		return this.appKey;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setServiceMethodHandler(
			ServiceMethodHandler serviceMethodHandler) {
		this.serviceMethodHandler = serviceMethodHandler;
	}

	public void setMessageFormat(String messageFormat) {
		SimpleRopRequestContext.messageFormat.set(messageFormat);
	}

	@Override
	public void setRopResponse(Object ropResponse) {
		this.ropResponse = ropResponse;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	@Override
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setMainError(MainError mainError) {
		this.mainError = mainError;
	}

	public MainError getMainError() {
		return this.mainError;
	}

	@Override
	public Object getAttribute(String name) {
		return this.attributes.get(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		this.attributes.put(name, value);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public boolean isSignEnable() {
		return ropContext.isSignEnable()
				&& !getServiceMethodDefinition().isIgnoreSign();
	}

	@Override
	public ServiceMethodDefinition getServiceMethodDefinition() {
		return serviceMethodHandler.getServiceMethodDefinition();
	}

	@Override
	public Map<String, String> getAllParams() {
		return this.allParams;
	}

	public void setAllParams(Map<String, String> allParams) {
		this.allParams = allParams;
	}

	@Override
	public String getParamValue(String paramName) {
		if (allParams != null) {
			return allParams.get(paramName);
		} else {
			return null;
		}
	}

	public void setHttpAction(HttpAction httpAction) {
		this.httpAction = httpAction;
	}

	@Override
	public HttpAction getHttpAction() {
		return this.httpAction;
	}

	@Override
	public String getRequestId() {
		return this.requestId;
	}

	@Override
	public void setOtherObject(Object otherObject) {
		// TODO Auto-generated method stub
		this.otherObject = otherObject;
	}

	@Override
	public Object getOtherObject() {
		// TODO Auto-generated method stub
		return otherObject;
	}
}
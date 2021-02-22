package com.zte.cbss.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

public class WebUtil extends WebUtils{

	public final static String AJAX_HEADER = "x-requested-with";
	public final static String XMLHTTPREQUEST = "XMLHttpRequest";
	
	@Autowired
	private static CustomPropertyPlaceholderConfigurer propertyConfigurer;
	
	public static boolean isAjaxRequest() {
		return isAjaxRequest(getHttpServletRequest());
	}
	
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestType = request.getHeader(AJAX_HEADER);
		return XMLHTTPREQUEST.equalsIgnoreCase(requestType);
	}
	
	public static HttpSession getHttpSession() {
		return getHttpServletRequest().getSession();
	}
	
	public static HttpSession getHttpSession(HttpServletRequest request) {
		return request.getSession();
	}
	
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static String[] getParameterValues(String parameterName) {
		return getHttpServletRequest().getParameterValues(parameterName);
	}
	
	public static String getParameterValue(String parameterName) {
		return getHttpServletRequest().getParameter(parameterName);
	}
	
	public static void setAttribute(String attributeName, Object obj) {
		getHttpServletRequest().setAttribute(attributeName, obj);
	}
	
	public static Object getAttribute(String attributeName) {
		return getHttpServletRequest().getAttribute(attributeName);
	}
	
	public static void setAttribute(HttpServletRequest request, String attributeName, Object obj) {
		request.setAttribute(attributeName, obj);
	}
	
	public static Object getAttribute(HttpServletRequest request, String attributeName) {
		return request.getAttribute(attributeName);
	}
	
	public static Object getObjectInSession(String key) {
		return getHttpSession().getAttribute(key);
	}
	
	public static Object getObjectInSession(HttpServletRequest request, String key) {
		return getHttpSession(request).getAttribute(key);
	}
}

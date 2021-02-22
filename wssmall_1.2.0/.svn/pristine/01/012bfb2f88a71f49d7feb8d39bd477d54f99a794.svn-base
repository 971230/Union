package com.ztesoft.common.util;

import com.powerise.ibss.framework.DynamicDict;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.WebContextFactory.WebContextBuilder;
import org.directwebremoting.impl.DefaultContainer;
import org.directwebremoting.impl.DefaultWebContextBuilder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

/**
 * 通用的取线程绑定中对象的工具类
 * @author Reason.Yea
 * @version Created Mar 1, 2012
 */
public class ContextHelper {
	/**
	 * 获取request对象
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		WebContext ctx = WebContextFactory.get();
		if(ctx==null){
			return null;
		}
		HttpServletRequest request = ctx.getHttpServletRequest();
		return request;
	}
	/**
	 * 获取session对象
	 * @return
	 */
	public static HttpSession getSession() {
		WebContext ctx = WebContextFactory.get();
		if(ctx==null){
			return null;
		}
		HttpSession session = ctx.getSession();
		return session;
	}
	/**
	 * 获取request中的key值
	 * @param key
	 * @return
	 */
	public static Object getRequestObj(String key){
		if(getRequest()==null)return null;
		return getRequest().getAttribute(key);
	}
	/**
	 * 设置request中的key值
	 * @param key
	 * @param value
	 */
	public static boolean setReqeustObj(String key,Object value){
		if(getRequest()==null)return false;
		getRequest().setAttribute(key, value);
		return true;
	}
	/**
	 * 获取session中的key值
	 * @param key
	 * @return
	 */
	public static Object getSessionObj(String key){
		if(getSession()==null)return null;
		return getSession().getAttribute(key);
	}
	/**
	 * 设置session中的key值
	 * @param key
	 * @param value
	 */
	public static boolean setSessionObj(String key,Object value){
		if(getSession()==null)return false;
		getSession().setAttribute(key, value);
		return true;
	}
	
	/**
	 * 获取线程IP
	 * @return
	 */
	public static String getIp(){
		HttpServletRequest request = getRequest();
		return getRequestIp(request);
		
	}
	public static String getRequestIp(HttpServletRequest request) {
		// 取IP地址
		String vIP = request.getHeader("x-forwarded-for");
		if (vIP == null || vIP.length() == 0|| "unknown".equalsIgnoreCase(vIP)) {
			vIP = request.getHeader("X-Forwarded-For");
		}
		if (vIP == null || vIP.length() == 0|| "unknown".equalsIgnoreCase(vIP)) {
			vIP = request.getHeader("Proxy-Client-IP");
		}
		if (vIP == null || vIP.length() == 0|| "unknown".equalsIgnoreCase(vIP)) {
			vIP = request.getHeader("WL-Proxy-Client-IP");
		}
		if (vIP == null || vIP.length() == 0|| "unknown".equalsIgnoreCase(vIP)) {
			vIP = request.getRemoteAddr();
		}
		return vIP;
	}
	
	public static void setRequestContext(PageContext pageContext,DynamicDict dict) {
		
		ServletContext servletContext = pageContext.getServletContext();
		ServletConfig servletConfig = pageContext.getServletConfig();
		HttpServletRequest httpRequest = (HttpServletRequest)pageContext.getRequest();
		HttpServletResponse httpResponse = (HttpServletResponse) pageContext.getResponse();
		WebContextBuilder contextBuilder=new DefaultWebContextBuilder();
		contextBuilder.set(httpRequest, httpResponse, servletConfig, servletContext, new DefaultContainer());
		WebContextFactory.setWebContextBuilder(contextBuilder);
		try {
			dict.setValueByName("ip", ContextHelper.getIp());
		} catch (Exception e) {
		}
	}
	public static void setRequestContext(ServletContext servletContext, ServletConfig servletConfig,
			HttpServletRequest request, HttpServletResponse response,DynamicDict dict) {
		WebContextBuilder contextBuilder=new DefaultWebContextBuilder();
		contextBuilder.set(request, response, servletConfig, servletContext, new DefaultContainer());
		WebContextFactory.setWebContextBuilder(contextBuilder);
		try {
			dict.setValueByName("ip", ContextHelper.getIp());
		} catch (Exception e) {
		}
	}
	
	
	public static void setRequest( HttpServletRequest request, HttpServletResponse response) {
		WebContextBuilder contextBuilder=new DefaultWebContextBuilder();
		contextBuilder.set(request, response, null, null, new DefaultContainer());
		WebContextFactory.setWebContextBuilder(contextBuilder);
		
	}
	
}

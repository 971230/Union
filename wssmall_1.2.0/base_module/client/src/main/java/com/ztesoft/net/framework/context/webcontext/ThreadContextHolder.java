package com.ztesoft.net.framework.context.webcontext;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.CoreThreadLocalHolder;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.webcontext.impl.CacheSessionContextImpl;
import com.ztesoft.net.framework.context.webcontext.impl.WebSessionContextImpl;


/**
 *  用ThreadLocal来存储Session,以便实现Session any where 
 * @author kingapex
 * <p>2009-12-17 下午03:10:09</p>
 * @version 1.1
 * 新增request any where
 */
public class ThreadContextHolder  {

	private static ThreadLocal<WebSessionContext> HttpSessionThreadLocalHolder = new ThreadLocal<WebSessionContext>();
	private static ThreadLocal<WebSessionContext> CacheSessiontThreadLocalHolder = new ThreadLocal<WebSessionContext>();
	private static ThreadLocal<HttpServletRequest> HttpRequestThreadLocalHolder = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> HttpResponseThreadLocalHolder = new ThreadLocal<HttpServletResponse>();
	
	
	/**
	 * 字符串
	 */
	private static ThreadLocal<Map<String,String>> stringLocal = new ThreadLocal<Map<String,String>>();
	public static final String MEMBER_LV_CACHE_KEY = "member_lv_cache";
	
	public static boolean isMemberLvCache(){
		if("yes".equals(ThreadContextHolder.getStringValue(MEMBER_LV_CACHE_KEY))){
			return true;
		}else{
			return false;
		}
	}
	
	public static void setMemberLvCacheValue(String value){
		ThreadContextHolder.setStringValue(MEMBER_LV_CACHE_KEY, value);
	}
	
	/**
	 * 设置字符串值
	 * @作者 MoChunrun
	 * @创建日期 2013-11-19 
	 * @param key
	 * @param value
	 */
	public static void setStringValue(String key,String value){
		Map<String,String> map = stringLocal.get();
		if(map==null){
			map = new HashMap<String,String>();
			map.put(key, value);
			stringLocal.set(map);
		}else{
			map.put(key, value);
		}
	}
	/**
	 * 获取字符串值
	 * @作者 MoChunrun
	 * @创建日期 2013-11-19 
	 * @param key
	 * @return
	 */
	public static String getStringValue(String key){
		Map<String,String> map = stringLocal.get();
		if(map!=null)
			return map.get(key);
		return null;
	}
	
	public static void setHttpRequest(HttpServletRequest request){
		HttpRequestThreadLocalHolder.set(request);
	}
	
	public static HttpServletRequest getHttpRequest(){
		return  HttpRequestThreadLocalHolder.get();
	}
	
	
	public static void setHttpResponse(HttpServletResponse response){
		HttpResponseThreadLocalHolder.set(response);	
	}
	
	public static HttpServletResponse getHttpResponse(){
		
		return HttpResponseThreadLocalHolder.get();
	}
	

//	public static void destorySessionContext() {
//		WebSessionContext context = SessionContextThreadLocalHolder.get();
//		if (context != null) {
//			context.destory();
//		}
//	}

	/**
	 * 缓存的Session，用于存储用户状态
	 * @return Cache 缓存上下文
	 */
	public static  WebSessionContext  getSessionContext() {
		//获取rop或者dubbo的服务的sessionId
		//此处为核心点，修改前需要讨论 add by wui
		String sessionId = CoreThreadLocalHolder.getInstance().getZteCommonData().getUserSession_id(); //rop、dubbo
		if(StringUtil.isEmpty(sessionId)){//如果sessionId为空，返回正常的HttpSession
			if (HttpSessionThreadLocalHolder.get() == null) {
				HttpSessionThreadLocalHolder.set(new WebSessionContextImpl());
			}
			return HttpSessionThreadLocalHolder.get();
		}else{//如果sessionId不为空，表示经过dubbo或者rop服务，返回分布式缓存的CacheSession
//			if (CacheSessiontThreadLocalHolder.get() == null) {
//				CacheSessiontThreadLocalHolder.set(new CacheSessionContextImpl(sessionId));
//			}
			getCacheSessionContext(sessionId);
			return CacheSessiontThreadLocalHolder.get();
		}
	}
	
	
	public static  WebSessionContext  getHttpSessionContext() {
		if (HttpSessionThreadLocalHolder.get() == null) {
			HttpSessionThreadLocalHolder.set(new WebSessionContextImpl());
		}
		return HttpSessionThreadLocalHolder.get();
	}
	
	
	/**
	 * 缓存的Session，用于存储用户状态
	 * @return Cache 缓存上下文
	 */
	public static  WebSessionContext  getCacheSessionContext(String sessionId) {
		//此处为核心点，修改前需要讨论 add by wui
		if (CacheSessiontThreadLocalHolder.get() == null) {
			CacheSessiontThreadLocalHolder.set(new CacheSessionContextImpl(sessionId));
		}else{
			CacheSessionContextImpl webSessionContext = (CacheSessionContextImpl) CacheSessiontThreadLocalHolder.get();
			if(!sessionId.equals(webSessionContext.getUserKey()))
				CacheSessiontThreadLocalHolder.set(new CacheSessionContextImpl(sessionId));
		}
		return CacheSessiontThreadLocalHolder.get();
	}
}

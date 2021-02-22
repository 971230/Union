package com.ztesoft.net.mall.core.util;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionContextHolder {

	private static Logger logger = Logger.getLogger(HttpSessionContextHolder.class);
	private static final HttpSessionContextHolder holder = new HttpSessionContextHolder();
	
	private Map<String,HttpSession> sessions = new HashMap<String,HttpSession>();
	
	private HttpSessionContextHolder(){}
	
	public static HttpSessionContextHolder getInstance(){
		return holder;
	}
	
	/**
	 * 登录成功后增加session
	 * @作者 MoChunrun
	 * @创建日期 2013-11-2 
	 * @param session
	 */
	public void putHttpSession(HttpSession session){
		if(session!=null)
			sessions.put(session.getId(), session);
		logger.info("===添加在线用户======");
	}
	
	/**
	 * 失效session
	 * @作者 MoChunrun
	 * @创建日期 2013-11-2 
	 * @param sessionId
	 */
	public void invalidateSession(String sessionId){
		HttpSession session = sessions.get(sessionId);
		if(session!=null){
			try{
				sessions.remove(sessionId);
				session.invalidate();
				logger.info("===下线在线用户成功======");
			}catch(Exception ex){
				
			}
		}
	}
}

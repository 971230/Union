package com.ztesoft.net.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.service.ICacheManager;

    
public class CacheInfoQueryServlet  extends HttpServlet {
	private ICacheManager cacheManagerImpl ;
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String returnValue = null;
        try {
        	String key = request.getParameter("cache_key");
        	key = new String(key.getBytes("iso8859-1"),"utf-8"); 
        	String key_config_id = request.getParameter("cache_config_id");
        	String cache_type = request.getParameter("cache_type");
        	this.cacheManagerImpl =  SpringContextHolder.getBean("cacheManagerImpl");
        	returnValue =  this.cacheManagerImpl.getCacheByKey(key, key_config_id, cache_type);
        }
        catch (Exception ex) {
           ex.printStackTrace();
           returnValue = ex.getMessage();
        }
        PrintWriter out = response.getWriter();
        String jsonp = request.getParameter("jsonpcallback");  
        
        out.print( jsonp+"('"+returnValue+"')");
        out.close();
	}
	public ICacheManager getCacheManagerImpl() {
		return cacheManagerImpl;
	}
	public void setCacheManagerImpl(ICacheManager cacheManagerImpl) {
		this.cacheManagerImpl = cacheManagerImpl;
	}
	
}

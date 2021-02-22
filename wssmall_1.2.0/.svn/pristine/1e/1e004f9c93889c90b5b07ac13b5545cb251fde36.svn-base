package com.ztesoft.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.net.cache.common.INetCache;

public class RefrenshDataServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		execute(request, response);
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		     String code = request.getParameter("code");
	         String nameSpace =request.getParameter("nameSpace");
	         
	         INetCache cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	         
	         Object preVal = "";
	         Object bakVal = "";
	         if(code!=null && !code.equals("")){
	        	    Object value ;
	        	    value = getCache( nameSpace,code);
					preVal = value;
					if(value!=null){
						if(nameSpace!=null&&!"".equals(nameSpace)){
							int space = Integer.parseInt(nameSpace);
							cache.delete(space, code);
						}else{
							cache.delete(code);
						}
						bakVal = getCache( nameSpace,code);
						String str = "\npre:"+preVal+"\nbak:"+bakVal;
						response.getOutputStream().write(("code:"+code+" has delete from cache;"+str).getBytes("UTF-8"));
					
					}else{
						response.getOutputStream().write(("code:"+code+" not in the cache").getBytes("UTF-8"));
					}
				}else{
					response.getOutputStream().write("code is not null".getBytes("UTF-8"));
				}
	}
	public Object getCache(String nameSpace,String code){
		Object value = "";
		INetCache cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
        if(nameSpace!=null&&!"".equals(nameSpace)){
			 int space = Integer.parseInt(nameSpace);
       	 value = cache.get(space,code);
		}else{
			 value = cache.get(code);
		} 
        return value;
	}
	/**
	 * 获取总部商城请求的json数据
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getRequestJson(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		StringBuffer jsonBuffer = new StringBuffer();
		Map map = request.getParameterMap();
		Set keys = map.keySet();
		Iterator<String> iterator = keys.iterator();
		String strKey = null;
		String[] strVal = null;
		while (iterator.hasNext()) {
			strKey = iterator.next();
			strVal = (String[]) map.get(strKey);
			if (!"".equals(strVal[0])) {
				jsonBuffer.append(strKey).append("=").append(strVal[0]);
			} else {
				jsonBuffer.append(strKey);
			}
		}
		return jsonBuffer.toString();
	}

}

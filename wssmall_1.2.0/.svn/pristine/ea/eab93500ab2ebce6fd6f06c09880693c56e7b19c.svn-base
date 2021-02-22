package com.ztesoft.net.mall.core.utils;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

public class SingleLogin{
	
	private IDaoSupport daoSupport =  (IDaoSupport)SpringContextHolder.getBean("daoSupport");
	
	public static SingleLogin getInstanse(){
		
		return new SingleLogin();
	}
	
	public boolean isSingle(String uri){
		
		boolean result = false;
		try {
			if(uri.indexOf("?")>-1)
				uri = uri.substring(0,uri.indexOf("?"));
			String sql = " select sl_id from es_single_login where sl_uri = '" + uri + "'";					
			String url = daoSupport.queryForString(sql);
			if(null != url && !"".equals(url)){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

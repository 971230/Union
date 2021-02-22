package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;

import java.util.Map;

public interface IGoodsSearchManager2 {
	
	
	public Page search(int pageNo,int pageSize ,String url) ;
	
	
	public Map<String,Object > getSelector(String url);
	
	
	public void putParams(Map<String,Object> params,String url);
	
	
}

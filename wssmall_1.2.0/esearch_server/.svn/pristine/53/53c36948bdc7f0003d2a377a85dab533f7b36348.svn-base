package com.ztesoft.net.search.action;

import java.util.HashMap;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.search.service.imp.EsearchManager;

public class EsearchAction extends WWAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6596792636651905577L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void esearchLogInfoDate(){
		EsearchManager esManager = SpringContextHolder.getBean("esearchManager");
		System.out.println(esManager.toString());
		try {
			esManager.logInfoIndexByDateRange(new HashMap());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}
	
}

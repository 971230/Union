package com.ztesoft.net.action;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.CacheUtil;

public class OrderExpStatisticsAction extends WWAction{
	private String url;
	
	/**
	 * 
	 * 异常单数据
	 * @return
	 */
	public String showOrderException(){
		url = getConfigInfoValueByCfId("KIBANA_URL");
		return "order_exception";
	}
	
	/**
	 * 
	 * @Description: kibana统计url
	 * @param cfId
	 * @return   
	 */
	public String getConfigInfoValueByCfId(String cfId){
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		return cacheUtil.getConfigInfo(cfId);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

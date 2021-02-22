package com.ztesoft.rule.core.module.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 业务方案对应的缓存数据
 * @author easonwu 
 * @creation Dec 13, 2013
 * 
 */
public class FactCacheDatas {
	//方案编码
	private String planCode ;
	
	private List<FactCacheData> factDatas ;

	
	
	public void addFactCacheData(FactCacheData factCacheData){
		if(factDatas == null )
			factDatas = new ArrayList<FactCacheData>() ;
		factDatas.add(factCacheData) ;
	}
	
	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public List<FactCacheData> getFactDatas() {
		return factDatas;
	}

	public void setFactDatas(List<FactCacheData> factDatas) {
		this.factDatas = factDatas;
	}
	
	public static class FactCacheData{
		private String factCode ;
		private List<Map> datas ;
		public String getFactCode() {
			return factCode;
		}
		public void setFactCode(String factCode) {
			this.factCode = factCode;
		}
		public List<Map> getDatas() {
			return datas;
		}
		public void setDatas(List<Map> datas) {
			this.datas = datas;
		}
		
	}
}

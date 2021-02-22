package com.ztesoft.rule.core.ext;

import java.util.List;

import java.util.Map;

import com.ztesoft.net.mall.core.model.CommissionDetail;


/**
 * 规则结果持久化处理接口
 * @author easonwu 
 * @creation Dec 14, 2013
 * 
 */
public interface IRuleResultProssor {
	//汇总执行SQL
	String saveSumarryData(String sql , Map fact) ;
	
	//汇总执行java
	String saveSumarryData(Map fact) ;
	
	/**
	 * 插入审核人
	 * @param commissionDetail
	 */
	public void setAuditor(CommissionDetail commissionDetail);
	
	
	/**
	 * 提取对象关系
	 * @param spread_id
	 * @param parnet_id
	 * @param service_code
	 * @return
	 */
	
	
	//清单执行SQL
	void saveListData(String sql , List<Map> facts , String dataIndex ) ;
	

	//清单执行java
	void saveListData(List<Map> facts , String dataIndex ) ;
	
}

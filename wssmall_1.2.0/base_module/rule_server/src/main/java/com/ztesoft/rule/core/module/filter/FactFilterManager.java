package com.ztesoft.rule.core.module.filter;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.rule.core.ext.DefFactFilterLoader;
import com.ztesoft.rule.core.module.fact.DefFact;
import com.ztesoft.rule.core.util.ServiceException;

/**
 * 
 * Fact过滤管理 每个Fact均需要通过FactFilterManager，如果Fact的isValid熟悉为true，则参与下一步骤计算
 * select f.filter_handler from es_rule_fact_filter f where f.plan_id=? and f.status_cd='00A' ;

 * @author easonwu 2012-06-08
 * 
 */
public class FactFilterManager {


	private DefFactFilterLoader defFactFilterLoader;


	public List<IFactFilter> loadFactFilters(String planId){
		return defFactFilterLoader.loadFactFilters(planId) ;
	}
	
	public boolean doFilter(DefFact fact , List<IFactFilter> filters ) {
		if(StringUtils.isEmpty(fact.getPlan_id()))
			throw new ServiceException("fact.getPlanId() can't be empty ") ;
		
//		if(filters == null ){
		
//			if(filters == null ) filters = new ArrayList<IFactFilter>() ;
//		}
		
		if(filters != null && filters.size() > 0){
			
			// 加载处理链中
			FactFilterChain chain = new FactFilterChain();
			for (IFactFilter filter : filters) {
				chain.add(filter);
			}
			
			// 开始处理
			chain.doFilter(fact, chain);// 循环每一个filter执行doFilter（map），现在暂时配了一个NoiseFilter噪音过滤
		}

		return fact.isValidFlag();
	}
	

	public DefFactFilterLoader getDefFactFilterLoader() {
		return defFactFilterLoader;
	}

	public void setDefFactFilterLoader(DefFactFilterLoader defFactFilterLoader) {
		this.defFactFilterLoader = defFactFilterLoader;
	}

	
}

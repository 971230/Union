package com.ztesoft.rule.core.ext;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.rule.core.module.cfg.BizPlan;
import com.ztesoft.rule.core.util.ServiceException;


/**
 * 默认事实加载器实现
 * 
 * 功能：
 * 1.提供两种fact中间表数据加载模式：
 * - sql模式 loadFacts(String sql ,Class clazz , Map<String, String> partner, BizPlan plan)【已实现】 
 * - javabean模式 loadFacts(Class clazz , Map<String, String> partner, BizPlan plan) 【子类扩展实现】
 * 
 * 2.对子类要求：
 * 实现[javabean模式],也可以根据业务场景需要,扩展[sql模式 ]
 * 
 * @author easonwu 
 * @creation Dec 13, 2013
 * 
 */

public class DefFactLoader<T>  extends BaseSupport  implements IFactLoader<T> {

	@Override
	public List<T> loadFacts(String sql ,Class clazz , Map<String, String> partner, BizPlan plan) {
		paramSetter(partner,  plan);
		return baseDaoSupport.queryForListByMap(sql,clazz , partner);
	}

	/**
	 * 
	 * 如需要,子类根据需要设置，将plan数据设置到partner中
	 * @param partner
	 * @param plan
	 * 
	 */
	public void paramSetter( Map<String, String> partner, BizPlan plan){
		
	}
	
	@Override
	public List<T> loadFacts(Class clazz , Map<String, String> partner, BizPlan plan) {
		throw new ServiceException("DefFactDataLoader unsupport loadFacts(Class clazz , Map<String, String> partner, BizPlan plan) invoke") ;
	}
}
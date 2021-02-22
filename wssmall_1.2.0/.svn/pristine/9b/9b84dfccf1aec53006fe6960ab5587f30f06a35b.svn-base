/**
 * 
 */
package com.ztesoft.rule.manager.service;



/**
 * @author ZX
 * IOrderRefreshCache.java
 * 刷新订单缓存
 * 2014-11-5
 */
public interface IOrderRefreshCache {

	/**
	 * 刷新订单规则缓存
	 * @return
	 */
	boolean rfsOrderRule();
	
	/**
	 * 刷新MALL_CONFIG
	 */
	void refreshPkConfig();
	
	/**
	 * 刷新DSTORE_CONFIG
	 */
	void rfsDStoreConfig();
	void refreshRemoteService();
//	List test(String test_type, String test_val, String test_auto_exe);
	/**
	 * 按目录=》方案=》规则=》子规则层级缓存规则
	 */
	void cachePlanRuleCond();
}

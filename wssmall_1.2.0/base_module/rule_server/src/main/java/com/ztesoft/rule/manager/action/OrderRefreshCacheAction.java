/**
 * 
 */
package com.ztesoft.rule.manager.action;

import zte.net.iservice.IOrderServices;

import com.ztesoft.net.auto.rule.drools.DroolsUtil;
import com.ztesoft.net.auto.rule.parser.IKExpressionUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.rule.manager.service.IOrderRefreshCache;

/**
 * @author ZX
 * 刷新规则缓存
 * OrderRefreshCacheAction.java
 * 2014-11-5
 */
public class OrderRefreshCacheAction extends WWAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */	
	private IOrderRefreshCache orderRefreshCache;
	
	private IOrderServices orderServices;
	
//	/**
//	 * 以下三个属性测试用，后面记得删除
//	 */
//	private String test_auto_exe;
//	private String test_type;
//	private String test_val;
	
	/**
	 * 刷新方案规则
	 * @return
	 */
	public String rfsOrderRule() {
		
		try {
			boolean flag = orderRefreshCache.rfsOrderRule();
			if (flag)
				json = "{'result':0,'msg':'刷新成功！'}";
			else
				json = "{'result':1,'msg':'刷新成功！'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = "{'result':1,'msg':'刷新失败！'}";
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 清空规则脚本缓存
	 * @return
	 */
	public String rfsClearRuleScript() {
		
		try {
			DroolsUtil.clearRuleSession();
			IKExpressionUtil.clearRuleExpress();
			json = "{'result':0,'msg':'刷新成功！'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = "{'result':1,'msg':'刷新失败！'}";
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 刷新权限数据
	 * @return
	 */
	public String rfsLimitData() {
		
		try {
			orderServices.cacheOrderRoleData();
			json = "{'result':0,'msg':'刷新成功！'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = "{'result':1,'msg':'刷新失败！'}";
		}
		return JSON_MESSAGE;
	}
	
//	public String test() {
//		List list = orderRefreshCache.test(test_type, test_val, test_auto_exe);
//		json = JSONArray.toJSONString(list);
//		return JSON_MESSAGE;
//	}
	
	public String rfsMallConfig() {
		
		try {
			orderRefreshCache.refreshPkConfig();
			json = "{'result':0,'msg':'刷新成功！'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = "{'result':1,'msg':'刷新失败！'}";
		}
		
		return JSON_MESSAGE;
	}
	
public String refreshRemoteService() {
		
		try {
			orderRefreshCache.refreshRemoteService();
			json = "{'result':0,'msg':'刷新成功！'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = "{'result':1,'msg':'刷新失败！'}";
		}
		
		return JSON_MESSAGE;
	}
	
	public String rfsDStoreConfig() {
		
		try {
			orderRefreshCache.rfsDStoreConfig();
			json = "{'result':0,'msg':'刷新成功！'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = "{'result':1,'msg':'刷新失败！'}";
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 缓存方案规则
	 * @author ZX (2015-01-07)
	 * @return
	 */
	public String cachePlanRuleCond() {
		
		try {
			orderRefreshCache.cachePlanRuleCond();
			json = "{'result':0,'msg':'刷新成功！'}";
		} catch (Exception e) {
			e.printStackTrace();
			json = "{'result':1, 'msg':'失败！'}";
		}
		
		return JSON_MESSAGE;
	}
	
	public IOrderRefreshCache getOrderRefreshCache() {
		return orderRefreshCache;
	}
	public void setOrderRefreshCache(IOrderRefreshCache orderRefreshCache) {
		this.orderRefreshCache = orderRefreshCache;
	}

	public IOrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}
	
//	public String getTest_auto_exe() {
//		return test_auto_exe;
//	}
//
//	public void setTest_auto_exe(String test_auto_exe) {
//		this.test_auto_exe = test_auto_exe;
//	}
//
//	public String getTest_type() {
//		return test_type;
//	}
//
//	public void setTest_type(String test_type) {
//		this.test_type = test_type;
//	}
//
//	public String getTest_val() {
//		return test_val;
//	}
//
//	public void setTest_val(String test_val) {
//		this.test_val = test_val;
//	}
	
}

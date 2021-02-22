/**
 * 
 */
package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.auto.rule.vo.Plan;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IRefreshPlanRuleManager;

/**
 * @author ZX
 * RefreshPlanRuleManager.java
 * 2014-11-14
 */
public class RefreshPlanRuleManager extends BaseSupport implements
		IRefreshPlanRuleManager {

	
	public static int space = 444;
	private INetCache cache = CacheFactory.getCacheByType("");
	
	@Override
	public boolean refreshPlan(String ids) {
		// TODO Auto-generated method stub
		boolean flag = true;
		List<String> list = splitString(ids);
		if (list != null && list.size() > 0) {
			for (String plan_id : list) {
				flag = plan(plan_id);
			}
		}
		return flag;
	}
	

	@Override
	public boolean refreshRule(String ids) {
		// TODO Auto-generated method stub
		boolean flag = true;
		List<String> list = splitString(ids);
		if (list != null && list.size() > 0) {
			for (String rule_id : list) {
				flag = rule(rule_id);
			}
		}
		return flag;
	}
	
	@Override
	public String queryRuleId(String rule_code) {
		StringBuffer sqlSb = new StringBuffer();
		sqlSb.append("select ")
			 .append("	t.rule_id ")
			 .append("from ")
			 .append("	es_rule_config t ")
			 .append("where ")
			 .append("	t.rule_code = '").append(rule_code).append("'")
			 .append("	and t.source_from = '"+ManagerUtils.getSourceFrom()+"'");
		String rule_id = daoSupport.queryForString(sqlSb.toString());
		return rule_id;
	}
	
	private boolean rule(String rule_id) {
		boolean flag = true;
		
		
		
		return flag;
	}
	
	private boolean plan(String plan_id) {
		boolean flag = true;
		StringBuilder plan_sql = new StringBuilder("select t.* from es_rule_biz_plan t");
		plan_sql.append(" where t.plan_id='"+plan_id+"'");		
		List<Plan> planList = baseDaoSupport.queryForList(plan_sql.toString(), Plan.class);
		if (planList != null && planList.size() > 0) {
			for (Plan plan : planList) {
				flag = cachePlan(plan);
			}
		}
		return flag;
	}
	
	/**
	 * 缓存方案
	 * @param plan_id
	 * @return
	 */
	private boolean cachePlan(Plan plan) {
		try {
			String PLAN_KEY = "PLAN_";
			if (plan != null) {
				cache.set(space, PLAN_KEY+plan.getPlan_id(), plan);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	private List<String> splitString(String ids) {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isNotBlank(ids)) {
			String[] strs = ids.split(",");
			for (String s : strs) {
				if (StringUtils.isNotBlank(s)) {
					list.add(s);
				}
			}
		}
		return list;
	}
}

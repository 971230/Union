/**
 * 
 */
package com.ztesoft.rule.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.cache.CacheList;
import com.ztesoft.cache.CacheMap;
import com.ztesoft.net.auto.rule.i.IAutoRule;
import com.ztesoft.net.auto.rule.vo.Catalogue;
import com.ztesoft.net.auto.rule.vo.Plan;
import com.ztesoft.net.auto.rule.vo.PlanRule;
import com.ztesoft.net.auto.rule.vo.RuleConsts;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.cache.common.SerializeList;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.blog.StoreProcesser;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.DStoreConfig;
import com.ztesoft.net.mall.core.model.RuleCondCache;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.rule.manager.service.IOrderRefreshCache;
import com.ztesoft.util.CacheUtils;

/**
 * @author ZX 
 * OrderRefreshCache.java 
 * 刷新订单缓存
 * 2014-11-5
 */
public class OrderRefreshCache extends BaseSupport implements
		IOrderRefreshCache {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ztesoft.net.service.IOrderRefreshCache#rfsOrderRule()
	 */

	private INetCache cache = CacheFactory.getCacheByType("");
	public static int space = 444;

	private IAutoRule autoRuleImpl;

//	public List test(String test_type, String test_val, String test_auto_exe) {
//		String auto_exe = "";
//		if (StringUtils.isBlank(test_auto_exe))
//			auto_exe = "0";		
//		auto_exe = test_auto_exe;
//		
//		if (test_type.equals("0")) {
//			String[] ss = test_val.split(",");
//			String rule_id = ss[0];
//			String action_id = ss[1];
//			List<RuleConsts> list = autoRuleImpl.queryRuleConsts(rule_id, action_id);
//			return list;
//			
//		} else if (test_type.equals("1")) {
//			String[] ss = test_val.split(",");
//			String plan_id = ss[0];
//			Plan plan = autoRuleImpl.getPlan(plan_id);
//			List<Plan> list = new ArrayList<Plan>();
//			list.add(plan);
//			return list;
//			
//		} else if (test_type.equals("2")) {
//			String[] ss = test_val.split(",");
//			String plan_id = ss[0];
//			List<PlanRule> list = autoRuleImpl.listPlanRule(plan_id, Integer.valueOf(auto_exe));
//			return list;
//			
//		} else if (test_type.equals("3")) {
//			String[] ss = test_val.split(",");
//			String plan_id = ss[0];
//			String rule_id = ss[1];
//			PlanRule planRule = autoRuleImpl.getPlanRule(rule_id, plan_id, Integer.valueOf(auto_exe));
//			List<PlanRule> list = new ArrayList<PlanRule>();
//			list.add(planRule);
//			return list;
//						
//		} else if (test_type.equals("4")) {
//			String[] ss = test_val.split(",");
//			String plan_id = ss[0];
//			String rule_id = ss[1];
//			List<PlanRule> list = autoRuleImpl.queryChildrenRule(plan_id, rule_id, Integer.valueOf(auto_exe));
//			return list;
//						
//		} else if (test_type.equals("5")) {
//			String[] ss = test_val.split(",");
//			String plan_id = ss[0];
//			String rule_id = ss[1];
//			List<PlanRule> list = autoRuleImpl.queryRuleRelyOnRule(plan_id, rule_id, Integer.valueOf(auto_exe));
//			return list;
//		} else {
//			return null;
//		}
//	}
	
	@Override
	public boolean rfsOrderRule() {
		// TODO Auto-generated method stub

//		/**
//		 * 缓存规则
//		 */
		boolean flag = true;
//		List<RuleConsts> ruleActionList = baseDaoSupport.queryForList(getQUERY_RULE_CONSTS(), RuleConsts.class, ManagerUtils.getSourceFrom());
//		if (ruleActionList != null && ruleActionList.size() > 0) {
//			for (RuleConsts ruleConsts : ruleActionList) {
//				flag = cacheRuleConsts(ruleConsts.getRule_id(),
//						ruleConsts.getAction_id());
//			}
//		}
//
//		/**
//		 * 缓存方案
//		 */
//		List<Plan> planList = baseDaoSupport.queryForList(getQUERY_PLAN_BY_ID(), Plan.class, ManagerUtils.getSourceFrom());
//		if (planList != null && planList.size() > 0) {
//			for (Plan plan : planList) {
//				flag = cachePlan(plan);
//			}
//		}
//		
//		/**
//		 * 方案下一级规则缓存
//		 */
//		List<PlanRule> PlanRuleList = baseDaoSupport.queryForList(getQUERY_PLAN_RULE(), PlanRule.class, ManagerUtils.getSourceFrom());
//		if (PlanRuleList != null && PlanRuleList.size() > 0) {
//			for (PlanRule planRule : PlanRuleList) {
////				if(planRule.getPlan_id().equals("100"))
////					logger.info("1111111111");
//				flag = cachePlanRule(planRule.getPlan_id());
//			}
//		}
//		
//		/**
//		 * 缓存方案根据规则ID
//		 */
//		List<PlanRule> planRuleList = baseDaoSupport.queryForList(getQUERY_PLAN_RULE_BY_ID(), PlanRule.class, ManagerUtils.getSourceFrom());
//		if (planRuleList!= null && planRuleList.size()>0) {
//			for (PlanRule planRule:planRuleList) {
//				flag = cachePlanRuleByRuleId(planRule.getRule_id(), planRule.getPlan_id());
//			}
//		}
//		
//		/**
//		 * 缓存子规则
//		 */
//		List<PlanRule> parentPlanRuleList = baseDaoSupport.queryForList(getQUERY_PARENT_RULE(), PlanRule.class, ManagerUtils.getSourceFrom());
//		if (parentPlanRuleList!=null&&parentPlanRuleList.size()>0) {			
//			flag = cacheChildrenRule(parentPlanRuleList);
//		}
//		
//		/**
//		 * 缓存方案规则依赖的所有规则
//		 */
//		List<PlanRule> relyonRuleList = baseDaoSupport.queryForList(getQUERY_RELYON(), PlanRule.class, ManagerUtils.getSourceFrom());
//		if (relyonRuleList != null && relyonRuleList.size() > 0) {
//			for (PlanRule planRule : relyonRuleList) {
//				flag = cacheRelyonRule(planRule);
//			}
//		}
		try {
			autoRuleImpl.refershCache();
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
		}
		return flag;
	}

	/**
	 * 缓存规则
	 * @param rule_id
	 * @param action_id
	 * @return
	 */
	private boolean cacheRuleConsts(String rule_id, String action_id) {
		try {
			List<RuleConsts> list = null;
			String sql = SF.ruleSql("QUERY_RULE_CONSTS");
			list = this.baseDaoSupport.queryForList(sql, RuleConsts.class,
					rule_id, action_id, ManagerUtils.getSourceFrom());
			CacheList<RuleConsts> clist = new CacheList<RuleConsts>();
			clist.addAll(list);
			cache.set(space, "CSKEY" + rule_id + "_" + action_id, clist);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

		return true;
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
	
	/**
	 * 缓存方案规则关系
	 * @param plan_id
	 * @param auto_exe
	 * @return
	 */
	private boolean cachePlanRule(String plan_id) {
		try {
			
			int auto_exe = -1;
			List<PlanRule> list = null;
			String sql = SF.ruleSql("QUERY_PLAN_RULE");
//			sql += " and rr.auto_exe="+auto_exe;
			sql += " order by rr.priority asc";
			list = this.baseDaoSupport.queryForList(sql, PlanRule.class, ManagerUtils.getSourceFrom(),plan_id);
			if(list!=null){
				CacheList<PlanRule> clist = new CacheList<PlanRule>();
				clist.addAll(list);
				cache.set(space, "PLANRULEKEY"+plan_id+"_"+auto_exe, clist);
			}
			

			int auto_exe0 = 0;
			List<PlanRule> list0 = null;
			String sql0 = SF.ruleSql("QUERY_PLAN_RULE");
			sql0 += " and rr.auto_exe="+auto_exe0;
			sql0 += " order by rr.priority asc";
			list0 = this.baseDaoSupport.queryForList(sql0, PlanRule.class, ManagerUtils.getSourceFrom(),plan_id);
			if(list0!=null){
				CacheList<PlanRule> clist = new CacheList<PlanRule>();
				clist.addAll(list0);
				cache.set(space, "PLANRULEKEY"+plan_id+"_"+auto_exe0, clist);
			}
			
			int auto_exe1 = 1;
			List<PlanRule> list1 = null;
			String sql1 = SF.ruleSql("QUERY_PLAN_RULE");
			sql1 += " and rr.auto_exe="+auto_exe1;
			sql1 += " order by rr.priority asc";
			list1 = this.baseDaoSupport.queryForList(sql1, PlanRule.class, ManagerUtils.getSourceFrom(),plan_id);
			if(list1!=null){
				CacheList<PlanRule> clist = new CacheList<PlanRule>();
				clist.addAll(list1);
				cache.set(space, "PLANRULEKEY"+plan_id+"_"+auto_exe1, clist);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 根据规则ID缓存方案
	 * @return
	 */
	private boolean cachePlanRuleByRuleId(String rule_id,String plan_id) {
		
		try {
			int auto_exe = -1;
			String PLAN_RULE_KEY = "PLAN_RULE_KEY_";
			PlanRule plan = null;
			String sql = SF.ruleSql("QUERY_PLAN_RULE_BY_ID");
			if(!StringUtil.isEmpty(plan_id)){
				sql += "  and rr.plan_id='"+plan_id+"'";
			}
//			sql += " and rr.auto_exe="+auto_exe;
			sql += " order by rr.priority asc";
			List<PlanRule> list = this.baseDaoSupport.queryForList(sql, PlanRule.class, ManagerUtils.getSourceFrom(),rule_id);
			plan = (list!=null && list.size()>0)?list.get(0):null;
			if(plan!=null){
				cache.set(space, PLAN_RULE_KEY+rule_id+auto_exe, plan);
			}
			
			int auto_exe0 = 0;
			PlanRule plan0 = null;
			String sql0 = SF.ruleSql("QUERY_PLAN_RULE_BY_ID");
			if(!StringUtil.isEmpty(plan_id)){
				sql0 += "  and rr.plan_id='"+plan_id+"'";
			}
			sql0 += " and rr.auto_exe="+auto_exe0;
			sql0 += " order by rr.priority asc";
			List<PlanRule> list0 = this.baseDaoSupport.queryForList(sql0, PlanRule.class, ManagerUtils.getSourceFrom(),rule_id);
			plan0 = (list0!=null && list0.size()>0)?list0.get(0):null;
			if(plan0!=null){
				cache.set(space, PLAN_RULE_KEY+rule_id+auto_exe0, plan0);
			}
			
			
			int auto_exe1 = 1;
			PlanRule plan1 = null;
			String sql1 = SF.ruleSql("QUERY_PLAN_RULE_BY_ID");
			if(!StringUtil.isEmpty(plan_id)){
				sql1 += "  and rr.plan_id='"+plan_id+"'";
			}
			sql1 += " and rr.auto_exe="+auto_exe1;
			sql1 += " order by rr.priority asc";
			List<PlanRule> list1 = this.baseDaoSupport.queryForList(sql1, PlanRule.class, ManagerUtils.getSourceFrom(),rule_id);
			plan1 = (list1!=null && list1.size()>0)?list1.get(0):null;
			if(plan1!=null){
				cache.set(space, PLAN_RULE_KEY+rule_id+auto_exe1, plan1);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * 缓存子规则
	 * @param plan_id
	 * @param rule_id
	 * @param auto_exe
	 * @return
	 */
	private boolean cacheChildrenRule(List<PlanRule> parentPlanRuleList) {
		boolean flag = true;
		try {
			
			for (PlanRule planRule : parentPlanRuleList) {
			
				String plan_id = planRule.getPlan_id();
				String rule_id = planRule.getRule_id();
				//-1 0 1
				
				int auto_exe = -1;
				List<PlanRule> list = null;
				String sql9 = SF.ruleSql("QUERY_CHILDREN_RULE");
//				sql9 += " and rr.auto_exe="+auto_exe;
				sql9 += "  order by rr.priority asc";
				list = this.baseDaoSupport.queryForList(sql9, PlanRule.class, 
						ManagerUtils.getSourceFrom(), plan_id, rule_id);
				if(list!=null){
					CacheList<PlanRule> clist = new CacheList<PlanRule>();
					clist.addAll(list);
					cache.set(space, "CPLANRULEKEY_"+plan_id+"_"+rule_id+"_"+auto_exe, clist);					
					flag = cacheChildrenRule(list);					
				}
				
				int auto_exe0 = 0;
				List<PlanRule> list0 = null;
				String sql0 = SF.ruleSql("QUERY_CHILDREN_RULE");
				sql0 += " and rr.auto_exe="+auto_exe0;
				sql0 += "  order by rr.priority asc";
				list0 = this.baseDaoSupport.queryForList(sql0, PlanRule.class, 
						ManagerUtils.getSourceFrom(), plan_id, rule_id);
				if(list0!=null){
					CacheList<PlanRule> clist = new CacheList<PlanRule>();
					clist.addAll(list0);
					cache.set(space, "CPLANRULEKEY_"+plan_id+"_"+rule_id+"_"+auto_exe0, clist);					
					flag = cacheChildrenRule(list0);					
				}
				
				int auto_exe1 = 1;
				List<PlanRule> list1 = null;
				String sql1 = SF.ruleSql("QUERY_CHILDREN_RULE");
				sql1 += " and rr.auto_exe="+auto_exe1;
				sql1 += "  order by rr.priority asc";
				list1 = this.baseDaoSupport.queryForList(sql1, PlanRule.class, 
						ManagerUtils.getSourceFrom(), plan_id, rule_id);
				if(list1!=null){
					CacheList<PlanRule> clist = new CacheList<PlanRule>();
					clist.addAll(list1);
					cache.set(space, "CPLANRULEKEY_"+plan_id+"_"+rule_id+"_"+auto_exe1, clist);					
					flag = cacheChildrenRule(list1);					
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return flag;
	}
	
	/**
	 * 缓存依赖关系规则
	 * @param planRule
	 * @return
	 */
	private boolean cacheRelyonRule(PlanRule planRule) {
		
		try {

			String plan_id = planRule.getPlan_id();
			String rule_id = planRule.getRule_id();
			
			int auto_exe = -1;
			List<PlanRule> list = null;
			String sql = SF.ruleSql("QUERY_RULE_RELY_ON_RULE");
//			sql += " and rr.auto_exe="+auto_exe;
			sql += " order by rr.priority asc";
			list = this.baseDaoSupport.queryForList(sql, PlanRule.class, ManagerUtils.getSourceFrom(),plan_id,rule_id);
			if(list!=null){
				CacheList<PlanRule> clist = new CacheList<PlanRule>();
				clist.addAll(list);
				cache.set(space, "RELYPLANRULE_"+plan_id+"_"+rule_id+"_"+auto_exe,clist);
			}
			
			int auto_exe0 = 0;
			String sql0 = SF.ruleSql("QUERY_RULE_RELY_ON_RULE");
			sql0 += " and rr.auto_exe="+auto_exe0;
			sql0 += " order by rr.priority asc";
			List<PlanRule> list0 = this.baseDaoSupport.queryForList(sql0, PlanRule.class, ManagerUtils.getSourceFrom(),plan_id,rule_id);
			if(list0!=null){
				CacheList<PlanRule> clist = new CacheList<PlanRule>();
				clist.addAll(list0);
				cache.set(space, "RELYPLANRULE_"+plan_id+"_"+rule_id+"_"+auto_exe0,clist);
			}
			
			int auto_exe1 = 1;
			String sql1 = SF.ruleSql("QUERY_RULE_RELY_ON_RULE");
			sql1 += " and rr.auto_exe="+auto_exe1;
			sql1 += " order by rr.priority asc";
			List<PlanRule> list1 = this.baseDaoSupport.queryForList(sql1, PlanRule.class, ManagerUtils.getSourceFrom(),plan_id,rule_id);
			if(list1!=null){
				CacheList<PlanRule> clist = new CacheList<PlanRule>();
				clist.addAll(list1);
				cache.set(space, "RELYPLANRULE_"+plan_id+"_"+rule_id+"_"+auto_exe1,clist);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public void refreshPkConfig(){
		String sql = "select * from es_mall_config where 1=1 ";
		List<Map> keyList = baseDaoSupport.queryForList(sql);
		if(keyList!=null){
			CacheMap<String,Map> cacheMallConfig = new CacheMap<String,Map>();
			for(Map m:keyList){
				String field_name = (String) m.get("field_name");
				String field_value = (String) m.get("field_value");
				CacheUtils.addCache(CacheUtils.MALL_DATA_CONFIG+field_name+":"+field_value,field_value);
				//刷新根据名称查找编码的缓存 订单批量导入功能 add by mo.chencheng 2016-11-26
				String fieldDecs = (String) m.get("field_desc");
				CacheUtils.addCache(CacheUtils.MALL_DATA_CONFIG+field_name+":"+fieldDecs,field_value);
			}
			CacheUtils.addCache(CacheUtils.MALL_DATA_CONFIG+"YES","yes");
			//刷新根据名称查找编码的缓存 订单批量导入功能 add by mo.chencheng 2016-11-26
			CacheUtils.addCache(CacheUtils.MALL_DATA_CONFIG+"MALLDATA","yes");
		}
	}
	
	@Override
	public void refreshRemoteService(){
		try{
			String sql = "select service_id serviceId,service_code servicecode,op_code opCode,api_name apiName,api_param apiParam from es_remote_service t where t.status = 1";
			List serviceList = baseDaoSupport.queryForList(sql);
			for(int i=0;i<serviceList.size();i++){
				HashMap serMap = (HashMap)serviceList.get(i);	
				CacheMap cMap = new CacheMap();
				cMap.putAll(serMap);
				cache.set(serMap.get("apiname").toString(), cMap);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void rfsDStoreConfig() {
		String sql = "select a.* from es_blob_config a where a.source_from = '" + ManagerUtils.getSourceFrom() + "'" ;
		List<DStoreConfig> dStoreConfig = baseDaoSupport.queryForList(sql, DStoreConfig.class);
		SerializeList serializeList = new SerializeList();
		if (dStoreConfig != null && !dStoreConfig.isEmpty()) {
			serializeList.setObj(dStoreConfig);
		}
		this.cache.set(StoreProcesser.LIST_DSTORE_CONFIG, serializeList);
	}
	
	private String getQUERY_RULE_CONSTS() {
		String sql = "select c.*,o.obj_name,o.obj_code,a.attr_name,a.attr_code " +
					" from es_rule_config_consts c,es_rule_obj o,es_rule_obj_attr a "+
					" where c.obj_id=o.obj_id and c.attr_id=a.attr_id and c.source_from=o.source_from" +
					" and c.source_from=a.source_from and c.source_from=?";
		return sql;
	}
	
	private String getQUERY_PLAN_BY_ID() {
		String sql = "select t.* from es_rule_biz_plan t where t.source_from=?";
		return sql;
	}
	
	private String getQUERY_PLAN_RULE(){
		String sql = "select c.rule_id,c.rule_code,c.rule_name,c.resource_type,c.rule_script,c.status_cd,c.pid,rr.rel_type,rr.relyon_rule_id,rr.plan_id,rr.auto_exe,a.exe_path,c.pid,rr.priority "+
				" from es_rule_config c,es_rule_rel rr,es_assembly a where c.rule_id=rr.rule_id and c.ass_id=a.ass_id and c.source_from=rr.source_from and c.source_from=a.source_from "+
				" and c.source_from=? and c.pid='0' and c.status_cd='00A' ";
		return sql;
	}
	
	private String getQUERY_PLAN_RULE_BY_ID(){
		String sql = "select c.rule_id,c.rule_code,c.rule_name,c.resource_type,c.rule_script,c.status_cd,c.pid,rr.rel_type,rr.relyon_rule_id,rr.plan_id,rr.auto_exe,a.exe_path,c.pid,rr.priority "+
				" from es_rule_config c,es_rule_rel rr,es_assembly a where c.rule_id=rr.rule_id and c.ass_id=a.ass_id and c.source_from=rr.source_from and c.source_from=a.source_from "+
				" and c.source_from=? and c.status_cd='00A' ";
		return sql;
	}
	
	private String getQUERY_PARENT_RULE(){
		String sql = "select c.rule_id,c.rule_code,c.rule_name,c.resource_type,c.rule_script,c.status_cd,c.pid,rr.rel_type,rr.relyon_rule_id,rr.plan_id,rr.auto_exe,a.exe_path,c.pid,rr.priority "+
				" from es_rule_config c,es_rule_rel rr,es_assembly a where c.rule_id=rr.rule_id and c.ass_id=a.ass_id and c.source_from=rr.source_from and c.source_from=a.source_from "+
				" and c.source_from=? and c.pid='0' and c.status_cd='"+EcsOrderConsts.RUN_STATUS_00A+"'";
		return sql;
	}
	
	private String getQUERY_RELYON() {
		
//		String sql = "select c.rule_id,c.rule_code,c.rule_name,c.resource_type,c.rule_script,c.status_cd,c.pid," +
//					 "rr.rel_type,rr.relyon_rule_id,rr.plan_id,rr.auto_exe,rr.priority "+
//					 "from " +
//					 "es_rule_config c," +
//					 "es_rule_rel rr " +
//					 "where c.source_from=rr.source_from " +
//					 "and c.rule_id=rr.rule_id " +
//					 "and c.status_cd='"+EcsOrderConsts.RUN_STATUS_00A+"'";
		// 莫言的源码
		String sql = "select c.rule_id,"+
				       "c.rule_code,"+
				       "c.rule_name,"+
				       "c.resource_type,"+
				       "c.rule_script,"+
				       "c.status_cd,"+
				       "c.pid,"+
				       "rr2.rel_type,"+
				       "rr2.relyon_rule_id,"+
				       "rr2.plan_id,"+
				       "rr2.auto_exe,"+
				       "a.exe_path,"+
//				       "c.pid,"+
				       "rr2.priority"+
				  " from es_rule_config c, es_rule_rel rr, es_assembly a, es_rule_rel rr2"+
				  " where c.rule_id = rr.relyon_rule_id"+
				  " and c.ass_id = a.ass_id"+
				  " and c.source_from = rr.source_from"+
				  " and c.source_from = a.source_from"+
				  " and c.source_from = rr2.source_from"+
				  " and c.source_from = ?"+
//				  " and rr.plan_id = ?"+
//				  " and rr.rule_id = ?"+
				  " and rr.relyon_rule_id = rr2.rule_id"+
				  " and rr.plan_id = rr2.plan_id"+
				  " and rr.source_from = rr2.source_from"+
				  " and c.status_cd = '"+EcsOrderConsts.RUN_STATUS_00A+"'";
		return sql;
	}
	
	public IAutoRule getAutoRuleImpl() {
		return autoRuleImpl;
	}
	public void setAutoRuleImpl(IAutoRule autoRuleImpl) {
		this.autoRuleImpl = autoRuleImpl;
	}
	
	private String DIR_KEY = "DIR_KEY"; // 目录KEY
	private String PLAN_KEY = "PLAN_KEY"; // 方案KEY
	private String COND_KEY = "COND_KEY"; // 规则条件KEY
	@Override
	public void cachePlanRuleCond() {
		// TODO Auto-generated method stub
		String sql_dir = getDirSQL(); // 目录SQL
		List<Catalogue> dir_list = baseDaoSupport.queryForList(sql_dir.toString(), Catalogue.class, ManagerUtils.getSourceFrom());
		for(int a = 0 ; a < dir_list.size() ; a ++) {
			String sql_plan = getPlanSQL(); // 方案SQL
			Catalogue catalogue = dir_list.get(a);
			List<Plan> plan_list = baseDaoSupport.queryForList(sql_plan.toString(), Plan.class, ManagerUtils.getSourceFrom(), catalogue.getId());
			CacheList<Plan> cList = new CacheList<Plan>();
			cList.addAll(plan_list);
			cache.set(space, DIR_KEY+"_"+catalogue.getId(), cList); // 按目录缓存方案
			for (int b = 0 ; b < plan_list.size(); b ++) {
				String sql_rule = getRuleSQL(); // 规则SQL
				sql_rule += " AND T.PID='0'";
				Plan plan = plan_list.get(b);
				List<PlanRule> rule_list = baseDaoSupport.queryForList(sql_rule, PlanRule.class, ManagerUtils.getSourceFrom(), plan.getPlan_id());
				CacheList<PlanRule> clist = new CacheList<PlanRule>();
				clist.addAll(rule_list);
				cache.set(space, PLAN_KEY+"_"+plan.getPlan_id(), clist); // 按方案缓存一级规则
				cacheRule(rule_list);
			}
		}
	}
	private void cacheRuleCond(String rule_id) {
		String sql = SF.ruleSql("RULE_COND_QUERY");
		List<RuleCondCache> ruleCondList = this.baseDaoSupport.queryForList(sql, RuleCondCache.class, rule_id,ManagerUtils.getSourceFrom());
		if (ruleCondList != null && ruleCondList.size() > 0) {
			CacheList<RuleCondCache> clist = new CacheList<RuleCondCache>();
			clist.addAll(ruleCondList);
			cache.set(space, COND_KEY+"_"+rule_id, clist);
		}
	}
	private void cacheRule(List<PlanRule> rule_list) {
		for(int d = 0 ; d < rule_list.size() ; d ++) {
			String sql_rule = getRuleSQL();
			sql_rule += " AND T.PID=?";
			PlanRule planRule = rule_list.get(d);
			cacheRuleCond(planRule!=null?planRule.getRule_id():""); // 缓存规则条件
			List<PlanRule> rule_list_c = baseDaoSupport.queryForList(sql_rule, PlanRule.class, ManagerUtils.getSourceFrom(), planRule.getPlan_id(), planRule.getRule_id());
			if (rule_list_c != null && rule_list_c.size() > 0) {
				CacheList<PlanRule> clist = new CacheList<PlanRule>();
				clist.addAll(rule_list_c);
				cache.set(space, PLAN_KEY+"_"+planRule.getPlan_id()+"_"+planRule.getRule_id(), clist); // 按方案和一级规则缓存二级规则
				cacheRule(rule_list_c); // 递归缓存多级规则
			}
		}
	}
	private String getDirSQL() {
		StringBuilder sql_dir = new StringBuilder("SELECT"); // 查询目录表
		sql_dir.append(" T.*");
		sql_dir.append(" FROM");
		sql_dir.append(" ES_CATALOGUE T");
		sql_dir.append(" WHERE");
		sql_dir.append(" T.SOURCE_FROM=?");
		return sql_dir.toString();
	}
	private String getPlanSQL() {
		StringBuilder sql_plan = new StringBuilder("SELECT"); // 查询方案表
		sql_plan.append(" T.*");
		sql_plan.append(" FROM");
		sql_plan.append(" ES_RULE_BIZ_PLAN T");
		sql_plan.append(" WHERE");
		sql_plan.append(" T.SOURCE_FROM=?");
		sql_plan.append(" AND T.CATALOGUE_ID=?");
		sql_plan.append(" AND T.STATUS_CD='00A'");
		return sql_plan.toString();
	}
	private String getRuleSQL() {
		StringBuilder sql_rule = new StringBuilder("SELECT"); // 查询规则表
		sql_rule.append(" T.RULE_ID,");
		sql_rule.append(" T.RULE_CODE,");
		sql_rule.append(" T.RULE_NAME,");
		sql_rule.append(" T.RESOURCE_TYPE,");
		sql_rule.append(" T.RULE_SCRIPT,");
		sql_rule.append(" T.STATUS_CD,");
		sql_rule.append(" T.PID,");
		sql_rule.append(" TT.REL_TYPE,");
		sql_rule.append(" TT.RELYON_RULE_ID,");
		sql_rule.append(" TT.PLAN_ID,");
//		sql_rule.append(" TTTT.ACTION_ID,");
		sql_rule.append(" TT.AUTO_EXE,");
		sql_rule.append(" TT.PRIORITY,");
		sql_rule.append(" TTT.EXE_PATH");
		sql_rule.append(" FROM");
		sql_rule.append(" ES_RULE_CONFIG T,");
		sql_rule.append(" ES_RULE_REL TT,");
		sql_rule.append(" ES_ASSEMBLY TTT");
//		sql_rule.append(" ES_RULE_ACTION TTTT");
		sql_rule.append(" WHERE");
		sql_rule.append(" T.RULE_ID=TT.RULE_ID");
//		sql_rule.append(" AND T.RULE_ID=TTTT.RULE_ID");
		sql_rule.append(" AND T.ASS_ID=TTT.ASS_ID");
		sql_rule.append(" AND T.SOURCE_FROM=TT.SOURCE_FROM");
		sql_rule.append(" AND TT.SOURCE_FROM=TTT.SOURCE_FROM");
//		sql_rule.append(" AND TTT.SOURCE_FROM=TTTT.SOURCE_FROM");
		sql_rule.append(" AND T.SOURCE_FROM=?");
		sql_rule.append(" AND TT.PLAN_ID=?");
		return sql_rule.toString();
	}
}
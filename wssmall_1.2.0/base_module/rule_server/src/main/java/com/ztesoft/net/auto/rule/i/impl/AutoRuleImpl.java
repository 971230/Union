package com.ztesoft.net.auto.rule.i.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.util.RuleUtil;
import com.ztesoft.cache.CacheList;
import com.ztesoft.net.auto.rule.i.IAutoRule;
import com.ztesoft.net.auto.rule.vo.Plan;
import com.ztesoft.net.auto.rule.vo.PlanRule;
import com.ztesoft.net.auto.rule.vo.RuleConsts;
import com.ztesoft.net.auto.rule.vo.RuleExeLog;
import com.ztesoft.net.auto.rule.vo.RuleRel;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.RuleAction;
import com.ztesoft.net.mall.core.model.RuleCond;
import com.ztesoft.net.mall.core.model.RuleCondCache;
import com.ztesoft.net.mall.core.model.RuleConfig;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.util.CacheUtils;

public class AutoRuleImpl extends BaseSupport implements IAutoRule {
	
	public static boolean _CACHE_RULE = true; //add by wui规则缓存存在问题，不能开启
	public static boolean _CACHE_RULE_LOG = false;
	public static ThreadLocal<Boolean> IS_REFERSH_CACHE = new ThreadLocal<Boolean>();
	int time =60*24*60*20;//缺省缓存20天,memcache最大有效期是30天
	private static INetCache cache = null;
	static{
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType(""); //add by wui订单调用单独的订单缓存机器，通过业务分开，和业务静态数据分开
	}
	public static int space = 7799;
	
	public boolean isRefershCache(){
		Boolean flag = IS_REFERSH_CACHE.get();
		if(flag==null)return false;
		return flag;
	}

	@Override
	public List<RuleConsts> queryRuleConsts(String rule_id, String action_id) {
		List<RuleConsts> list = null;
		if(_CACHE_RULE && !isRefershCache())list =(List<RuleConsts>) cache.get(space, CacheUtils.RULE_CSKEY+rule_id+"_"+action_id);
		if(list!=null)return list;
		String sql = SF.ruleSql("QUERY_RULE_CONSTS");
		list = this.baseDaoSupport.queryForList(sql, RuleConsts.class, rule_id,action_id,ManagerUtils.getSourceFrom());
		if(list!=null && _CACHE_RULE){
			CacheList<RuleConsts> clist = new CacheList<RuleConsts>();
			clist.addAll(list);
			cache.set(space, CacheUtils.RULE_CSKEY+rule_id+"_"+action_id, clist,time);
		}
		return list;
	}

	@Override
	public void log(RuleExeLog log) {
		//****************** add by qin.yingxiong at 2016/10/20 start *******************
		String table = "es_rule_exe_log";
		boolean useOrgTable = RuleUtil.useOrgTable(log.getObj_id());
		if(useOrgTable) {
			table = RuleUtil.replaceTableNameForOrgBySql(table);
		}
		//****************** add by qin.yingxiong at 2016/10/20 end *******************
		this.baseDaoSupport.insert(table, log);
		if(_CACHE_RULE_LOG){
			cache.set(space, "RULELGKEY"+log.getPlan_id()+"_"+log.getRule_id()+"_"+log.getObj_id(),log,time);
			cache.set(space, "RULELGKEY"+log.getLog_id(),log,time);
			cache.set(space, CacheUtils.RULELGKEY_P_R_O+log.getPlan_id()+"_"+log.getRule_id()+"_"+log.getObj_id(),log,time);
		}
	}

	@Override
	public String getSeq(String seq_name) {
		return this.baseDaoSupport.getSequences(seq_name);
	}

	@Override
	public Plan getPlan(String plan_id) {
		String sql = SF.ruleSql("QUERY_PLAN_BY_ID");
		String PLAN_KEY = CacheUtils.PLAN_KEY;
		Plan plan = null;
		if(_CACHE_RULE && !isRefershCache())
			plan = (Plan) cache.get(space, PLAN_KEY+plan_id);
		if(plan!=null)return plan;
		List<Plan> list = this.baseDaoSupport.queryForList(sql, Plan.class, plan_id);
		plan = (list!=null && list.size()>0)?list.get(0):null;
		if(_CACHE_RULE && plan!=null)cache.set(space, PLAN_KEY+plan_id,plan,time);
		return plan;
	}

	@Override
	public List<PlanRule> listPlanRule(String plan_id,int auto_exe) {
		List<PlanRule> list = null;
		if(_CACHE_RULE && !isRefershCache())
			list = (List<PlanRule>) cache.get(space, CacheUtils.PLANRULEKEY+plan_id+"_"+auto_exe);
		if(list!=null)return list;
		String sql = SF.ruleSql("QUERY_PLAN_RULE");
		if(-1!=auto_exe)sql += " and rr.auto_exe="+auto_exe;
		sql += " order by rr.priority asc";
		list = this.baseDaoSupport.queryForList(sql, PlanRule.class, ManagerUtils.getSourceFrom(),plan_id);
		if(list!=null && _CACHE_RULE){
			CacheList<PlanRule> clist = new CacheList<PlanRule>();
			clist.addAll(list);
			cache.set(space, CacheUtils.PLANRULEKEY+plan_id+"_"+auto_exe, clist,time);
		}
		return list;
	}

	@Override
	public PlanRule getPlanRule(String rule_id,String plan_id,int auto_exe) {
		String PLAN_RULE_KEY = CacheUtils.PLAN_RULE_KEY_ID;
		PlanRule plan = null;
		if(_CACHE_RULE && !isRefershCache())plan = (PlanRule) cache.get(space, PLAN_RULE_KEY+rule_id+auto_exe);
		if(plan!=null)return plan;
		String sql = SF.ruleSql("QUERY_PLAN_RULE_BY_ID");
		if(!StringUtil.isEmpty(plan_id)){
			sql += "  and rr.plan_id='"+plan_id+"'";
		}
		if(-1!=auto_exe)
			sql += " and rr.auto_exe="+auto_exe;
		sql += " order by rr.priority asc";
		List<PlanRule> list = this.baseDaoSupport.queryForList(sql, PlanRule.class, ManagerUtils.getSourceFrom(),rule_id);
		plan = (list!=null && list.size()>0)?list.get(0):null;
		if(_CACHE_RULE && plan!=null){
			cache.set(space, PLAN_RULE_KEY+rule_id+auto_exe, plan,time);
		}
		return (list!=null && list.size()>0)?list.get(0):null;
	}

	@Override
	public boolean isRuleExeSuccess(String plan_id,String rule_id, String obj_id) {
		String sql = SF.ruleSql("RULE_IS_EXE_SUCCESS");
		//****************** add by qin.yingxiong at 2016/10/20 start *******************
		boolean useOrgTable = RuleUtil.useOrgTable(obj_id);
		if(useOrgTable) {
			sql = RuleUtil.replaceTableNameForOrgBySql(sql);
		}
		//****************** add by qin.yingxiong at 2016/10/20 end *******************
		return this.baseDaoSupport.queryForInt(sql, rule_id,plan_id,obj_id)>0;
	}

	@Override
	public RuleExeLog getRuleExecuteLog(String plan_id,String rule_id, String obj_id) {
		RuleExeLog log = null;
		if(_CACHE_RULE_LOG)log =(RuleExeLog) cache.get(space, CacheUtils.RULELGKEY_P_R_O+plan_id+"_"+rule_id+"_"+obj_id);
		if(log!=null) return log;
		String sql = SF.ruleSql("QUERY_RULE_EXE_LOG_BY_PLAN_RULE");
		//****************** add by qin.yingxiong at 2016/10/20 start *******************
		boolean useOrgTable = RuleUtil.useOrgTable(obj_id);
		if(useOrgTable) {
			sql = RuleUtil.replaceTableNameForOrgBySql(sql);
		}
		//****************** add by qin.yingxiong at 2016/10/20 end *******************
		List<RuleExeLog> list = this.baseDaoSupport.queryForList(sql,RuleExeLog.class,rule_id,plan_id,obj_id);
		log = (list!=null && list.size()>0)?list.get(0):null;
		if(log!=null && _CACHE_RULE_LOG){
			cache.set(space, CacheUtils.RULELGKEY_P_R_O+plan_id+"_"+rule_id+"_"+obj_id,log,time);
		}
		return log;
	}

	@Override
	public List<PlanRule> queryChildrenRule(String plan_id,String rule_id,int auto_exe) {
		List<PlanRule> list = null;
		if(_CACHE_RULE && !isRefershCache())list =(List<PlanRule>) cache.get(space, CacheUtils.CPLANRULEKEY+plan_id+"_"+rule_id+"_"+auto_exe);
		if(list!=null)return list;
		String sql = SF.ruleSql("QUERY_CHILDREN_RULE");
		if(auto_exe!=-1)sql += " and rr.auto_exe="+auto_exe;
		sql += "  order by rr.priority asc";
		list = this.baseDaoSupport.queryForList(sql, PlanRule.class, ManagerUtils.getSourceFrom(),plan_id,rule_id);
		if(list!=null && _CACHE_RULE){
			CacheList<PlanRule> clist = new CacheList<PlanRule>();
			clist.addAll(list);
			cache.set(space, CacheUtils.CPLANRULEKEY+plan_id+"_"+rule_id+"_"+auto_exe, clist,time);
		}
		return list;
	}

	@Override
	public RuleExeLog getRuleExeLog(String obj_id,String log_id) {
		RuleExeLog log = null;
		if(_CACHE_RULE_LOG)log = (RuleExeLog) cache.get(space, CacheUtils.RULE_LOG_ID+log.getLog_id());
		if(log!=null)return log;
		String sql = SF.ruleSql("QUERY_RULE_EXE_LOG_BY_ID");
		//****************** add by qin.yingxiong at 2016/10/20 start *******************
		boolean useOrgTable = RuleUtil.useOrgTable(obj_id);
		if(useOrgTable) {
			sql = RuleUtil.replaceTableNameForOrgBySql(sql);
		}
		//****************** add by qin.yingxiong at 2016/10/20 end *******************
		List<RuleExeLog> list = this.baseDaoSupport.queryForList(sql,RuleExeLog.class,log_id);
		log = (list!=null && list.size()>0)?list.get(0):null;
		if(_CACHE_RULE_LOG && log!=null){
			cache.set(space, CacheUtils.RULE_LOG_ID+log.getLog_id(),log,time);
		}
		return log;
	}

	@Override
	public List<PlanRule> queryRuleRelyOnRule(String plan_id, String rule_id,int auto_exe) {
		List<PlanRule> list = null;
		if(_CACHE_RULE && !isRefershCache())list =(List<PlanRule>) cache.get(space, CacheUtils.RELYPLANRULE+plan_id+"_"+rule_id+"_"+auto_exe);
		if(list!=null)return list;
		String sql = SF.ruleSql("QUERY_RULE_RELY_ON_RULE");
		if(auto_exe!=-1)sql += " and rr.auto_exe="+auto_exe;
		sql += " order by rr.priority asc";
		list = this.baseDaoSupport.queryForList(sql, PlanRule.class, ManagerUtils.getSourceFrom(),plan_id,rule_id);
		if(list!=null && _CACHE_RULE){
			CacheList<PlanRule> clist = new CacheList<PlanRule>();
			clist.addAll(list);
			cache.set(space, CacheUtils.RELYPLANRULE+plan_id+"_"+rule_id+"_"+auto_exe,clist,time);
		}
		return list;
	}

	@Override
	public boolean hasExeMutexRule(String rule_id,String plan_id,String obj_id){
		String sql = SF.ruleSql("HAS_EXE_RULE_MUTEX_RULE");
		//****************** add by qin.yingxiong at 2016/10/20 start *******************
		boolean useOrgTable = RuleUtil.useOrgTable(obj_id);
		if(useOrgTable) {
			sql = RuleUtil.replaceTableNameForOrgBySql(sql);
		}
		//****************** add by qin.yingxiong at 2016/10/20 end *******************
		return this.baseDaoSupport.queryForInt(sql, rule_id,plan_id,obj_id,rule_id,rule_id,ManagerUtils.getSourceFrom())>0;
	}

	@Override
	public void updateLogChildrenErrorInfo(String log_id,int status,String info){
		/*String sql = SF.ruleSql("UPDATE_LOG_CHILD_ERROR");
		this.baseDaoSupport.execute(sql, status,info,log_id);
		if(_CACHE_RULE_LOG){
			RuleExeLog log = (RuleExeLog) cache.get(space, CacheUtils.RULELGKEY_P_R_O+log_id);
			if(log!=null){
				log.setChildren_error(status);
				log.setChildren_info(info);
				cache.set(space, CacheUtils.RULELGKEY_P_R_O+log_id,log,time);
			}
		}*///add by qin.yingxiong at 2016/10/20 发现此代码无用
	}

	@Override
	public void updateLogChildrenErrorInfo(String plan_id, String rule_id,String obj_id,int status,String info) {
		String sql = SF.ruleSql("UPDATE_LOG_CHILD_ERROR2");
		//****************** add by qin.yingxiong at 2016/10/20 start *******************
		boolean useOrgTable = RuleUtil.useOrgTable(obj_id);
		if(useOrgTable) {
			sql = RuleUtil.replaceTableNameForOrgBySql(sql);
		}
		//****************** add by qin.yingxiong at 2016/10/20 end *******************
		this.baseDaoSupport.execute(sql, status,info,plan_id,rule_id,obj_id);
		if(_CACHE_RULE_LOG){
			RuleExeLog log = (RuleExeLog) cache.get(space, CacheUtils.RULELGKEY_P_R_O+plan_id+"_"+rule_id+"_"+obj_id);
			if(log!=null){
				log.setChildren_error(status);
				log.setChildren_info(info);
				cache.set(space, CacheUtils.RULELGKEY_P_R_O+log.getPlan_id()+"_"+log.getRule_id()+"_"+log.getObj_id(),log,time);
			}
		}
	}

	@Override
	public List<PlanRule> queryPeerPlanRule(String plan_id,String p_rule_id, int priority,int auto_exe) {
		List<PlanRule> list = null;
		String PLAN_RULE_KEY = CacheUtils.PLAN_RULE_KEY_P_R__E;
		if(false && _CACHE_RULE && !isRefershCache())list = (List<PlanRule>) cache.get(space, PLAN_RULE_KEY+plan_id+"_"+p_rule_id+"_"+auto_exe);
		if(list!=null)return list;
		String sql = SF.ruleSql("QUERY_PEER_PLAN_RULE");
		if(auto_exe!=-1)sql += " and rr.auto_exe="+auto_exe;
		sql += " order by rr.priority asc";
		list = this.baseDaoSupport.queryForList(sql, PlanRule.class, ManagerUtils.getSourceFrom(),plan_id,p_rule_id,priority);
		if(_CACHE_RULE && list!=null){
			CacheList<PlanRule> clist = new CacheList<PlanRule>();
			clist.addAll(list);
			cache.set(space,PLAN_RULE_KEY+plan_id+"_"+p_rule_id+"_"+auto_exe,clist,time);
		}
		return list;
	}
	
	@Override
	public void updateParentRuleExeLogChildErrorInfo(String plan_id,String chileren_rule_id,String obj_id,int status,String info){
		String sql = SF.ruleSql("UpdateParentRuleExeLogChildErrorInfo");
		//****************** add by qin.yingxiong at 2016/10/20 start *******************
		boolean useOrgTable = RuleUtil.useOrgTable(obj_id);
		if(useOrgTable) {
			sql = RuleUtil.replaceTableNameForOrgBySql(sql);
		}
		//****************** add by qin.yingxiong at 2016/10/20 end *******************
		this.baseDaoSupport.execute(sql, status,info,plan_id,obj_id,chileren_rule_id);
		if(_CACHE_RULE_LOG){
			RuleExeLog log = (RuleExeLog) cache.get(space, CacheUtils.RULELGKEY_P_R_O+plan_id+"_"+chileren_rule_id+"_"+obj_id);
			log.setChildren_error(status);
			log.setChildren_info(info);
			cache.set(space, CacheUtils.RULELGKEY_P_R_O+log.getPlan_id()+"_"+log.getRule_id()+"_"+log.getObj_id(),log,time);
		}
	}

	@Override
	public boolean hasAllMutexRuleExe(String rule_pid, String plan_id,
			String obj_id,String curr_rule_id) {
		//long aa = System.currentTimeMillis();
		String sql = SF.ruleSql("HasAllMutexRuleExe");
		//****************** add by qin.yingxiong at 2016/10/20 start *******************
		boolean useOrgTable = RuleUtil.useOrgTable(obj_id);
		if(useOrgTable) {
			sql = RuleUtil.replaceTableNameForOrgBySql(sql);
		}
		//****************** add by qin.yingxiong at 2016/10/20 end *******************
		int count = this.baseDaoSupport.queryForInt(sql, rule_pid,plan_id,obj_id,ManagerUtils.getSourceFrom(),curr_rule_id);
		//long end = System.currentTimeMillis();
		//logger.info(end-aa);
		return count>0;
	}
	
	@Override
	public List<Plan> queryPlanByCatalogueId(String catalogue_id){
		List<Plan> list = null;
		if(_CACHE_RULE && !isRefershCache())list =(List<Plan>) cache.get(space, CacheUtils.CATALOGUE_PLAN_KEY+catalogue_id);
		if(list!=null)return list;
		String sql = SF.ruleSql("QueryPlanByCatalogueId");
		list = this.baseDaoSupport.queryForList(sql, Plan.class, catalogue_id);
		if(list!=null && _CACHE_RULE){
			CacheList<Plan> clist = new CacheList<Plan>();
			clist.addAll(list);
			cache.set(space, CacheUtils.CATALOGUE_PLAN_KEY+catalogue_id,clist,time);
		}
		return list;
	}
	
	@Override
	public boolean isPlanHasRuleExecute(String catalogue_id,String plan_id,String obj_id){
		String sql = SF.ruleSql("IsPlanHasRuleExecute");
		//****************** add by qin.yingxiong at 2016/10/20 start *******************
		boolean useOrgTable = RuleUtil.useOrgTable(obj_id);
		if(useOrgTable) {
			sql = RuleUtil.replaceTableNameForOrgBySql(sql);
		}
		//****************** add by qin.yingxiong at 2016/10/20 end *******************
		return this.baseDaoSupport.queryForInt(sql, catalogue_id,plan_id,obj_id,ManagerUtils.getSourceFrom())>0;
	}

	@Override
	public void deleteRuleExeLogs(String[] plan_id, String rule_id, String obj_id) {
		String sql = SF.ruleSql("DELETE_RULE_EXECUTE_LOG");
		List params = new ArrayList();
		params.add(obj_id);
		if(!StringUtil.isEmpty(rule_id)){
			sql += " and t.rule_id=? ";
			params.add(rule_id);
		}
		if(null != plan_id && plan_id.length > 0){
			String planId = "'";
			for(int i = 0; i < plan_id.length; i++){
				if(i == plan_id.length - 1){
					planId += plan_id[i] + "'";
				}else{
					planId += plan_id[i] + "','";
				}
					
			}
			sql += " and t.plan_id in (" + planId + ")";
		}
		//****************** add by qin.yingxiong at 2016/10/20 start *******************
		boolean useOrgTable = RuleUtil.useOrgTable(obj_id);
		if(useOrgTable) {
			sql = RuleUtil.replaceTableNameForOrgBySql(sql);
		}
		//****************** add by qin.yingxiong at 2016/10/20 end *******************
		this.baseDaoSupport.execute(sql, params.toArray());
	}
	
	@Override
	public CoQueue getQueueById(String co_queue_id){
		return getQueueById(co_queue_id, false);
	}
	
	@Override
	public CoQueue getQueueById(String co_queue_id,boolean isBak){
		String table = "es_co_queue";
		if(isBak)table = "es_co_queue_bak";
		String sql = "select * from "+table+" t where t.co_id=?";
		List<CoQueue> list = this.baseDaoSupport.queryForList(sql, CoQueue.class, co_queue_id);
		return (list!=null&& list.size()>0)?list.get(0):null;
	}

	@Override
	public List<RuleCondCache> getRuleCond(String rule_id) {
		List<RuleCondCache> list = null;
		if(_CACHE_RULE && !isRefershCache())list =(List<RuleCondCache>) cache.get(space, CacheUtils.RULE_COND_KEY+rule_id);
		if(list!=null)return list;
		String sql = SF.ruleSql("RULE_COND_QUERY");
		list = this.baseDaoSupport.queryForList(sql, RuleCondCache.class, rule_id,ManagerUtils.getSourceFrom());
		if(list==null || list.size()==0)list = getUnContRuleCond(rule_id);
		if(_CACHE_RULE){
			if(list!=null){
				CacheList<RuleCondCache> clist = new CacheList<RuleCondCache>();
				clist.addAll(list);
				cache.delete(space, CacheUtils.RULE_COND_KEY+rule_id);
				cache.set(space,CacheUtils.RULE_COND_KEY+rule_id,clist,time);
			}
		}
		return list;
	}
	
	@Override
	public List<RuleAction> getRuleActions(String rule_id){
		List<RuleAction> list = null;
		if(_CACHE_RULE && !isRefershCache())list =(List<RuleAction>) cache.get(space, CacheUtils.RULE_ACTION_KEY+rule_id);
		if(list!=null)return list;
		String sql = SF.ruleSql("RULE_ACTION_QUERY");
		list = this.baseDaoSupport.queryForList(sql, RuleCond.class, rule_id);
		if(_CACHE_RULE){
			if(list!=null){
				CacheList<RuleAction> clist = new CacheList<RuleAction>();
				clist.addAll(list);
				cache.set(space,CacheUtils.RULE_ACTION_KEY+rule_id,clist,time);
			}
		}
		return list;
	}
	
	@Override
	public List<RuleCondCache> getUnContRuleCond(String rule_id){
		List<RuleCondCache> list = null;
		if(_CACHE_RULE && !isRefershCache())list =(List<RuleCondCache>) cache.get(space, CacheUtils.RULE_COND_KEY+rule_id);
		if(list!=null)return list;
		String sql = SF.ruleSql("UnContRuleCond");
		list = this.baseDaoSupport.queryForList(sql, RuleCondCache.class, rule_id,ManagerUtils.getSourceFrom());
		if(_CACHE_RULE){
			if(list!=null){
				CacheList<RuleCondCache> clist = new CacheList<RuleCondCache>();
				clist.addAll(list);
				cache.set(space,CacheUtils.RULE_COND_KEY+rule_id,clist,time);
			}
		}
		return list;
	}
	
	@Override
	public void refershCache(){
		IS_REFERSH_CACHE.set(true);
		String sql = "select t.* from es_rule_config t";
		List<RuleConfig> ruleList = this.baseDaoSupport.queryForList(sql, RuleConfig.class);
		for(RuleConfig rc:ruleList){
			/*this.getRuleCond(rc.getRule_id());
			this.getRuleActions(rc.getRule_id());
			this.getUnContRuleCond(rc.getRule_id());*/
			cache.delete(space, CacheUtils.RULE_COND_KEY+rc.getRule_id());
			cache.delete(space, CacheUtils.RULE_ACTION_KEY+rc.getRule_id());
		}
		
		String csSql= "select t.* from es_rule_action t";
		List<RuleAction> actionList = this.baseDaoSupport.queryForList(csSql, RuleAction.class);
		for(RuleAction ra:actionList){
			cache.delete(space, CacheUtils.RULE_CSKEY+ra.getRule_id()+"_"+ra.getAction_id());
			//this.queryRuleConsts(ra.getRule_id(), ra.getAction_id());
		}
		
		String preeSql ="select distinct a.rule_id,a.pid,c.plan_id from es_rule_config a,es_rule_rel b,es_rule_biz_plan c where a.rule_id = b.rule_id and b.plan_id = c.plan_id and  a.source_from='"+ManagerUtils.getSourceFrom()+"'";
		List<Map> preeLists = this.baseDaoSupport.queryForList(preeSql);
		for(Map ra:preeLists){
			cache.delete(space,CacheUtils.PLAN_RULE_KEY_P_R__E+(String)ra.get("plan_id")+"_"+(String)ra.get("pid")+"_"+EcsOrderConsts.RULE_EXE_0);
//			this.queryPeerPlanRule((String)ra.get("plan_id"), (String)ra.get("pid"), 0,  EcsOrderConsts.RULE_EXE_ALL);
			//this.queryPeerPlanRule((String)ra.get("plan_id"), (String)ra.get("pid"), 0,  EcsOrderConsts.RULE_EXE_0);
//			this.queryPeerPlanRule((String)ra.get("plan_id"), (String)ra.get("pid"), 0,  EcsOrderConsts.RULE_EXE_1);
			
		}
		//刷新方案与规则
		String planSql = "select t.* from es_rule_biz_plan t";
		List<Plan> planList = this.baseDaoSupport.queryForList(planSql, Plan.class);
		for(Plan p:planList){
//			if(!StringUtil.isEmpty(p.getCol3())){
//				this.getPlanByTacheCode(p.getCol3());
//			}
			cache.delete(space, CacheUtils.PLAN_KEY+p.getPlan_id());
			//this.getPlan(p.getPlan_id());
			//缓存方案规则
			/*this.listPlanRule(p.getPlan_id(), EcsOrderConsts.RULE_EXE_ALL);
			this.listPlanRule(p.getPlan_id(), EcsOrderConsts.RULE_EXE_0);
			this.listPlanRule(p.getPlan_id(), EcsOrderConsts.RULE_EXE_1);*/
			cache.delete(space, CacheUtils.PLANRULEKEY+p.getPlan_id()+"_"+EcsOrderConsts.RULE_EXE_ALL);
			cache.delete(space, CacheUtils.PLANRULEKEY+p.getPlan_id()+"_"+EcsOrderConsts.RULE_EXE_0);
			cache.delete(space, CacheUtils.PLANRULEKEY+p.getPlan_id()+"_"+EcsOrderConsts.RULE_EXE_1);
		}
		
		String relSql = "select distinct t.rule_id,t.plan_id from es_rule_rel t";
		List<RuleRel> list = this.baseDaoSupport.queryForList(relSql, RuleRel.class);
		for(RuleRel rr:list){
			//缓存规则
			/*this.getPlanRule(rr.getRule_id(), rr.getPlan_id(), EcsOrderConsts.RULE_EXE_ALL);
			this.getPlanRule(rr.getRule_id(), rr.getPlan_id(), EcsOrderConsts.RULE_EXE_0);
			this.getPlanRule(rr.getRule_id(), rr.getPlan_id(), EcsOrderConsts.RULE_EXE_1);*/
			cache.delete(space, CacheUtils.PLAN_RULE_KEY_ID+rr.getRule_id()+EcsOrderConsts.RULE_EXE_ALL);
			cache.delete(space, CacheUtils.PLAN_RULE_KEY_ID+rr.getRule_id()+EcsOrderConsts.RULE_EXE_0);
			cache.delete(space, CacheUtils.PLAN_RULE_KEY_ID+rr.getRule_id()+EcsOrderConsts.RULE_EXE_1);
			//缓存子规则
			/*this.queryChildrenRule(rr.getPlan_id(), rr.getRule_id(), EcsOrderConsts.RULE_EXE_ALL);
			this.queryChildrenRule(rr.getPlan_id(), rr.getRule_id(), EcsOrderConsts.RULE_EXE_0);
			this.queryChildrenRule(rr.getPlan_id(), rr.getRule_id(), EcsOrderConsts.RULE_EXE_1);*/
			cache.delete(space, CacheUtils.CPLANRULEKEY+rr.getPlan_id()+"_"+rr.getRule_id()+"_"+EcsOrderConsts.RULE_EXE_ALL);
			cache.delete(space, CacheUtils.CPLANRULEKEY+rr.getPlan_id()+"_"+rr.getRule_id()+"_"+EcsOrderConsts.RULE_EXE_0);
			cache.delete(space, CacheUtils.CPLANRULEKEY+rr.getPlan_id()+"_"+rr.getRule_id()+"_"+EcsOrderConsts.RULE_EXE_1);
			//缓存依赖规则
			/*this.queryRuleRelyOnRule(rr.getPlan_id(), rr.getRule_id(), EcsOrderConsts.RULE_EXE_ALL);
			this.queryRuleRelyOnRule(rr.getPlan_id(), rr.getRule_id(), EcsOrderConsts.RULE_EXE_0);
			this.queryRuleRelyOnRule(rr.getPlan_id(), rr.getRule_id(), EcsOrderConsts.RULE_EXE_1);*/
			cache.delete(space, CacheUtils.RELYPLANRULE+rr.getPlan_id()+"_"+rr.getRule_id()+"_"+EcsOrderConsts.RULE_EXE_ALL);
			cache.delete(space, CacheUtils.RELYPLANRULE+rr.getPlan_id()+"_"+rr.getRule_id()+"_"+EcsOrderConsts.RULE_EXE_0);
			cache.delete(space, CacheUtils.RELYPLANRULE+rr.getPlan_id()+"_"+rr.getRule_id()+"_"+EcsOrderConsts.RULE_EXE_1);
		}
		
		//缓存方案目录
		//this.queryPlanByCatalogueId(EcsOrderConsts.ORDER_MODEL_MATCH_DIR);
		cache.delete(space, CacheUtils.CATALOGUE_PLAN_KEY+EcsOrderConsts.ORDER_MODEL_MATCH_DIR);
		
		IS_REFERSH_CACHE.remove();
		logger.info("刷新规则缓存完成===============================");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Plan getPlanByTacheCode(String tacheCode){
		Plan plan = null;
		if(_CACHE_RULE && !isRefershCache())plan = (Plan) cache.get(space, CacheUtils.PLAN_TACHE_CODE + "_" + tacheCode);
		String sql = SF.ruleSql("");
		if(plan!=null)return plan;
		List<Plan> list = this.baseDaoSupport.queryForList(sql, Plan.class, tacheCode);
		plan = (list!=null && list.size()>0)?list.get(0):null;
		if(_CACHE_RULE && plan!=null)cache.set(space, CacheUtils.PLAN_TACHE_CODE + "_" + tacheCode, plan,time);
		return plan;
	}
	
}

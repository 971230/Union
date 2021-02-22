package com.ztesoft.net.auto.rule.i.impl;

import java.util.List;

import org.drools.core.util.StringUtils;

import com.powerise.ibss.util.RuleUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.auto.rule.i.IAutoRule;
import com.ztesoft.net.auto.rule.i.IPlanConfigManager;
import com.ztesoft.net.auto.rule.vo.Plan;
import com.ztesoft.net.auto.rule.vo.RuleExeLog;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.RuleConfig;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

public class PlanConfigManager extends BaseSupport implements IPlanConfigManager {
	
	private IAutoRule autoRuleImpl;
	
	public IAutoRule getAutoRuleImpl() {
		return autoRuleImpl;
	}

	public void setAutoRuleImpl(IAutoRule autoRuleImpl) {
		this.autoRuleImpl = autoRuleImpl;
	}

	@Override
	public Plan getPlan(String plan_id) {
		return autoRuleImpl.getPlan(plan_id);
	}

	@Override
	public List<RuleConfig> queryRuleConfigByConfig(String plan_id,String p_rule_id) {
		String sql = SF.ruleSql("QUERY_PLAN_RULE_CONFIG");
		if(StringUtil.isEmpty(p_rule_id))p_rule_id = "0";
		List<RuleConfig> list = this.baseDaoSupport.queryForList(sql, RuleConfig.class, plan_id,ManagerUtils.getSourceFrom(),p_rule_id);
		return list;
	}
	
	@Override
	public List<RuleConfig> queryLoopRuleConfigByConfig(String plan_id,String p_rule_id,String is_his_order){
		return queryLoopRuleConfigByConfig(plan_id, p_rule_id, null,is_his_order);
	}
	
	@Override
	public List<RuleConfig> queryLoopRuleConfigByConfig(String plan_id,String p_rule_id,String obj_id,String is_his_order){
		List<RuleConfig> list = queryRuleConfigByConfig(plan_id,p_rule_id);
		if(list!=null && list.size()>0){
			for(RuleConfig c:list){
				c.setChildrenList(queryLoopRuleConfigByConfig(plan_id, c.getRule_id(),obj_id,is_his_order));
				if(!StringUtil.isEmpty(obj_id)){
					c.setObjRuleExeLogList(this.queryRuleExeLog(plan_id, c.getRule_id(), obj_id,is_his_order));
				}
			}
		}
		return list;
	}
	
	@Override
	public List<RuleExeLog> queryRuleExeLog(String plan_id, String rule_id,
			String obj_id,String is_his_order) {
		String sql =null;
		if(StringUtils.isEmpty(plan_id)){
			if(is_his_order!=null&&is_his_order.equals(EcsOrderConsts.IS_ORDER_HIS_YES)){//历史表
				sql = SF.ruleSql("QUERY_RULE_EXE_LOG_HIS");
			}else{
				sql = SF.ruleSql("QUERY_RULE_EXE_LOG");
			}
			//****************** add by qin.yingxiong at 2016/10/20 start *******************
			boolean useOrgTable = RuleUtil.useOrgTable(obj_id);
			if(useOrgTable) {
				sql = RuleUtil.replaceTableNameForOrgBySql(sql);
			}
			//****************** add by qin.yingxiong at 2016/10/20 end *******************
			return this.baseDaoSupport.queryForList(sql, RuleExeLog.class, rule_id,obj_id);
		}else{
			if(is_his_order!=null&&is_his_order.equals(EcsOrderConsts.IS_ORDER_HIS_YES)){//历史表
				sql = SF.ruleSql("QUERY_PLAN_RULE_EXE_LOG_HIS");
			}else{
				sql = SF.ruleSql("QUERY_PLAN_RULE_EXE_LOG");
			}
			//****************** add by qin.yingxiong at 2016/10/20 start *******************
			boolean useOrgTable = RuleUtil.useOrgTable(obj_id);
			if(useOrgTable) {
				sql = RuleUtil.replaceTableNameForOrgBySql(sql);
			}
			//****************** add by qin.yingxiong at 2016/10/20 end *******************
			return this.baseDaoSupport.queryForList(sql, RuleExeLog.class, plan_id,rule_id,obj_id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RuleExeLog> qryRuleLogByPojo(RuleExeLog pojo,List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.baseDaoSupport.queryPojoList("es_rule_exe_log", pojo, sqlBuilds);
	}
}

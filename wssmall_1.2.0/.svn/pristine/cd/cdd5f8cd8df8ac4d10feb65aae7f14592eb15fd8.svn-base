package com.ztesoft.rule.core.bo;

import java.util.List;
import java.util.Map;

import com.ztesoft.rule.core.module.cfg.BizPlan;
import com.ztesoft.rule.core.module.cfg.ConfigData;
import com.ztesoft.rule.core.module.cfg.MidDataConfig;
import com.ztesoft.rule.core.module.cfg.RetryInfo;
import com.ztesoft.rule.core.module.cfg.RuleConfig;

public interface IRuleDBAccess {
	
	public List loadPartners(BizPlan plan);
	public int partnersSize(BizPlan plan);
	public int partnersSize(BizPlan plan , int mold);
	public List<Map> loadPartners(BizPlan plan ,int pi , int ps );
	public List<Map> loadPartners(BizPlan plan, int mold ,int pi , int ps );
	
	public List<MidDataConfig> loadMidDataConfigs(String planId);
	public List<RuleConfig> loadRuleConfigs(String planId) ;
	
	List<BizPlan> loadExecBizPlans() ;
	public BizPlan getBizPlansByCode(String planCode);
		
	public ConfigData loadConfigData(String plan_code) ;
	
	public List<Map<String,String>> loadFactDataConfigs(String planCode) ;
	
	public List<String> loadFactFilterConfigs(String planId) ;
	
	public List<Map> loadRuleObjConfigs()  ;
	public List<Map> loadRuleObjAttrConfigs() ;
	
	public String nextVal(String seqName) ;
	
	
	public void updateRuleObj(List<Map> ruleObjs);
	public void insertRuleObj(List<Map> ruleObjs) ;
	public void updateRuleObjAttr(List<Map> ruleObjAttrs);
	public void insertRuleObjAttr(List<Map> ruleObjAttrs);
	
	public List<RetryInfo> loadRetryInfo() ;
	
	public void updateBizPlan(BizPlan plan) ;
	
	public int saveRes(Map data) ;
	public void updateResStatus(long resId , String statusCd) ;
	public void updateFailRes() ;
	
	
	
	public boolean takeUpBizPlan(String planId , String stateCd)  ;
	public boolean releaseBizPlan(String planId , String stateCd)  ;
	public String insertPlanLog(Map data) ;
	public void updatePlanLog(Map data)  ;
	
	/**
	 * 获取方案参与者数量
	 * @作者 MoChunrun
	 * @创建日期 2014-2-26 
	 * @param plan_id
	 * @return
	 */
	public int getPlanEntityCount(String plan_id);
}

package com.ztesoft.rule.core.module.cfg;

import java.util.List;
import java.util.Map;



public class ConfigData {
	//业务方案
	private BizPlan bizPlan ;
	
	//中间数据配置
	List<MidDataConfig> mdConfigs ;
	
	//规则配置数据
	List<RuleConfig> ruleConfigs ;
	
	//是否已处理完毕?
	private boolean isFinshed = false ;
	
	//方案参与者数量
	private int planEntityNum;

	
	public int getPlanEntityNum() {
		return planEntityNum;
	}

	public void setPlanEntityNum(int planEntityNum) {
		this.planEntityNum = planEntityNum;
	}

	private String resId ;
	private String  logId ;
	
	
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public ConfigData(){
		
	}
	
	//设置运行时上下文参数(由外到内传递的参数)
	public void setJitContextParam(Map param){
		bizPlan.setParamMap(param) ;
	}
	
	public List<MidDataConfig> getMdConfigs() {
		return mdConfigs;
	}

	public void setMdConfigs(List<MidDataConfig> mdConfigs , BizPlan bizPlan ) {
		if(mdConfigs != null && !mdConfigs.isEmpty() && bizPlan != null ){
			for(MidDataConfig mdConfig : mdConfigs){
				mdConfig.setPlan(bizPlan) ;
 			}
		}
		this.mdConfigs = mdConfigs;
	}


	public List<RuleConfig> getRuleConfigs() {
		return ruleConfigs;
	}

	public void setRuleConfigs(List<RuleConfig> ruleConfigs) {
		this.ruleConfigs = ruleConfigs;
	}


	public BizPlan getBizPlan() {
		return bizPlan;
	}


	public void setBizPlan(BizPlan bizPlan) {
		this.bizPlan = bizPlan;
	}


	//设置运行模式
	public void setRunType(String run_type ){
		bizPlan.setRun_type(run_type) ;
	}

	public String getRunType(){
		return bizPlan.getRun_type() ;
	}
	

	
	public boolean isFinshed() {
		return isFinshed;
	}

	public void setFinshed(boolean isFinshed) {
		this.isFinshed = isFinshed;
	}
}

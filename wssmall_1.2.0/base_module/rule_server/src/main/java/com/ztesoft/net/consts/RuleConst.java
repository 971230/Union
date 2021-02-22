package com.ztesoft.net.consts;

public class RuleConst {
	/**
	 * 联通订单系统标识，主要用于区分新老规则界面
	 */
	public static final String RULE_SYS_FLAG = "RULE_ECSORD";
	
	/**
	 * 联通订单系统标识，规则条件类型
	 */
	public static final String RULE_CAL_COND = "cal_cond";		//参与计算
	public static final String RULE_FINAL_COND = "final_cond";	//不参与计算
	
	/**
	 * Drools 操作符号类型
	 */
	public static final String DROOLS_KEY = "100001";	//不参与计算
	
	public static final String DROOLS_CLASS_PATH = "com.ztesoft.net.rule.common.CommonFact";
	public static final String DROOLS_CLASS = "commonFact";
	public static final String DROOLS_CLASS_NAME = "CommonFact";
	
	public static final String DROOLS_WITHOUT_COND = "without_cond";
	public static final String DROOLS_NEVER_RUN = "never_run_cond";
	
	
}

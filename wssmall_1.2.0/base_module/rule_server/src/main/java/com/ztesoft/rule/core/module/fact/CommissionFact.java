package com.ztesoft.rule.core.module.fact;

import java.util.List;
import java.util.Map;

public class CommissionFact extends DefFact{
//	业务类型	业务方案大类	业务方案小类	业务方案名称	酬金类型	支付方式	区公司佣金上限	酬金规则定义						开始时间	结束时间	扣罚规则	支付规则备注
//	计算规则			分公司酬金标准（元/户）	酬金归属帐期	产品或业务				
	
	//业务类型
	private String bizType ; 
	
	//业务方案大类
	private String planMinClass ;
	
	//业务方案小类
	private String planMaxClass ;
	
//	//业务方案名称
//	private String planName ;
	
	//业务方案编码
	private String planCode ;
	
	//酬金类型
	private String commissionType ;
	
	//支付方式
	private String paymentMode ;
	
	//区公司佣金上限
	private String commissionUpperLimit ;
	
	//酬金规则标识
	private String ruleId ;
	
	//酬金归属帐期
	private String belogCycleId ;
	
	//产品或业务
	private String prodType ;
	
	

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getPlanMinClass() {
		return planMinClass;
	}

	public void setPlanMinClass(String planMinClass) {
		this.planMinClass = planMinClass;
	}

	public String getPlanMaxClass() {
		return planMaxClass;
	}

	public void setPlanMaxClass(String planMaxClass) {
		this.planMaxClass = planMaxClass;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getCommissionUpperLimit() {
		return commissionUpperLimit;
	}

	public void setCommissionUpperLimit(String commissionUpperLimit) {
		this.commissionUpperLimit = commissionUpperLimit;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getBelogCycleId() {
		return belogCycleId;
	}

	public void setBelogCycleId(String belogCycleId) {
		this.belogCycleId = belogCycleId;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	@Override
	public void handleOneRuleResule(Map data, RuleResult ruleResult) {
		// TODO Auto-generated method stub
		data.put("result_jk", ruleResult.getResult()) ;
	}

	@Override
	public void processFinalResult(Map data, RuleResults ruleResults) {
		// TODO Auto-generated method stub
		int result = 0 ; 
		for(RuleResult r : ruleResults.getResults()){
			result+=Integer.parseInt((String) r.getResult() ) ;
		}
		data.put("final_result", result) ;
	}
	
	
	
	public static void main(String[] aa){
		CommissionFact fact = new CommissionFact() ;
		fact.addResult("ruleCode_001", "56") ;
		fact.addResult("ruleCode_002", "44") ;
		
		fact.setBelogCycleId("201312");
		fact.setBizType("uucall") ;
		fact.setCommissionType("小猫咪") ;
		fact.setCommissionUpperLimit("ulimit") ;
		fact.setMid_data_code("blueSky") ;//无
		fact.setPaymentMode("PAYMENT_001") ;
		fact.setPlanCode("HeHe") ;
		fact.setPlan_id("1000") ;//无
		fact.setPlanMaxClass("F1") ;
		fact.setPlanMinClass("f2") ;
		fact.setProdType("ADSL") ;
		fact.setRuleId("1") ;
//		fact.setValidFlag(false) ;
		DefFact ff = fact ;
		Map m1 = ff.factFinalResult() ;
		List<Map> lm = ff.factEveryRuleResults() ;
		int ii = 0 ; 
	}
}

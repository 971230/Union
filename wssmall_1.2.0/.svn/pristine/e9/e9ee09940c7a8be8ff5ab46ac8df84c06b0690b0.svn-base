package com.ztesoft.rule.core.module.fact;

import java.util.Map;


/**
 * TODO
 * @author easonwu 
 * @creation Dec 18, 2013
 * 
 */
public class TestBiz1 extends DefFact {

	private String id ;
	private String biz_type ;
	private int prod_num ;
	private String agent_id ;
	private String create_date ;
	private String cycle_id ;
	private double prod_price ;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBiz_type() {
		return biz_type;
	}

	public void setBiz_type(String biz_type) {
		this.biz_type = biz_type;
	}


	public int getProd_num() {
		return prod_num;
	}

	public void setProd_num(int prod_num) {
		this.prod_num = prod_num;
	}

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getCycle_id() {
		return cycle_id;
	}

	public void setCycle_id(String cycle_id) {
		this.cycle_id = cycle_id;
	}


	public double getProd_price() {
		return prod_price;
	}

	public void setProd_price(double prod_price) {
		this.prod_price = prod_price;
	}

	@Override
	public void handleOneRuleResule(Map data, RuleResult ruleResult) {
		// TODO Auto-generated method stub
		data.put("renum_value", ruleResult.getResult().toString()) ;
	}

	@Override
	public void processFinalResult(Map data, RuleResults ruleResults) {
		// TODO Auto-generated method stub
		double result = 0.0 ; 
		for(RuleResult r : ruleResults.getResults()){
			result+=((Double) r.getResult())  ;
		}
		data.put("renum_value",Double.valueOf( result).toString() ) ;
	}

}

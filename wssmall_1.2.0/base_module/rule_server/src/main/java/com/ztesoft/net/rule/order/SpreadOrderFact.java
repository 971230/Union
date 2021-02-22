package com.ztesoft.net.rule.order;

import java.util.Map;

import com.ztesoft.net.annotation.DroolsFactField;
import com.ztesoft.rule.core.module.fact.DefFact;
import com.ztesoft.rule.core.module.fact.RuleResult;
import com.ztesoft.rule.core.module.fact.RuleResults;

//@DroolsFact(name="订单结算",code="spreadOrderFact")
public class SpreadOrderFact extends DefFact{

	@DroolsFactField(name="结算佣金")
	private Double amount;	
	@DroolsFactField(name="商品id")
	private String goods_id;
	@DroolsFactField(name="商品价格")
	private Double goods_price;
	@DroolsFactField(name="商品数量")
	private String goods_num;
	@DroolsFactField(name="订单id")
	private String order_id;
	@DroolsFactField(name="推荐人id")
	private String spread_id;
	
	
	
	@Override
	public void processFinalResult(Map data, RuleResults ruleResults) {
		double amount = 0d;
		for(RuleResult rs:ruleResults.getResults()){
			amount += Double.parseDouble(String.valueOf(rs.getResult()));
		}
		data.put("amount", amount) ;
	}

	@Override
	public void handleOneRuleResule(Map data, RuleResult ruleResult) {
		data.put("amount", ruleResult.getResult()) ;
		data.put("rule_id", ruleResult.getRule_id());
		data.put("rule_code", ruleResult.getRuleCode());
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}


	public Double getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(Double goods_price) {
		this.goods_price = goods_price;
	}

	public String getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getSpread_id() {
		return spread_id;
	}

	public void setSpread_id(String spread_id) {
		this.spread_id = spread_id;
	}

}

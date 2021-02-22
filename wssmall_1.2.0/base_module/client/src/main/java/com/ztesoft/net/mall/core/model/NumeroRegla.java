package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.mall.core.annotation.Id;
import com.ztesoft.net.mall.core.annotation.Table;

@Table(name="ES_NO_RULE")
public class NumeroRegla {

	/**
	 * ID
	 */
	private String rule_id;

	/**
	 * 规则名称
	 */
	private String rule_name;

	/**
	 * 规则描述
	 */
	private String rule_desc;

	/**
	 * 靓号分类
	 */
	private String no_classify;

	/**
	 * 预存款
	 */
	private  String deposit;

	/**
	 * 合约期
	 */
	private String period;

	/**
	 * 最低消费
	 */
	private String lowest;

	/**
	 * 数据来源
	 */
	private String source_from;

	@Id
	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public String getRule_name() {
		return rule_name;
	}

	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

	public String getRule_desc() {
		return rule_desc;
	}

	public void setRule_desc(String rule_desc) {
		this.rule_desc = rule_desc;
	}

	public String getNo_classify() {
		return no_classify;
	}

	public void setNo_classify(String no_classify) {
		this.no_classify = no_classify;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getLowest() {
		return lowest;
	}

	public void setLowest(String lowest) {
		this.lowest = lowest;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

}

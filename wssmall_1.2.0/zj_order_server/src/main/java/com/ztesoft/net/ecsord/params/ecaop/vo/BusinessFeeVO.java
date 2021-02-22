package com.ztesoft.net.ecsord.params.ecaop.vo;

/**
 * 费用详情
 * 
 * @author songqi
 *
 */
public class BusinessFeeVO {
	
	private String fee_desc;
	private String subject_id;
	private String need_amount;
	private String deration_amount;
	private String real_amount;
	private String can_change;
	private String max_deration_value;
	private String fee_rule_id;

	public String getFee_desc() {
		return fee_desc;
	}

	public void setFee_desc(String fee_desc) {
		this.fee_desc = fee_desc;
	}

	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	public String getNeed_amount() {
		return need_amount;
	}

	public void setNeed_amount(String need_amount) {
		this.need_amount = need_amount;
	}

	public String getDeration_amount() {
		return deration_amount;
	}

	public void setDeration_amount(String deration_amount) {
		this.deration_amount = deration_amount;
	}

	public String getReal_amount() {
		return real_amount;
	}

	public void setReal_amount(String real_amount) {
		this.real_amount = real_amount;
	}

	public String getCan_change() {
		return can_change;
	}

	public void setCan_change(String can_change) {
		this.can_change = can_change;
	}

	public String getMax_deration_value() {
		return max_deration_value;
	}

	public void setMax_deration_value(String max_deration_value) {
		this.max_deration_value = max_deration_value;
	}

	public String getFee_rule_id() {
		return fee_rule_id;
	}

	public void setFee_rule_id(String fee_rule_id) {
		this.fee_rule_id = fee_rule_id;
	}

}

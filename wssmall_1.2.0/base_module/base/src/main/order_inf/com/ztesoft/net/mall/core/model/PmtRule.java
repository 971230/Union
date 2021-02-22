package com.ztesoft.net.mall.core.model;

/**
 * PmtRule generated by MyEclipse - Hibernate Tools
 */

public class PmtRule implements java.io.Serializable {

	// Fields

	private String rule_id;

	private String pmt_id;

	private String begin_time;

	private String end_time;

	private Double discount;

	private String brief;

	private Integer type;

	private String update_time;

	private Double order_price;

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Double getOrder_price() {
		return order_price;
	}

	public void setOrder_price(Double order_price) {
		this.order_price = order_price;
	}

	public String getPmt_id() {
		return pmt_id;
	}

	public void setPmt_id(String pmt_id) {
		this.pmt_id = pmt_id;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}


	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

}
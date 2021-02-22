package com.ztesoft.net.mall.core.model;

/**
 * 流量卡调拨实体
 * 
 * @author wui
 */
public class Rate implements java.io.Serializable {
	private String card_id;
	private String order_id;
	private String to_userid;
	private String from_userid;
	private String visit_url;
	private String state;
	private String sec_order_id;
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getTo_userid() {
		return to_userid;
	}
	public void setTo_userid(String to_userid) {
		this.to_userid = to_userid;
	}
	public String getFrom_userid() {
		return from_userid;
	}
	public void setFrom_userid(String from_userid) {
		this.from_userid = from_userid;
	}
	public String getVisit_url() {
		return visit_url;
	}
	public void setVisit_url(String visit_url) {
		this.visit_url = visit_url;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSec_order_id() {
		return sec_order_id;
	}
	public void setSec_order_id(String sec_order_id) {
		this.sec_order_id = sec_order_id;
	}
	
}
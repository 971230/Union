package com.ztesoft.net.mall.core.model;

/**
 * 服务规则关联实体
 * @author hu.yi
 * @date 2014.02.21
 */
public class ServiceOfferRule {

	private String rule_id;
	private String service_id;
	private String order_id;
	private String source_from;
	
	
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
}

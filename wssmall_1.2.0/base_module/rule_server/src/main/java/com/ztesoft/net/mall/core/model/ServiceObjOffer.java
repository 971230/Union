package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;

public class ServiceObjOffer {
	private String service_id;
	private String product_id;
	private String service_offer_name;
	private String remark;
	private String create_date;
	private String display_flag;
	private String parent_service_id;
	private String page_url;
	private String service_code;
	private String service_tip;
	private String service_scope;
	private String service_promt_desc;
	private String source_from;
	
	private String start_time;
	private String end_time;
	
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public String getService_offer_name() {
		return service_offer_name;
	}
	public void setService_offer_name(String service_offer_name) {
		this.service_offer_name = service_offer_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getDisplay_flag() {
		return display_flag;
	}
	public void setDisplay_flag(String display_flag) {
		this.display_flag = display_flag;
	}
	public String getParent_service_id() {
		return parent_service_id;
	}
	public void setParent_service_id(String parent_service_id) {
		this.parent_service_id = parent_service_id;
	}
	public String getPage_url() {
		return page_url;
	}
	public void setPage_url(String page_url) {
		this.page_url = page_url;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getService_tip() {
		return service_tip;
	}
	public void setService_tip(String service_tip) {
		this.service_tip = service_tip;
	}
	public String getService_scope() {
		return service_scope;
	}
	public void setService_scope(String service_scope) {
		this.service_scope = service_scope;
	}
	public String getService_promt_desc() {
		return service_promt_desc;
	}
	public void setService_promt_desc(String service_promt_desc) {
		this.service_promt_desc = service_promt_desc;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	@NotDbField
	public String getStart_time() {
		return start_time;
	}
	@NotDbField
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	@NotDbField
	public String getEnd_time() {
		return end_time;
	}
	@NotDbField
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
}

package com.ztesoft.net.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

public class EsearchExpInst implements Serializable {
	private static final long serialVersionUID = 1L;
	private String excp_inst_id;//异常实例id
	private String rel_obj_id;//关联实例id
	private String rel_obj_type;//管理实例类型
	private Integer seq;//序号
	private String excp_create_time;//异常创建时间
	private String excp_update_time;//异常更新时间
	private String log_id;//报文实例id
	private String search_id;//搜索id
	private String key_id;//关键字id
	private String catalog_id;//归类id
	private String record_status;//(0：已处理 1：未处理)
	private String deal_result;//处理结果
	private String deal_staff_no;//处理人
	private String deal_time;//处理时间
	private String rel_obj_create_time;//关联实例创建时间
	private String out_tid;//外部订单id
	//是否可见 Y 可见   N 不可见
	private String is_visible;
	
	//订单扩展表的orderid
	private String ext_order_id;
	//订单当前环节编码
	private String flow_trace_id;
	//退单状态
	private String refund_deal_type;
	
	public String getExcp_inst_id() {
		return excp_inst_id;
	}
	public void setExcp_inst_id(String excp_inst_id) {
		this.excp_inst_id = excp_inst_id;
	}
	public String getRel_obj_id() {
		return rel_obj_id;
	}
	public void setRel_obj_id(String rel_obj_id) {
		this.rel_obj_id = rel_obj_id;
	}
	public String getRel_obj_type() {
		return rel_obj_type;
	}
	public void setRel_obj_type(String rel_obj_type) {
		this.rel_obj_type = rel_obj_type;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getExcp_create_time() {
		return excp_create_time;
	}
	public void setExcp_create_time(String excp_create_time) {
		this.excp_create_time = excp_create_time;
	}
	public String getExcp_update_time() {
		return excp_update_time;
	}
	public void setExcp_update_time(String excp_update_time) {
		this.excp_update_time = excp_update_time;
	}
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getSearch_id() {
		return search_id;
	}
	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}
	public String getKey_id() {
		return key_id;
	}
	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}
	public String getCatalog_id() {
		return catalog_id;
	}
	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}
	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
	public String getDeal_result() {
		return deal_result;
	}
	public void setDeal_result(String deal_result) {
		this.deal_result = deal_result;
	}
	public String getDeal_staff_no() {
		return deal_staff_no;
	}
	public void setDeal_staff_no(String deal_staff_no) {
		this.deal_staff_no = deal_staff_no;
	}
	public String getDeal_time() {
		return deal_time;
	}
	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}
	public String getRel_obj_create_time() {
		return rel_obj_create_time;
	}
	public void setRel_obj_create_time(String rel_obj_create_time) {
		this.rel_obj_create_time = rel_obj_create_time;
	}
	public String getOut_tid() {
		return out_tid;
	}
	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}
	public String getIs_visible() {
		return is_visible;
	}
	public void setIs_visible(String is_visible) {
		this.is_visible = is_visible;
	}
	@NotDbField
	public String getExt_order_id() {
		return ext_order_id;
	}
	@NotDbField
	public void setExt_order_id(String ext_order_id) {
		this.ext_order_id = ext_order_id;
	}
	@NotDbField
	public String getFlow_trace_id() {
		return flow_trace_id;
	}
	@NotDbField
	public void setFlow_trace_id(String flow_trace_id) {
		this.flow_trace_id = flow_trace_id;
	}
	@NotDbField
	public String getRefund_deal_type() {
		return refund_deal_type;
	}
	@NotDbField
	public void setRefund_deal_type(String refund_deal_type) {
		this.refund_deal_type = refund_deal_type;
	}
}

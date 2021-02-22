package com.ztesoft.net.param.inner;

import java.io.Serializable;

public class ExpInstQueryInner implements Serializable{
	private static final long serialVersionUID = 1L;
	//关键字id
	private String key_id;
	//关键字名称
	private String key_name;
	//归类id
	private String catalog_id;
	//归类名称
	private String catalog_name;
	//esearch实例id
	private String esearch_id;
	//搜索id
	private String search_id;
	//搜索编码
	private String search_code;
	//查询时间段（开始时间）
	private String start_excp_time;
	//查询时间段（结束时间）
	private String end_excp_time;
	//查询时间段（订单创建开始时间）
	private String start_obj_create_time;
	//查询时间段（订单创建结束时间）
	private String end_obj_create_time;
	//关联实例iid
	private String rel_obj_id;
	//关联实例类型
	private String rel_obj_type;
	//异常实例id
	private String excp_inst_id;
	//关键字值
	private String match_content;
	//是否处理状态
	private String record_status;
	//报文日志id
	private String log_id;
	
	//外补单号
	private String out_tid;
	
	private String shortcut_solution_id;
	
	//订单环节流程ID ES_ORDER_EXT->flow_trace_id
	private String flow_id;
	private String flow_id_c;
	// 开户号码
	private String phone_num;
	//订单归属地市 es_order->order_city_code
	private String order_city_code;
	private String order_city_code_c;
	//订单来源   ES_ORDER_EXT->order_from
	private String order_from;
	private String order_from_c;
	//是否历史数据
	private String is_history;
	//有无关键字
	private String has_match_content;
	//人工标记异常名称
	private String artificial_exception_type;
	
	public String getKey_id() {
		return key_id;
	}
	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}
	public String getKey_name() {
		return key_name;
	}
	public void setKey_name(String key_name) {
		this.key_name = key_name;
	}
	public String getCatalog_id() {
		return catalog_id;
	}
	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}
	public String getCatalog_name() {
		return catalog_name;
	}
	public void setCatalog_name(String catalog_name) {
		this.catalog_name = catalog_name;
	}
	public String getEsearch_id() {
		return esearch_id;
	}
	public void setEsearch_id(String esearch_id) {
		this.esearch_id = esearch_id;
	}
	public String getSearch_id() {
		return search_id;
	}
	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}
	public String getStart_excp_time() {
		return start_excp_time;
	}
	public void setStart_excp_time(String start_excp_time) {
		this.start_excp_time = start_excp_time;
	}
	public String getEnd_excp_time() {
		return end_excp_time;
	}
	public void setEnd_excp_time(String end_excp_time) {
		this.end_excp_time = end_excp_time;
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
	public String getExcp_inst_id() {
		return excp_inst_id;
	}
	public void setExcp_inst_id(String excp_inst_id) {
		this.excp_inst_id = excp_inst_id;
	}
	public String getMatch_content() {
		return match_content;
	}
	public void setMatch_content(String match_content) {
		this.match_content = match_content;
	}
	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getSearch_code() {
		return search_code;
	}
	public void setSearch_code(String search_code) {
		this.search_code = search_code;
	}
	public String getStart_obj_create_time() {
		return start_obj_create_time;
	}
	public void setStart_obj_create_time(String start_obj_create_time) {
		this.start_obj_create_time = start_obj_create_time;
	}
	public String getEnd_obj_create_time() {
		return end_obj_create_time;
	}
	public void setEnd_obj_create_time(String end_obj_create_time) {
		this.end_obj_create_time = end_obj_create_time;
	}
	public String getShortcut_solution_id() {
		return shortcut_solution_id;
	}
	public void setShortcut_solution_id(String shortcut_solution_id) {
		this.shortcut_solution_id = shortcut_solution_id;
	}
	public String getOut_tid() {
		return out_tid;
	}
	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}
	public String getFlow_id() {
		return flow_id;
	}
	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}
	public String getFlow_id_c() {
		return flow_id_c;
	}
	public void setFlow_id_c(String flow_id_c) {
		this.flow_id_c = flow_id_c;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getOrder_city_code() {
		return order_city_code;
	}
	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}
	public String getOrder_city_code_c() {
		return order_city_code_c;
	}
	public void setOrder_city_code_c(String order_city_code_c) {
		this.order_city_code_c = order_city_code_c;
	}
	public String getOrder_from() {
		return order_from;
	}
	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}
	public String getOrder_from_c() {
		return order_from_c;
	}
	public void setOrder_from_c(String order_from_c) {
		this.order_from_c = order_from_c;
	}
	public String getIs_history() {
		return is_history;
	}
	public void setIs_history(String is_history) {
		this.is_history = is_history;
	}
	public String getHas_match_content() {
		return has_match_content;
	}
	public void setHas_match_content(String has_match_content) {
		this.has_match_content = has_match_content;
	}
	public String getArtificial_exception_type() {
		return artificial_exception_type;
	}
	public void setArtificial_exception_type(String artificial_exception_type) {
		this.artificial_exception_type = artificial_exception_type;
	}
}

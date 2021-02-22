package com.ztesoft.net.param.inner;

import java.io.Serializable;

/**
 * 异常实例入参
 * @author shen.qiyu
 *
 */
public class ExpInstInner implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//关联实例id
	private String rel_obj_id;
	//关联实例类型
	private String rel_obj_type;
	//报文实例id
	private String esearch_id;
	//搜索id
	private String search_id;
	//关键字id
	private String key_id;
	//异常实例id
	private String excp_inst_id;
	//归类id
	private String catalog_id;
	//外部订单id
	private String out_tid;
	//异常更新时间
	private String excp_update_time;
	//是否可见 Y 可见   N 不可见
	private String is_visible;
	//异常单状态
	private String record_status;//(0：已处理 1：未处理)
	
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
	public String getKey_id() {
		return key_id;
	}
	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}
	public String getExcp_inst_id() {
		return excp_inst_id;
	}
	public void setExcp_inst_id(String excp_inst_id) {
		this.excp_inst_id = excp_inst_id;
	}
	public String getCatalog_id() {
		return catalog_id;
	}
	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}
	public String getOut_tid() {
		return out_tid;
	}
	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}
	public String getExcp_update_time() {
		return excp_update_time;
	}
	public void setExcp_update_time(String excp_update_time) {
		this.excp_update_time = excp_update_time;
	}
	public String getIs_visible() {
		return is_visible;
	}
	public void setIs_visible(String is_visible) {
		this.is_visible = is_visible;
	}
	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
}

package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

public class ImportMidData implements Serializable {

	private String id;
	private String log_id;
	private String batch_id;
	private String file_name;
	private String template_id;
	private String data_json;
	private String created_time;
	private String oper_id;
	private String oper_name;
	private String status;
	private String deal_time;
	private String deal_desc;
	private String rel_object;
	private String total_num;
	private String succ_num;
	private String fail_num;
	private String wait_num;
	private String template_name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getData_json() {
		return data_json;
	}
	public void setData_json(String data_json) {
		this.data_json = data_json;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	@NotDbField
	public String getOper_name() {
		return oper_name;
	}
	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeal_time() {
		return deal_time;
	}
	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}
	public String getDeal_desc() {
		return deal_desc;
	}
	public void setDeal_desc(String deal_desc) {
		this.deal_desc = deal_desc;
	}
	public String getRel_object() {
		return rel_object;
	}
	public void setRel_object(String rel_object) {
		this.rel_object = rel_object;
	}
	@NotDbField
	public String getTotal_num() {
		return total_num;
	}
	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}
	@NotDbField
	public String getSucc_num() {
		return succ_num;
	}
	public void setSucc_num(String succ_num) {
		this.succ_num = succ_num;
	}
	@NotDbField
	public String getFail_num() {
		return fail_num;
	}
	public void setFail_num(String fail_num) {
		this.fail_num = fail_num;
	}
	@NotDbField
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	@NotDbField
	public String getWait_num() {
		return wait_num;
	}
	public void setWait_num(String wait_num) {
		this.wait_num = wait_num;
	}
	
	
}

package com.ztesoft.net.model;

import java.io.Serializable;

public class OrderBatchLogiImport implements Serializable {

	private static final long serialVersionUID = -8370434690639454570L;

	private String batch_id; //批次号
	private String order_id; // 订单编号
	private String op_id;// 操作人员
	private String org_id;// 操作组织
	private String logi_company;// 物流公司编码
	private String logi_no;// 物流编号
	private String create_time;// 创建时间
	private String state;// 是否成功
	private String cause_failure;// 失败原因
	private String remark;// 备注
	
	private String start_time;  //开始时间 
	private String end_time;// 结束时间 
	
	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public String getOp_id() {
		return op_id;
	}

	public void setOp_id(String op_id) {
		this.op_id = op_id;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getLogi_company() {
		return logi_company;
	}

	public void setLogi_company(String logi_company) {
		this.logi_company = logi_company;
	}

	public String getLogi_no() {
		return logi_no;
	}

	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCause_failure() {
		return cause_failure;
	}

	public void setCause_failure(String cause_failure) {
		this.cause_failure = cause_failure;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

}

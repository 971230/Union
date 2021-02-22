package com.ztesoft.net.model;

import java.io.Serializable;

/**
 * @Description 财务稽核查询参数封装
 * @author zhangJun
 * @date 2016年10月1日
 */
public class AuditQueryParame implements Serializable {

	private static final long serialVersionUID = 1L;
	private String audit_busi_money_status;//营业款稽核状态
	private String audit_receive_money_status ;//实收款稽核状态
	private String start_time;//开始时间
	private String end_time ;//结束时间
	private String order_id ;//订单编号
	private String pay_type ;//支付方式
	private String order_from ;//订单来源
	private String order_type ;//订单类型
	private String payproviderid ;//支付机构
	
	private String now_date ;//支付机构
	
	private String data_export_type ;//到导出类型
	
	
	public String getNow_date() {
		return now_date;
	}
	public void setNow_date(String now_date) {
		this.now_date = now_date;
	}
	public String getAudit_busi_money_status() {
		return audit_busi_money_status;
	}
	public void setAudit_busi_money_status(String audit_busi_money_status) {
		this.audit_busi_money_status = audit_busi_money_status;
	}
	public String getAudit_receive_money_status() {
		return audit_receive_money_status;
	}
	public void setAudit_receive_money_status(String audit_receive_money_status) {
		this.audit_receive_money_status = audit_receive_money_status;
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
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getOrder_from() {
		return order_from;
	}
	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getPayproviderid() {
		return payproviderid;
	}
	public void setPayproviderid(String payproviderid) {
		this.payproviderid = payproviderid;
	}
	public String getData_export_type() {
		return data_export_type;
	}
	public void setData_export_type(String data_export_type) {
		this.data_export_type = data_export_type;
	}

	
	
	
	
	
	
}

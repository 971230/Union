package com.ztesoft.net.model;

import java.io.Serializable;

/**
 * 对账记录表
 * @作者 MoChunrun
 * @创建日期 2015-1-18 
 * @版本 V 1.0
 */
public class CheckAcctLog implements Serializable {

	private String accounts_id        ;
	private Integer seq               ;
	private String system_id          ;
	private String file_name          ;
	private String begin_date         ;
	private String end_date           ;
	private Integer record_sum        ;
	private Integer right_sum         ;
	private Integer error_sum         ;
	private Integer exception_sum     ;
	private Integer adjust_right_sum  ;
	private Integer adjust_error_sum  ;
	private Integer money_sum         ;
	private Integer right_money       ;
	private Integer error_money       ;
	private Integer exception_money   ;
	private Integer adjust_right_money;
	private Integer adjust_error_money;
	private String state              ;
	private String notes              ;
	private String source_from        ;
	public String getAccounts_id() {
		return accounts_id;
	}
	public void setAccounts_id(String accounts_id) {
		this.accounts_id = accounts_id;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getSystem_id() {
		return system_id;
	}
	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public Integer getRecord_sum() {
		return record_sum;
	}
	public void setRecord_sum(Integer record_sum) {
		this.record_sum = record_sum;
	}
	public Integer getRight_sum() {
		return right_sum;
	}
	public void setRight_sum(Integer right_sum) {
		this.right_sum = right_sum;
	}
	public Integer getError_sum() {
		return error_sum;
	}
	public void setError_sum(Integer error_sum) {
		this.error_sum = error_sum;
	}
	public Integer getException_sum() {
		return exception_sum;
	}
	public void setException_sum(Integer exception_sum) {
		this.exception_sum = exception_sum;
	}
	public Integer getAdjust_right_sum() {
		return adjust_right_sum;
	}
	public void setAdjust_right_sum(Integer adjust_right_sum) {
		this.adjust_right_sum = adjust_right_sum;
	}
	public Integer getAdjust_error_sum() {
		return adjust_error_sum;
	}
	public void setAdjust_error_sum(Integer adjust_error_sum) {
		this.adjust_error_sum = adjust_error_sum;
	}
	public Integer getMoney_sum() {
		return money_sum;
	}
	public void setMoney_sum(Integer money_sum) {
		this.money_sum = money_sum;
	}
	public Integer getRight_money() {
		return right_money;
	}
	public void setRight_money(Integer right_money) {
		this.right_money = right_money;
	}
	public Integer getError_money() {
		return error_money;
	}
	public void setError_money(Integer error_money) {
		this.error_money = error_money;
	}
	public Integer getException_money() {
		return exception_money;
	}
	public void setException_money(Integer exception_money) {
		this.exception_money = exception_money;
	}
	public Integer getAdjust_right_money() {
		return adjust_right_money;
	}
	public void setAdjust_right_money(Integer adjust_right_money) {
		this.adjust_right_money = adjust_right_money;
	}
	public Integer getAdjust_error_money() {
		return adjust_error_money;
	}
	public void setAdjust_error_money(Integer adjust_error_money) {
		this.adjust_error_money = adjust_error_money;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
	
}

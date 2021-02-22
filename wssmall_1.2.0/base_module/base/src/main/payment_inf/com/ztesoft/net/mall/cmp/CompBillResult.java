package com.ztesoft.net.mall.cmp;

import com.ztesoft.net.mall.core.model.AlarmTask;


public class CompBillResult {
	
	String err_type;
	String err_message;
	String sms_content; //短信内容
	String exception_msg;
	private AlarmTask alarmTask;
	private long bill_num;	//异常账单数量
	private long bill_money;	//异常金额数量
	
	
	public String getErr_type() {
		return err_type;
	}
	public void setErr_type(String err_type) {
		this.err_type = err_type;
	}
	public String getErr_message() {
		return err_message;
	}
	public void setErr_message(String err_message) {
		this.err_message = err_message;
	}
	public String getSms_content() {
		return sms_content;
	}
	public void setSms_content(String sms_content) {
		this.sms_content = sms_content;
	}
	public String getException_msg() {
		return exception_msg;
	}
	public void setException_msg(String exception_msg) {
		this.exception_msg = exception_msg;
	}
	public AlarmTask getAlarmTask() {
		return alarmTask;
	}
	public void setAlarmTask(AlarmTask alarmTask) {
		this.alarmTask = alarmTask;
	}
	public long getBill_num() {
		return bill_num;
	}
	public void setBill_num(long bill_num) {
		this.bill_num = bill_num;
	}
	public long getBill_money() {
		return bill_money;
	}
	public void setBill_money(long bill_money) {
		this.bill_money = bill_money;
	}
}

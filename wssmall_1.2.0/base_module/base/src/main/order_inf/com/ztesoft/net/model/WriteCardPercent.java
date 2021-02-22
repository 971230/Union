package com.ztesoft.net.model;

import java.io.Serializable;

public class WriteCardPercent implements Serializable {
	
	private String success;
	
	private String fail ;
	
	private String machine_no;
	
	private String num ;
	
	private String create_time;
	
	private String failPercent ;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public String getMachine_no() {
		return machine_no;
	}

	public void setMachine_no(String machine_no) {
		this.machine_no = machine_no;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getFailPercent() {
		return failPercent;
	}

	public void setFailPercent(String failPercent) {
		this.failPercent = failPercent;
	}

	
	
	

}

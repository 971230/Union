package com.ztesoft.cms.login.vo;

import java.io.Serializable;

public class TsmStaff implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String staff_no;
	
	private String staff_name;
	
	private String state;
	
	private String staff_code;
	
	

	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStaff_code() {
		return staff_code;
	}

	public void setStaff_code(String staff_code) {
		this.staff_code = staff_code;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	

}

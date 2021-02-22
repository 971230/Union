package com.ztesoft.net.param.outer;

import java.io.Serializable;

public abstract class BaseOuter implements Serializable {
	
	private String outer_status;
	private String outer_msg;
	
	public String getOuter_status() {
		return outer_status;
	}
	public void setOuter_status(String outer_status) {
		this.outer_status = outer_status;
	}
	public String getOuter_msg() {
		return outer_msg;
	}
	public void setOuter_msg(String outer_msg) {
		this.outer_msg = outer_msg;
	}
}

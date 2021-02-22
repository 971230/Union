package com.ztesoft.autoprocess.base.model;

import java.io.Serializable;

public class OperatorStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3013131845050715263L;

	private String operId;
	
	private String isOnline;

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}


	
}

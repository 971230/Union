package com.ztesoft.net.model.inf;

import java.io.Serializable;

public class InfCompareReq implements Serializable{
	 	
	private static final long serialVersionUID = -5977800767574837826L;

	private String order_id;
	
	private String op_code;
	
	private String out_id;
	
	private String oldXml;
	
	private String newXml;
	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOp_code() {
		return op_code;
	}

	public void setOp_code(String op_code) {
		this.op_code = op_code;
	}

	public String getOut_id() {
		return out_id;
	}

	public void setOut_id(String out_id) {
		this.out_id = out_id;
	}

	public String getOldXml() {
		return oldXml;
	}

	public void setOldXml(String oldXml) {
		this.oldXml = oldXml;
	}

	public String getNewXml() {
		return newXml;
	}

	public void setNewXml(String newXml) {
		this.newXml = newXml;
	}
	
}

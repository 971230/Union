package com.ztesoft.net.model.inf;

import java.io.Serializable;

public class InfRecVO implements Serializable{
	  
	private static final long serialVersionUID = -6708180207088340216L;

	private String rec_id;
	
	private String order_id;
	
	private String op_code;
	
	private String out_id;
	
	private String old_xml;
	
	private String new_xml;
	
	private String comp_result;
	
	private String compare_time;

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

	public String getRec_id() {
		return rec_id;
	}

	public void setRec_id(String rec_id) {
		this.rec_id = rec_id;
	}

	public String getOld_xml() {
		return old_xml;
	}

	public void setOld_xml(String old_xml) {
		this.old_xml = old_xml;
	}

	public String getNew_xml() {
		return new_xml;
	}

	public void setNew_xml(String new_xml) {
		this.new_xml = new_xml;
	}

	public String getComp_result() {
		return comp_result;
	}

	public void setComp_result(String comp_result) {
		this.comp_result = comp_result;
	}

	public String getCompare_time() {
		return compare_time;
	}

	public void setCompare_time(String compare_time) {
		this.compare_time = compare_time;
	}	
	
}

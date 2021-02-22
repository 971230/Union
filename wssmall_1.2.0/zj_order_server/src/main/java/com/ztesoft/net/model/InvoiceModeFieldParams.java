package com.ztesoft.net.model;

import java.io.Serializable;

public class InvoiceModeFieldParams implements Serializable{
	private String model_cd;
	private String print_mode;
	private String[] field_cdArr;
	public String getModel_cd() {
		return model_cd;
	}
	public void setModel_cd(String model_cd) {
		this.model_cd = model_cd;
	}
	public String getPrint_mode() {
		return print_mode;
	}
	public void setPrint_mode(String print_mode) {
		this.print_mode = print_mode;
	}
	public String[] getField_cdArr() {
		return field_cdArr;
	}
	public void setField_cdArr(String[] field_cdArr) {
		this.field_cdArr = field_cdArr;
	}
	
}

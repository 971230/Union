package com.ztesoft.net.model;

import java.io.UnsupportedEncodingException;




/**
 * 模板实例实体
 * @author hu.yi
 * @date 2013.9.26
 */
public class TplContentAttr implements java.io.Serializable {
	
	private String c_name;
	private String e_name;
	public String getC_name() {
		try {
			return new String(c_name.getBytes("gb2312"), "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getE_name() {
		return e_name;
	}
	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
	
	
	
}
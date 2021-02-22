package com.ztesoft.net.mall.core.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * print配置名称选项
 * @作者 MoChunrun
 * @创建日期 2013-11-7 
 * @版本 V 1.0
 */
public class PrintConfigName implements Serializable {

	private String e_name;
	private String c_name;
	public String getE_name() {
		return e_name;
	}
	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	
	public static void main(String[] args) {
		String json = "[{e_name:'ship_name',c_name:'收货人-性名'},{e_name:'ship_name',c_name:'收货人-性名'}]";
		List<PrintConfigName> list = JSON.parseArray(json,PrintConfigName.class);
		System.out.println("==");
	}
	
}

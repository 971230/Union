package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/**
 * @author liqingyi
 * @createTime 2014-3-12下午5:18:33
 * 作用：
 * return 
 */
public class Model implements Serializable{
	
	private String name;
	private String code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	

}

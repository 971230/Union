package com.ztesoft.inf.vo;

import java.util.List;

public class paramList {
	private String name;
	private List<GoodsVo> paramList;
	private int paramNum;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<GoodsVo> getParamList() {
		return paramList;
	}
	public void setParamList(List<GoodsVo> paramList) {
		this.paramList = paramList;
	}
	public int getParamNum() {
		return paramNum;
	}
	public void setParamNum(int paramNum) {
		this.paramNum = paramNum;
	}
	
	

}

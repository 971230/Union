package com.ztesoft.inf.infclient.vo;

import java.util.List;

public class paramCatList {
	private String name;
	private List<GoodsCatVo> paramList;
	private int paramNum;
	
	
	public List<GoodsCatVo> getParamList() {
		return paramList;
	}
	public void setParamList(List<GoodsCatVo> paramList) {
		this.paramList = paramList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	public int getParamNum() {
		return paramNum;
	}
	public void setParamNum(int paramNum) {
		this.paramNum = paramNum;
	}
	
	

}

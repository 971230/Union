package com.ztesoft.inf.infclient.vo;
import java.util.List;

public class paramList {
	private String name;
	private List<GoodsVo> paramsList;
	private int paramNum;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<GoodsVo> getParamsList() {
		return paramsList;
	}
	public void setParamList(List<GoodsVo> paramsList) {
		this.paramsList = paramsList;
	}
	public int getParamNum() {
		return paramNum;
	}
	public void setParamNum(int paramNum) {
		this.paramNum = paramNum;
	}
	
	

}

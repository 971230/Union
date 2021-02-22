package com.ztesoft.autoprocess.base.model;

public class ZbOpenType {
	private String openType;// auto自动开户 manual手动开户,null在开户界面未查询到订单
	private String openDetailHtml;
	public String getOpenType() {
		return openType;
	}
	public void setOpenType(String openType) {
		this.openType = openType;
	}
	public String getOpenDetailHtml() {
		return openDetailHtml;
	}
	public void setOpenDetailHtml(String openDetailHtml) {
		this.openDetailHtml = openDetailHtml;
	}
	
}

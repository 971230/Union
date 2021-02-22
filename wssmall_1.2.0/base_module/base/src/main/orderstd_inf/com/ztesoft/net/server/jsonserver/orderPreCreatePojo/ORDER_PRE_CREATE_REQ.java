package com.ztesoft.net.server.jsonserver.orderPreCreatePojo;

public class ORDER_PRE_CREATE_REQ {

	private REQ_HEAD REQ_HEAD;
	private REQ_DATA REQ_DATA;

	public void setREQ_HEAD(REQ_HEAD REQ_HEAD) {
		this.REQ_HEAD = REQ_HEAD;
	}

	public REQ_HEAD getREQ_HEAD() {
		return REQ_HEAD;
	}

	public void setREQ_DATA(REQ_DATA REQ_DATA) {
		this.REQ_DATA = REQ_DATA;
	}

	public REQ_DATA getREQ_DATA() {
		return REQ_DATA;
	}

}
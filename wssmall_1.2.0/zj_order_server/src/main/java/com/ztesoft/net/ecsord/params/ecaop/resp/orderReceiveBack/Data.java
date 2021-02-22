package com.ztesoft.net.ecsord.params.ecaop.resp.orderReceiveBack;

public class Data {

	private String PAY_URL;
	private String PAPER_LESS_URL;
	private String paper_less_String;
	private String ORDER_ID;

	public void setPAY_URL(String PAY_URL) {
		this.PAY_URL = PAY_URL;
	}

	public String getPAY_URL() {
		return PAY_URL;
	}

	public void setPAPER_LESS_URL(String PAPER_LESS_URL) {
		this.PAPER_LESS_URL = PAPER_LESS_URL;
	}

	public String getPAPER_LESS_URL() {
		return PAPER_LESS_URL;
	}

	public void setPaper_less_String(String paper_less_String) {
		this.paper_less_String = paper_less_String;
	}

	public String getPaper_less_String() {
		return paper_less_String;
	}

	public void setORDER_ID(String ORDER_ID) {
		this.ORDER_ID = ORDER_ID;
	}

	public String getORDER_ID() {
		return ORDER_ID;
	}

}
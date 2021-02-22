package com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack;

public class PROM_REDUCE {

	private int RULE_ID;
	private String DISCOUNT_TYPE;
	private int DISCOUNT_NUM;

	public int getRULE_ID() {
		return RULE_ID;
	}

	public void setRULE_ID(int rULE_ID) {
		RULE_ID = rULE_ID;
	}

	public void setDISCOUNT_TYPE(String DISCOUNT_TYPE) {
		this.DISCOUNT_TYPE = DISCOUNT_TYPE;
	}

	public String getDISCOUNT_TYPE() {
		return DISCOUNT_TYPE;
	}

	public int getDISCOUNT_NUM() {
		return DISCOUNT_NUM;
	}

	public void setDISCOUNT_NUM(int dISCOUNT_NUM) {
		DISCOUNT_NUM = dISCOUNT_NUM;
	}

}
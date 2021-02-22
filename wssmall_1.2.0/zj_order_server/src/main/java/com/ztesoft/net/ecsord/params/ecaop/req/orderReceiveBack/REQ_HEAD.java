package com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack;

public class REQ_HEAD {

	private String IN_MODE_CODE;
	private String OUTER_ORDER_ID;
	private String ORDER_PROVINCE;
	private String ORDER_CITY;
	private String STORE_ID;
	private String TRADE_STAFFID;
	private String DEPART_ID;
	private String CHANNEL_ID;
	private String CHANNEL_TYPE;
	private String ORDER_TIME;
	private String PRODUCE_CUST_ID;

	public void setIN_MODE_CODE(String IN_MODE_CODE) {
		this.IN_MODE_CODE = IN_MODE_CODE;
	}

	public String getIN_MODE_CODE() {
		return IN_MODE_CODE;
	}

	public void setOUTER_ORDER_ID(String OUTER_ORDER_ID) {
		this.OUTER_ORDER_ID = OUTER_ORDER_ID;
	}

	public String getOUTER_ORDER_ID() {
		return OUTER_ORDER_ID;
	}

	public void setORDER_PROVINCE(String ORDER_PROVINCE) {
		this.ORDER_PROVINCE = ORDER_PROVINCE;
	}

	public String getORDER_PROVINCE() {
		return ORDER_PROVINCE;
	}

	public void setORDER_CITY(String ORDER_CITY) {
		this.ORDER_CITY = ORDER_CITY;
	}

	public String getORDER_CITY() {
		return ORDER_CITY;
	}

	public void setSTORE_ID(String STORE_ID) {
		this.STORE_ID = STORE_ID;
	}

	public String getSTORE_ID() {
		return STORE_ID;
	}

	public void setTRADE_STAFFID(String TRADE_STAFFID) {
		this.TRADE_STAFFID = TRADE_STAFFID;
	}

	public String getTRADE_STAFFID() {
		return TRADE_STAFFID;
	}

	public void setCHANNEL_ID(String CHANNEL_ID) {
		this.CHANNEL_ID = CHANNEL_ID;
	}

	public String getCHANNEL_ID() {
		return CHANNEL_ID;
	}

	public void setCHANNEL_TYPE(String CHANNEL_TYPE) {
		this.CHANNEL_TYPE = CHANNEL_TYPE;
	}

	public String getCHANNEL_TYPE() {
		return CHANNEL_TYPE;
	}

	public void setORDER_TIME(String ORDER_TIME) {
		this.ORDER_TIME = ORDER_TIME;
	}

	public String getORDER_TIME() {
		return ORDER_TIME;
	}

	public void setPRODUCE_CUST_ID(String PRODUCE_CUST_ID) {
		this.PRODUCE_CUST_ID = PRODUCE_CUST_ID;
	}

	public String getPRODUCE_CUST_ID() {
		return PRODUCE_CUST_ID;
	}

	public String getDEPART_ID() {
		return DEPART_ID;
	}

	public void setDEPART_ID(String dEPART_ID) {
		DEPART_ID = dEPART_ID;
	}

}
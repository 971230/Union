package com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack;

public class NUMBER_INFO {

	private String SERIAL_NUMBER;
	private String NUMBER_LEVER;
	private String COMM_ID;
	private String NUM_PROVINCE;
	private String NUM_CITY;
	private double STORE_FEE;
	private double LIMIT_FEE;

	public void setSERIAL_NUMBER(String SERIAL_NUMBER) {
		this.SERIAL_NUMBER = SERIAL_NUMBER;
	}

	public String getSERIAL_NUMBER() {
		return SERIAL_NUMBER;
	}

	public void setNUMBER_LEVER(String NUMBER_LEVER) {
		this.NUMBER_LEVER = NUMBER_LEVER;
	}

	public String getNUMBER_LEVER() {
		return NUMBER_LEVER;
	}

	public void setCOMM_ID(String COMM_ID) {
		this.COMM_ID = COMM_ID;
	}

	public String getCOMM_ID() {
		return COMM_ID;
	}

	public void setNUM_PROVINCE(String NUM_PROVINCE) {
		this.NUM_PROVINCE = NUM_PROVINCE;
	}

	public String getNUM_PROVINCE() {
		return NUM_PROVINCE;
	}

	public void setNUM_CITY(String NUM_CITY) {
		this.NUM_CITY = NUM_CITY;
	}

	public String getNUM_CITY() {
		return NUM_CITY;
	}

	public double getSTORE_FEE() {
		return STORE_FEE;
	}

	public void setSTORE_FEE(double sTORE_FEE) {
		STORE_FEE = sTORE_FEE;
	}

	public double getLIMIT_FEE() {
		return LIMIT_FEE;
	}

	public void setLIMIT_FEE(double lIMIT_FEE) {
		LIMIT_FEE = lIMIT_FEE;
	}

}
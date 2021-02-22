package com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack;

import java.util.List;

public class COMM_OBJECT {

	private String COMM_ID;
	private double COMM_COUNT;
	private double COMM_PRICE;
	private String IMEI;
	private String DISCNT_TYPE;
	private List<SUB_COMM_INFO> SUB_COMM_INFO;
	private List<NUMBER_INFO> NUMBER_INFO;
	private List<NETWORKRES_INFO> NETWORKRES_INFO;
	private List<COMM_ATTR_INFO> COMM_ATTR_INFO;
	private List<PROM_INFO> PROM_INFO;

	public void setCOMM_ID(String COMM_ID) {
		this.COMM_ID = COMM_ID;
	}

	public String getCOMM_ID() {
		return COMM_ID;
	}

	public double getCOMM_COUNT() {
		return COMM_COUNT;
	}

	public void setCOMM_COUNT(double cOMM_COUNT) {
		COMM_COUNT = cOMM_COUNT;
	}

	public double getCOMM_PRICE() {
		return COMM_PRICE;
	}

	public void setCOMM_PRICE(double cOMM_PRICE) {
		COMM_PRICE = cOMM_PRICE;
	}

	public void setIMEI(String IMEI) {
		this.IMEI = IMEI;
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setDISCNT_TYPE(String DISCNT_TYPE) {
		this.DISCNT_TYPE = DISCNT_TYPE;
	}

	public String getDISCNT_TYPE() {
		return DISCNT_TYPE;
	}

	public void setSUB_COMM_INFO(List<SUB_COMM_INFO> SUB_COMM_INFO) {
		this.SUB_COMM_INFO = SUB_COMM_INFO;
	}

	public List<SUB_COMM_INFO> getSUB_COMM_INFO() {
		return SUB_COMM_INFO;
	}

	public void setNUMBER_INFO(List<NUMBER_INFO> NUMBER_INFO) {
		this.NUMBER_INFO = NUMBER_INFO;
	}

	public List<NUMBER_INFO> getNUMBER_INFO() {
		return NUMBER_INFO;
	}

	public void setNETWORKRES_INFO(List<NETWORKRES_INFO> NETWORKRES_INFO) {
		this.NETWORKRES_INFO = NETWORKRES_INFO;
	}

	public List<NETWORKRES_INFO> getNETWORKRES_INFO() {
		return NETWORKRES_INFO;
	}

	public void setCOMM_ATTR_INFO(List<COMM_ATTR_INFO> COMM_ATTR_INFO) {
		this.COMM_ATTR_INFO = COMM_ATTR_INFO;
	}

	public List<COMM_ATTR_INFO> getCOMM_ATTR_INFO() {
		return COMM_ATTR_INFO;
	}

	public void setPROM_INFO(List<PROM_INFO> PROM_INFO) {
		this.PROM_INFO = PROM_INFO;
	}

	public List<PROM_INFO> getPROM_INFO() {
		return PROM_INFO;
	}

}
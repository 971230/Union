package com.ztesoft.net.server.jsonserver.orderPreCreatePojo;

import java.util.List;

public class REQ_DATA {

	private String MEMBER_ID;
	private DEVELOPER_INFO DEVELOPER_INFO;
	private CUST_INFO CUST_INFO;
	private List<COMM_OBJECT> COMM_OBJECT;

	public void setMEMBER_ID(String MEMBER_ID) {
		this.MEMBER_ID = MEMBER_ID;
	}

	public String getMEMBER_ID() {
		return MEMBER_ID;
	}

	public void setDEVELOPER_INFO(DEVELOPER_INFO DEVELOPER_INFO) {
		this.DEVELOPER_INFO = DEVELOPER_INFO;
	}

	public DEVELOPER_INFO getDEVELOPER_INFO() {
		return DEVELOPER_INFO;
	}

	public void setCUST_INFO(CUST_INFO CUST_INFO) {
		this.CUST_INFO = CUST_INFO;
	}

	public CUST_INFO getCUST_INFO() {
		return CUST_INFO;
	}

	public void setCOMM_OBJECT(List<COMM_OBJECT> COMM_OBJECT) {
		this.COMM_OBJECT = COMM_OBJECT;
	}

	public List<COMM_OBJECT> getCOMM_OBJECT() {
		return COMM_OBJECT;
	}

}
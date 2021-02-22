package com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack;

import java.util.List;

public class REQ_DATA {

	private String PAY_TAG;
	private CUST_INFO CUST_INFO;
	private List<DEVELOPER_INFO> DEVELOPER_INFO;
	private List<COMM_OBJECT> COMM_OBJECT;
	private POST_INFO POST_INFO;
	private List<PAY_INFO> PAY_INFO;
	private double PRICE_SUM;
	private List<COMM_PRICE_DETAIL> COMM_PRICE_DETAIL;
	private List<INVOICE_INFO> INVOICE_INFO;

	public void setPAY_TAG(String PAY_TAG) {
		this.PAY_TAG = PAY_TAG;
	}

	public String getPAY_TAG() {
		return PAY_TAG;
	}

	public void setCUST_INFO(CUST_INFO CUST_INFO) {
		this.CUST_INFO = CUST_INFO;
	}

	public CUST_INFO getCUST_INFO() {
		return CUST_INFO;
	}

	public List<DEVELOPER_INFO> getDEVELOPER_INFO() {
		return DEVELOPER_INFO;
	}

	public void setDEVELOPER_INFO(List<DEVELOPER_INFO> dEVELOPER_INFO) {
		DEVELOPER_INFO = dEVELOPER_INFO;
	}

	public void setCOMM_OBJECT(List<COMM_OBJECT> COMM_OBJECT) {
		this.COMM_OBJECT = COMM_OBJECT;
	}

	public List<COMM_OBJECT> getCOMM_OBJECT() {
		return COMM_OBJECT;
	}

	public void setPOST_INFO(POST_INFO POST_INFO) {
		this.POST_INFO = POST_INFO;
	}

	public POST_INFO getPOST_INFO() {
		return POST_INFO;
	}

	public void setPAY_INFO(List<PAY_INFO> PAY_INFO) {
		this.PAY_INFO = PAY_INFO;
	}

	public List<PAY_INFO> getPAY_INFO() {
		return PAY_INFO;
	}

	public double getPRICE_SUM() {
		return PRICE_SUM;
	}

	public void setPRICE_SUM(double pRICE_SUM) {
		PRICE_SUM = pRICE_SUM;
	}

	public void setCOMM_PRICE_DETAIL(List<COMM_PRICE_DETAIL> COMM_PRICE_DETAIL) {
		this.COMM_PRICE_DETAIL = COMM_PRICE_DETAIL;
	}

	public List<COMM_PRICE_DETAIL> getCOMM_PRICE_DETAIL() {
		return COMM_PRICE_DETAIL;
	}

	public void setINVOICE_INFO(List<INVOICE_INFO> INVOICE_INFO) {
		this.INVOICE_INFO = INVOICE_INFO;
	}

	public List<INVOICE_INFO> getINVOICE_INFO() {
		return INVOICE_INFO;
	}

}
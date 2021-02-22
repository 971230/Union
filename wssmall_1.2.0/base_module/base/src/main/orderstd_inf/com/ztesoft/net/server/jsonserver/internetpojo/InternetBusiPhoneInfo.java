package com.ztesoft.net.server.jsonserver.internetpojo;

public class InternetBusiPhoneInfo {

	private String acc_nbr;//业务号码
	
	private String contract_month;//合约期
	
	private String prod_offer_code;//商品编码
	
	private String prod_offer_name;//商品名称
	
	private String iCCID;
	
	private String bss_order_id;//

	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getContract_month() {
		return contract_month;
	}

	public void setContract_month(String contract_month) {
		this.contract_month = contract_month;
	}

	public String getProd_offer_code() {
		return prod_offer_code;
	}

	public void setProd_offer_code(String prod_offer_code) {
		this.prod_offer_code = prod_offer_code;
	}

	public String getProd_offer_name() {
		return prod_offer_name;
	}

	public void setProd_offer_name(String prod_offer_name) {
		this.prod_offer_name = prod_offer_name;
	}
	
	
	


	public String getiCCID() {
		return iCCID;
	}

	public void setiCCID(String iccid) {
		this.iCCID = iccid;
	}

	public String getBss_order_id() {
		return bss_order_id;
	}

	public void setBss_order_id(String bss_order_id) {
		this.bss_order_id = bss_order_id;
	}
	
	

}

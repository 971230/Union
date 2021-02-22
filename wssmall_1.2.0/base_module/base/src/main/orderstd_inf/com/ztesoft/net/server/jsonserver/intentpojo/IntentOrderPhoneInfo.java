package com.ztesoft.net.server.jsonserver.intentpojo;

import com.ztesoft.net.server.jsonserver.hlwrhywpojo.InterGroupBusiOrderNiceInfo;

public class IntentOrderPhoneInfo {
	private String acc_nbr;// 用户号码
	private String contract_month;// 合约期
	private InterGroupBusiOrderNiceInfo nice_info;// 靓号信息
	private String  mainNumber ;//副卡开户时对应的主卡号码，副卡开户必传
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

	public InterGroupBusiOrderNiceInfo getNice_info() {
		return nice_info;
	}

	public void setNice_info(InterGroupBusiOrderNiceInfo nice_info) {
		this.nice_info = nice_info;
	}

    public String getMainNumber() {
        return mainNumber;
    }
    public void setMainNumber(String mainNumber) {
        this.mainNumber = mainNumber;
    }
	
}

package com.ztesoft.net.model;

import java.io.Serializable;

/**
 * 商家支付账户
 * @作者 MoChunrun
 * @创建日期 2013-12-2 
 * @版本 V 1.0
 */
public class PaymentAccount implements Serializable {

	private String cfg_id;
	private String accounted_code;
	private String account_key;
	private String owner_userid;
	private String create_time;
	public String getCfg_id() {
		return cfg_id;
	}
	public void setCfg_id(String cfg_id) {
		this.cfg_id = cfg_id;
	}
	public String getAccounted_code() {
		return accounted_code;
	}
	public void setAccounted_code(String accounted_code) {
		this.accounted_code = accounted_code;
	}
	public String getAccount_key() {
		return account_key;
	}
	public void setAccount_key(String account_key) {
		this.account_key = account_key;
	}
	public String getOwner_userid() {
		return owner_userid;
	}
	public void setOwner_userid(String owner_userid) {
		this.owner_userid = owner_userid;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
}

package com.ztesoft.net.app.base.core.model;

public class PartnerAccount implements java.io.Serializable {
	
	private String partner_id;
	private String account_type;
	private Integer account_amount;
	private String account_code;
	private String account_name;
	private String create_date;
	private Integer frost_deposit;
	private String account_id;
	
	
	public String getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public Integer getAccount_amount() {
		if(account_amount == null)
			return 0;
		return account_amount;
	}
	public void setAccount_amount(Integer account_amount) {
		this.account_amount = account_amount;
	}
	public String getAccount_code() {
		return account_code;
	}
	public void setAccount_code(String account_code) {
		this.account_code = account_code;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public Integer getFrost_deposit() {
		if(frost_deposit == null)
			return 0;
		return frost_deposit;
	}
	public void setFrost_deposit(Integer frost_deposit) {
		this.frost_deposit = frost_deposit;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	
	
}
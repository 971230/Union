package com.ztesoft.net.app.base.core.model;

/**
 * 供货商账户
 * 
 * @author chenlijun
 * 
 */
public class SupplierAccount {

	private String account_id;
	private String open_bank;
	private String address;
	private String accoun_name;
	private String bank_account;
	private String account_attachment;
	private String is_default;
	private String register_time;
	private String state;

	private String supplier_id;

	
	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}


	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getOpen_bank() {
		return open_bank;
	}

	public void setOpen_bank(String open_bank) {
		this.open_bank = open_bank;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAccoun_name() {
		return accoun_name;
	}

	public void setAccoun_name(String accoun_name) {
		this.accoun_name = accoun_name;
	}

	public String getBank_account() {
		return bank_account;
	}

	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}

	

	public String getAccount_attachment() {
		return account_attachment;
	}

	public void setAccount_attachment(String account_attachment) {
		this.account_attachment = account_attachment;
	}

	public String getIs_default() {
		return is_default;
	}

	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}

	public String getRegister_time() {
		return register_time;
	}

	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	
}

package com.ztesoft.component.common.staticdata.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class BankBranchVO extends ValueObject implements VO {

	private String bankBranchId = "";
	private String bankId = "";
	private String bankBranchName = "";
	private String bankAcct = "";
	private String bankAcctName = "";
	private String branchCode = "";

	public BankBranchVO() {}

	public BankBranchVO( String pbankBranchId, String pbankId, String pbankBranchName, String pbankAcct, String pbankAcctName, String pbranchCode ) {
		bankBranchId = pbankBranchId;
		bankId = pbankId;
		bankBranchName = pbankBranchName;
		bankAcct = pbankAcct;
		bankAcctName = pbankAcctName;
		branchCode = pbranchCode;
	}

	public String getBankBranchId() {
		return bankBranchId;
	}

	public String getBankId() {
		return bankId;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public String getBankAcctName() {
		return bankAcctName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBankBranchId(String pBankBranchId) {
		bankBranchId = pBankBranchId;
	}

	public void setBankId(String pBankId) {
		bankId = pBankId;
	}

	public void setBankBranchName(String pBankBranchName) {
		bankBranchName = pBankBranchName;
	}

	public void setBankAcct(String pBankAcct) {
		bankAcct = pBankAcct;
	}

	public void setBankAcctName(String pBankAcctName) {
		bankAcctName = pBankAcctName;
	}

	public void setBranchCode(String pBranchCode) {
		branchCode = pBranchCode;
	}

	@Override
	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("BANK_BRANCH_ID",this.bankBranchId);
		hashMap.put("BANK_ID",this.bankId);
		hashMap.put("BANK_BRANCH_NAME",this.bankBranchName);
		hashMap.put("BANK_ACCT",this.bankAcct);
		hashMap.put("BANK_ACCT_NAME",this.bankAcctName);
		hashMap.put("BRANCH_CODE",this.branchCode);
		return hashMap;
	}

	@Override
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.bankBranchId = (String) hashMap.get("BANK_BRANCH_ID");
			this.bankId = (String) hashMap.get("BANK_ID");
			this.bankBranchName = (String) hashMap.get("BANK_BRANCH_NAME");
			this.bankAcct = (String) hashMap.get("BANK_ACCT");
			this.bankAcctName = (String) hashMap.get("BANK_ACCT_NAME");
			this.branchCode = (String) hashMap.get("BRANCH_CODE");
		}
	}

	@Override
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("BANK_BRANCH_ID");
		return arrayList;
	}

	@Override
	public String getTableName() {
		return "BANK_BRANCH";
	}

}

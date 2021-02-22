package com.ztesoft.component.common.staticdata.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class BankVO extends ValueObject implements VO {

	private String bankId = "";
	private String bankName = "";
	private String bankCode = "";

	public BankVO() {}

	public BankVO( String pbankId, String pbankName, String pbankCode ) {
		bankId = pbankId;
		bankName = pbankName;
		bankCode = pbankCode;
	}

	public String getBankId() {
		return bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankId(String pBankId) {
		bankId = pBankId;
	}

	public void setBankName(String pBankName) {
		bankName = pBankName;
	}

	public void setBankCode(String pBankCode) {
		bankCode = pBankCode;
	}

	@Override
	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("BANK_ID",this.bankId);
		hashMap.put("BANK_NAME",this.bankName);
		hashMap.put("BANK_CODE",this.bankCode);
		return hashMap;
	}

	@Override
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.bankId = (String) hashMap.get("BANK_ID");
			this.bankName = (String) hashMap.get("BANK_NAME");
			this.bankCode = (String) hashMap.get("BANK_CODE");
		}
	}

	@Override
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("BANK_ID");
		return arrayList;
	}

	@Override
	public String getTableName() {
		return "BANK";
	}

}

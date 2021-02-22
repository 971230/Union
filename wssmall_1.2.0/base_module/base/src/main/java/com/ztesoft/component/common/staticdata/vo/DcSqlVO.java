package com.ztesoft.component.common.staticdata.vo;

import com.ztesoft.common.valueobject.ValueObject;

public class DcSqlVO extends ValueObject{

	private String dcName;
	private String dcSql;
	
	public String getDcName() {
		return dcName;
	}
	public void setDcName(String dcName) {
		this.dcName = dcName;
	}
	public String getDcSql() {
		return dcSql;
	}
	public void setDcSql(String dcSql) {
		this.dcSql = dcSql;
	}
	
	
	
}

package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.mall.core.annotation.Table;

@Table(name="ES_NO_BAK")
public class NumeroBak extends Numero{
	
	/**
	 * 操作类型 
	 * M修改 D删除
	 */
	private String action_code;

	public String getAction_code() {
		return action_code;
	}

	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}
	
	
}

package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.app.base.core.model.Member;


public class MemberDTO extends Member {
	
	private String lv_name;

	@Override
	public String getLv_name() {
		return lv_name;
	}

	@Override
	public void setLv_name(String lv_name) {
		this.lv_name = lv_name;
	}
	
}

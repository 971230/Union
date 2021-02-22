package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.service.ISinLoginInfoManager;

import java.util.List;

public class SinLoginManagerImpl extends BaseSupport implements ISinLoginInfoManager {
	
	@Override
	public List list(){
		return this.baseDaoSupport.queryForList("select * from single_login");
	}
	

	@Override
	public List getSingleLoginByKey(String stype) {
		return this.baseDaoSupport.queryForList("select * from single_login where stype = ? ", stype);
	}
}
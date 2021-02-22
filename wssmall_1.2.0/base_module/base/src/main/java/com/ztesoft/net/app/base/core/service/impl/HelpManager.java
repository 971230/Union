package com.ztesoft.net.app.base.core.service.impl;

import com.ztesoft.net.app.base.core.model.Help;
import com.ztesoft.net.app.base.core.service.IHelpManager;
import com.ztesoft.net.eop.sdk.database.BaseSupport;

/**
 * 帮助管理
 * @author kingapex
 * 2010-10-18上午01:37:12
 */
public class HelpManager extends BaseSupport<Help> implements IHelpManager {

	@Override
	public Help get(String helpid) {
		String sql ="select * from es_help_1_1 where helpid=?";
		return this.daoSupport.queryForObject(sql, Help.class, helpid);
	}
	
}

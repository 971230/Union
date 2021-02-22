package com.ztesoft.net.eop.resource.impl;

import java.util.List;

import com.ztesoft.net.eop.resource.IAppManager;
import com.ztesoft.net.eop.resource.model.EopApp;
import com.ztesoft.net.framework.database.IDaoSupport;

/**
 * 应用管理
 * @author kingapex
 *2010-5-10上午11:13:21
 */
public class AppManagerImpl implements IAppManager {

	private IDaoSupport< EopApp > daoSupport;
	
	@Override
	public EopApp get(String appid) {
		String sql ="select * from eop_app where id=?";
		return this.daoSupport.queryForObject(sql, EopApp.class, appid);
	}

	
	@Override
	public List<EopApp> list() {
		String sql ="select * from eop_app";
		return this.daoSupport.queryForList(sql,  EopApp.class);
	}

	public IDaoSupport<EopApp> getDaoSupport() {
		return daoSupport;
	}

	public void setDaoSupport(IDaoSupport<EopApp> daoSupport) {
		this.daoSupport = daoSupport;
	}

	
	@Override
	public void add(EopApp app) {
		this.daoSupport.insert("eop_app", app);
	}

}

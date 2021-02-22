package com.ztesoft.net.eop.resource.impl;

import java.util.List;

import com.ztesoft.net.eop.resource.IDomainManager;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.resource.model.EopSiteDomain;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.database.IDaoSupport;

/**
 * 域名管理
 * 
 * @author kingapex 2010-5-9下午08:14:11
 */
public class DomainManagerImpl implements IDomainManager {

	private IDaoSupport<EopSiteDomain> daoSupport;

	
	
	@Override
	public EopSiteDomain get(Integer id) {
		String sql = "select * from eop_sitedomain where id = ?";
		return daoSupport.queryForObject(sql, EopSiteDomain.class, id);
	}

	
	@Override
	public List<EopSiteDomain> listUserDomain() {
		String userid = EopContext.getContext().getCurrentSite().getUserid();
		String sql = "select * from eop_sitedomain where userid=?";
		return this.daoSupport.queryForList(sql, EopSiteDomain.class, userid);
	}

	
	@Override
	public List<EopSiteDomain> listSiteDomain() {
		EopSite site = EopContext.getContext().getCurrentSite();
		String sql = "select * from eop_sitedomain where userid=? and siteid =?";
		return this.daoSupport.queryForList(sql, EopSiteDomain.class, site
				.getUserid(), site.getId());
	}
	
	@Override
	public List<EopSiteDomain> listSiteDomain(String siteid) {
		String sql = "select * from eop_sitedomain where  siteid =?";
		return this.daoSupport.queryForList(sql, EopSiteDomain.class, siteid);
	}
	
	@Override
	public void edit(EopSiteDomain domain) {
		this.daoSupport.update("eop_sitedomain", domain, " id = "
				+ domain.getId());
	}

	public IDaoSupport<EopSiteDomain> getDaoSupport() {
		return daoSupport;
	}

	public void setDaoSupport(IDaoSupport<EopSiteDomain> daoSupport) {
		this.daoSupport = daoSupport;
	}

	
	@Override
	public int getDomainCount(String siteid) {
		String sql = "select count(0)  from eop_sitedomain where  siteid =?";
		return this.daoSupport.queryForInt(sql, siteid);
	}




}
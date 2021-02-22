package com.ztesoft.net.eop.resource.impl;

import java.util.Date;
import java.util.List;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.DataLog;
import com.ztesoft.net.app.base.core.model.DataLogMapper;
import com.ztesoft.net.eop.resource.IDataLogManager;
import com.ztesoft.net.eop.resource.IDomainManager;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.resource.model.EopSiteDomain;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.framework.util.StringUtil;


/**
 * 数据日志管理
 * @author kingapex
 * 2010-10-19下午04:37:23
 */
public class DataLogManager extends BaseSupport implements IDataLogManager {

	private IDomainManager domainManager;
	
	@Override
	public void add(DataLog datalog) {
		
		if(datalog==null ) throw new IllegalArgumentException("参数datalog为空");
		EopSite site = EopContext.getContext().getCurrentSite();
		
		datalog.setDateline(DBTUtil.current());
		datalog.setUserid(site.getUserid());
		datalog.setSiteid(site.getId());
		datalog.setSitename(site.getSitename());
		
		List<EopSiteDomain> domains =  domainManager.listSiteDomain( site.getId());
		if(domains!=null && domains.size()>0){
			datalog.setDomain(domains.get(0).getDomain());
		}
		this.daoSupport.insert("eop_data_log", datalog);
		
	}

	@Override
	public Page list(String start, String end, int pageNo, int pageSize) {
		Date startDate = start==null?null:DateUtil.toDate(start, "yyyy-MM-dd");
		Date endDate = end==null?null:DateUtil.toDate(end, "yyyy-MM-dd");
		
		//如果没有输入结束日期，则为当前时间
		endDate= endDate==null? new Date():endDate;
		
		Integer startSec= null;
		if(startDate!=null)
		 startSec  = (int) (startDate.getTime()/1000);
		int endSec  = (int) (endDate.getTime()/1000);
		
		String sql ="select * from eop_data_log where dateline<="+ endSec;
		if(startDate!=null)  sql+=" and dateline>="+startSec;
		sql+=" order by dateline desc";
		
		
		return this.daoSupport.queryForPage(sql ,pageNo, pageSize,new DataLogMapper());
	}

	public IDomainManager getDomainManager() {
		return domainManager;
	}

	public void setDomainManager(IDomainManager domainManager) {
		this.domainManager = domainManager;
	}

	@Override
	public void delete(Integer[] ids) {
		String idstr = StringUtil.arrayToString(ids, ",");
		this.daoSupport.execute("delete from eop_data_log where id in("+idstr+")");
		
	}

	
}

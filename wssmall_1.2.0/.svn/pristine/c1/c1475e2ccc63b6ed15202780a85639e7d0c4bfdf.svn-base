package com.ztesoft.net.eop.sdk.database;


import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.database.IDBRouter;
import com.ztesoft.net.framework.database.IDaoSupport;

import org.apache.log4j.Logger;

public abstract class BaseSupportWork<T> {
	//protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Logger logger = Logger.getLogger(getClass());
		
	private IDBRouter baseDBRouterWork; 
	protected IDaoSupport<T> baseDaoSupportWork;  
	protected IDaoSupport<T> daoSupportWork;   

	/**
	 * 获取表名
	 * @return
	 */
	protected String getTableName(String moude){
		return baseDBRouterWork.getTableName(  moude);
		
	}

	/**
	 * 检测操作的“属主”合法性
	 * @param userid
	 */
	protected void checkIsOwner( final Integer userid){
		if(userid==null){
			throw new PermssionRuntimeException();
		}
		
		String suserid = EopContext.getContext().getCurrentSite().getUserid();	
		
		if(!suserid.equals(userid)){
			throw new PermssionRuntimeException();
		}
	}
	
	public IDaoSupport<T> getBaseDaoSupportWork() {
		return baseDaoSupportWork;
	}

	public void setBaseDaoSupportWork(IDaoSupport<T> baseDaoSupportWork) {
		this.baseDaoSupportWork = baseDaoSupportWork;
	}

	public IDBRouter getBaseDBRouterWork() {
		return baseDBRouterWork;
	}

	public void setBaseDBRouterWork(IDBRouter baseDBRouterWork) {
		this.baseDBRouterWork = baseDBRouterWork;
	}

	public IDaoSupport<T> getDaoSupportWork() {
		return daoSupportWork;
	}

	public void setDaoSupportWork(IDaoSupport<T> daoSupportWork) {
		this.daoSupportWork = daoSupportWork;
	}
	
}

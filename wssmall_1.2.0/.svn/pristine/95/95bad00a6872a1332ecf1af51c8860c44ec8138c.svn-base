package com.ztesoft.net.eop.sdk.database;


import org.apache.log4j.Logger;

import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.database.IDBRouter;
import com.ztesoft.net.framework.database.IDaoSupport;

public abstract class BaseSupportRop<T> {
	//protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Logger logger = Logger.getLogger(getClass());
		
	private IDBRouter baseDBRouter; 
	protected IDaoSupport<T> baseDaoSupport;  
	protected IDaoSupport<T> daoSupport;   

	/**
	 * 获取表名
	 * @return
	 */
	protected String getTableName(String moude){
		return baseDBRouter.getTableName(  moude);
		
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

	

	

	public IDBRouter getBaseDBRouter() {
		return baseDBRouter;
	}

	public void setBaseDBRouter(IDBRouter baseDBRouter) {
		this.baseDBRouter = baseDBRouter;
	}

	public IDaoSupport<T> getBaseDaoSupport() {
		return baseDaoSupport;
	}

	public void setBaseDaoSupport(IDaoSupport<T> baseDaoSupport) {
		this.baseDaoSupport = baseDaoSupport;
	}

	public IDaoSupport<T> getDaoSupport() {
		return daoSupport;
	}

	public void setDaoSupport(IDaoSupport<T> daoSupport) {
		this.daoSupport = daoSupport;
	}

	
	
	
}

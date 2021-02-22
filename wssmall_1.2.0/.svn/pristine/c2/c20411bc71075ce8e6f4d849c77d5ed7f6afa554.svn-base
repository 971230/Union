package com.ztesoft.net.eop.sdk.database;


import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.database.IDBRouter;
import com.ztesoft.net.framework.database.IDaoSupport;

import org.apache.log4j.Logger;

public abstract class BaseSupportGProduct<T> {
	//protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Logger logger = Logger.getLogger(getClass());
		
	private IDBRouter baseDBRouterGProd; 
	protected IDaoSupport<T> baseDaoSupportGProd;  
	protected IDaoSupport<T> daoSupportGProd;   

	/**
	 * 获取表名
	 * @return
	 */
	protected String getTableName(String moude){
		return baseDBRouterGProd.getTableName(  moude);
		
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
	
	public IDaoSupport<T> getBaseDaoSupportGProd() {
		return baseDaoSupportGProd;
	}

	public void setBaseDaoSupportGProd(IDaoSupport<T> baseDaoSupportGProd) {
		this.baseDaoSupportGProd = baseDaoSupportGProd;
	}

	public IDBRouter getBaseDBRouterGProd() {
		return baseDBRouterGProd;
	}

	public void setBaseDBRouterGProd(IDBRouter baseDBRouterGProd) {
		this.baseDBRouterGProd = baseDBRouterGProd;
	}

	public IDaoSupport<T> getDaoSupportGProd() {
		return daoSupportGProd;
	}

	public void setDaoSupportGProd(IDaoSupport<T> daoSupportGProd) {
		this.daoSupportGProd = daoSupportGProd;
	}
	
}

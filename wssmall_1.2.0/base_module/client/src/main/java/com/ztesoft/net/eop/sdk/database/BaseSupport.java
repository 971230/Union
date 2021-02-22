package com.ztesoft.net.eop.sdk.database;


import com.alibaba.fastjson.annotation.JSONField;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.database.IDBRouter;
import com.ztesoft.net.framework.database.IDaoSupport;

import org.apache.log4j.Logger;

public abstract class BaseSupport<T> {
	//protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Logger logger = Logger.getLogger(getClass());
	
	private IDBRouter baseDBRouter; 
	protected IDaoSupport<T> baseDaoSupport;
	protected IDaoSupport<T> daoSupport;   

	/**
	 * 该dao查询分页信息时不会返回正确的总记录数，只会将总数sql
	 * 存入缓存中
	 */
	protected IDaoSupport<T> daoSupportForAsyCount;


	public IDaoSupport<T> getDaoSupportForAsyCount() {
		return daoSupportForAsyCount;
	}

	@JSONField(serialize = false)
	public void setDaoSupportForAsyCount(IDaoSupport<T> daoSupportForAsyCount) {
		this.daoSupportForAsyCount = daoSupportForAsyCount;
	}
	
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
	@JSONField(serialize = false)
	public IDaoSupport<T> getBaseDaoSupport() {
		return baseDaoSupport;
	}

	public void setBaseDaoSupport(IDaoSupport<T> baseDaoSupport) {
		this.baseDaoSupport = baseDaoSupport;
	}
	
	public void setDaoSupport(IDaoSupport<T> daoSupport) {
		this.daoSupport = daoSupport;
	}
	
	@JSONField(serialize = false)
	public IDBRouter getBaseDBRouter() {
		return baseDBRouter;
	}

	public void setBaseDBRouter(IDBRouter baseDBRouter) {
		this.baseDBRouter = baseDBRouter;
	}
	@JSONField(serialize = false)
	public IDaoSupport<T> getDaoSupport() {
		return daoSupport;
	}
	
}

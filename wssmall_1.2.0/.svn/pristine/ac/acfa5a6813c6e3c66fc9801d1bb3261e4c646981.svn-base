package com.ztesoft.component.common.staticdata.dao;

import com.ztesoft.component.common.staticdata.dao.impl.AcctItemGrpDAOImpl;
import com.ztesoft.component.common.staticdata.dao.impl.StaticAttrDAOImpl;
import com.ztesoft.component.common.staticdata.dao.impl.StaticDataDAOImpl;

public class StaticAttrDAOFactory {

	protected StaticAttrDAOFactory() {
	}

	private static StaticAttrDAOFactory instance = new StaticAttrDAOFactory();

	/**
	 * 获取工厂实例
	 * 
	 * @return
	 */
	public static StaticAttrDAOFactory getInstance() {
		return instance;
	}

	public StaticAttrDAO getStaticAttrDAO() {
		return new StaticAttrDAOImpl();
	}

	public StaticDataDAO getStaticDataDAO() {
		return new StaticDataDAOImpl();
	}

	public AcctItemGrpDAO getAcctItemGrpDAO() {
		return new AcctItemGrpDAOImpl();
	}

}

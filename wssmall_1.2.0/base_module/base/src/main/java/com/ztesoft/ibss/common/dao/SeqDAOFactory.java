package com.ztesoft.ibss.common.dao;



/**
 * @Classname     : StaticDataDAOFactory
 * @Description   : 获取序列DAO的抽象工厂
 * @Copyright     : 2006 ZTEsoft.
 * @Author        : llh
 * @Create Date   : 2006-3-27
 *
 * @Last Modified : 
 * @Modified by   : 
 * @Version       : 1.0
*/
public class SeqDAOFactory {
	
	protected SeqDAOFactory()
	{
		
	}

	private static SeqDAOFactory instance = new SeqDAOFactory();
	
	private static SequenceManageDAO sequenceManageDAO = new SequenceManageDAOImpl();

	/**获取工厂实例
	 * @return
	 */
	public static SeqDAOFactory getInstance() {
		return instance;
	}

	public SequenceManageDAO getSequenceManageDAO()
	{
		return SeqDAOFactory.sequenceManageDAO;
	}
}

package com.ztesoft.ibss.common.dao;

import java.sql.ResultSet;
import java.util.List;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;


/**
 * 
 * @Classname : DAO
 * @Description : 数据存取对象接口
 * @Copyright 2006 ZTEsoft.
 * @Author :  fjy
 * @Create Date : 2006-3-22
 *
 * @Last Modified :
 * @Modified by :
 * @Version : 1.0
 */
public interface DAO {
	
	/**
	 * 插入数据
	 * @param vo
	 * @throws DAOSystemException
	 */
	public void insert(VO vo) throws DAOSystemException;

	/**
	 * 修改数据
	 * @param cond
	 * @param vo
	 * @return
	 * @throws DAOSystemException
	 */
	public boolean update(String cond, VO vo) throws DAOSystemException;

	/**
	 * 删除数据
	 * @param whereCond
	 * @return
	 * @throws DAOSystemException
	 */
	public long deleteByCond(String whereCond) throws DAOSystemException;

	/**
	 * 查找数据。如果没有数据返回空列表[]， 不能返回NULL;
	 * @param whereCond
	 * @return
	 * @throws DAOSystemException
	 */
	public List findByCond(String whereCond) throws DAOSystemException;
	
	/**
	 * 
	 * @param whereCond
	 * @return
	 * @throws DAOSystemException
	 */
	public long countByCond(String whereCond) throws DAOSystemException;

	/**
	 * 获取一个空的值对象。
	 * @return
	 */
	public VO getEmptyVO();
	
	
	/**
	 * 通过查询条件和过滤器得到过滤数据。
	 * @param where
	 * @param queryFilter
	 * @return
	 * @throws DAOSystemException
	 */
	public List findByCond(String where, QueryFilter queryFilter) throws DAOSystemException;
	
	/**
	 * 取得结果集的当前记录。
	 * @param rs
	 * @return
	 * @throws DAOSystemException
	 */
	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
}

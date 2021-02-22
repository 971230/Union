package com.ztesoft.ibss.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * 
 * @Classname     : QueryFilter
 * @Description   : 查询拦截过滤器QueryFilter
 * @Copyright ? 2006 ZTEsoft.
 * @Author        : fjy
 * @Create Date   : 2006-3-22
 *
 * @Last Modified : 
 * @Modified by   : 
 * @Version       : 1.0
 */
public interface QueryFilter {
	
	/**
	 * 预过滤，在sql中过滤数据,统一进行分库处理。
	 */
	public String doPreFilter(String where);
	
	/**
	 * 预过滤，在sql中过滤数据,不进行分库处理。
	 */
	public String doPreFilterWithoutFilterSQL(String sql);

//	/**
//	 * 后过滤，在结果集中过滤数据。
//	 * 
//	 * @param resultSet
//	 * @param dao
//	 * @return
//	 * @throws SQLException
//	 */
//	public ArrayList doPostFilter(ResultSet resultSet, DAO dao)throws SQLException;
	
	
	/**
	 * 后过滤，在结果集中过滤数据。
	 * 
	 * @param resultSet
	 * @param dao
	 * @return
	 * @throws SQLException
	 */
	public ArrayList doPostFilter(ResultSet resultSet)throws SQLException;
	
}

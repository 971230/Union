/**
 * 
 */
package com.ztesoft.common.query;

import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAOSystemException;




public interface SqlDAO {

	//通过业务码找到业务类型
	public abstract String getServTypeByServCode(String servCode);
	
	//	通过查询标识找到配置的SQL语句
	public abstract String getQuerySql(String queryId);
	
	
	
	//通过ＳＱＬ名称和条件查询取值
	public abstract String getValueBySqlNameAndCond(String sqlName,String cond);
	
	//通过ＳＱＬ语句和条件查询单条值
	public String getValueBySqlAndCond(String sql, String cond) ;
	
	
	
	//通过ＳＱＬ语句和数组参数值查询单条值
	public String getValueBySqlAndCond(String sql, String []params) ;
	
	//通过ＳＱＬ语句和数组参数值查询单条值
	public Map queryRowsForMap(String sql,String[] sqlParams);
	
	
	//通过SQL语句和参数查询string list	
	public List getStringList(String sql,String[]sqlParams);
	
	
	//通过SQL语句和参数查询MAP list	
	public List getMapList(String sql,String[]sqlParams);
	
	//通过SQL语句查询MAP list	
	public List queryForMapList(String sql);
	
//	更新数据
	public int  executeUpdate(String sql) throws DAOSystemException ;
}

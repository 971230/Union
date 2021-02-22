/**
 * 
 */
package com.ztesoft.common.query;

import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAOSystemException;


/**
 * @author lhw
 *
 */
public class SqlDAOImpl implements SqlDAO{


	private SqlMapExe sqlMapExe;

	
	private static SqlDAO sqldao=new SqlDAOImpl();
	
	private SqlDAOImpl(){
		
		sqlMapExe=SqlMapExe.getInstance();
	}
	
	public static SqlDAO getInstance(){
		
		return sqldao;
	}

	
	//通过业务编码查询业务类型
	@Override
	public String getServTypeByServCode(String servCode){
		
		return sqlMapExe.querySingleValue("getServType", servCode);
	}
//	通过查询标识找到配置的SQL语句
	@Override
	public String getQuerySql(String queryId) {
		
		return sqlMapExe.querySingleValue("getQuerySql", queryId);
	}

//	通过ＳＱＬ名称和条件查询取值
	@Override
	public String getValueBySqlNameAndCond(String sqlName, String cond) {
		
		return sqlMapExe.querySingleValue(sqlName, cond);
		
	}
//	通过ＳＱＬ语句和条件查询单条值
	@Override
	public String getValueBySqlAndCond(String sql, String cond) {
		
		return sqlMapExe.queryValueBySqlAndCond(sql,  cond);
		
	}
//	通过ＳＱＬ语句和数组参数值查询单条值
	@Override
	public String getValueBySqlAndCond(String sql, String[] params) {
		
		return sqlMapExe.queryValueBySqlAndCond(sql, params);
	}
	

	@Override
	public List getStringList(String sql,String[] sqlParams){
		
		return sqlMapExe.queryForStringList(sql, sqlParams);
		
	}
	
	//通过SQL语句与参数值查询多字段多纪录
	@Override
	public List getMapList(String sql, String[] sqlParams){
		
		return sqlMapExe.queryForMapListBySql(sql, sqlParams);
		
	}
	
	//根据SQL进行查询
	@Override
	public List queryForMapList(String sql) {
		
		return sqlMapExe.queryForMapList(sql);
		
	}
	//更新数据
	@Override
	public int  executeUpdate(String sql) throws DAOSystemException {
		return sqlMapExe.executeUpdate(sql);
	}

	@Override
	public Map queryRowsForMap(String sql, String[] sqlParams)  throws DAOSystemException {
		

		
		return sqlMapExe.queryRowsForMap(sql,sqlParams);
	}
	
	
}

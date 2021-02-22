package com.ztesoft.common.staticdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.query.SqlMapExe;
import com.ztesoft.ibss.common.dao.DAOUtils;

/**
 * @author Reason.Yea
 * @version Created Mar 2, 2013
 */
public class StaticDataCache {
	private final static String SELECT_DC_SQL_STR = "SELECT dc_sql from dc_sql where dc_name = ?";
	private final static String ALL_SELECT_DC_SQL_STR ="SELECT dc_name, dc_sql from dc_sql";
	
	public static List getAllStaticSql(Connection conn){
		SqlMapExe sqlExe = SqlMapExe.getInstance();
		List sqls = sqlExe.queryForMapListBySql(ALL_SELECT_DC_SQL_STR, new String[]{});
		return sqls;
	}
	
	public static String getSql(Connection conn,String dc_name){
		SqlMapExe sqlExe =  SqlMapExe.getInstance();
		List sqls = sqlExe.queryForMapListBySql(SELECT_DC_SQL_STR, new String[]{dc_name});
		if(sqls.size()>0)return (String) sqls.get(0);
		return "";
	}
	
	public static ArrayList getData(Connection conn,String dc_sql){
		PreparedStatement stmt = null;
		ResultSet result = null;

		ArrayList retList = new ArrayList();

		try {
			stmt = conn.prepareStatement(dc_sql);
			result = stmt.executeQuery();
			// 判断有多少个字段在结果集中
			ResultSetMetaData md = stmt.getMetaData();
			int columCount = md.getColumnCount();
			md = null;
			while (result.next() ) {
				HashMap map = new HashMap();
				map.put("id", DAOUtils.trimStr(result.getString(1)));
//				map.put("code", DAOUtils.trimStr(result.getString(1)));
				map.put("text", DAOUtils.trimStr(result.getString(2)));
//				map.put("desc", DAOUtils.trimStr(result.getString(2)));
				
				//如果有大于2个字段取parentValueId，即第三个字段是上级节点的对应的值。
//				if(columCount > 2)
//					map.put("pid", result.getString(3));
//				else
//					map.put("pid", "");
//				if(columCount > 3)
//					map.put("para", result.getString(3));
//				else
//					map.put("para", "");			
								
				retList.add(map);
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting " + "dc_sql:"+dc_sql+"\n" + se.getMessage(), se);
		} finally {
			DAOUtils.closeResultSet(result, null);
			DAOUtils.closeStatement(stmt, null);
		}

		return retList;
	}
}

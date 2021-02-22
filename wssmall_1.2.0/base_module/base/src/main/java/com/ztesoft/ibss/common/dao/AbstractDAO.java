package com.ztesoft.ibss.common.dao;

import com.ztesoft.ibss.common.util.SqlMapExe;

import java.sql.Connection;

/**
 * @author Reason
 * @version Created Jan 17, 2012
 */
public abstract class AbstractDAO {
	public Connection connection;
	public SqlMapExe  sqlExe;
	
	public AbstractDAO(Connection conn){
		this.connection = conn;
		this.sqlExe = new SqlMapExe(conn);
	}
}

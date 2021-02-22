package com.ztesoft.face.comm;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.query.SqlMapExe;

/**
 * @author Reason.Yea
 * @version Created Feb 18, 2013
 */
public abstract class FaceVO {
//	public abstract void loadFromMap(HashMap log);
//	public abstract HashMap toMap();
	
	public abstract String getInsertSql();
	public abstract ArrayList toList();
	
//	public abstract String getUpdateSql();
//	public abstract ArrayList toUpdateList();
	
	public SqlMapExe sqlExe = FaceContext.get().getSqlExe();
	
	
	public int save(){
		String insertSql = getInsertSql();
		List sqlParams = toList();
		return sqlExe.excuteUpdate(insertSql, sqlParams);
	}
	
}

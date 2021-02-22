package com.ztesoft.face.comm;

import com.ztesoft.common.query.SqlMapExe;
import com.ztesoft.face.comm.FaceContext.ContextObj;

/**
 * @author Reason.Yea
 * @version Created Feb 21, 2013
 */
public abstract class FaceDAO {
	public ContextObj context;
	public SqlMapExe sqlExe;
	public Object get(String name ){
		return context.getMap().get(name);
	}
	public FaceDAO(){
		this.context = FaceContext.get();
		this.sqlExe = context.getSqlExe();
	}
}

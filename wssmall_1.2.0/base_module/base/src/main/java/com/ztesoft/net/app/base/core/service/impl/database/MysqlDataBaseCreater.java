package com.ztesoft.net.app.base.core.service.impl.database;

import com.ztesoft.net.app.base.core.service.IDataBaseCreater;
import com.ztesoft.net.framework.database.ISqlFileExecutor;

/**
 *  mysql数据库创建
 * @author kingapex
 *
 */
public class MysqlDataBaseCreater implements IDataBaseCreater {
	private  ISqlFileExecutor sqlFileExecutor;
	
	@Override
	public void create() {
		sqlFileExecutor.execute("file:com/enation/eop/eop_mysql.sql");
		sqlFileExecutor.execute("file:com/enation/mall/mall_mysql.sql");
		sqlFileExecutor.execute("file:com/enation/cms/cms_mysql.sql");
	}
	public ISqlFileExecutor getSqlFileExecutor() {
		return sqlFileExecutor;
	}
	public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
		this.sqlFileExecutor = sqlFileExecutor;
	}

}

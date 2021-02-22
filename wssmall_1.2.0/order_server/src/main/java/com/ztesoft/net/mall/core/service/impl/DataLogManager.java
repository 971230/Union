
package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.model.DataLog;
import com.ztesoft.net.mall.core.service.IDataLogManager;

/**
 * 数据日志实体
 * @author wui
 */

public class DataLogManager  implements IDataLogManager {

	@Override
	public void insertDataLog(DataLog dataLog) {
		IDaoSupport  daoSupport = SpringContextHolder.getBean("daoSupport");
		daoSupport.insert("es_data_log", dataLog);
	}

	@Override
	public void insertDataBeforeLog(DataLog dataLog) {
//		IDaoSupport  baseDaoSupport = SpringContextHolder.getBean("daoSupport");
//		daoSupport.execute("insert into ES_DATA_LOG_BEFORE(LOG_ID,CREATE_TIME) values(?,sysdate)", System.currentTimeMillis());
	}

	@Override
	public void insertDataAfterLog(DataLog dataLog) {
//		IDaoSupport  baseDaoSupport = SpringContextHolder.getBean("daoSupport");
//		daoSupport.execute("insert into ES_DATA_LOG_After(LOG_ID,CREATE_TIME)  values(?,sysdate)", System.currentTimeMillis());
	}
}

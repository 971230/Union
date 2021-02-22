package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.mall.core.model.DataLog;


/**
 * 数据日志管理
 * 
 * @author wu.i
 */
public interface IDataLogManager {
	public void insertDataLog(DataLog dataLog);
	
	public void insertDataBeforeLog(DataLog dataLog);
	
	public void insertDataAfterLog(DataLog dataLog);
}

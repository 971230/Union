package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.HostEnvVo;

public interface IGrayDataSyncManager {
	
	public String doGrayDataSync(String data_type,String from_db_db,String to_db_db);
	public Page getHostEnvList(HostEnvVo hostEnvVo,int pageSize,int pageNo);
	public List getHostEnvAllList();
	public List refreshHostEnv();
	public void refrenshDataSourceConfig();
	public List getDataSyncHostEnv();
	
}

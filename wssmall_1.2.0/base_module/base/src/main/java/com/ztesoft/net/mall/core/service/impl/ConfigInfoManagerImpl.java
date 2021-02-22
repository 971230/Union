package com.ztesoft.net.mall.core.service.impl;

import java.util.List;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.ConfigInfo;
import com.ztesoft.net.mall.core.service.IConfigInfoManager;

public class ConfigInfoManagerImpl extends BaseSupport<ConfigInfo> implements IConfigInfoManager {
	
	@Override
	public List<ConfigInfo> list(){
		
		String sql = "select * from CONFIG_INFO where source_from is not null" ;
		
		return this.baseDaoSupport.queryForList(sql, ConfigInfo.class);
	}
	
	@Override
	public ConfigInfo getCofigByKey(String cfId){
		String sql = "select * from es_config_info where cf_id = ? and source_from is not null";
		ConfigInfo info = null;
		List config_list = this.baseDaoSupport.queryForList(sql, ConfigInfo.class, cfId);
		if(config_list!=null && config_list.size()>0)
			info = (ConfigInfo)config_list.get(0);
		if(info == null) info = new ConfigInfo();
		return info;
	}
	
	@Override
	public List<ConfigInfo> getServiceList(String cfId){
		String sql = "select * from CONFIG_INFO where cf_id like '" + cfId + "%' and source_from is not null" ;
		
		return this.baseDaoSupport.queryForList(sql, ConfigInfo.class);
	}
}
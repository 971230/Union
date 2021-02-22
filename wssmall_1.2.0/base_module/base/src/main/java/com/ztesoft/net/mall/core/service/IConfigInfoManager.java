package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.mall.core.model.ConfigInfo;

import java.util.List;


public interface IConfigInfoManager {
	public List<ConfigInfo> list();
	public ConfigInfo getCofigByKey(String cfId);
	public List<ConfigInfo> getServiceList(String cfId);
}

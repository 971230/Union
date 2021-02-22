package com.ztesoft.rule.core.exe;

import java.util.List;

import com.ztesoft.rule.core.module.cfg.ConfigData;
import com.ztesoft.rule.core.module.cfg.RetryInfo;

public interface IRuleConfigs {
	//加载所有能跑
//	public List<ConfigData> loadTimingRuleConfigDatas() ;
	
	public void setConfigData(ConfigData configData) ;
	public ConfigData getConfigData() ;
	
	public ConfigData getConfigData(String planCode) ;
	
	public List<RetryInfo> loadRetryInfo() ;
	
	public ConfigData apply(long resId) ;
	
	
	//当前方案执行完毕后，更新状态
	public void updateConfigData() ;
}

package com.ztesoft.rule.core.exe;

import java.util.List;
import java.util.Map;

import com.ztesoft.rule.core.ext.IFactLoader;
import com.ztesoft.rule.core.module.cfg.MidDataConfig;


public interface IRuleFacts {
	//依赖设置,按需设置,不设置,则采用默认值
	public void setFactLoader(IFactLoader factLoader) ;
	
	List loadFacts(MidDataConfig cfg, Map partner) ;
}

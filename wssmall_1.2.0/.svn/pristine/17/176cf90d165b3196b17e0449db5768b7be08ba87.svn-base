package com.ztesoft.rule.core.exe;

import com.ztesoft.rule.core.ext.IPlanCtrl;
import com.ztesoft.rule.core.module.cfg.ConfigData;


/**
 * 执行方案申请：
 * 1.一次只提供一个可执行方案
 * 2.好处：简单,容易控制、分配
 * 
 * @author easonwu 
 * @creation Dec 23, 2013
 * 
 */
public interface IApplyPlan {
	//每次提取最大数据量
	public static final int FETCH_MAX_SIZE = 20 ;
	
	//【设置扩展点】
	public void setPlanCtrl(IPlanCtrl planCtrl) ;
	
	ConfigData apply(long resId) ;
}

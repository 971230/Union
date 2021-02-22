package com.ztesoft.net.mall.core.service;


import com.ztesoft.net.mall.core.model.RuleTypeRela;


/***
 * 业务类型规则关联接口
 * @author huang.xiaoming
 *
 */
public interface IRuleTypeRelaManager {
	
	public RuleTypeRela getRuleTypeRela(String busiType);
	
	public void refreshCache();
}

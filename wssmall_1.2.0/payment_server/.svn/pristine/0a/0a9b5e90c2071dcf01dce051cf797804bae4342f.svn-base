package com.ztesoft.net.mall.cmp;

import java.util.List;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.CheckAcctConfig;
import com.ztesoft.net.model.CheckacctRule;

import params.checkacctconfig.resp.CheckAcctConfigResp;

public interface ICheckAcctManager {
	
	public CheckAcctConfigResp checkAcctByDateAndSystemId(String p_str);
	public CheckAcctConfigResp checkAcct();
	public CheckAcctConfigResp checkAcctBySystemId(String systemid);
	/**
	 * 查询所有对账规则
	 * @作者 MoChunrun
	 * @创建日期 2013-10-23 
	 * @return
	 */
	public List<CheckacctRule> listCheckacct(int type);
	public CheckacctRule getCheckacct(String system_id,int type);
	
	/**
	 * 对账配置列表
	 * @param pageNO
	 * @param pageSize
	 * @param checkAcctConfig
	 * @return
	 */
	public Page listAcct(int pageNO, int pageSize,CheckAcctConfig checkAcctConfig);
	
	
	/**
	 * 保存配置
	 * @param checkAcctConfig
	 */
	public void saveAcct(CheckAcctConfig checkAcctConfig);
	
	
	/**
	 * 更新配置
	 * @param checkAcctConfig
	 */
	public void updateAcct(CheckAcctConfig checkAcctConfig);
	
	
	/**
	 * 根据id查询配置
	 * @param system_id
	 * @return
	 */
	public CheckAcctConfig qyrAcctById(String system_id);
}

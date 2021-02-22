package com.ztesoft.net.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.CheckAcctConfig;
import com.ztesoft.net.model.CheckAcctLog;
import com.ztesoft.net.model.OrderItemAgentMoney;

/**
 * 对账管理类
 * @作者 MoChunrun
 * @创建日期 2015-1-18 
 * @版本 V 1.0
 */
public interface IAgentMoneyManager {

	/**
	 * 查询前一天的对账明细
	 * @作者 MoChunrun
	 * @创建日期 2015-1-18 
	 * @return
	 */
	public List<OrderItemAgentMoney> listPreDayAgentItems();
	
	/**
	 * 添加对账记录
	 * @作者 MoChunrun
	 * @创建日期 2015-1-18 
	 * @param log
	 */
	public void insertCheckAccLog(CheckAcctLog log);
	
	public CheckAcctConfig getCheckConfig(String system_id);
	
}

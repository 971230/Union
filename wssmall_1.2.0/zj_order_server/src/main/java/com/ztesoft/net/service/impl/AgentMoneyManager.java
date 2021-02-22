package com.ztesoft.net.service.impl;

import java.util.List;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.CheckAcctConfig;
import com.ztesoft.net.model.CheckAcctLog;
import com.ztesoft.net.model.OrderItemAgentMoney;
import com.ztesoft.net.service.IAgentMoneyManager;
import com.ztesoft.net.sqls.SF;

public class AgentMoneyManager extends BaseSupport implements IAgentMoneyManager {

	@Override
	public List<OrderItemAgentMoney> listPreDayAgentItems() {
		String sql = SF.ecsordSql("OrderItemAgentMoneys");
		List<OrderItemAgentMoney> list = this.baseDaoSupport.queryForList(sql, OrderItemAgentMoney.class);
		return list;
	}

	@Override
	public void insertCheckAccLog(CheckAcctLog log) {
		this.baseDaoSupport.insert("ES_CHECKACCT", log);
	}

	@Override
	public CheckAcctConfig getCheckConfig(String system_id) {
		String sql = SF.ecsordSql("CheckAccConfigBySystemID");
		List<CheckAcctConfig> list = this.baseDaoSupport.queryForList(sql, CheckAcctConfig.class,system_id);
		return list.size()>0?list.get(0):null;
	}

}

package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.DealLog;
import com.ztesoft.net.mall.core.service.IDealLogManager;
import com.ztesoft.net.sqls.SF;

public class DealLogManager extends BaseSupport<DealLog> implements IDealLogManager {
	
	@Override
	public void addLog(DealLog dealLog){
		
		this.baseDaoSupport.insert("deal_log", dealLog);
	}
	
	@Override
	public void updateLog(DealLog dealLog){
		
		this.baseDaoSupport.update("deal_log", dealLog, " deal_order_id = '" + dealLog.getDeal_order_id() + "'");
	}
	
	@Override
	public DealLog getDealLogbyId(String orderId){
		
		String sql = SF.orderSql("SERVICE_DEAL_LOG_SELECT");
		return this.baseDaoSupport.queryForObject(sql, DealLog.class, orderId);
	}
}

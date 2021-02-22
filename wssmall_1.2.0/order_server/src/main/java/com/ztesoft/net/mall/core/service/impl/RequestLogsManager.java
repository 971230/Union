package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.RequestLogs;
import com.ztesoft.net.mall.core.service.IRequestLogsManager;
import com.ztesoft.net.sqls.SF;

public class RequestLogsManager extends BaseSupport<RequestLogs> implements IRequestLogsManager{
	
	@Override
	public void addReqLog(RequestLogs requestLog){

		this.baseDaoSupport.insert("request_logs", requestLog);
	}
	
	@Override
	public RequestLogs getRequestLogs(String requestId){
		String sql = SF.orderSql("SERVICE_REQ_LOGS_SELECT");
		return this.baseDaoSupport.queryForObject(sql, RequestLogs.class, requestId);
	}

	@Override
	public void updateLog(RequestLogs requestLogs){
		
		this.baseDaoSupport.update("request_logs", requestLogs, " request_id = '" + requestLogs.getRequest_id() + "'");
	}
}

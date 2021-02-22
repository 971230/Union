package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.mall.core.model.RequestLogs;

public interface IRequestLogsManager {
	
	public void addReqLog(RequestLogs requestLog);
	public RequestLogs getRequestLogs(String requestId);
	public void updateLog(RequestLogs requestLogs);
}

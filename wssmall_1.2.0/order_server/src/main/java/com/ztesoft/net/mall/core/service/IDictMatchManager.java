package com.ztesoft.net.mall.core.service;

import zte.net.ecsord.params.logs.req.MatchDictLogsReq;
import zte.net.ecsord.params.logs.req.MatchInfLogStateReq;
import zte.net.ecsord.params.logs.resp.MatchInfLogStateResp;

/**
 * 
 * @author ZX
 * @version 2015-08-27
 * @see 字典匹配管理
 *
 */
public interface IDictMatchManager {

	/**
	 * 日志接口匹配字典
	 * @param req
	 */
	void matchDictLogs(MatchDictLogsReq req);
	
	/**
	 * 计算日志接口返回的状态
	 * @param req
	 * @param resp
	 */
	void matchInfLogState(MatchInfLogStateReq req, MatchInfLogStateResp resp);

}

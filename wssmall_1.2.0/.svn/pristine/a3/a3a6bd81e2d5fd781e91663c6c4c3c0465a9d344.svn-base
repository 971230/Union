/**
 * 
 */
package com.ztesoft.rule.manager.service;

import java.util.List;

import zte.net.ecsord.params.logs.req.QryMatchDictLogsReq;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.LogDictDetailContent;
import com.ztesoft.net.model.DataPraserCatalog;
import com.ztesoft.net.model.DataPraserInst;

/**
 * @author ZX
 * @version 2015-08-27
 * @see 匹配订单日志
 *
 */
public interface IDictMatchLogsManager {
	
	/**
	 * 检索错误日志
	 * @param req
	 * @return
	 */
	Page qryDataPraserInst(QryMatchDictLogsReq req);
	/**
	 * 检索字典目录
	 * @return
	 */
	List<DataPraserCatalog> qryDictCatalog();
	
	/**
	 * 日志处理
	 * @param dataPraserInst
	 * @return
	 */
	DataPraserInst saveDealContent(DataPraserInst dataPraserInst);
	
	/**
	 * 日志详细信息
	 * @param log_id
	 * @return
	 */
	LogDictDetailContent detailContent(String log_id);
}

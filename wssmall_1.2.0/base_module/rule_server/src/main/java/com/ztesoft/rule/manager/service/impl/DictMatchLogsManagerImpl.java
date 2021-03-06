/**
 * 
 */
package com.ztesoft.rule.manager.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.params.logs.req.QryMatchDictLogsReq;

import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.LogDictDetailContent;
import com.ztesoft.net.model.DataPraserCatalog;
import com.ztesoft.net.model.DataPraserInst;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.rule.manager.service.IDictMatchLogsManager;

/**
 * @author ZX
 * @version 2015-08-27
 *
 */
public class DictMatchLogsManagerImpl extends BaseSupport implements IDictMatchLogsManager {
	
	@Override
	public Page qryDataPraserInst(QryMatchDictLogsReq req) {
//		String sql = SF.ruleSql("QRY_MATCH_DICT_LOGS");
		String sql = "SELECT T.*, E.DICT_NAME, E.DEAL_OPINION FROM ES_DATA_PRASER_INST T, " +
				"ES_DATA_PRASER_DICT E WHERE T.DICT_ID=E.DICT_ID AND T.SOURCE_FROM=E.SOURCE_FROM";
		if (StringUtils.isNotBlank(req.getDict_type()) 
				&& !req.getDict_type().equals("-1")) {
			sql += " AND T.CATALOG_ID='"+req.getDict_type()+"'";
		}
		if (StringUtils.isNotBlank(req.getObj_id())) {
			sql += " AND T.OBJ_ID='"+req.getObj_id()+"'";
		}
		if (StringUtils.isNotBlank(req.getDict_desc())) {
//			if (req.getDict_desc().equals("1")) {
//				sql += " AND E.DICT_MATCH_CONTENT='"+req.getDict_desc()+"'";
//			} else if (req.getDict_desc().equals("2")) {
				sql += " AND E.DICT_MATCH_CONTENT LIKE '%"+req.getDict_desc()+"%'";
//			}
		}
		if (StringUtils.isNotBlank(req.getBegin_time())) {
			sql += " AND TO_CHAR(T.CREATE_TIME, 'yyyy-MM-dd hh:mm:ss') >= '"+req.getBegin_time()+"'";
		}
		if (StringUtils.isNotBlank(req.getEnd_time())) {
			sql += " AND TO_CHAR(T.CREATE_TIME, 'yyyy-MM-dd hh:mm:ss') <= '"+req.getEnd_time()+"'";
		}
		return baseDaoSupport.queryForPage(sql, req.getPageNo(), req.getPageSize(), DataPraserInst.class);
	}
	
	@Override
	public List<DataPraserCatalog> qryDictCatalog() {
		String sql = SF.ruleSql("QRY_DICT_CATALOG");
		sql += " AND T.STATE='1'";
		return baseDaoSupport.queryForList(sql);
	}
	
	@Override
	public DataPraserInst saveDealContent(DataPraserInst dataPraserInst) {
		DataPraserInst dataPraserInst0 = new DataPraserInst();
		try {
			String sql0 = "SELECT T.* FROM ES_DATA_PRASER_INST T WHERE T.LOG_ID='"+dataPraserInst.getLog_id()+"'";
			List<DataPraserInst> list = baseDaoSupport.queryForList(sql0, DataPraserInst.class);
			if (list != null && list.size() > 0) {
				dataPraserInst0 = list.get(0);
				dataPraserInst0.setDeal_state(dataPraserInst.getDeal_state());
				dataPraserInst0.setDeal_content(dataPraserInst.getDeal_content());
				dataPraserInst0.setUpdate_time(DateUtil.getTime2());
				baseDaoSupport.update("ES_DATA_PRASER_INST", dataPraserInst0, "LOG_ID='"+dataPraserInst.getLog_id()+"'");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dataPraserInst0;
	}

	@Override
	public LogDictDetailContent detailContent(String log_id) {
		String sql = "SELECT T.RESULT_DESC, E.OP_CODE, E.LOG_ID, T.LOG_ID, D.DEAL_OPINION, D.DICT_NAME"
				   +" FROM INF_COMM_CLIENT_CALLLOG T, ES_DATA_PRASER_INST E, ES_DATA_PRASER_DICT D"
				   +" WHERE 1=1"
//				   +" AND T.SOURCE_FROM = E.SOURCE_FROM"
				   +" AND E.SOURCE_FROM = D.SOURCE_FROM"
				   +" AND E.DICT_ID = D.DICT_ID"
				   +" AND T.OP_CODE = E.OP_CODE"
				   +" AND T.COL3 = E.OBJ_ID"
				   +" AND E.LOG_ID = '"+log_id+"'";
		List<LogDictDetailContent> list = baseDaoSupport.queryForList(sql, LogDictDetailContent.class);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}

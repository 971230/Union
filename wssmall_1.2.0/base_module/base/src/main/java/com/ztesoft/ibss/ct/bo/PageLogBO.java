package com.ztesoft.ibss.ct.bo;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.ztesoft.ibss.common.util.SqlMapExe;
import com.ztesoft.ibss.ct.vo.PageLogVO;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PageLogBO implements IAction {

    private static Logger log = Logger.getLogger(PageLogBO.class);
    private SqlMapExe sqlExe ;
    String 	batchSql = "INSERT INTO TWB_EVENT(EVENT_ID, USER_NO, EVENT_TIME, LOGIN_TYPE, VISIT_URL, " +
	  "OBJECT_ID, VISIT_IP, LOGIN_PROD_NO, OPR_USER_NO, OPR_PROD_NO,LOGIN_AREA_CODE," +
	  "OPR_AREA_CODE,CUSTGROUP,CUSTBRAND,LOGON_NAME,SERV_KIND," +
	  "SERV_TYPE,SERV_NO,SESSION_ID,SEQU,SOURCE_URL,OPER_SYSTEM,BROWSER_NAME,SCREEN_SIZE )" +
	  "VALUES(to_char2(SYSDATE,'yyyymmddhh24miss')||LPAD(getseq('s_TWB_EVENT'),6,'0'), ?, to_date(?,'yyyy-MM-dd HH24:mi:ss'), ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    @Override
	public int perform(DynamicDict aDict) throws FrameException {
    	
        String actId = aDict.m_ActionId.toUpperCase().trim();
        sqlExe = new SqlMapExe(aDict.GetConnection());
        if(actId.equals("PAGE_LOG")){
        	PAGE_LOG(aDict);
        }
        return 0;
    }

	private void PAGE_LOG(DynamicDict aDict) throws FrameException {
		List ls = (List) aDict.getValueByName("SESSIONLOGS", false);
		log.info("batch page log:"+ls);
		this.batchLog(ls);
	}
	public void batchLog(List ls) throws FrameException {
		if(ls==null)return;
		List sqlParams = new ArrayList();
		for (int i = 0; i < ls.size(); i++) {
			List param = new ArrayList();
			//logger.info("batch  logvo name:"+ls.get(i).getClass());
			PageLogVO log=null;
			try{
				log = (PageLogVO) ls.get(i);
			}catch (Exception e) {//cast exception
				continue;
			}
			//取消息ID，由14位年月日时分秒+6位序号组成
			//String	EVENT_ID = Swb_public.fucGetSeq(connection, "s_TWB_EVENT","2", 20);
			//param.add(EVENT_ID);
			param.add(log.getUser_no());
			param.add(log.getEvent_time());
			param.add(log.getLogin_type());
			param.add(log.getVisit_url());
			param.add(log.getObject_id());
			param.add(log.getVisit_ip());
			param.add(log.getLogin_prod_no());
			param.add(log.getOpr_user_no());
			param.add(log.getOpr_prod_no());
			param.add(log.getLogin_area_code());
			param.add(log.getOpr_area_code());
			param.add(log.getCustgroup());
			param.add(log.getCustgroup());
			param.add(log.getLogon_name());
			param.add(log.getServ_kind());
			param.add(log.getServ_type());
			param.add(log.getServ_no());
			param.add(log.getSession_id());
			param.add(log.getSequ());
			param.add(log.getSource_url());
			param.add(log.getOper_system());
			param.add(log.getBrowser_name());
			param.add(log.getScreen_size());
			sqlParams.add(param);
		}
		sqlExe.excuteBatchUpdate(batchSql, sqlParams);
	}
}

package com.ztesoft.face.comm;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.query.SqlMapExe;
import com.ztesoft.common.util.ContextHelper;
import com.ztesoft.face.vo.OperLog;
import com.ztesoft.ibss.common.util.DateFormatUtils;
//import com.ztesoft.inf.framework.utils.SequenceUtils;

/**
 * @author Reason.Yea
 * @version Created Feb 23, 2013
 */
public class ServHelper {

	/**
	 * 日志记录
	 * @param servHandle 
	 */
	public static void log(IServHandle servHandle, OperLog operLog){
		if(!operLog.isLog()){
			servHandle.logger.debug("====operLog:"+operLog.toList());
			return;
		}
		operLog.setLog_ip(ContextHelper.getIp());
		operLog.setLog_time(DateFormatUtils.getFormatedDateTime());
		if(StringUtils.isEmpty(operLog.getLog_id())){
			operLog.setLog_id(getSeqId(servHandle.sqlExe,"TFM_OPER_LOG_SEQ"));
		}
		if(StringUtils.isEmpty(operLog.getOper_staff_no())){
			operLog.setOper_staff_no("X");
		}
		if(StringUtils.isEmpty(operLog.getTable_name())){
			operLog.setTable_name(StringUtils.substring(servHandle.getModuleName(), 0,30));
		}
		if(StringUtils.isEmpty(operLog.getRel_id())){
			operLog.setRel_id(StringUtils.substring(servHandle.getMethodName(), 0,30));
		}
		operLog.save();
	}
	
	public static String getSeqId(SqlMapExe sqlExe,String seq_name){
		
		return new SequenceManageDAOImpl().getNextSequence(seq_name);
	}
	
}

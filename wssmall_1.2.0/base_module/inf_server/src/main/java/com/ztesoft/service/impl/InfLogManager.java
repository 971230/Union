package com.ztesoft.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.model.InfCommClientCalllog;
import com.ztesoft.service.IInfLogManager;

public class InfLogManager extends BaseSupport implements IInfLogManager {

	@Override
	public void log(InfCommClientCalllog log) {
		String log_id = this.baseDaoSupport.getSequences("seq_inf_col_log_id","10000",32);
		log.setLog_id(log_id);
		this.baseDaoSupport.insert("inf_comm_client_calllog", log);
	}

}

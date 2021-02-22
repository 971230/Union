package com.ztesoft.net.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.model.UnimallOrderInfo;
import com.ztesoft.net.service.IUnimallOrderLocalManager;

public class UnimallOrderLocalManager extends BaseSupport implements IUnimallOrderLocalManager {
	
	@Transactional(propagation = Propagation.REQUIRED)  
	public void oper(String sqlLocalDayDel, String sqlInsert, List params) {
			this.getBaseDaoSupport().execute(sqlLocalDayDel);
			this.getBaseDaoSupport().batchExecute(sqlInsert, params);
		}

	@Transactional(propagation = Propagation.REQUIRED)  
	public void oper(String sqldel) {
		// TODO Auto-generated method stub
		this.getBaseDaoSupport().execute(sqldel);
//		this.getBaseDaoSupport().insert("es_unimall_order",unimallOrderInfo);
	}
	
}

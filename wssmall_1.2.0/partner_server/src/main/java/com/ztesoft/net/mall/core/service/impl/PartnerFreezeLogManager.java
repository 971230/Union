package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.model.PartnerFreezeLog;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.IPartnerFreezeLogManager;

public class PartnerFreezeLogManager extends BaseSupport<PartnerFreezeLog> implements IPartnerFreezeLogManager {
	
	@Override
	public List<Map> list() {
		String sql = "select * from partner_freeze_log where disabled = 0";
		return this.baseDaoSupport.queryForList(sql);
	}
	
	@Override
	public Page qyFreezeLogList(String partnerId, String beginTime,String endTime,String opType, int page,int pageSize){
		
		String sql = this.getQrySql() + " and partner_id = '" + partnerId + "'";
		
		if(beginTime != null && !"".equals(beginTime)){
			sql += " and op_time >= to_date('" + beginTime + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		}
		if(endTime != null && !"".equals(endTime)){
			sql += " and op_time <= to_date('" + endTime + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		}
		if(opType != null && !"".equals(opType)){
			sql += " and op_type = '" + opType + "'";
		}
		
		sql += " order by op_time desc";
		Page webpage  = this.daoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}
	
	@Override
	public boolean insertLog(PartnerFreezeLog freezeLog){
			
		this.baseDaoSupport.insert("partner_freeze_log", freezeLog);
		return true;
	}
	
	@Override
	public List getFreezeLogById(String partnerId){
		String sql = "";
		List result = new ArrayList();
		if(null != partnerId && !"".equals(partnerId)){
			sql += this.getQrySql() + " and partner_id = ? ";
		}else {
			return result;
		}
		sql += " order by op_time desc";
		result = this.baseDaoSupport.queryForList(sql, partnerId);
		return result;
	}
	
	private String getQrySql(){
		
		String qrySql = "select partner_id, log_id, op_time, decode(op_type,'00A','冻结','00B','解冻','')op_type," +
		" nvl(old_amount,0)/100 old_amount, nvl(new_amount,0)/100 new_amount, comments, nvl(amount,0)/100 amount, userid from es_partner_freeze_log where 1=1 ";
		
		return qrySql;
	}
}
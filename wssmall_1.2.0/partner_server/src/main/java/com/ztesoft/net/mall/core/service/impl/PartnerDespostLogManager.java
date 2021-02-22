package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.net.app.base.core.model.PartnerAccount;
import com.ztesoft.net.app.base.core.model.PartnerDespostLog;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.service.IPartnerDespostLogManager;

public class PartnerDespostLogManager extends BaseSupport<PartnerAccount> implements IPartnerDespostLogManager{
	@Override
	public boolean insertLog(PartnerDespostLog log){
		
			
		this.baseDaoSupport.insert("partner_despost_log", log);
		return true ;
	}
	@Override
	public List getDespostLogById(String partnerId){
		List result = new ArrayList();
		String sql = this.getQrySql() + " and partner_id = ? and op_type in('00C','00D')";
		sql += "order by op_time desc";
		try {
			result = this.baseDaoSupport.queryForList(sql, partnerId);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return result;
	}
	
	private String getQrySql(){
		
		String sql = "select partner_id, log_id, op_time, decode(op_type,'00A','冻结','00B','解冻','00C','充值','')op_type, nvl(old_amount,0)/100 old_amount, " +
				"nvl(new_amount,0)/100 new_amount, comments, nvl(amount,0)/100 amount, userid from es_partner_despost_log where 1 = 1";
		
		return sql;
	}
}

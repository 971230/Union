package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.PartnerDespost;
import com.ztesoft.net.mall.core.service.IPartnerDespostManager;

public class PartnerDespostManager extends BaseSupport<PartnerDespost> implements IPartnerDespostManager{
	@Override
	public boolean insert(PartnerDespost despost){
		
		this.baseDaoSupport.insert("partner_despost", despost);
		return true;
	}
	
	@Override
	public boolean edit(PartnerDespost partnerDespost){
			
		this.baseDaoSupport.update("partner_despost", partnerDespost, " depost_id = '" + partnerDespost.getDepost_id() + "'");
		return true;
	}
	
	@Override
	public PartnerDespost getDespostById(String depostId){
		try {
			
			String sql = "select * from partner_despost where depost_id = ?";			
			return this.baseDaoSupport.queryForObject(sql, PartnerDespost.class, depostId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
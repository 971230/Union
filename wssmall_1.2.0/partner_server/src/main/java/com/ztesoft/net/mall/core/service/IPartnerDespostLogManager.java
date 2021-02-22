package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.app.base.core.model.PartnerDespostLog;

public interface IPartnerDespostLogManager {
	
	public boolean insertLog(PartnerDespostLog log);
	public List getDespostLogById(String partnerId);
}

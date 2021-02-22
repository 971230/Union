package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.mall.core.model.PartnerDespost;

public interface IPartnerDespostManager {
	public boolean insert(PartnerDespost despost);
	public boolean edit(PartnerDespost partnerDespost);
	public PartnerDespost getDespostById(String depostId);
}

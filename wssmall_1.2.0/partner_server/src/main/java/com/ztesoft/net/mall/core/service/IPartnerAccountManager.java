package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.app.base.core.model.PartnerAccount;
import com.ztesoft.net.framework.database.Page;

public interface IPartnerAccountManager {
	
	public boolean add(PartnerAccount partnerAccount);
	public PartnerAccount getAccountById(String partnerId);
	public boolean edit(PartnerAccount partnerAccount);
	public PartnerAccount getAccountByCurrentUserId();

	public PartnerAccount getprimaryAccount(String partnerId);
	
	public PartnerAccount getSecondaryAccount(String partnerId);
	
	public int delete(String shop_code);
	
	public Page list(PartnerAccount pAccount, int page, int pageSize);
}
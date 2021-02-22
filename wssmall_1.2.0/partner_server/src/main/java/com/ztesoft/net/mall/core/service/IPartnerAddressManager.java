package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.app.base.core.model.PartnerAddress;
import com.ztesoft.net.framework.database.Page;

/**
 * 分销商申请
 * @author dengxiuping
 *
 */
public interface IPartnerAddressManager {
	public int add(PartnerAddress obj);
	public Page list(PartnerAddress obj, int page, int pageSize);
	public PartnerAddress getPartnerAddrByAddrId(String id);
	public PartnerAddress getPartnerAddressByParId(String id);
	public List<PartnerAddress> getPartnerAddress(String id);
	public PartnerAddress getPartnerAddressByAddr_id(String id);
	public int deleteAddr(String id);
	public int edit(PartnerAddress partner);
}

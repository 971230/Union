package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.service.IDlyTypeAddressManager;
import com.ztesoft.net.model.DlyTypeAddress;
import com.ztesoft.net.sqls.SF;

import java.util.List;

public class DlyTypeAddressManager extends BaseSupport implements IDlyTypeAddressManager {

	@Override
	public List<DlyTypeAddress> qryDlyTypeAddressByRegionId(String region_id) {
		String sql = SF.orderSql("SERVICE_DLY_ADDR_SELECT");
		return this.baseDaoSupport.queryForList(sql, DlyTypeAddress.class, region_id);
	}

}

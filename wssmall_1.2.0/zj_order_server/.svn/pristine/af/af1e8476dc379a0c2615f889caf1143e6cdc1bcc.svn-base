package com.ztesoft.net.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.DeliveryPrints;
import com.ztesoft.net.service.IDeliveryPrintsManager;
import com.ztesoft.net.sqls.SF;

public class DeliveryPrintsManager extends BaseSupport implements IDeliveryPrintsManager {

	@Override
	public DeliveryPrints get(String delvery_print_id){
		String sql = SF.ecsordSql("DELIVERY_PRINTS_SELECT_BY_ID");
		return (DeliveryPrints) this.baseDaoSupport.queryForObject(sql, DeliveryPrints.class, delvery_print_id);
	}
}

package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.OrderMeta;
import com.ztesoft.net.mall.core.service.IOrderMetaManager;
import com.ztesoft.net.sqls.SF;

import java.util.List;

public class OrderMetaManager extends BaseSupport<OrderMeta> implements
		IOrderMetaManager {

	@Override
	public void add(OrderMeta orderMeta) {
		this.baseDaoSupport.insert("order_meta", orderMeta) ;
	}

	@Override
	public List<OrderMeta> list(String orderid) {
		String sql = SF.orderSql("SERVICE_ORDER_META_SELECT")+" and orderid=?";
		return this.baseDaoSupport.queryForList(sql, OrderMeta.class,orderid);
	}

}

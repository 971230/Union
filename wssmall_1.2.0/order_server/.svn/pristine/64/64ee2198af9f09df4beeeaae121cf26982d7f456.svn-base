package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.OrderAccept;
import com.ztesoft.net.mall.core.service.IOrderAcceptManager;
import com.ztesoft.net.sqls.SF;

import java.util.ArrayList;
import java.util.List;

public class OrderAcceptManager extends BaseSupport implements IOrderAcceptManager {

	@Override
	public OrderAccept get(String accept_id) {
		String sql = SF.orderSql("SERVICE_ORDER_ACCEPT_SELECT_BY_ID");
		return (OrderAccept) this.baseDaoSupport.queryForObject(sql, OrderAccept.class, accept_id);
	}
	
	@Override
	public void add(OrderAccept accept) {
		this.baseDaoSupport.insert("ES_ORDER_ACCEPT", accept);
	}

	@Override
	public List<OrderAccept> listOrderAccept(String user_id,int accept_status) {
		List params = new ArrayList();
		String sql = SF.orderSql("SERVICE_ORDER_ACCEPT_SELECT");
		if(user_id!=null && !"".equals(user_id) && !"-1".equals(user_id)){
			sql += " and t.userid=?";
			params.add(user_id);
		}
		if(accept_status!=999){
			sql += " and t.accept_status=?";
			params.add(accept_status);
		}
		return this.baseDaoSupport.queryForList(sql, OrderAccept.class, params.toArray());
	}

	@Override
	public void updateAcceptStatus(String accept_id, int accept_status) {
		String sql = SF.orderSql("SERVICE_ACCEPT_STATUS_UPDATE");
		this.baseDaoSupport.execute(sql,accept_status, accept_id);
	}

	@Override
	public OrderAccept qryByOrderId(String order_id) {
		String sql = SF.orderSql("SERVICE_ACCEPT_SELECT_BY_ORDER");
		List<OrderAccept> orderAccepts =  this.baseDaoSupport.queryForList(sql, OrderAccept.class, order_id);
		if(orderAccepts.size()>0)
			return orderAccepts.get(0);
		return null;
	}

	@Override
	public void updateAcceptStatusByOrderId(String order_id,
			int accept_status) {
		String sql = SF.orderSql("SERVICE_ACCEPT_STATUS_UPDATE_BY_ORDER");
		this.baseDaoSupport.execute(sql,accept_status, order_id);
	}


	@Override
	public void updateOrderAcceeptStatusByOrderId(String order_id,
			int accept_status) {
		String sql = SF.orderSql("SERVICE_ORD_ACCEPT_STATUS_UPDATE");
		this.baseDaoSupport.execute(sql, accept_status,order_id);
	}

}

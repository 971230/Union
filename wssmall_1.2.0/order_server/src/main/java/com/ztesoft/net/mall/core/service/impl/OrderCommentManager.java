package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.OrderComments;
import com.ztesoft.net.mall.core.model.OrderMessage;
import com.ztesoft.net.mall.core.model.OrderUncomments;
import com.ztesoft.net.mall.core.service.IOrderCommentManager;
import com.ztesoft.net.sqls.SF;

import java.util.List;


public class OrderCommentManager extends BaseSupport implements
		IOrderCommentManager {

	@Override
	public List<OrderComments> qryOrderComments(String order_id) {
		String sql = SF.orderSql("SERVICE_ORDER_COMMENTS_SELECT");
		return this.baseDaoSupport.queryForList(sql, OrderComments.class, order_id);
	}

	@Override
	public void add(OrderComments comment) {
		this.baseDaoSupport.insert("es_order_comments", comment);
	}

	@Override
	public List listUnComments(String order_id) {
		String sql = SF.orderSql("SERVICE_UNCOMMENTS_SELECT");
		return this.baseDaoSupport.queryForList(sql, order_id);
	}

	@Override
	public void addUnComments(OrderUncomments orderUnComments) {
		this.baseDaoSupport.insert("es_order_uncomments", orderUnComments);
	}

	@Override
	public List<OrderMessage> listOrderMessage(String order_id) {
		String sql = SF.orderSql("SERVICE_ORDER_MESSAGE_SELECT");
		return this.baseDaoSupport.queryForList(sql, OrderMessage.class, order_id);
	}

	@Override
	public void addOrderMessage(OrderMessage message) {
		this.baseDaoSupport.insert("es_order_message", message);
	}

	@Override
	public void updateConfirmStatus(int confirm_status, String order_id) {
		String sql = SF.orderSql("SERVICE_CONFIRM_STATUE_UPDATE");
		this.baseDaoSupport.execute(sql, confirm_status,order_id);
	}

	@Override
	public void updateConfirmStatus(int status, int confirm_status,
			String order_id) {
		String sql = SF.orderSql("SERVICE_STATUS_UPDATE");
		this.baseDaoSupport.execute(sql,status, confirm_status,order_id);
	}

}

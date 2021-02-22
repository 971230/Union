package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.action.order.orderc.OrderApply;
import com.ztesoft.net.mall.core.model.OrderApplyItem;
import com.ztesoft.net.mall.core.service.IOrderApplycMamager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

import java.util.List;

import org.drools.core.util.StringUtils;

public class OrderApplycMamager extends BaseSupport implements IOrderApplycMamager {

	@Override
	public List<OrderApply> queryApplyByOrderId(String orderId,String serviceType,String applyState) {
		String sql = SF.orderSql("SERVICE_APPLY_SELECT");
		if(OrderStatus.ORDER_APPLY_STATUS_2.equals(serviceType))
			sql = SF.orderSql("SERVICE_APPLY_SELECT_2");
		sql += " and a.source_from=?";
		List<OrderApply> list = this.baseDaoSupport.queryForList(sql, OrderApply.class, orderId,applyState,serviceType,ManagerUtils.getSourceFrom());
		return list;
	}

	@Override
	public void insert(String tableName, OrderApply apply) {
		this.baseDaoSupport.insert("es_order_apply", apply);
	}

	@Override
	public void updateApplyState(String applyId, String applyState) {
		String sql = SF.orderSql("SERVICE_APPLY_UPDATE");
		this.baseDaoSupport.execute(sql, applyState,applyId);
	}

	@Override
	public OrderApply qryByAppliId(String applyId) {
		String sql = SF.orderSql("SERVICE_APPLY_SELECT_BY_ID");
		List<OrderApply> list = this.baseDaoSupport.queryForList(sql, OrderApply.class, applyId);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int countAmountByServiceType(String serviceType,String orderId) {
		String sql = SF.orderSql("SERVICE_APPLY_COUNT");
		int c = this.baseDaoSupport.queryForInt(sql, OrderStatus.ORDER_APPLY_STATUS_1,serviceType,orderId);
		return c;
	}
	
	@Override
	public List<OrderApplyItem> queryApplyItems(String appli_id,
			String item_type) {
		String sql = SF.orderSql("QUERY_ORDER_APPLY_ITEMS");
		String [] args = new String[]{appli_id};
		if(!StringUtils.isEmpty(item_type)){
			sql += " and t.item_type=?";
			args = new String[]{appli_id,item_type};
		}
		return this.baseDaoSupport.queryForList(sql, OrderApplyItem.class, args);
	}
	
	@Override
	public List<OrderApply> queryApplyByOrderId(String order_id){
		String sql = SF.orderSql("QueryApplyByOrderId");
		return this.baseDaoSupport.queryForList(sql, OrderApply.class, order_id);
	}

}

package com.ztesoft.net.mall.core.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.drools.core.util.StringUtils;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.OrderApply;
import com.ztesoft.net.mall.core.model.OrderApplyItem;
import com.ztesoft.net.mall.core.model.OrderRel;
import com.ztesoft.net.mall.core.service.IOrderApplyManage;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

public class OrderApplyManage extends BaseSupport implements IOrderApplyManage {

	@Override
	public String  addOrderApply(OrderApply orderApply) {
		boolean result=false;
		this.baseDaoSupport.insert("order_apply", orderApply);
		return orderApply.getOrder_apply_id();
	}

	@Override
	public Page listOrderApply(String service_type,String order_apply_id, String a_order_item_id,
			int pageNo, int pageSize) {
		String sql= SF.orderSql("SERVICE_APPLY_SELECT_BY_COND");
		if(!StringUtil.isEmpty(order_apply_id)){
			sql+=" and a.order_apply_id='"+order_apply_id+"'";
		}
		if (!StringUtil.isEmpty(a_order_item_id)) {
			sql+=" and a.a_order_item_id='"+a_order_item_id+"'";
		}
		sql+="order by create_time desc";
		Page page=this.baseDaoSupport.queryForPage(sql, pageNo, pageSize,service_type);
		return page;
	}
	
	@Override
	public OrderApply getOrderApply(String order_apply_id){
		OrderApply orderApply=(OrderApply)this.baseDaoSupport.queryForObject(SF.orderSql("SERVICE_APPLY_SELECT_BY_ID_2"), OrderApply.class, order_apply_id);
		return orderApply;
	}
	
	@Override
	public void updateOrderApply(OrderApply orderApply){
		this.baseDaoSupport.update("order_apply", orderApply, "order_apply_id="+orderApply.getOrder_apply_id());
	}
	
	@Override
	public OrderApply findOrderApplyByItemId(String item_id){
		OrderApply orderApply=null;
		String sql=SF.orderSql("SERVICE_APPLY_SELECT_BY_ITEM");
		List<OrderApply> list=this.baseDaoSupport.queryForList(sql, OrderApply.class, item_id);
		if (list!=null&&list.size()>0) {
			orderApply=list.get(0);
		}
		return orderApply;
	}
	
	@Override
	public Page findOrderApply(int pageNo,int pageSize,String service_type,String user_id){
		Page page=null;
		String sql=SF.orderSql("SERVICE_APPLY_SELECT_BY_USER");
		page=this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, service_type,user_id);
		return page;
	}
	
	@Override
	public int countOrderApply(String service_type,String user_id){
		int result=0;
		List list=null;
		String sql=SF.orderSql("SERVICE_APPLY_COUNT_BY_USER");
		list=this.baseDaoSupport.queryForList(sql, service_type,user_id);
		if (list!=null&&list.size()>0) {
			result=list.size();
		}
		return result;
	}

	@Override
	public int sumReturnedItems(String item_id) {
		String sql = SF.orderSql("SERVICE_RETURNED_ITEM_SUM");
		return this.baseDaoSupport.queryForInt(sql, item_id);
	}

	@Override
	public void saveOrderApplyItem(OrderApplyItem item) {
		this.baseDaoSupport.insert("es_order_apply_item", item);
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
	public Page queryOrderApplyByType(String serviceType, String apply_id,
			String order_id,String member_id, int pageNo, int pageSize) {
		String sql = SF.orderSql("QUERY_ORDER_APPLY_PAGE");
		List params = new ArrayList();
		params.add(serviceType);
		if(!StringUtils.isEmpty(apply_id)){
			sql += " and a.order_apply_id=? ";
			params.add(apply_id);
		}
		if(!StringUtils.isEmpty(order_id)){
			sql += " and er.a_order_id=? ";
			params.add(order_id);
		}
		if(!StringUtils.isEmpty(member_id)){
			sql += " and a.member_id=? ";
			params.add(member_id);
		}
		sql += " and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
		String countSql = "select count(*) from ("+sql+") cc";
		sql += " order by a.create_time desc";
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, OrderApply.class, countSql, params.toArray());
	}

	@Override
	public void insertOrderApplyRel(OrderRel rel) {
		this.baseDaoSupport.insert("es_order_rel", rel);
	}

	@Override
	public List<OrderRel> queryOrderRelByApplyId(String apply_id,
			String service_type) {
		String sql = SF.orderSql("QUERY_ORDER_REL_BY_APPLY_ID");
		String [] args = new String[]{apply_id};
		if(!StringUtils.isEmpty(service_type)){
			sql += " and t.rel_type=?";
			args = new String[]{apply_id,service_type};
		}
		return this.baseDaoSupport.queryForList(sql, OrderRel.class, args);
	}

	@Override
	public void delApplyItems(String apply_id) {
		String sql = SF.orderSql("DELETE_ORDER_APPLY_ITEMS");
		this.baseDaoSupport.execute(sql,apply_id);
	}

	@Override
	public void delApplyOrderRel(String apply_id) {
		String sql = SF.orderSql("DELETE_ORDER_APPLY_ORDER_REL");
		this.baseDaoSupport.execute(sql,apply_id);
	}

	@Override
	public boolean orderIsOnApply(String order_id) {
		String sql = SF.orderSql("COUNT_ODER_APPLY_NUM");
		return this.baseDaoSupport.queryForInt(sql, order_id)>0;
	}

	@Override
	public void updateOrderApplyStatus(String apply_id, String status) {
		String sql = SF.orderSql("UPDATE_ORDER_APPLY_STATUS");
		this.baseDaoSupport.execute(sql, status,apply_id);
	}

	@Override
	public long countOrderApplyByTypeAndStatus(String service_type,String apply_status){
		String sql = SF.orderSql("CountOrderApplyByTypeAndStatus");
		List param = new ArrayList();
		param.add(service_type);
		if(!StringUtil.isEmpty(apply_status)){
			sql += " and t.apply_state=? ";
			param.add(apply_status);
		}
		return this.baseDaoSupport.queryForLong(sql, param.toArray());
	}
}

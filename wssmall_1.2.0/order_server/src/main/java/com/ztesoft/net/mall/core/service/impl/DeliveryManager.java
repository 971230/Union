package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.service.IDeliveryManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;

public class DeliveryManager extends BaseSupport implements IDeliveryManager {

	@Override
	public void add(Delivery delivery) {
		this.baseDaoSupport.insert("ES_DELIVERY", delivery);
	}
	
	@Override
	public void addDelivery(OrderDeliveryBusiRequest delivery){
		this.baseDaoSupport.insert("ES_DELIVERY", delivery);
	}

	@Override
	public Page qryDelivery(String create_type,String delivery_no,String type,String order_id,int pageNo,int pageSize,String audit_status,String name,String delivery_id){
		String sql = SF.orderSql("SERVICE_DELIVERY_SELECT");
		String countSql = SF.orderSql("SERVICE_DELIVERY_COUNT");
		List params = new ArrayList();
		if(!StringUtil.isEmpty(create_type)){
			sql += " and o.create_type=?";
			countSql += " and o.create_type=?";
			params.add(create_type);
		}
		if(!StringUtil.isEmpty(delivery_no)){
			sql += " and t.logi_no=?";
			countSql += " and t.logi_no=?";
			params.add(delivery_no);
		}
		if(!StringUtil.isEmpty(type)){
			sql += " and t.type=?";
			countSql += " and t.type=?";
			params.add(type);
		}
		if(!StringUtil.isEmpty(order_id)){
			sql += " and t.order_id=?";
			countSql += " and t.order_id=?";
			params.add(order_id);
		}
		if(!StringUtil.isEmpty(audit_status)){
			sql += " and a.AUDIT_STATUS=?";
			countSql += " and a.AUDIT_STATUS=?";
			params.add(audit_status);
		}
		if(!StringUtil.isEmpty(name)){
			sql += " and upper(a.pru_order_name) like '%"+name.trim().toUpperCase()+"%'";
			countSql += " and upper(a.pru_order_name) like '%"+name.trim().toUpperCase()+"%'";
		}
		if(!StringUtil.isEmpty(delivery_id)){
			sql += " and t.delivery_id=?";
			countSql += " and t.delivery_id=?";
			params.add(delivery_id);
		}
		sql += " order by a.create_time desc";
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, Delivery.class, countSql, params.toArray());
	}

	@Override
	public Delivery get(String delivery_id) {
		String sql = SF.orderSql("SERVICE_DELIVERY_SELECT_BY_ID");
		return (Delivery) this.baseDaoSupport.queryForObject(sql, Delivery.class, delivery_id);
	}
	
	@Override
	/**
	 * 查询ship_status=-1的数据
	 */
	public Delivery getByOrderId(String order_id){
		String sql = SF.orderSql("SERVICE_DELIVERY_SELECT_BY_STATUE");
		return (Delivery) this.baseDaoSupport.queryForObject(sql, Delivery.class, order_id);
	}
	
	@Override
	public Delivery getByOrderId(String order_id,String delivery_type){
		String sql = SF.orderSql("SERVICE_DELIVERY_SELECT_BY_STATUE");
		if(OrderStatus.DELIVERY_TYPE_0.equals(delivery_type)){
			sql += " and (delivery_type=? or delivery_type is null)";
		}else{
			sql += " and delivery_type=? ";
		}
		return (Delivery) this.baseDaoSupport.queryForObject(sql, Delivery.class, order_id,delivery_type);
	}

	@Override
	public List<DeliveryItem> qryDeliveryItems(String delivery_id) {
		String sql = SF.orderSql("SERVICE_DELIVERY_ITEM_SELECT");
		return this.baseDaoSupport.queryForList(sql, DeliveryItem.class, delivery_id,ManagerUtils.getSourceFrom());
	}

	@Override
	public List<Map> qryDeliveryItemsMap(String delivery_id) {
		String sql = SF.orderSql("SERVICE_DELIVERY_ITEM_GOODS_SELECT");
		return this.baseDaoSupport.queryForList(sql, delivery_id);
	}
	
	@Override
	public void addDeliveryItem(DeliveryItem item){
		this.baseDaoSupport.insert("es_delivery_item", item);
	}
	
	@Override
	public void updateShipStatus(String delivery_id,int status){
		String sql = SF.orderSql("UPDATE_DELIVERY_STATUS");
		this.baseDaoSupport.execute(sql, status,delivery_id);
	}
	
	@Override
	public void addDeliveryItem(OrderDeliveryItemBusiRequest item){
		this.baseDaoSupport.insert("es_delivery_item", item);
	}

	/**
	 * 已删除 条件 and d.delivery_type=0
	 */
	@Override
	public List<OrderDeliveryItemBusiRequest> queryDeliveryItems(String order_id,int item_type){
		String sql = SF.orderSql("QUERY_DELIVERY_ITEMS");
		List param = new ArrayList();
		param.add(order_id);
		if(-1!=item_type){
			sql += " and i.itemtype=? ";
			param.add(item_type);
		}
		if(item_type==OrderStatus.DELIVERY_ITEM_TYPE_3){
			sql += " and (i.delivery_id is null or exists(select 1 from es_delivery d where d.delivery_id=i.delivery_id and d.source_from=i.source_from )) and i.source_from=? ";
			//param.add("0");
			param.add(ManagerUtils.getSourceFrom());
		}
		return this.baseDaoSupport.queryForList(sql, OrderDeliveryItemBusiRequest.class, param.toArray());
	}
	
	@Override
	public void updateReissueDeliveryItemId(String delivery_id,String order_id){
		String sql = SF.orderSql("UpdateReissueDeliveryItemId");
		this.baseDaoSupport.execute(sql, delivery_id,order_id);
	}
	
	@Override
	public void updateDItemDeliveryId(String item_id,String delivery_id){
		String sql = SF.orderSql("UpdateDItemDeliveryId");
		this.baseDaoSupport.execute(sql, delivery_id,item_id);
	}
	
	@Override
	public void delDeliveryItem(String item_id){
		String sql = SF.orderSql("DelDeliveryItem");
		this.baseDaoSupport.execute(sql, item_id);
	}
	
}

/**
 * 
 */
package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;
import zte.params.order.resp.DeliveryItemsQueryResp;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IOrderSupplyManager;
import com.ztesoft.net.sqls.SF;

/**
 * @author ZX 
 * OrderSupplyManager.java
 * 订单补寄和重发
 *  2014-10-30
 */
public class OrderSupplyManager extends BaseSupport implements
		IOrderSupplyManager {

	/**
	 * 订单补寄和重发
	 * 
	 * @param order_id
	 * @return
	 */
	public List<OrderDeliveryBusiRequest> order_supply(String order_id,boolean isHis) {
		String delivery_table="ES_DELIVERY";
		if(isHis){
			delivery_table=delivery_table+EcsOrderConsts.HIS_TABLE_STR;
		}
		List<OrderDeliveryBusiRequest> deliveryLs = new ArrayList<OrderDeliveryBusiRequest>();

		StringBuilder sql = new StringBuilder(
				"SELECT T.* FROM "+delivery_table+" T");
		sql.append(" WHERE 1=1 AND T.ORDER_ID='" + order_id + "' and t.delivery_type<>'0' order by t.create_time ");
		List<Map> lsMp = baseDaoSupport.queryForList(sql.toString());
		if (lsMp != null && lsMp.size() > 0) {
			OrderDeliveryBusiRequest delivery = null;
			for (Map mp : lsMp) {
				delivery = new OrderDeliveryBusiRequest();
				delivery.setShipping_time(mp.get("shipping_time") != null ? mp
						.get("shipping_time").toString() : "");
				delivery.setShip_status((mp.get("ship_status") != null&&!mp.get("ship_status").equals("")) ? Integer
						.parseInt(mp.get("ship_status").toString()) : 0);
				delivery.setDelivery_type(mp.get("delivery_type") != null ? mp.get(
						"delivery_type").toString() : "");
				delivery.setLogi_no(mp.get("logi_no") != null ? mp.get(
						"logi_no").toString() : "");
				delivery.setDelivery_id(mp.get("delivery_id") != null ? mp.get(
						"delivery_id").toString() : "");
				delivery.setShip_type(mp.get("ship_type") != null ? mp.get(
						"ship_type").toString() : "");
				delivery.setN_shipping_amount(mp.get("n_shipping_amount") != null ? mp.get(
						"n_shipping_amount").toString() : "");
				delivery.setShipping_company(mp.get("shipping_company") != null ? mp.get(
						"shipping_company").toString() : "");
				delivery.setCreate_time(mp.get("create_time") != null ? mp.get(
						"create_time").toString() : "");
				deliveryLs.add(delivery);
			}
		}
		return deliveryLs;
	}

	@Override
	public String getSequences(String seqName) {
		 return daoSupport.getSequences(seqName, "1", 18);
	}

	
	public void updateDeliveryStatus(String delivery_id,boolean isHis) throws Exception{
		String delivery_table="ES_DELIVERY";
		String delivery_items_table="ES_DELIVERY_ITEM";
		if(isHis){
			delivery_table=delivery_table+EcsOrderConsts.HIS_TABLE_STR;
			delivery_items_table=delivery_items_table+EcsOrderConsts.HIS_TABLE_STR;
		}
		String shipping_time = DateUtil.getTime2();
		StringBuilder sql = new StringBuilder(
				"update "+delivery_table+" a set a.ship_status=? ,a.CREATE_TIME=?  where a.delivery_id=?");
		baseDaoSupport.execute(sql.toString(), OrderStatus.DELIVERY_SHIP_SATUS_1,new Date(),delivery_id);
		
		String SQLItem="update "+delivery_items_table+" a set a.col1=? where a.delivery_id=?";
		baseDaoSupport.execute(SQLItem, EcsOrderConsts.ITEM_PRINT_STATUS_2,delivery_id);
		
	}
	/**
	 * 插入物流表并更新items表
	 * isHis -true 操作历史表
	 */
	public void addReissueGoodsShippingDelivery(OrderDeliveryBusiRequest delivery,String [] deliveri_item_idArray,boolean isHis){
		String delivery_table="ES_DELIVERY";
		String delivery_items_table="ES_DELIVERY_ITEM";
		String delivery_id = delivery.getDelivery_id();
		if(isHis){
			delivery_table=delivery_table+EcsOrderConsts.HIS_TABLE_STR;
			delivery_items_table=delivery_items_table+EcsOrderConsts.HIS_TABLE_STR;
		}
		this.baseDaoSupport.insert(delivery_table, delivery);
		//items
		if(deliveri_item_idArray!=null){
			String sql="update "+delivery_items_table+" d set d.delivery_id=? where d.item_id=?";
			for(String item_id:deliveri_item_idArray){
				this.baseDaoSupport.execute(sql, delivery_id,item_id);
			}
		}
		
	}
	
	/**
	 * 根据order_id查询es_delivery_item或es_delivery_item_his表的数据
	 * @param order_id
	 * @param isHis   -true 历史表
	 */
	public List<OrderDeliveryItemBusiRequest> queryDeliveryItems(String order_id,boolean isHis ){
		String delivery_table="ES_DELIVERY";
		String delivery_items_table="ES_DELIVERY_ITEM";
		if(isHis){
			delivery_table=delivery_table+EcsOrderConsts.HIS_TABLE_STR;
			delivery_items_table=delivery_items_table+EcsOrderConsts.HIS_TABLE_STR;
		}
		String sql = "select a.* from "+delivery_items_table+" a where  "+
				" exists (select 1 from "+delivery_table+" d where d.delivery_id=a.delivery_id and d.order_id=? and d.source_from=? ) ";
		List param = new ArrayList();
		param.add(order_id);
		param.add(ManagerUtils.getSourceFrom());
		
		return this.baseDaoSupport.queryForList(sql, OrderDeliveryItemBusiRequest.class, param.toArray());
	}
	
	
	/**
	 * 新增物流子表es_delivery_item记录
	 * @param item
	 * @param isHis  -true 历史表
	 */
	public OrderDeliveryItemBusiRequest addDeliveryItem(OrderDeliveryItemBusiRequest item,boolean isHis){
		String delivery_items_table="ES_DELIVERY_ITEM";
		if(isHis){
			delivery_items_table=delivery_items_table+EcsOrderConsts.HIS_TABLE_STR;
		}
		item.setItem_id(this.baseDaoSupport.getSequences("s_es_delivery_item"));
		this.baseDaoSupport.insert(delivery_items_table, item);
		return item;
	}
	
	
	/**
	 * 根据delivery_id查询es_delivery_item或es_delivery_item_his表的数据
	 * @param delivery_id
	 * @param isHis   -true 历史表
	 */
	public List<OrderDeliveryItemBusiRequest> queryDeliveryItemsByDeId(String delivery_id,boolean isHis ){
		String delivery_items_table="ES_DELIVERY_ITEM";
		if(isHis){
			delivery_items_table=delivery_items_table+EcsOrderConsts.HIS_TABLE_STR;
		}
		String sql = "select t.* from "+delivery_items_table+" t where delivery_id=? and t.source_from=?";
		return this.baseDaoSupport.queryForList(sql, OrderDeliveryItemBusiRequest.class, delivery_id,ManagerUtils.getSourceFrom());
	}
	
	/**
	 * 根据id删除历史表es_delivery_item_his数据
	 * @param item_id
	 */
	public void delDeliveryItemHis(String item_id){
		String sql = "delete from es_delivery_item_his t where t.item_id=?";
		this.baseDaoSupport.execute(sql, item_id);
	}

	/**
	 * * 取费用表的序列，临时为iphone6s预售版本提供
	 */
	public String getSequences() {
		return daoSupport.getSequences("SEQ_ATTR_FEE_INFO", "1", 18);
	}
	
	
	/**
	 * 拷贝预售单的转兑包到正式单上，临时为iphone6s预售版本提供
	 */
	public void copyzhuanduibao(String ys_out_order_id,String zs_out_order_id,String ys_order_id,String zs_order_id){
		String sql = "SELECT t.* FROM es_gd_zhuanduibao t WHERE t.order_id =?" ;
		List<Map> ys_map = baseDaoSupport.queryForList(sql,ys_order_id);
		for(Map zs_map : ys_map ){
			zs_map.put("out_tid", zs_out_order_id);
			zs_map.put("order_id", zs_order_id);
			baseDaoSupport.insert("es_gd_zhuanduibao", zs_map);
		}
	}
	
}

package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryItem;

/**
 * 物流manager
 * @作者 MoChunrun
 * @创建日期 2013-11-11 
 * @版本 V 1.0
 */
public interface IDeliveryManager {

	/**
	 * 添加
	 * @作者 MoChunrun
	 * @创建日期 2013-11-11 
	 * @param delivery
	 */
	public void add(Delivery delivery);
	
	public Page qryDelivery(String create_type,String delivery_no,String type,String order_id,int pageNo,int pageSize,String audit_status,String name,String delivery_id);
	
	public Delivery get(String delivery_id);
	
	public Delivery getByOrderId(String order_id);
	public Delivery getByOrderId(String order_id,String delivery_type);
	
	public List<DeliveryItem> qryDeliveryItems(String delivery_id);
	
	public List<Map> qryDeliveryItemsMap(String delivery_id);
	
	public void addDeliveryItem(DeliveryItem item);
	
	/**
	 * 修改物流状态
	 * @作者 MoChunrun
	 * @创建日期 2014-10-28 
	 * @param delivery_id
	 * @param status
	 */
	public void updateShipStatus(String delivery_id,int status);
	
	/**
	 * 添加物流子单
	 * @作者 MoChunrun
	 * @创建日期 2014-10-28 
	 * @param item
	 */
	public void addDeliveryItem(OrderDeliveryItemBusiRequest item);
	
	/**
	 * 查询物单子单
	 * @作者 MoChunrun
	 * @创建日期 2014-10-28 
	 * @param order_id
	 * @param item_type
	 * @return
	 */
	public List<OrderDeliveryItemBusiRequest> queryDeliveryItems(String order_id,int item_type);
	
	/**
	 * 修改补发商品物子物流单ID
	 * @作者 MoChunrun
	 * @创建日期 2014-10-27 
	 * @param delivery_id
	 * @param order_id
	 */
	public void updateReissueDeliveryItemId(String delivery_id,String order_id);
	
	/**
	 * 添加物流信息
	 * @作者 MoChunrun
	 * @创建日期 2014-10-28 
	 * @param delivery
	 */
	public void addDelivery(OrderDeliveryBusiRequest delivery);
	
	/**
	 * 修改物流子单物流ID
	 * @作者 MoChunrun
	 * @创建日期 2014-10-28 
	 * @param item_id
	 * @param delivery_id
	 */
	public void updateDItemDeliveryId(String item_id,String delivery_id);
	
	public void delDeliveryItem(String item_id);
}

/**
 * 
 */
package com.ztesoft.net.service;

import java.util.List;

import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;
import zte.params.order.resp.DeliveryItemsQueryResp;

/**
 * @author ZX
 * IOrderSupplyManager.java
 * 订单补寄和重发
 * 2014-10-30
 */
public interface IOrderSupplyManager {


	/**
	 * 根据order_id查询物流表es_delivery、es_delivery_his中非正常发货的数据
	 * @param order_id
	 * @param isHis  true查询历史表
	 * @return
	 */
	List<OrderDeliveryBusiRequest> order_supply(String order_id,boolean isHis);
	
	/**
	 * 新增物流记录到es_delivery和es_delivery_item
	 * @param delivery
	 * @param deliveri_item_idArray
	 * @param isHis -true 历史表
	 * @return
	 */
	public void addReissueGoodsShippingDelivery(OrderDeliveryBusiRequest delivery,String [] deliveri_item_idArray,boolean isHis);
	
	/**
	 * 获取序列值
	 * @param seqName
	 * @return
	 */
	String getSequences(String seqName);
	
	/**
	 * 根据delivery_id更新物流表、物流子表的发货状态
	 * @param delivery_id
	 * @param isHis -true 历史表
	 * @throws Exception
	 */
	void updateDeliveryStatus(String delivery_id,boolean isHis)throws Exception;
	
	/**
	 * 根据order_id查询es_delivery_item或es_delivery_item_his表的数据
	 * @param order_id
	 * @param isHis   -true 历史表
	 */
	public List<OrderDeliveryItemBusiRequest> queryDeliveryItems(String order_id,boolean isHis);
	
	
	/**
	 * 新增物流子表es_delivery_item记录
	 * @param item
	 * @param isHis  -true 历史表
	 */
	public OrderDeliveryItemBusiRequest addDeliveryItem(OrderDeliveryItemBusiRequest item,boolean isHis);
	/**
	 * 根据delivery_id查询es_delivery_item或es_delivery_item_his表的数据
	 * @param delivery_id
	 * @param isHis   -true 历史表
	 */
	public List<OrderDeliveryItemBusiRequest> queryDeliveryItemsByDeId(String delivery_id,boolean isHis );
	/**
	 * 根据id删除历史表es_delivery_item_his数据
	 * @param item_id
	 */
	public void delDeliveryItemHis(String item_id);
	
	/**
	 * 取费用表的序列，临时为iphone6s预售版本提供
	 */
	public String getSequences();
	
	/**
	 * 拷贝预售单的转兑包到正式单上，临时为iphone6s预售版本提供
	 */
	public void copyzhuanduibao(String ys_out_order_id,String zs_out_order_id,String ys_order_id,String zs_order_id);
}

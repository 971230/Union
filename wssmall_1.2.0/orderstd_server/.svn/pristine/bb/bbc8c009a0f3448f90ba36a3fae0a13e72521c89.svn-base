package com.ztesoft.orderstd.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.model.AttrInst;

import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;

public interface IAttrDefSManager {

	//public void insertTable(String tableName,Map<String,String> keyValues)throws Exception;
	public void updateOrderStatus(int status,String order_id);
	public List<AttrInstBusiRequest> insertAttrTable(String attr_space_type,String attr_sub_space_type, Map values,Map extMap,String order_id,String inst_id,String goods_id,String pro_goods_id,String goods_pro_id,String product_pro_id,int index)throws Exception;
	public String getGoodsTypeCode(String goods_id);
	public List<AttrInst> getOuterAttrInst(OrderOuter orderOuter) ;
	public OrderOuter getOrderOuterByGoodsId(String product_id ,List<OrderOuter> orderOuters);
	public Order getOrder(String orderId);
}

package com.ztesoft.orderstd.service;

import java.util.List;
import java.util.Map;

import params.order.req.OrderSyReq;
import params.order.resp.OrderSyResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderOuter;

public interface IOrderSManager {

	public String getOrderSysCode(String order_id);
	public Order add(OrderOuter cp,Member member,Order order,String sessionid,String staff_no);
	public List<Map<String,String>> qryStaffNoBySessionID(String sessionID);
	public List<Map<String,String>> qryGoodsByStaffNo(String staff_no);
	public boolean isCreateOrder(String service_code, String goods_ids);
	public OrderSyResp perform(OrderSyReq syReq) throws ApiBusiException;
}

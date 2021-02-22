package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.mall.core.model.DlyCenter;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.service.AbsPrintTmplData;
import com.ztesoft.net.mall.core.service.IDlyCenterManager;
import com.ztesoft.net.mall.core.service.IOrderManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单数据解析类
 * @作者 MoChunrun
 * @创建日期 2013-11-7 
 * @版本 V 1.0
 */
public class OrderPrintData extends AbsPrintTmplData {

	private static final DateFormat YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
	private IDlyCenterManager dlyCenterManager;
	private IOrderManager orderManager;
	
	@Override
	public Map<String, String> perform(Map<String,String> data) {
		Order order = this.orderManager.get(data.get("orderId")); // 订单信息
		order.setShip_addr(data.get("ship_addr"));
		order.setShip_name(data.get("ship_name"));
		order.setShipping_area(data.get("shipping_area"));
		order.setShip_zip(data.get("ship_zip"));
		order.setShip_mobile(data.get("ship_mobile"));
		order.setShip_tel(data.get("ship_tel"));
		order.setRemark(data.get("remark"));
		DlyCenter dlyCenter = dlyCenterManager.get(data.get("dly_center_id"));
		Map<String, String> result = new HashMap<String,String>();
		result.put("ship_name",order.getShip_name());
		result.put("ship_area",order.getShipping_area());
		result.put("ship_addr",order.getShip_addr());
		result.put("ship_tel",order.getShip_tel());
		result.put("ship_mobile",order.getShip_mobile());
		result.put("ship_zip",order.getShip_zip());
		result.put("dly_name",dlyCenter.getName());
		result.put("dly_area",dlyCenter.getProvince()+"-"+dlyCenter.getCity()+"-"+dlyCenter.getRegion());
		result.put("dly_address",dlyCenter.getAddress());
		result.put("dly_tel",dlyCenter.getPhone());
		result.put("dly_mobile",dlyCenter.getCellphone());
		result.put("dly_zip",dlyCenter.getZip());
		String now = YYYYMMDD.format(new Date());
		String [] nows = now.split("-");
		result.put("date_y",nows[0]);
		result.put("date_m",nows[1]);
		result.put("date_d",nows[2]);
		result.put("order_id",order.getOrder_id());
		result.put("order_price",order.getOrder_amount()+"");
		result.put("ship_price",order.getShipping_amount()+"");
		result.put("order_weight",order.getWeight()+"");
		result.put("order_count",order.getGoods_amount()+"");
		result.put("order_memo",order.getRemark());
		result.put("ship_time",order.getShip_time());
		//result.put("shop_name",);
		result.put("text",data.get("txt")==null?"":data.get("txt"));
		return result;
	}

	public IDlyCenterManager getDlyCenterManager() {
		return dlyCenterManager;
	}

	public void setDlyCenterManager(IDlyCenterManager dlyCenterManager) {
		this.dlyCenterManager = dlyCenterManager;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

}

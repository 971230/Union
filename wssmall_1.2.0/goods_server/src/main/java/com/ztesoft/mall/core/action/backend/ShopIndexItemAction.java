package com.ztesoft.mall.core.action.backend;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.service.IGoodsManager;

/**
 * 后台首页显示项
 * @author kingapex
 * 2010-10-12上午10:18:15
 */
public class ShopIndexItemAction extends WWAction {
	
	private IGoodsManager goodsManager;
	private Map orderss; //订单统计信息
	private Map goodsss ;//商品统计信息
	
	//private OrderInf orderServ;
	
	//订单统计信息
	public String order(){
		this.orderss  = new HashMap();//orderServ.censusState(new OrderReq()).getUserIndexInfo();
		return "order";
	}
	
	
	//商品统计信息
	public String goods(){
		this.goodsss = this.goodsManager.census();
		return "goods";
	}

	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}


	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}


	public Map getOrderss() {
		return orderss;
	}


	public void setOrderss(Map orderss) {
		this.orderss = orderss;
	}


	public Map getGoodsss() {
		return goodsss;
	}


	public void setGoodsss(Map goodsss) {
		this.goodsss = goodsss;
	}






	
}

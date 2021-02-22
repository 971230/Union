package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.Product;

public interface IOrderGuijiManager {

	public Page searchOrderCoQueue(Map params, int pageNo, int pageSize);
	
	public Page listOrderSynService(Map params, int pageNo, int pageSize);
	
	public String doGuiji(Map params);
	
	public Map getDataMoveLog();
	
	public void insertGuijiCoQueue(List<CoQueue> queues);
	
	public void insertOrderOuter(List orders);
	
	public void insertOrderData(Goods goods,Product product,List<Map> relations,List<Map> packages,
			List<Goods> products,List<Product> productList,List<Map> pmtGoods,List<Map> pmts,List<Map> pmtActs);
	
	public void stopOrderSyn(Map params);
	
	public void startOrderSyn(Map params);
	
	public String getOrderGoodsId(Map params);
}

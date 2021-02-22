package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.Product;

public interface IGProdCoQueueManager {

	/**
	 * 定时任务扫描
	 * 只取状态为未发送【WFS】和响应失败【XYSB】（失败次数小于4）
	 * @param service_code
	 * @param max_count
	 * @return
	 */
	public List<CoQueue> getForJob(String service_code, int max_count);
	
	
	public List<CoQueue> listCoQueue(String service_code, String last_move_time);
	
	/**
	 * 上锁（扫描后需要更新状态为发送中【FSZ】）
	 * @param co_id
	 */
	public void lock(String co_id);
	
	public List listOrderOuter(String last_move_time);
	
	public OrderOuter getOrderOuter(Map params);
	
	public CoQueue getGuijiCoQueue(String tab_name, String object_id, String service_code);
	
	public Goods getGoods(String goods_id);
	
	public Product getProduct(String goods_id);
	
	public List<Map> getGoodsRelations(String a_goods_id);
	
	public List<Goods> getProducts(String goods_id);
	
	public List<Product> getProductList(String goods_id);
	
	public List<Map> getGoodsPackage(String goods_id);
	
	public List<Map> getPmtGoods(String goods_id);
	
	public List<Map> getPromotions(List<Map> pmts);
	
	public List<Map> getPromotionActivities(List<Map> pmts);
	
	public void moveBlobData(List list, String table_name);
}

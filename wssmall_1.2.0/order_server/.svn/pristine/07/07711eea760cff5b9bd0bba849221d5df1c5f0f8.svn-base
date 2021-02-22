package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.GoodsUsage;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.Product;

/**
 * 订单工具
 * 
 * @author wui
 * 
 */
public interface IOrderUtils {
	public String getTypeCodeByOrderId(String orderId);
	
	
	public GoodsType qryGoodsTypeByGoodsId(String goods_id);
	
	//获取商品
	public Goods getGoodsId(String goods_id);
	
	//获取产品
	public Product getProductByGoodsId(String goods_id);
	
	
	public OrderItem getOrderItemByOrderId(String orderId);
	
	public Goods getGoodsByOrderId(String orderId);
	
	
	public void insertGoodsUsage(GoodsUsage goodsUsage);
	
	public List searchGoodsUsageByUserGoodsId(String user_id,String goods_id);
	
	public AdminUser getAdminUserById(String userid);
	
	
	
	public List queryRechargeSuccForBill(String orderseq);
	
	public String getAdminUserByUserId(String userid);
	
	public String getGoodsUsageState(String goodsId,String userid);
	

	//根据流水号获取订单
	public Order getOrderByTrnsId(String transaction_id);
	
	
	
	@SuppressWarnings("unchecked")
	public Map getStaffByStaffCodeAndLanId(String staff_code,String lan_id);
	
	//获取市场价格
	public Double getMarketPrice(Order order);
	
	
	public Map getOrgByStaffIdAndLanid(String staff_code,String lan_id);
	
	public Goods getGoodsBySqlId(String goods_id);
	
	public void updateAccIntervalState(String order_id,String col1_state);
	
	/**
	 * 判断是否生成订单
	 */
	public List queryOrderByTransaction_id(String orderseq) ;
	
}

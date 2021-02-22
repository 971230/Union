package com.ztesoft.net.mall.plugin.standard.cart;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ztesoft.net.cache.common.GoodsNetCacheRead;
import com.ztesoft.net.cache.common.GoodsNetCacheWrite;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Cart;
import com.ztesoft.net.mall.core.model.support.CartItem;
import com.ztesoft.net.mall.core.plugin.cart.ICartAddEvent;
import com.ztesoft.net.mall.core.plugin.cart.ICartItemFilter;
import com.ztesoft.net.mall.core.service.ILimitBuyManager;
import com.ztesoft.net.mall.core.service.IProductManager;


public class LimitBuyCartPlugin extends AutoRegisterPlugin implements
		ICartItemFilter,ICartAddEvent{
	
	private ILimitBuyManager limitBuyManager;
	private IProductManager productManager;
	@Resource
	GoodsNetCacheWrite goodsNetCacheWrite;
	@Resource
	GoodsNetCacheRead goodsNetCacheRead;
	@Override
	public void register() {
		

	}

	@Override
	public void filter(List<CartItem> itemlist, String sessionid) {
		
		//add by wui 加入购物车前判断是否已达到最大数量
		//下订单过滤处理
		List<Map> lmtList  = limitBuyManager.listEnableGoods();
		for(CartItem item: itemlist){
			if(StringUtil.isEmpty(item.getSpec_id()))
				return;
			if(!lmtList.isEmpty()){
				for(Map lmt:lmtList){
					String goodsid  = lmt.get("goods_id").toString();
					String cart_num  = lmt.get("cart_num").toString();
					if( item.getGoods_id().equals(goodsid)){
						double price  = Double.valueOf( lmt.get("limitprice").toString() );
						item.setCoupPrice( price);
						item.setItemtype(OrderStatus.ITEM_TYPE_4);
						item.setSpec_id(lmt.get("limitbuyid")+"");
					}
				}
			}
		}
	}

	@Override
	public void add(Cart item) { //加入购物车时调用
		//add by wui 加入购物车前判断是否已达到最大数量
		if(StringUtil.isEmpty(item.getSpec_id()))
			return;
		List<Map> lmtList  = limitBuyManager.listEnableGoods();
			if(!lmtList.isEmpty()){
				for(Map lmt:lmtList){
					String goodsid  = lmt.get("goods_id").toString();
					String cart_num  = lmt.get("cart_num").toString();
					String goods_id = productManager.get(item.getProduct_id()).getGoods_id();
					if(goods_id.equals(goodsid)){
						int cartBuyedLimitNum = goodsNetCacheRead.loadLimitBuy(goodsid);
						int cart_num_i = StringUtil.isEmpty(cart_num)?0:new Integer(cart_num);
						if(cartBuyedLimitNum>cart_num_i) //购物车数量大于限购购物车限制数量
							 continue;
						goodsNetCacheWrite.setLimitBuy(goodsid); //设置购物车数量+1
						double price  = Double.valueOf( lmt.get("limitprice").toString() );
						item.setPrice(price);
						item.setItemtype(OrderStatus.ITEM_TYPE_4);
						item.setSpec_id(lmt.get("limitbuyid")+"");
					}
				}
			}
	}
	
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	@Override
	public String getId() {
		
		return "limitBuyCartPlugin";
	}

	@Override
	public String getName() {
		
		return "限时购买购物车插件";
	}

	@Override
	public String getType() {
		
		return "goods";
	}

	@Override
	public String getVersion() {
		
		return "1.0";
	}

	@Override
	public void perform(Object... params) {
		

	}

	public ILimitBuyManager getLimitBuyManager() {
		return limitBuyManager;
	}

	public void setLimitBuyManager(ILimitBuyManager limitBuyManager) {
		this.limitBuyManager = limitBuyManager;
	}

	public GoodsNetCacheWrite getGoodsNetCacheWrite() {
		return goodsNetCacheWrite;
	}

	public void setGoodsNetCacheWrite(GoodsNetCacheWrite goodsNetCacheWrite) {
		this.goodsNetCacheWrite = goodsNetCacheWrite;
	}

	public GoodsNetCacheRead getGoodsNetCacheRead() {
		return goodsNetCacheRead;
	}

	public void setGoodsNetCacheRead(GoodsNetCacheRead goodsNetCacheRead) {
		this.goodsNetCacheRead = goodsNetCacheRead;
	}

	public IProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(IProductManager productManager) {
		this.productManager = productManager;
	}

}

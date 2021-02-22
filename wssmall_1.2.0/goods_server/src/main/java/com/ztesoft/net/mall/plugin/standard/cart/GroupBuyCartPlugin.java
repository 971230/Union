package com.ztesoft.net.mall.plugin.standard.cart;

import java.util.List;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Cart;
import com.ztesoft.net.mall.core.model.GroupBuy;
import com.ztesoft.net.mall.core.model.support.CartItem;
import com.ztesoft.net.mall.core.plugin.cart.ICartAddEvent;
import com.ztesoft.net.mall.core.plugin.cart.ICartDeleteEvent;
import com.ztesoft.net.mall.core.plugin.cart.ICartItemFilter;
import com.ztesoft.net.mall.core.service.IGroupBuyManager;
import com.ztesoft.net.mall.core.service.IProductManager;


/**
 * 团购购物车过滤器
 * @author kingapex
 *
 */
public class GroupBuyCartPlugin extends AutoRegisterPlugin implements
		ICartItemFilter,ICartAddEvent,ICartDeleteEvent {

	private IGroupBuyManager groupBuyManager;
	private IProductManager productManager;

	/**
	 * 
	 * 原则：团购、秒杀时下购物车订单按原价展示商品价格信息
	 */
	@Override
	public void filter(List<CartItem> itemlist, String sessionid) {
		
		//add by wui 未传入活动id则认为非团购活动
		List<GroupBuy> groupBuyList  = groupBuyManager.listEnable();
		for(CartItem item: itemlist){
			if(StringUtil.isEmpty(item.getSpec_id()))
				return;
			if(!groupBuyList.isEmpty()){
				for(GroupBuy groupbuy:groupBuyList){
					if(item.getGoods_id().equals(groupbuy.getGoodsid()) ){
						item.setCoupPrice( groupbuy.getPrice() );
						item.setItemtype(OrderStatus.ITEM_TYPE_3);
						item.setSpec_id(groupbuy.getGroupid());
					}
				}
		  }
		}
	}
	
	@Override
	public void add(Cart item) {
		if(StringUtil.isEmpty(item.getSpec_id()))
			return;
		List<GroupBuy> groupBuyList  = groupBuyManager.listEnable();
			if(!groupBuyList.isEmpty()){
				for(GroupBuy groupbuy:groupBuyList){
					String goods_id = productManager.get(item.getProduct_id()).getGoods_id();
					if(goods_id.equals(groupbuy.getGoodsid())){
						item.setItemtype(OrderStatus.ITEM_TYPE_3);
						item.setSpec_id(groupbuy.getGroupid());
						item.setPrice(groupbuy.getPrice());
					}
				}
		  }
	}

	@Override
	public void delete(String sessionid, Long cartid) {
		
		
//		List<GroupBuy> groupBuyList  = groupBuyManager.listEnable();
//		if(!groupBuyList.isEmpty()){
//			for(GroupBuy groupbuy:groupBuyList){
//				String goods_id = productManager.get(item.getProduct_id()).getGoods_id();
//				if(goods_id.equals(groupbuy.getGoodsid())){
//					item.setItemtype(OrderStatus.ITEM_TYPE_3);
//					item.setSpec_id(groupbuy.getGroupid());
//					item.setPrice(groupbuy.getPrice());
//				}
//			}
//	  }
	}
	
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	@Override
	public String getId() {
		
		return "groupBuyCartPlugin";
	}

	@Override
	public String getName() {
		
		return "团购购物车过滤器";
	}

	@Override
	public String getType() {
		
		return "cart";
	}

	@Override
	public String getVersion() {
		
		return "1.0";
	}

	@Override
	public void perform(Object... params) {
		

	}
	@Override
	public void register() {

	}

	public IGroupBuyManager getGroupBuyManager() {
		return groupBuyManager;
	}

	public void setGroupBuyManager(IGroupBuyManager groupBuyManager) {
		this.groupBuyManager = groupBuyManager;
	}

	public IProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(IProductManager productManager) {
		this.productManager = productManager;
	}



	
	
	

}

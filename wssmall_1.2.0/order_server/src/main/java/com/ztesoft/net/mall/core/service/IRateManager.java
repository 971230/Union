package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;
import com.ztesoft.net.mall.core.model.Card;
import com.ztesoft.net.mall.core.model.CardInfRequest;


/**
 *  
 * 
 * @author wui
 */
public interface IRateManager {
	
	public void transfer_card(OrderRequst orderRequst,OrderResult orderResult);
	
	
	//充值成功后更新订单id
	public void updateCard(Card card);
	
	//调用UVC接口充值
	public boolean billRecharge(CardInfRequest cardInfRequest);
	
	public Card getCardByUserId(String userid);
	
	
}

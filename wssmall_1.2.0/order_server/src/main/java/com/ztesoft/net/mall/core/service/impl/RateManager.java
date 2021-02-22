package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;
import com.ztesoft.net.mall.core.model.Card;
import com.ztesoft.net.mall.core.model.CardInfRequest;
import com.ztesoft.net.mall.core.model.CardRequest;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.service.IRateManager;
import com.ztesoft.net.sqls.SF;

/**
 * 流量卡处理类
 * @author wui
 */

public class RateManager extends   BaseSupport implements IRateManager {

	@Override
	public void transfer_card(OrderRequst orderRequst, OrderResult orderResult) {
		CardRequest cardRequest = orderRequst.getCardRequest();
		//TODO 调用流量卡生成规则
	}
	
	
	//根据用户id,获取接口地址
	@Override
	public Card getCardByUserId(String userid){
		String sql = SF.orderSql("SERVICE_CARD_SELECT");
		return (Card) this.baseDaoSupport.queryForObject(sql, Card.class, userid);
	}
	
	//充值成功后更新订单id
	@Override
	public void updateCard(Card card){
		this.baseDaoSupport.update("es_card_info", card, " card_id = '"+card.getCard_id()+"'");
	}
	
	//调用UVC接口充值
	@Override
	public boolean billRecharge(CardInfRequest cardInfRequest){
		//TODO 
		return true;
	}
	
	public Order isRecharge(String accNbr){
		String sql = "";
		return null;
	}
}

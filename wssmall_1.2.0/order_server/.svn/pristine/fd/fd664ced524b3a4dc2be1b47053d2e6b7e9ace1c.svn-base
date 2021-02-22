package com.ztesoft.net.mall.core.action.order.service.card;

import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.accept.CommonAcceptHander;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Card;
import com.ztesoft.net.mall.core.model.CardInfRequest;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

import java.util.HashMap;
import java.util.Map;

public  class CardAcceptHander extends CommonAcceptHander{

	@Override
	public void display() {
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		
		Order order = getOrder();
		if (isTaobaoAgent()) {
			//根据金额获取充值卡，充值,需要设置接口超时
			
			Card card = getOrderRequst().getUsedCard();
			
			//获取充值卡
			if(card == null)
				  card = cardServ.getCardNoTnsByUserIdAndMoney(getOrder().getUserid(), ManagerUtils.getIntegerVal(getOrder().getOrder_amount())+"",getOrder().getType_code());
			CardInfRequest cardInfo = getOrderRequst().getCardInfRequest();
			
			if(card !=null){
				//调用接口
				Map retMap = new HashMap();
				try{
					retMap = cardServ.uvcRecharge(card, cardInfo);
				}catch (Exception e) {
					retMap = null;
					e.printStackTrace();
				}
				//更新库表状态
				if(retMap ==null || retMap.get("code") == null || Consts.CODE_FAIL.equals(retMap.get("code"))) //充值失败
				{	
					//重置可用
					card.setState(Consts.CARD_INFO_STATE_4);
					cardServ.updateCard(card);
					String message ="";
					if(retMap !=null){
						message =(String)retMap.get("massage");
						if(!StringUtil.isEmpty(message) && message.length()>3000)
							message = message.substring(0,3000);
					}
					
					orderNFlowManager.acceptFail(order.getOrder_id(), "调用UVC充值失败:"+message);
					//通知门户库存不足，短息提醒
					getOrderResult().setCode(Consts.CODE_FAIL);
					getOrderResult().setMessage("调用UVC充值失败"+ message);
					
					setCanNext(false);
					
					
					
				}else if(Consts.CODE_SUCC.equals(retMap.get("code"))) //充值成功
				{
					
					card.setSec_order_id(getOrder().getOrder_id());
					card.setState(Consts.CARD_INFO_STATE_2);
					cardServ.updateCard(card);
					//orderNFlowManager.accept(order.getOrder_id());
					orderNFlowManager.accept_ship_auto(order.getOrder_id());
					
					//更新购买数量
					this.baseDaoSupport.execute(SF.orderSql("SERVICE_BUY_NUM_UPDATE"),1,order.getGoods_id());
					
					setCanNext(false);
					
				}
				
			}else{
				
				getOrderResult().setCode(Consts.CODE_FAIL);
				getOrderResult().setMessage("充值卡库存不足缺货");
				orderNFlowManager.acceptFail(order.getOrder_id(), "充值卡库存缺货");
				setCanNext(false);
			}
			
		}else{
				/*cardServ.transfer_card(getOrderRequst(),getOrderResult()); //充值卡调拨
				if(Consts.CODE_SUCC.equals(getOrderResult().getCode())){
					orderNFlowManager.accept(order.getOrder_id());
				}else if(Consts.CODE_FAIL.equals(getOrderResult().getCode())){
					orderNFlowManager.acceptFail(order.getOrder_id(), getOrderResult().getMessage());
					setCanNext(false);
					return;
				}*/
				
		}
	}

	
	@Override
	public void accept() {
		
	}

}

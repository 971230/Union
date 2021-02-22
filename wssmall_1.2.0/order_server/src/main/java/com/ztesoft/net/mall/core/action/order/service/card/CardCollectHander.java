package com.ztesoft.net.mall.core.action.order.service.card;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.collect.CommonCollectHander;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.sqls.SF;


public class CardCollectHander  extends CommonCollectHander {
	
	/**
	 * 订单采集
	 *shop/admin/collect!addOrder
	 * 1、商品申请入口，调用地址为
	 */
	@Override
	public void collect() {
		super.collect();
	}

	@Override
	public void display() {
		super.display();
	}

	@Override
	public void execute() {
		
		if(OrderStatus.SOURCE_FROM_TAOBAO.equals(getOrderRequst().getGoodsApply().getSource_from())){ //外系统订单同步
			super.taobaoCardOrder(); // 写订单数据
			//插入订单关系
		}else{
			
			
			//事物控制，将方法锁定
			this.baseDaoSupport.execute(SF.orderSql("SERVICE_DC_PUBLIC_SELECT"),"CARD");
			
			
			// 设置支付方式
			Order order = getOrderResult().getOrder();
			getOrderRequst().getGoodsApply().setPayment_id(Consts.PAYMENT_ID_DEPOST);
			
			String cardIds = getOrderRequst().getGoodsApply().getReturned_ids();
			if(!StringUtil.isEmpty(cardIds)){
				//根据returned_ids 获取价格总额
				getOrderRequst().getGoodsApply().setSales_price(new Double(cardServ.getTransferPrice(cardIds)).doubleValue());
			}
			
			super.execute();
			
			//退费处理
			if(!StringUtil.isEmpty(cardIds)){
				
				cardServ.occupyCards(cardIds, getOrder().getOrder_id());
				super.addLog("商品退货申请");
				//addAutoAuditApply("卡资源退货申请",OrderStatus.AUDIT_TYPE_00B);
				
			}else
			{
				super.addLog("商品订购申请");
			}
			
			setCanNext(false);
		}
		
	}

}

package com.ztesoft.net.mall.core.action.order.button;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.AbstractHander;
import com.ztesoft.net.mall.core.action.order.Button;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderAuditRequest;
import com.ztesoft.net.mall.core.service.IOrderApplycMamager;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wui
 * 
 * 操作按钮展示
 * 
 */
public class CommonBtnsHander extends AbstractHander implements
		ICommonBtnsHander {
	private IOrderApplycMamager orderApplycMamager;

	public IOrderApplycMamager getOrderApplycMamager() {
		return orderApplycMamager;
	}

	public void setOrderApplycMamager(IOrderApplycMamager orderApplycMamager) {
		this.orderApplycMamager = orderApplycMamager;
	}

	@Override
	public void execute() {

	}

	@Override
	public boolean isCanExecute() {
		return true;
	}

	@Override
	public void display() {

		Order order = getOrder();
		/**
		 * 1.获取订单来源 2.获取订单类型
		 */
		@SuppressWarnings("unused")
		String orderId = order.getOrder_id();
		String type_code =  order.getType_code(); // 云卡
		Integer status = order.getStatus();
		Integer shipStatus = order.getShip_status();
		List<Button> buttons = new ArrayList<Button>();
		Integer paystatus = order.getPay_status();
		
		
		if(OrderBuilder.COMMONAGE.equals(type_code)){//大众版
			/**
			 * mochunrun 增加
			 */
			/*if(OrderStatus.PAY_YES==paystatus){
				if(OrderStatus.ORDER_PAY==status){//已支付、待备货
					buttons.add(new Button("备货",OrderStatus.BUTTON_CUST_ACCEPT_C,false,"备货"));
				}else if(OrderStatus.ORDER_ACCEPT==status || OrderStatus.SHIP_PARTIAL_SHIPED==shipStatus){//已备货待发货
					buttons.add(new Button(OrderStatus.BUTTON_NAME_SHIPPING,OrderStatus.BUTTON_SHIPPING_C,false,"发货"));
				}else if(OrderStatus.ORDER_SHIP==status){//已发货待确认
					buttons.add(new Button(OrderStatus.BUTTON_NAME_GET_SHIPPING,OrderStatus.BUTTON_GET_SHIPPING_C,false,"确认收货"));
				}else if(OrderStatus.ORDER_CONFIRM_SHIP==status){//确认收货
					buttons.add(new Button(OrderStatus.BUTTON_NAME_FINISHED,OrderStatus.BUTTON_FINISHED_C,false,"完成"));
				}else if(OrderStatus.ORDER_COMPLETE==status){//已完成
					//buttons.add(new Button(OrderStatus.BUTTON_NAME_FINISHED,OrderStatus.BUTTON_FINISHED_C,false,"已完成"));;
				}
			}*/
			if(orderApplycMamager.countAmountByServiceType(OrderStatus.ORDER_TYPE_4,orderId)>0){
				buttons.add(new Button(OrderStatus.BUTTON_NAME_RETURNED_SHIPPING,OrderStatus.BUTTON_RETURNED_SHIPPING_C,false,"退货"));
			}
			if(orderApplycMamager.countAmountByServiceType(OrderStatus.ORDER_TYPE_2,orderId)>0){
				buttons.add(new Button(OrderStatus.BUTTON_NAME_REFUND,OrderStatus.BUTTON_REFUND_C,false,"退费"));
			}
			
			
		}else if (OrderBuilder.CLOUD_KEY.equals(type_code)) // 云卡
		{
			
			if (isTaobaoAgent()) {
				if(OrderStatus.ORDER_PAY == status && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //已支付、展示资料返档
					buttons.add(new Button(OrderStatus.BUTTON_NAME_CUST_ACCEPT,OrderStatus.BUTTON_CUST_ACCEPT,false,"资料返档"));
				
				//合约机淘宝订单需要发货，确认收货
				if(OrderStatus.ORDER_ACCEPT == status && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //已受理、展示发货按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_SHIPPING,OrderStatus.BUTTON_SHIPPING,false,"发货"));
				
			}else{
				if(OrderStatus.ORDER_COLLECT == status && (isFirstPartner() || isNetStaff())  && isNotSameOwnerUserId()) //采集单、展示审核按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_AUDIT,OrderStatus.BUTTON_AUDIT,false,"审核"));
				if (isSecondAgent()) {

				} else if (isFristAgent()) {
					if(OrderStatus.ORDER_NOT_PAY == status && isFirstPartner()  && isSameOwnerUserId()) //未支付、展示支付按钮
						buttons.add(new Button(OrderStatus.BUTTON_NAME_PAY,OrderStatus.BUTTON_PAY,false,"支付"));
				}
				//add 已支付可以云卡调拨
				if(status==OrderStatus.ORDER_PAY && (isNetStaff() || isFirstPartner()) && isNotSameOwnerUserId()) //已支付展示云卡调拨按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_CLOUD_ACCEPT,OrderStatus.BUTTON_CLOUD_ACCEPT,false,"云卡调拨"));
				
				if(OrderStatus.ORDER_COLLECT == status && OrderStatus.ORDER_CONFIRM_CANCEL != status && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //采集单、展示取消按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_CANCEL,OrderStatus.BUTTON_CANCEL,false,"取消"));
				if(OrderStatus.ORDER_NOT_PAY == status && OrderStatus.ORDER_ACCEPT_FAIL != status && OrderStatus.ORDER_ACCEPT_FAIL != status &&(isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //审核通过未支付、展示撤单按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_DRAWBACK,OrderStatus.BUTTON_DRAWBACK,false,"撤单"));
			}
			
			if(OrderStatus.ORDER_ACCEPT == status && (isFirstPartner() || isNetStaff()) && isNotSameOwnerUserId()) //已受理、展示发货按钮
				buttons.add(new Button(OrderStatus.BUTTON_NAME_SHIPPING,OrderStatus.BUTTON_SHIPPING,false,"发货"));
			if(OrderStatus.ORDER_SHIP == status && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //已发货、展示确认收货按钮
				buttons.add(new Button(OrderStatus.BUTTON_NAME_GET_SHIPPING,OrderStatus.BUTTON_GET_SHIPPING,false,"确认收货"));
			
			if(OrderStatus.ORDER_CONFIRM_SHIP == status && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //确认收货、展示完成按钮
				buttons.add(new Button(OrderStatus.BUTTON_NAME_FINISHED,OrderStatus.BUTTON_FINISHED,false,"完成"));
//			if(OrderStatus.ORDER_COMPLETE == status && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //完成、展示作废按钮
//				buttons.add(new Button(OrderStatus.BUTTON_NAME_INVALID,OrderStatus.BUTTON_INVALID,false,"作废"));
			
		} 
		
		//流量卡走充值卡流程
//		else if(OrderBuilder.RECHARGE_CARD_KEY.equals(type_code)){ //流量卡
//			
//			if (isTaobaoAgent()) {
//				
//			}else{
//				if(OrderStatus.ORDER_COLLECT == status && (isFirstPartner() || isNetStaff()) && isNotSameOwnerUserId()) //采集单、展示审核按钮
//					buttons.add(new Button(OrderStatus.BUTTON_NAME_AUDIT,OrderStatus.BUTTON_AUDIT,false,"审核"));
////				if (isSecondAgent()) {
////
////				} else if (isFristAgent()) {
////					
////				}
////				
//				if(OrderStatus.ORDER_SHIP == status && isSameOwnerUserId())  //已发货、展示确认收货按钮
//					buttons.add(new Button(OrderStatus.BUTTON_NAME_GET_SHIPPING,OrderStatus.BUTTON_GET_SHIPPING,false,"确认收货"));
//				
//				if(OrderStatus.ORDER_COLLECT == status && OrderStatus.ORDER_CONFIRM_CANCEL != status && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //采集单、展示取消按钮
//					buttons.add(new Button(OrderStatus.BUTTON_NAME_CANCEL,OrderStatus.BUTTON_CANCEL,false,"取消"));
//				if(OrderStatus.ORDER_NOT_PAY == status && OrderStatus.ORDER_ACCEPT_FAIL != status &&(isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //审核通过未支付、展示撤单按钮
//					buttons.add(new Button(OrderStatus.BUTTON_NAME_DRAWBACK,OrderStatus.BUTTON_DRAWBACK,false,"撤单"));
//			}
//			if(OrderStatus.ORDER_CONFIRM_SHIP == status && (isFirstPartner() || isNetStaff()) && isSameOwnerUserId()) //确认收货、展示完成按钮
//				buttons.add(new Button(OrderStatus.BUTTON_NAME_FINISHED,OrderStatus.BUTTON_FINISHED,false,"完成"));
////			if(OrderStatus.ORDER_COMPLETE == status && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //完成、展示作废按钮
////				buttons.add(new Button(OrderStatus.BUTTON_NAME_INVALID,OrderStatus.BUTTON_INVALID,false,"作废"));
//			
//		}
		
		
		
		else if(OrderBuilder.CARD_KEY.equals(type_code) || OrderBuilder.RECHARGE_CARD_KEY.equals(type_code) || OrderBuilder.TIME_CARD_KEY.equals(type_code)){ //充值卡
			if (isTaobaoAgent()) {
				
			}else{
				if(OrderStatus.ORDER_COLLECT == status && (isFirstPartner() || isNetStaff())  && isNotSameOwnerUserId()) //采集单、展示审核按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_AUDIT,OrderStatus.BUTTON_AUDIT,false,"审核"));
				if (isSecondAgent()) {
					if(OrderStatus.ORDER_NOT_PAY == status && isFirstPartner() && isSameOwnerUserId()) //未支付、展示预存金支付按钮
						buttons.add(new Button(OrderStatus.BUTTON_NAME_PAY,OrderStatus.BUTTON_PAY,false,"支付"));
				} else if (isFristAgent()) {
					if(OrderStatus.ORDER_NOT_PAY == status && isSameOwnerUserId()) //未支付、展示支付按钮
						buttons.add(new Button(OrderStatus.BUTTON_NAME_PAY,OrderStatus.BUTTON_PAY,false,"支付"));
				}
				if(status==OrderStatus.ORDER_PAY  && (isNetStaff() || isFirstPartner()) && isNotSameOwnerUserId()) {//已支付展示流量卡调拨按钮
					if(OrderBuilder.RECHARGE_CARD_KEY.equals(getOrder().getType_code())){
						buttons.add(new Button("流量卡调拨",OrderStatus.BUTTON_CARD_ACCEPT,false,"流量卡调拨"));
					}else if(OrderBuilder.TIME_CARD_KEY.equals(getOrder().getType_code())){
						buttons.add(new Button("时长卡调拨",OrderStatus.BUTTON_CARD_ACCEPT,false,"时长卡调拨"));
					}else {
						buttons.add(new Button(OrderStatus.BUTTON_NAME_CARD_ACCEPT,OrderStatus.BUTTON_CARD_ACCEPT,false,"充值卡调拨"));
					}
				}
				
				if(OrderStatus.ORDER_SHIP == status && isSameOwnerUserId()) //已发货、展示确认收货按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_GET_SHIPPING,OrderStatus.BUTTON_GET_SHIPPING,false,"确认收货"));
				
				if(OrderStatus.ORDER_COLLECT == status && OrderStatus.ORDER_CONFIRM_CANCEL != status && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //采集单、展示取消按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_CANCEL,OrderStatus.BUTTON_CANCEL,false,"取消") );
				if(OrderStatus.ORDER_NOT_PAY == status && OrderStatus.ORDER_ACCEPT_FAIL != status &&(isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //审核通过未支付、展示撤单按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_DRAWBACK,OrderStatus.BUTTON_DRAWBACK,false,"撤单"));
			}
			if(OrderStatus.ORDER_CONFIRM_SHIP == status && (isFirstPartner() || isSecondPartner() ) && isSameOwnerUserId()) //确认收货、展示完成按钮
				buttons.add(new Button(OrderStatus.BUTTON_NAME_FINISHED,OrderStatus.BUTTON_FINISHED,false,"完成"));
//			if(OrderStatus.ORDER_COMPLETE == status && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //完成、展示作废按钮
//				buttons.add(new Button(OrderStatus.BUTTON_NAME_INVALID,OrderStatus.BUTTON_INVALID,false,"作废"));
			
			
		}else if(OrderBuilder.CONTRACT_KEY.equals(type_code)){ //合约机
			if (isTaobaoAgent()) {
				setAuditType(OrderStatus.AUDIT_TYPE_00A);
				String state = getLastAuditState();
				
				
				if(OrderStatus.ORDER_PAY == status){//已支付、展示资料返档
					if(StringUtil.isEmpty(state) && isSameOwnerUserId()){
						buttons.add(new Button(OrderStatus.BUTTON_NAME_CONTRACT_APPLY,OrderStatus.BUTTON_CONTRACT_APPLY,false,"合约机受理申请"));
					}else if((OrderStatus.ORDER_AUDIT_STATE_0.equals(state) || OrderStatus.ORDER_AUDIT_STATE_2.equals(state)) && isNotSameOwnerUserId()){
						if((OrderStatus.ORDER_AUDIT_STATE_2.equals(state))
							|| (isSecondPartnerOrder() && OrderStatus.ORDER_AUDIT_STATE_0.equals(state)))
						buttons.add(new Button(OrderStatus.BUTTON_NAME_CONTRACT_AUDIT,OrderStatus.BUTTON_CONTRACT_AUDIT,false,"合约机受理审核"));
						
					}else if(OrderStatus.ORDER_AUDIT_STATE_4.equals(state) && isSameOwnerUserId()){
						buttons.add(new Button(OrderStatus.BUTTON_NAME_CONTRACT_ACCEPT,OrderStatus.BUTTON_CONTRACT_ACCEPT,false,"合约机受理")); //受理的同时扣除二级、一级分销商的费用
					}
				}
				
				if(OrderStatus.ORDER_COMPLETE != status && OrderStatus.ORDER_CANCELLATION != status ) //&&存在换货单
				{
					//完成、作废不允许 换货、退货处理
					if(!StringUtil.isEmpty(orderNFlowManager.getZOrderId(getOrder().getOrder_id(), OrderStatus.ORDER_TYPE_3)) && isSameOwnerUserId() && OrderStatus.ORDER_CHANGED  != getOrder().getStatus()){
						buttons.add(new Button(OrderStatus.BUTTON_NAME_CHANGE_SHIPPING,OrderStatus.BUTTON_CHANGE_SHIPPING,false,"换货"));
					}
				
					if(!StringUtil.isEmpty(orderNFlowManager.getZOrderId(getOrder().getOrder_id(), OrderStatus.ORDER_TYPE_4)) && isSameOwnerUserId() &&  OrderStatus.ORDER_CANCEL_SHIP  != getOrder().getStatus()){
						buttons.add(new Button(OrderStatus.BUTTON_NAME_RETURNED_SHIPPING,OrderStatus.BUTTON_RETURNED_SHIPPING,false,"退货"));
					}
				}

				//合约机淘宝订单需要发货，确认收货
				if(OrderStatus.ORDER_ACCEPT == status && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //已受理、展示发货按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_SHIPPING,OrderStatus.BUTTON_SHIPPING,false,"发货"));
				if(OrderStatus.ORDER_SHIP == status && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //已发货、展示确认收货按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_GET_SHIPPING,OrderStatus.BUTTON_GET_SHIPPING,false,"确认收货"));
				
				
			}else{
				if(OrderStatus.ORDER_COLLECT == status && (isFirstPartner() || isNetStaff()) && isNotSameOwnerUserId()) //采集单、展示审核按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_AUDIT,OrderStatus.BUTTON_AUDIT,false,"审核"));
				if (isSecondAgent()) {

				} else if (isFristAgent()) {
					
				}
				if(OrderStatus.ORDER_PAY == status && (isNetStaff() || isFirstPartner()) && isNotSameOwnerUserId()) //已支付展示云卡调拨按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_CONTRACT_TANSFER,OrderStatus.BUTTON_CONTRACT_TANSFER,false,"号码调拨"));
				
				if(OrderStatus.ORDER_COLLECT == status && OrderStatus.ORDER_CONFIRM_CANCEL != status  && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //采集单、展示取消按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_CANCEL,OrderStatus.BUTTON_CANCEL,false,"取消"));
				if(OrderStatus.ORDER_NOT_PAY == status && OrderStatus.ORDER_ACCEPT_FAIL != status  &&(isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //审核通过未支付、展示撤单按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_DRAWBACK,OrderStatus.BUTTON_DRAWBACK,false,"撤单"));
				
				if(OrderStatus.ORDER_SHIP == status && isSameOwnerUserId() ) //已发货、展示确认收货按钮
					buttons.add(new Button(OrderStatus.BUTTON_NAME_GET_SHIPPING,OrderStatus.BUTTON_GET_SHIPPING,false,"确认收货"));
				
				
			}
			
			if(OrderStatus.ORDER_CONFIRM_SHIP == status && (isFirstPartner() || isSecondPartner()) && isSameOwnerUserId()) //确认收货、展示完成按钮
				buttons.add(new Button(OrderStatus.BUTTON_NAME_FINISHED,OrderStatus.BUTTON_FINISHED,false,"完成"));
//			if(OrderStatus.ORDER_COMPLETE == status && (isFirstPartner() || isSecondPartner() || isSecondPartner()) && isSameOwnerUserId()) //完成、展示作废按钮
//				buttons.add(new Button(OrderStatus.BUTTON_NAME_INVALID,OrderStatus.BUTTON_INVALID,false,"作废"));
//			
			
			}
		
		
		//存在退费申请单
		//流量卡允许退费处理，二级、一级分销商选中流量卡商品发起退费申请时生成退费订单
		setAuditType(OrderStatus.AUDIT_TYPE_00B);
		String state = getLastAuditState();
		if(OrderBuilder.CARD_KEY.equals(type_code) && OrderStatus.ORDER_TYPE_2.equals(getOrder().getOrder_type()) && OrderStatus.ORDER_CANCEL_PAY  != getOrder().getStatus()){//充值卡
//			if(StringUtil.isEmpty(state) && isSameOwnerUserId()){
//				buttons.add(new Button(OrderStatus.BUTTON_NAME_REFUND_APPLY,OrderStatus.BUTTON_REFUND_APPLY,false,"退费申请"));
//			}
			if(OrderStatus.ORDER_AUDIT_STATE_0.equals(state) || OrderStatus.ORDER_AUDIT_STATE_2.equals(state)){
				
				if(((OrderStatus.ORDER_AUDIT_STATE_2.equals(state)) && isNetStaff())
						|| (isSecondPartnerOrder() && OrderStatus.ORDER_AUDIT_STATE_0.equals(state)))
				buttons.add(new Button(OrderStatus.BUTTON_NAME_REFUND_AUDIT,OrderStatus.BUTTON_REFUND_AUDIT,false,"退费审核"));
			}else if(OrderStatus.ORDER_AUDIT_STATE_4.equals(state) && isNotSameOwnerUserId() ){
				buttons.add(new Button(OrderStatus.BUTTON_NAME_REFUND,OrderStatus.BUTTON_REFUND,false,"退费处理"));
			}
		}else if(OrderBuilder.CONTRACT_KEY.equals(type_code) && !StringUtil.isEmpty(orderNFlowManager.getZOrderId(getOrder().getOrder_id(), OrderStatus.ORDER_TYPE_2)) && OrderStatus.ORDER_CANCEL_PAY  != getOrder().getStatus()){ //合约机
			if(StringUtil.isEmpty(state) && isSameOwnerUserId()){
				buttons.add(new Button(OrderStatus.BUTTON_NAME_REFUND_APPLY,OrderStatus.BUTTON_REFUND_APPLY,false,"退费申请"));
			}else if(OrderStatus.ORDER_AUDIT_STATE_0.equals(state) || OrderStatus.ORDER_AUDIT_STATE_2.equals(state)){
				buttons.add(new Button(OrderStatus.BUTTON_NAME_REFUND_AUDIT,OrderStatus.BUTTON_REFUND_AUDIT,false,"退费审核"));
			}else if(OrderStatus.ORDER_AUDIT_STATE_4.equals(state) && isNotSameOwnerUserId()){
				buttons.add(new Button(OrderStatus.BUTTON_NAME_REFUND,OrderStatus.BUTTON_REFUND,false,"退费处理"));
			}
		}
		getOrderResult().setButtons(buttons);
		
	}

	private void setAuditType(String audit_type) {
		OrderAuditRequest orderAuditRequest =new OrderAuditRequest();
		getOrderRequst().setOrderAuditRequest(orderAuditRequest);
		orderAuditRequest.setAudit_type(audit_type );
	}

}

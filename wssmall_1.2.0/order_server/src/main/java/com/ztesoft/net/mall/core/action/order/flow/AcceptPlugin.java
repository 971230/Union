package com.ztesoft.net.mall.core.action.order.flow;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.webcontext.ThreadOrderHolder;
import com.ztesoft.net.mall.core.action.order.AbstractHander;
import com.ztesoft.net.mall.core.action.order.FlowEntry;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;
import com.ztesoft.net.mall.core.service.impl.IAcceptPlugin;

public class AcceptPlugin  extends   BaseSupport implements IAcceptPlugin{

	AbstractHander cloudCardCollectHander;
	AbstractHander cloudCardAuditHander;
	AbstractHander cloudCardPaymentHander;
	AbstractHander cloudCardAcceptHander;
	AbstractHander cloudCardDilveryHander;

	AbstractHander contractCollectHander;
	AbstractHander contractAuditHander;
	AbstractHander contractPaymentHander;
	AbstractHander contractAcceptHander;
	AbstractHander contractDilveryHander;

	AbstractHander cardCollectHander;
	AbstractHander cardAuditHander;
	AbstractHander cardPaymentHander;
	AbstractHander cardAcceptHander;
	AbstractHander cardDilveryHander;

	AbstractHander commonChangeShipHander;
	AbstractHander commonReturnedShipHander;
	AbstractHander commonRefundHander;
	AbstractHander commonCancelHander;
	AbstractHander commonWithDrawsHander;

	AbstractHander commonBtnsHander;
	
	AbstractHander orderHandleHander;
	AbstractHander paymentHander;
	AbstractHander goodsReturnedShipHander;
	AbstractHander goodsRefundHander;

	public AbstractHander getPlugByActionName(String action_name) {
		if (action_name.equals("cloudCardCollectHander"))
			return cloudCardCollectHander;
		if (action_name.equals("cloudCardAuditHander"))
			return cloudCardAuditHander;
		if (action_name.equals("cloudCardPaymentHander"))
			return cloudCardPaymentHander;
		if (action_name.equals("cloudCardAcceptHander"))
			return cloudCardAcceptHander;
		if (action_name.equals("cloudCardDilveryHander"))
			return cloudCardDilveryHander;
		if (action_name.equals("contractCollectHander"))
			return contractCollectHander;
		if (action_name.equals("contractAuditHander"))
			return contractAuditHander;
		if (action_name.equals("contractPaymentHander"))
			return contractPaymentHander;
		if (action_name.equals("contractAcceptHander"))
			return contractAcceptHander;
		if (action_name.equals("contractDilveryHander"))
			return contractDilveryHander;
		if (action_name.equals("cardCollectHander"))
			return cardCollectHander;
		if (action_name.equals("cardAuditHander"))
			return cardAuditHander;
		if (action_name.equals("cardPaymentHander"))
			return cardPaymentHander;
		if (action_name.equals("cardAcceptHander"))
			return cardAcceptHander;
		if (action_name.equals("cardDilveryHander"))
			return cardDilveryHander;
		if (action_name.equals("commonChangeShipHander"))
			return commonChangeShipHander;
		if (action_name.equals("commonReturnedShipHander"))
			return commonReturnedShipHander;
		if (action_name.equals("commonRefundHander"))
			return commonRefundHander;
		if (action_name.equals("commonCancelHander"))
			return commonCancelHander;
		if (action_name.equals("commonWithDrawsHander"))
			return commonWithDrawsHander;
		if (action_name.equals("orderHandleHander"))
			return orderHandleHander;
		if(action_name.equals("paymentHander"))
			return paymentHander;
		if(action_name.equals("goodsReturnedShipHander"))
			return goodsReturnedShipHander;
		if(action_name.equals("goodsRefundHander"))
			return goodsRefundHander;
		return null;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public OrderResult perform(OrderRequst orderRequst, List<FlowEntry> flowEntrys) {
		

		
		
		OrderResult orderResult = new OrderResult();
		ThreadOrderHolder.getOrderParams().setOrderResult(orderResult);
		ThreadOrderHolder.getOrderParams().setOrderRequst(orderRequst);
		
		for (FlowEntry flowEntry : flowEntrys) {
			String action_name = flowEntry.getAction_name();
			AbstractHander action = getPlugByActionName(action_name);
			//action.setOrderRequst(orderRequst);
			//action.setOrderResult(orderResult);
			if (!action.isCanExecute())
				break;
			action.perform();
		}
		return orderResult;
	}

	
	public AbstractHander getCloudCardCollectHander() {
		return cloudCardCollectHander;
	}

	public void setCloudCardCollectHander(AbstractHander cloudCardCollectHander) {
		this.cloudCardCollectHander = cloudCardCollectHander;
	}

	public AbstractHander getCloudCardAuditHander() {
		return cloudCardAuditHander;
	}

	public void setCloudCardAuditHander(AbstractHander cloudCardAuditHander) {
		this.cloudCardAuditHander = cloudCardAuditHander;
	}

	public AbstractHander getCloudCardPaymentHander() {
		return cloudCardPaymentHander;
	}

	public void setCloudCardPaymentHander(AbstractHander cloudCardPaymentHander) {
		this.cloudCardPaymentHander = cloudCardPaymentHander;
	}

	public AbstractHander getCloudCardAcceptHander() {
		return cloudCardAcceptHander;
	}

	public void setCloudCardAcceptHander(AbstractHander cloudCardAcceptHander) {
		this.cloudCardAcceptHander = cloudCardAcceptHander;
	}

	public AbstractHander getCloudCardDilveryHander() {
		return cloudCardDilveryHander;
	}

	public void setCloudCardDilveryHander(AbstractHander cloudCardDilveryHander) {
		this.cloudCardDilveryHander = cloudCardDilveryHander;
	}

	public AbstractHander getContractCollectHander() {
		return contractCollectHander;
	}

	public void setContractCollectHander(AbstractHander contractCollectHander) {
		this.contractCollectHander = contractCollectHander;
	}

	public AbstractHander getContractAuditHander() {
		return contractAuditHander;
	}

	public void setContractAuditHander(AbstractHander contractAuditHander) {
		this.contractAuditHander = contractAuditHander;
	}

	public AbstractHander getContractPaymentHander() {
		return contractPaymentHander;
	}

	public void setContractPaymentHander(AbstractHander contractPaymentHander) {
		this.contractPaymentHander = contractPaymentHander;
	}

	public AbstractHander getContractAcceptHander() {
		return contractAcceptHander;
	}

	public void setContractAcceptHander(AbstractHander contractAcceptHander) {
		this.contractAcceptHander = contractAcceptHander;
	}

	public AbstractHander getContractDilveryHander() {
		return contractDilveryHander;
	}

	public void setContractDilveryHander(AbstractHander contractDilveryHander) {
		this.contractDilveryHander = contractDilveryHander;
	}

	public AbstractHander getCardCollectHander() {
		return cardCollectHander;
	}

	public void setCardCollectHander(AbstractHander cardCollectHander) {
		this.cardCollectHander = cardCollectHander;
	}

	public AbstractHander getCardAuditHander() {
		return cardAuditHander;
	}

	public void setCardAuditHander(AbstractHander cardAuditHander) {
		this.cardAuditHander = cardAuditHander;
	}

	public AbstractHander getCardAcceptHander() {
		return cardAcceptHander;
	}

	public void setCardAcceptHander(AbstractHander cardAcceptHander) {
		this.cardAcceptHander = cardAcceptHander;
	}

	public AbstractHander getCardDilveryHander() {
		return cardDilveryHander;
	}

	public void setCardDilveryHander(AbstractHander cardDilveryHander) {
		this.cardDilveryHander = cardDilveryHander;
	}

	public AbstractHander getCardPaymentHander() {
		return cardPaymentHander;
	}

	public void setCardPaymentHander(AbstractHander cardPaymentHander) {
		this.cardPaymentHander = cardPaymentHander;
	}

	public AbstractHander getCommonChangeShipHander() {
		return commonChangeShipHander;
	}

	public void setCommonChangeShipHander(AbstractHander commonChangeShipHander) {
		this.commonChangeShipHander = commonChangeShipHander;
	}

	public AbstractHander getCommonReturnedShipHander() {
		return commonReturnedShipHander;
	}

	public void setCommonReturnedShipHander(AbstractHander commonReturnedShipHander) {
		this.commonReturnedShipHander = commonReturnedShipHander;
	}

	public AbstractHander getCommonRefundHander() {
		return commonRefundHander;
	}

	public void setCommonRefundHander(AbstractHander commonRefundHander) {
		this.commonRefundHander = commonRefundHander;
	}

	public AbstractHander getCommonCancelHander() {
		return commonCancelHander;
	}

	public void setCommonCancelHander(AbstractHander commonCancelHander) {
		this.commonCancelHander = commonCancelHander;
	}

	public AbstractHander getCommonWithDrawsHander() {
		return commonWithDrawsHander;
	}

	public void setCommonWithDrawsHander(AbstractHander commonWithDrawsHander) {
		this.commonWithDrawsHander = commonWithDrawsHander;
	}

	public AbstractHander getCommonBtnsHander() {
		return commonBtnsHander;
	}

	public void setCommonBtnsHander(AbstractHander commonBtnsHander) {
		this.commonBtnsHander = commonBtnsHander;
	}

	public AbstractHander getOrderHandleHander() {
		return orderHandleHander;
	}

	public void setOrderHandleHander(AbstractHander orderHandleHander) {
		this.orderHandleHander = orderHandleHander;
	}

	public AbstractHander getPaymentHander() {
		return paymentHander;
	}

	public void setPaymentHander(AbstractHander paymentHander) {
		this.paymentHander = paymentHander;
	}

	public AbstractHander getGoodsReturnedShipHander() {
		return goodsReturnedShipHander;
	}

	public void setGoodsReturnedShipHander(AbstractHander goodsReturnedShipHander) {
		this.goodsReturnedShipHander = goodsReturnedShipHander;
	}

	public AbstractHander getGoodsRefundHander() {
		return goodsRefundHander;
	}

	public void setGoodsRefundHander(AbstractHander goodsRefundHander) {
		this.goodsRefundHander = goodsRefundHander;
	}


}

package com.ztesoft.net.mall.core.action.order;

import com.ztesoft.net.mall.core.action.order.orderc.OrderHandleParam;
import com.ztesoft.net.mall.core.model.*;

public class OrderRequst {
	private PaymentLog payment; //商品支付
	private GoodsApply goodsApply; //商品申请
	
	private GoodsAudit goodsAudit; //订单审核处理
	private CustReturned custReturned; //订单资料返档对象
	private Contract contract;//订单合约机受理对象
	private CloudRequest cloudRequest;//订单云卡调拨对象
	private RateRequest rateRequest;//订单流量卡调拨对象
	private AccNbrRequest accNbrRequest;//订单号码调拨对象
	private CardRequest cardRequest;//流量卡调拨对象
	
	private ShipRequest shipRequest; //物流支付处理
	
	private OrderAuditRequest orderAuditRequest; //订单审核申请,合约受理申请、退费申请
	
	private CardInfRequest cardInfRequest; //淘宝充值卡请求参数
	
	private RateInfRequest rateInfRequest; //淘宝流量卡请求参数
	
	private Card usedCard;
	private String orderId; //订单id
	private String ship_action; //物流动作，
	private String payment_action; //支付动作
	
	
	private String service_name;
	private String accept_action;
	private String flow_name;
	
	private OrderOuter orderOuter; //  外系统订单
	
	private boolean can_execute = true;
	private OrderHandleParam orderParam;
	

	public PaymentLog getPayment() {
		return payment;
	}

	public void setPayment(PaymentLog payment) {
		this.payment = payment;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	

	public ShipRequest getShipRequest() {
		return shipRequest;
	}

	public void setShipRequest(ShipRequest shipRequest) {
		this.shipRequest = shipRequest;
	}

	public String getFlow_name() {
		return flow_name;
	}

	public void setFlow_name(String flow_name) {
		this.flow_name = flow_name;
	}

	

	public GoodsApply getGoodsApply() {
		return goodsApply;
	}

	public void setGoodsApply(GoodsApply goodsApply) {
		this.goodsApply = goodsApply;
	}

	public GoodsAudit getGoodsAudit() {
		return goodsAudit;
	}

	public void setGoodsAudit(GoodsAudit goodsAudit) {
		this.goodsAudit = goodsAudit;
	}

	

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getAccept_action() {
		return accept_action;
	}

	public void setAccept_action(String accept_action) {
		this.accept_action = accept_action;
	}

	public boolean isCan_execute() {
		return can_execute;
	}

	public void setCan_execute(boolean can_execute) {
		this.can_execute = can_execute;
	}

	public CustReturned getCustReturned() {
		return custReturned;
	}

	public void setCustReturned(CustReturned custReturned) {
		this.custReturned = custReturned;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public CloudRequest getCloudRequest() {
		return cloudRequest;
	}

	public void setCloudRequest(CloudRequest cloudRequest) {
		this.cloudRequest = cloudRequest;
	}

	public RateRequest getRateRequest() {
		return rateRequest;
	}

	public void setRateRequest(RateRequest rateRequest) {
		this.rateRequest = rateRequest;
	}

	public AccNbrRequest getAccNbrRequest() {
		return accNbrRequest;
	}

	public void setAccNbrRequest(AccNbrRequest accNbrRequest) {
		this.accNbrRequest = accNbrRequest;
	}

	public CardRequest getCardRequest() {
		return cardRequest;
	}

	public void setCardRequest(CardRequest cardRequest) {
		this.cardRequest = cardRequest;
	}

	public String getShip_action() {
		return ship_action;
	}

	public void setShip_action(String ship_action) {
		this.ship_action = ship_action;
	}

	public String getPayment_action() {
		return payment_action;
	}

	public void setPayment_action(String payment_action) {
		this.payment_action = payment_action;
	}

	public OrderAuditRequest getOrderAuditRequest() {
		return orderAuditRequest;
	}

	public void setOrderAuditRequest(OrderAuditRequest orderAuditRequest) {
		this.orderAuditRequest = orderAuditRequest;
	}

	public OrderOuter getOrderOuter() {
		return orderOuter;
	}

	public void setOrderOuter(OrderOuter orderOuter) {
		this.orderOuter = orderOuter;
	}



	public RateInfRequest getRateInfRequest() {
		return rateInfRequest;
	}

	public void setRateInfRequest(RateInfRequest rateInfRequest) {
		this.rateInfRequest = rateInfRequest;
	}

	public CardInfRequest getCardInfRequest() {
		return cardInfRequest;
	}

	public void setCardInfRequest(CardInfRequest cardInfRequest) {
		this.cardInfRequest = cardInfRequest;
	}

	public Card getUsedCard() {
		return usedCard;
	}

	public void setUsedCard(Card usedCard) {
		this.usedCard = usedCard;
	}

	public OrderHandleParam getOrderParam() {
		return orderParam;
	}

	public void setOrderParam(OrderHandleParam orderParam) {
		this.orderParam = orderParam;
	}


}

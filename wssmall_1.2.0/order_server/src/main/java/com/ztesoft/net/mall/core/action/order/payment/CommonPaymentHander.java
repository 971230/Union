package com.ztesoft.net.mall.core.action.order.payment;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.payment.req.EditPaymentListReq;
import params.payment.req.GetPaymentListReq;
import services.BankInf;
import services.PaymentListInf;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.mall.core.action.order.AbstractHander;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.PaymentList;


public class CommonPaymentHander extends AbstractHander{

	protected BankInf bankServ;
	protected PaymentListInf paymentListServ;
	
	protected void init(){
		if(null == paymentListServ) paymentListServ = ApiContextHolder.getBean("paymentListServ");
		if(null == bankServ) bankServ = ApiContextHolder.getBean("bankServ");
	}

	public boolean canPayment(){
		if(OrderStatus.ORDER_NOT_PAY ==getOrder().getStatus()) 
			return true;
		return false;
	}
	@Override
	public void display() {
		
	}

	@Override
	public void execute() {
	
	}
	
	public void syOrderAutoPay(){
		Order order = getOrder();
		orderNFlowManager.pay_auto(order.getOrder_id()); //自动支付
		
	}

	@Override
	public boolean isCanExecute() {
		return this.getOrderRequst().isCan_execute();
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void afterPay(String ordeSeq, Integer orderAmount, String  retnCode, String tranDate){
		try {
			//初始化beans
			init();
			
			//PaymentList payment = commonPayHander.getPaymentById(ordeSeq);
			GetPaymentListReq gpreq = new GetPaymentListReq();
			gpreq.setTransactionid(ordeSeq);
			PaymentList payment = paymentListServ.getPaymentListById(gpreq).getPaymentList();
			
			if(retnCode.equals(Consts.BANK_BACK_PAY_SUCC)){
				//支付成功修改订单状态						
				//this.updatePartnerDespost(payment.getDepost_id(),"00B");
				if(orderAmount<payment.getPay_amount()){
					//银行返回支付金额小于应支付金额
					//commonPayHander.updatePayment(payment.getTransaction_id(), Consts.BANK_PAYED_EXCEPTION);
					EditPaymentListReq preq = new EditPaymentListReq();
					preq.setTransactionId(payment.getTransaction_id());
					preq.setDealFlag(Consts.BANK_PAYED_EXCEPTION);
					//preq.setPaymentList(payment);
			        paymentListServ.editPaymentList(preq);
					orderNFlowManager.payExcetion(payment.getTransaction_id(), orderAmount, payment.getType_code());
				}else{
					//修改支付表状态
					//commonPayHander.updatePayment(payment.getTransaction_id(), Consts.BANK_PAYED_SUCC);
					EditPaymentListReq preq = new EditPaymentListReq();
					preq.setTransactionId(payment.getTransaction_id());
					preq.setDealFlag(Consts.BANK_PAYED_SUCC);
					//preq.setPaymentList(payment);
			        paymentListServ.editPaymentList(preq);
					orderNFlowManager.paySucc(payment.getTransaction_id(),payment.getType_code()); //更新主订单状态为已用
					
					
					//Order order = orderManager.getOrderByTransId(payment.getTransaction_id());
					
					
				}
			}else {	//银行支付不成功
				//commonPayHander.updatePayment(payment.getTransaction_id(), Consts.BANK_PAYED_FAIL);
				EditPaymentListReq preq = new EditPaymentListReq();
				preq.setTransactionId(payment.getTransaction_id());
				preq.setDealFlag(Consts.BANK_PAYED_FAIL);
				//preq.setPaymentList(payment);
		        paymentListServ.editPaymentList(preq);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());			
		}
	}
	
}

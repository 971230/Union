package com.ztesoft.net.mall.core.action.order.service.cloud;

import java.util.Map;

import params.bank.req.BankReq;
import params.payment.req.EditPaymentListReq;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.payment.CommonPaymentHander;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.PayReponse;
import com.ztesoft.net.mall.core.model.PayRequest;
import com.ztesoft.net.mall.core.model.PaymentList;
import com.ztesoft.net.mall.core.model.PaymentLog;

/**
 * 云卡处理
 * 
 * @author wui
 * 
 */
/**
 * 银行支付模拟界面：
 * 
 * http://localhost:8080/wssmall/shop/admin/pay/payNotify.jsp?UPTRANSEQ=123&RETNCODE=0000&RETNINFO=0000&ORDERSEQ=201304244688000265&ORDERAMOUNT=100&ENCODETYPE=1&TRANDATE=20130416183957&SIGN=AEDA482E6F613FE9E15D0C2F622FC4C0
 * 
 */
public class CloudCardPaymentHander extends CommonPaymentHander {

	public void payment() {
	}

	@Override
	public void display() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() { // 云卡支付处理
		//初始化beans
		init();
		
		Order order = getOrder();
		if (isTaobaoAgent()) {
			// TODO 外系统直接到已收费环节
			super.syOrderAutoPay();
			setCanNext(false);
		} else {
			if (order.getSource_from().equals(OrderStatus.SOURCE_FROM_AGENT_ONE)) {// 一级分销商弹出支付界面支付，点击支付按钮时调用后台处理逻辑
				
				//弹出银行支付界面，选择银行支付处理
				PaymentLog payment = getOrderRequst().getPayment();
				String bankId =payment.getBank_id();
				
				//调用网银支付功能
				//Bank bank = commonPayHander.getBankById(bankId);
				BankReq req = new BankReq();
				req.setBank_id(bankId);
				Bank bank = bankServ.getBankByCode(req).getBank();
				
				//add bu wui 支付金额强制为收取金额，防止页面串改金额
				Double pay_money =getOrder().getOrder_amount();
				payment.setMoney(Double.valueOf(pay_money));
				
				PayRequest payReq = new PayRequest(pay_money,bank.getBank_code(),Consts.PAY_SOURCE_CLOUD_PAY,"commonPaymentHander",OrderBuilder.CLOUD_KEY); 
				//PayReponse payRsp =commonPayHander.bankPay(payReq);
				PayReponse payRsp =paymentListServ.bankPay(payReq);
				
				PaymentList paymentList = new PaymentList();
				paymentList.setOrder_id(getOrder().getOrder_id());
				paymentList.setTransaction_id(payRsp.getTransaction_id());
				order.setTransaction_id(payRsp.getTransaction_id());
				//commonPayHander.updatePayment(paymentList);
				EditPaymentListReq preq = new EditPaymentListReq();
				preq.setTransactionId(paymentList.getTransaction_id());
				preq.setPaymentList(paymentList);
		        paymentListServ.editPaymentList(preq);
				
				order.setTransaction_id(payRsp.getTransaction_id());
				
				payment.setOrder_id(order.getOrder_id());
				payment.setStatus(OrderStatus.PAY_STATUS_2);
				payment.setTransaction_id(payRsp.getTransaction_id());
				
				Map  resultMap = payRsp.getResult();
				resultMap.put("ORDERREQTRANSEQ", payRsp.getTransaction_id() );//order.getOrder_id()
				orderNFlowManager.pay(payment, false,false);//未支付
				getOrderResult().setPayReponse(payRsp);
				
				
				
			} else {
				orderNFlowManager.pay_auto(order.getOrder_id()); // 自动支付
			}
			setCanNext(false);//需要调拨
		}

	}
}

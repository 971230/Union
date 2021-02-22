package com.ztesoft.net.mall.core.pay;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.payment.CommonPaymentHander;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.*;

import java.util.List;

import params.bank.req.BankReq;
import params.payment.req.EditPaymentListReq;

public class PaymentHander  extends CommonPaymentHander{

	@Override
	public void execute() {
		//初始化beans
		init();
		
		//去掉  以下按batchid查询
		//Order order = getOrder();//自动支付,淘宝平台已经支付
		
		//弹出银行支付界面，选择银行支付处理
		PaymentLog payment = getOrderRequst().getPayment();
		String bankId =payment.getBank_id();
		
		//调用网银支付功能
		//Bank bank = commonPayHander.getBankById(bankId);
		BankReq req = new BankReq();
		req.setBank_id(bankId);
		Bank bank = bankServ.getBankByCode(req).getBank();
		Order order = null;
		List<Order> list = null;
		//add bu wui 支付金额强制为收取金额，防止页面串改金额
		Double pay_money = 0d;//getOrder().getOrder_amount();
		if(payment.getPaytype()!=null && payment.getPaytype().intValue()!=0){
			//批量提交
			list = orderManager.getByBatchID(getOrderRequst().getOrderId());
			for(Order o:list){
				pay_money += o.getOrder_amount()-o.getPaymoney();
			}
			order = list.get(0);
		}else{
			//单个订单提交
			order = getOrder();
			pay_money = order.getOrder_amount()-order.getPaymoney();
		}
		
		payment.setMoney(Double.valueOf(pay_money));
		OrderRequst or = getOrderRequst();
		PayRequest payReq = new PayRequest(pay_money,bank.getBank_code(),Consts.PAY_SOURCE_CLOUD_PAY,"commonPaymentHander",or.getOrderParam().getType_code()); 
		payReq.setOrder_id(getOrderRequst().getOrderId());
		/**
		 * 0订单ID 1批量ID
		 */
		payReq.setPayType(payment.getPaytype());
		PayReponse payRsp =paymentListServ.bankPay(payReq);
		
		PaymentList paymentList = new PaymentList();
		paymentList.setOrder_id(getOrder().getOrder_id());
		paymentList.setTransaction_id(payRsp.getTransaction_id());
		paymentList.setCreate_date(DBTUtil.current());
		if(payment.getPaytype()!=null && payment.getPaytype().intValue()!=0){
			for(Order o:list){
				o.setTransaction_id(payRsp.getTransaction_id());
			}
		}else{
			order.setTransaction_id(payRsp.getTransaction_id());
		}
		
		EditPaymentListReq preq = new EditPaymentListReq();
		preq.setTransactionId(paymentList.getTransaction_id());
		preq.setPaymentList(paymentList);
        paymentListServ.editPaymentList(preq);
		//commonPayHander.updatePayment(paymentList);
		
		/**
		 * orderID或batchID
		 */
		payment.setOrder_id(getOrderRequst().getOrderId());
		payment.setStatus(OrderStatus.PAY_STATUS_2);
		payment.setTransaction_id(payRsp.getTransaction_id());
		
		orderNFlowManager.pay(payment, false,false);//未支付
		getOrderResult().setPayReponse(payRsp);
		//String json = "{result:1,message:'支付'," +payRsp.toString() + "}";
		setCanNext(false);
	}
	
}

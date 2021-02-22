package com.ztesoft.net.mall.service;

import com.ztesoft.net.mall.core.model.PaymentLog;

/**
 * 退款
 * @作者 MoChunrun
 * @创建日期 2013-11-5 
 * @版本 V 1.0
 */
public interface IRefundManager {

	/**
	 * 按orderid 
	 * @作者 MoChunrun
	 * @创建日期 2013-11-5 
	 * @param order_id
	 * @return
	 */
	public PaymentLog qrySuccessPaymentLogByOrderId(String order_id);
	
	/**
	 * 退款 
	 * @作者 MoChunrun
	 * @创建日期 2013-11-5 
	 * @param paymentLog  已支持的支付记录
	 */
	public void refund(PaymentLog paymentLog);
	public void refund(String order_id);
	
}

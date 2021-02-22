package services;

import params.order.req.PaymentLogReq;
import params.order.resp.PaymentLogResp;
import params.pay.req.PaySucessReq;
import params.pay.resp.PaySucessResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.mall.core.model.PaymentList;

/**
 * 去付日志
* @作者 MoChunrun 
* @创建日期 2013-10-10 
* @版本 V 1.0
 */
public interface PaymentInf {

	/**
	 * 生成支付日志
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-9 
	 * @param Order
	 */
	public PaymentLogResp createPaymentLog(PaymentLogReq req) throws ApiBusiException;
	
	/**
	 * 去支付
	 * @作者 MoChunrun
	 * @创建日期 2013-10-21 
	 * @param req
	 * @return
	 */
	//public GoToPayResp goToPay(GoToPayReq req);
	
	/**
	 * 银行回调
	 * @作者 MoChunrun
	 * @创建日期 2013-10-21 
	 * @param req
	 * @return
	 */
	//public BankCallbackResp bankCallback(BankCallbackReq req);
	
	/**
	 * 支付成功后银行重定向地址
	 * @作者 MoChunrun
	 * @创建日期 2013-10-21 
	 * @param req
	 * @return
	 */
	//public BankRedirectResp bankRedirect(BankRedirectReq req);
	
	/**
	 * 支付成功
	 * @作者 MoChunrun
	 * @创建日期 2013-11-5 
	 * @param req
	 * @return
	 */
	public PaySucessResp paySuccess(PaySucessReq req);
	
	//public PaymentResp updatePayment(PaymentReq paymentReq);
	
	public void insertPaymentList(PaymentList paymentList);
	
}

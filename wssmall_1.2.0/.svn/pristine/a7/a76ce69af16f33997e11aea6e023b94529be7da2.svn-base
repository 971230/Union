package zte.net.iservice;

import params.onlinepay.req.OnlinePayReq;
import params.onlinepay.resp.OnlinePayResp;
import params.order.resp.PaymentListResp;
import params.paycfg.req.PaymentCfgBankReq;
import params.paycfg.resp.PaymentCfgBankResp;
import zte.params.order.req.PaymentListReq;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="支付管理API",summary="支付管理API[支付单-订单支付]")
public interface IPaymentServices {

	/**
	 * 添加订单
	 * @作者 wui
	 * @创建日期 2014-4-8 
	 * @param orderAddReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="添加订单",summary="添加订单")
	public PaymentListResp addPaymentList(PaymentListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="跳转银行",summary="跳转银行")
	public OnlinePayResp goToPay(OnlinePayReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="银行列表",summary="银行列表")
	public PaymentCfgBankResp queryCfgBankList(PaymentCfgBankReq req);

}

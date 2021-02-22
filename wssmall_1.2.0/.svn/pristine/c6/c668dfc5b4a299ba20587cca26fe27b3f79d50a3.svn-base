package services;

import params.onlinepay.req.OnlinePayCallbackReq;
import params.onlinepay.req.OnlinePayRedirectReq;
import params.onlinepay.req.OnlinePayReq;
import params.onlinepay.resp.OnlinePayCallbackResp;
import params.onlinepay.resp.OnlinePayRedirectResp;
import params.onlinepay.resp.OnlinePayResp;

/**
 * 在线支付接口
 * @作者 MoChunrun
 * @创建日期 2013-12-23 
 * @版本 V 1.0
 */
public interface OnlinePaymentInf {

	public OnlinePayResp goToPay(OnlinePayReq req);
	
	public OnlinePayCallbackResp payCallback(OnlinePayCallbackReq req);
	
	public OnlinePayRedirectResp payRedirect(OnlinePayRedirectReq req);
	
}

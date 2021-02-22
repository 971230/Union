package services;

import java.util.List;
import java.util.Map;

import params.onlinepay.req.OnlinePayCallbackReq;
import params.onlinepay.req.OnlinePayRedirectReq;
import params.onlinepay.req.OnlinePayReq;

import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.model.PaymentList;
import com.ztesoft.net.model.PayResult;
import com.ztesoft.net.model.PaymentAccount;
import com.ztesoft.net.model.PaymentCfgAttr;

public interface IOnlinePayParams {

	/**
	 * 点击支付构造参数
	 * @param cfg
	 * @param bank
	 * @param account
	 * @param attrs
	 * @param payment
	 * @param req
	 * @param resultMap
	 */
	public void packPayParams(PayCfg cfg,Bank bank,PaymentAccount account,List<PaymentCfgAttr> attrs,PaymentList payment,OnlinePayReq req,Map<String,String> resultMap);
	
	/**
	 * 支付回调
	 * @param cfg
	 * @param account
	 * @param payment
	 * @param req
	 * @return
	 */
	public PayResult checkCallBack(PayCfg cfg,PaymentAccount account,PaymentList payment,OnlinePayCallbackReq req);
	
	/**
	 * 支付show
	 * @param cfg
	 * @param account
	 * @param payment
	 * @param req
	 * @return
	 */
	public PayResult checkRedirect(PayCfg cfg,PaymentAccount account,PaymentList payment,OnlinePayRedirectReq req);
}

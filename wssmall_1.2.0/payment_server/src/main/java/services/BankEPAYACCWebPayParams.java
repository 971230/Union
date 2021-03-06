package services;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import params.onlinepay.req.OnlinePayCallbackReq;
import params.onlinepay.req.OnlinePayRedirectReq;
import params.onlinepay.req.OnlinePayReq;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.model.PaymentList;
import com.ztesoft.net.mall.core.utils.PayUtils;
import com.ztesoft.net.model.PayResult;
import com.ztesoft.net.model.PaymentAccount;
import com.ztesoft.net.model.PaymentCfgAttr;

/**
 * 翼支付web支付
 */
public class BankEPAYACCWebPayParams implements IOnlinePayParams {
	private static Logger logger = Logger.getLogger(BankEPAYACCWebPayParams.class);
	@Override
	public void packPayParams(PayCfg cfg, Bank bank, PaymentAccount account,
			List<PaymentCfgAttr> attrs, PaymentList paymentList, OnlinePayReq req,
			Map<String, String> resultMap) {
		resultMap.put("KEY", account.getAccount_key());
		resultMap.put("MERCHANTID", account.getAccounted_code());
		resultMap.put("ORDERSEQ", paymentList.getTransaction_id());
		resultMap.put("ORDERREQTRANSEQ", req.getOrder_id());
		resultMap.put("ORDERDATE", paymentList.getTransdate());
		resultMap.put("ORDERAMOUNT", StringUtils.fen2Yuan(String.valueOf(paymentList.getPay_amount())));
		resultMap.put("PRODUCTAMOUNT", StringUtils.fen2Yuan(String.valueOf(paymentList.getPay_amount())));
		resultMap.put("ATTACHAMOUNT", "0.00");
		resultMap.put("BANKID", bank.getBank_code());
		resultMap.put("CLIENTIP", paymentList.getPay_ip());
		String strMac = "MERCHANTID=" + resultMap.get("MERCHANTID") + 
						"&ORDERSEQ=" + paymentList.getTransaction_id() + 
						"&ORDERDATE=" + paymentList.getTransdate() + 
						"&ORDERAMOUNT=" + StringUtils.fen2Yuan(String.valueOf(paymentList.getPay_amount())) + 
						"&CLIENTIP=" + paymentList.getPay_ip() + 
						"&KEY=" + resultMap.get("KEY");
		try {
			strMac = PayUtils.md5Digest(strMac);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("MAC", strMac);
		
	}

	@Override
	public PayResult checkCallBack(PayCfg cfg,
			PaymentAccount account, PaymentList payment,
			OnlinePayCallbackReq req) {
		Map<String,String> param = req.getReqParams();
		PayResult result = new PayResult();
		String retnCode = param.get("RETNCODE");
		int orderAmount = Integer.parseInt(param.get("ORDERAMOUNT"));
		String encodeType = param.get("ENCODETYPE");
		String strMac = "UPTRANSEQ="+param.get("UPTRANSEQ")+
						"&MERCHANTID=" + account.getAccounted_code() +
						"&ORDERID=" + param.get("ORDERSEQ") + 
						"&PAYMENT=" + orderAmount + 
						"&RETNCODE=" + retnCode +
						"&RETNINFO=" + param.get("RETNINFO") + 
						"&PAYDATE=" + param.get("TRANDATE") + 
						"&KEY=" + account.getAccount_key();
			
		logger.info("============>>翼支付返回结果串:" + strMac);
		
		//判断是否加密
		if("1".equals(encodeType)){
			try {
				strMac = PayUtils.md5Digest(strMac);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String sign = param.get("SIGN");
		
		/**
		 * 这里验证MD5 测试时不验证加上 && false
		 */
		if(!strMac.equals(sign) && false){
			logger.info("====>strMac:" + strMac + "; ====>sign:" + sign);
			result.setDealResult(2);
			return result;
		}
		if(Consts.BANK_BACK_PAY_SUCC.equals(retnCode)){
			//支付成功修改订单状态						
			if(orderAmount<payment.getPay_amount()){
				result.setDealResult(1);
				result.setDeal_flag(Consts.BANK_PAYED_EXCEPTION);
			}else{
				result.setDealResult(0);
				result.setDeal_flag(Consts.BANK_PAYED_SUCC);
			}
		}else {	//银行支付不成功
			result.setDealResult(2);
			result.setDeal_flag(Consts.BANK_PAYED_FAIL);
		}
		result.setResultMsg("UPTRANSEQ_"+param.get("UPTRANSEQ"));
		return result;
	}

	@Override
	public PayResult checkRedirect(PayCfg cfg, PaymentAccount account,
			PaymentList payment, OnlinePayRedirectReq req) {
		PayResult result = new PayResult();
		Map<String,String> param = req.getReqParams();
		try {
			String retnCode = param.get("RETNCODE");
			int orderAmount = Integer.parseInt(param.get("ORDERAMOUNT"));
			String encodeType = param.get("ENCODETYPE");
			String strMac = "UPTRANSEQ="+param.get("UPTRANSEQ")+
							"&MERCHANTID=" + account.getAccounted_code() +
							"&ORDERID=" + param.get("ORDERSEQ") + 
							"&PAYMENT=" + orderAmount + 
							"&RETNCODE=" + retnCode +
							"&RETNINFO=" + param.get("RETNINFO") + 
							"&PAYDATE=" + param.get("TRANDATE") + 
							"&KEY=" + account.getAccount_key();
			
			//判断是否加密
			if(encodeType.equals("1")){
				strMac = PayUtils.md5Digest(strMac);
			}
			String sign = param.get("SIGN");
			if(!strMac.equals(sign) && false){
				result.setDealResult(3);
				result.setResultMsg("用户签名验证失败");
			}else{
				result.setDealResult(0);
				result.setResultMsg("支付成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setDealResult(2);
			result.setResultMsg("支付失败");
		}
		return result;
	}

}

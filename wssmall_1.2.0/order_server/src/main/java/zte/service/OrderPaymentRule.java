package zte.service;

import javax.annotation.Resource;

import params.order.req.PaymentLogReq;
import params.order.resp.PaymentLogResp;
import params.pay.req.PaySucessReq;
import rule.impl.PayBaseRule;
import services.PaymentInf;

public class OrderPaymentRule extends PayBaseRule {

	@Resource
	private PaymentInf paymentServ;
	
	@Override
	public PaymentLogResp computePayStatus(PaymentLogReq payRuleReq) {
		PaymentLogResp resp = new PaymentLogResp();
    	resp.setError_code("0");
		String reg = "3|4|5|6|7";
		String status=payRuleReq.getOrderOuters().get(0).getStatus();
		if(status==null)return resp;
    	//支付
    	if(status!=null && status.matches(reg)){
    		PaySucessReq payReq = new PaySucessReq();
    		payReq.setUserSessionId(payRuleReq.getUserSessionId());
    		payReq.setOrder_id(payRuleReq.getOrder().getOrder_id());
    		paymentServ.paySuccess(payReq);
    	}
    	return resp;
	}

}

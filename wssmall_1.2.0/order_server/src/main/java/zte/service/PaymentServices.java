package zte.service;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.onlinepay.req.OnlinePayReq;
import params.onlinepay.resp.OnlinePayResp;
import params.order.resp.PaymentListResp;
import params.pay.req.PaySucessReq;
import params.pay.resp.PaySucessResp;
import params.paycfg.req.PaymentCfgBankReq;
import params.paycfg.resp.PaymentCfgBankResp;
import services.OnlinePaymentInf;
import services.PaymentCfgInf;
import services.PaymentServ;
import services.ServiceBase;
import zte.net.iservice.IPaymentServices;
import zte.params.order.req.PaymentListReq;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.rop.annotation.ServiceMethodBean;

import consts.ConstsCore;

@ServiceMethodBean(version="1.0")
@ZteSoftCommentAnnotation(type = "class", desc = "支付服务", summary = "支付服务")
@Transactional(propagation = Propagation.REQUIRED)
public class PaymentServices extends ServiceBase implements IPaymentServices {

	@Resource
	private PaymentServ paymentServ;

	private OnlinePaymentInf onlinePaymentServ;
	private PaymentCfgInf paymentCfgServ;
	
	private void init(){
		if(null == onlinePaymentServ) onlinePaymentServ = ApiContextHolder.getBean("onlinePaymentServ");
		if(null == paymentCfgServ) paymentCfgServ = ApiContextHolder.getBean("paymentCfgServ");
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@ZteSoftCommentAnnotation(type = "method", desc = "订单支付成功", summary = "订单支付成功")
	public PaymentListResp addPaymentList(PaymentListReq req) {
		PaySucessReq paySucessReq = new PaySucessReq();
		paySucessReq.setOrder_id(req.getOrder_id());
		paySucessReq.setDeal_flag(req.getDeal_flag());
		PaySucessResp payResp = paymentServ.paySuccess(paySucessReq);
		PaymentListResp paySuccResp = new PaymentListResp();
		paySuccResp.setError_code(ConstsCore.ERROR_SUCC);
		return paySuccResp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单支付成功", summary = "订单支付成功")
	public OnlinePayResp goToPay(OnlinePayReq req) {
		//初始化beans
		init();
		
		return onlinePaymentServ.goToPay(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "银行列表", summary = "银行列表")
	public PaymentCfgBankResp queryCfgBankList(PaymentCfgBankReq req) {
		//初始化beans
		init();
		
		return paymentCfgServ.queryCfgBankList(req);
	}
}

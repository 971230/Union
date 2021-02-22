package services;

import params.payment.req.AddPaymentListReq;
import params.payment.req.EditPaymentListReq;
import params.payment.req.GetPaymentListReq;
import params.payment.resp.AddPaymentListResp;
import params.payment.resp.EditPaymentListResp;
import params.payment.resp.GetPaymentListResp;

import com.ztesoft.net.mall.core.model.PayReponse;
import com.ztesoft.net.mall.core.model.PayRequest;

public interface PaymentListInf {

	public AddPaymentListResp addPaymentList(AddPaymentListReq req);
	
	public EditPaymentListResp editPaymentList(EditPaymentListReq req);
	
	public GetPaymentListResp getPaymentListById(GetPaymentListReq req);
	
	public PayReponse bankPay(PayRequest payRequest);
	
}

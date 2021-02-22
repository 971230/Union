package services;

import params.payment.req.AddPaymentListReq;
import params.payment.req.EditPaymentListReq;
import params.payment.req.GetPaymentListReq;
import params.payment.resp.AddPaymentListResp;
import params.payment.resp.EditPaymentListResp;
import params.payment.resp.GetPaymentListResp;

import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.desposit.pay.ICommonPayHander;
import com.ztesoft.net.mall.core.model.PayReponse;
import com.ztesoft.net.mall.core.model.PayRequest;
import com.ztesoft.net.mall.core.model.PaymentList;
import com.ztesoft.net.mall.core.service.IPaymentListManager;

public class PaymentListServ extends ServiceBase implements PaymentListInf {

	private IPaymentListManager paymentListManager;
	
	private ICommonPayHander commonPayHander;
	
	@Override
	public AddPaymentListResp addPaymentList(AddPaymentListReq req) {
		paymentListManager.insertPayment(req.getPaymentList());
		AddPaymentListResp resp = new AddPaymentListResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public EditPaymentListResp editPaymentList(EditPaymentListReq req) {
		if(StringUtil.isEmpty(req.getDealFlag())){
			req.getPaymentList().setTransaction_id(req.getTransactionId());
			paymentListManager.edit(req.getPaymentList());
		}else{
			paymentListManager.updatePayment(req.getTransactionId(), req.getDealFlag());
		}
		EditPaymentListResp resp = new EditPaymentListResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public GetPaymentListResp getPaymentListById(GetPaymentListReq req) {
		PaymentList payment = paymentListManager.getPaymentById(req.getTransactionid());
		GetPaymentListResp resp = new GetPaymentListResp();
		resp.setPaymentList(payment);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}
	
	@Override
	public PayReponse bankPay(PayRequest payRequest) {
		return commonPayHander.bankPay(payRequest);
	}


	public IPaymentListManager getPaymentListManager() {
		return paymentListManager;
	}

	public void setPaymentListManager(IPaymentListManager paymentListManager) {
		this.paymentListManager = paymentListManager;
	}

	
	public ICommonPayHander getCommonPayHander() {
		return commonPayHander;
	}

	public void setCommonPayHander(ICommonPayHander commonPayHander) {
		this.commonPayHander = commonPayHander;
	}

}

package services;

import java.util.List;

import params.paycfg.req.PaymentCfgBankReq;
import params.paycfg.req.PaymentCfgListReq;
import params.paycfg.req.PaymentCfgReq;
import params.paycfg.resp.PaymentCfgBankResp;
import params.paycfg.resp.PaymentCfgListResp;
import params.paycfg.resp.PaymentCfgResp;

import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.service.IBankManager;
import com.ztesoft.net.mall.core.service.IPaymentManager;

public class PaymentCfgServ extends ServiceBase implements PaymentCfgInf {

	private IPaymentManager paymentManager;
	private IBankManager bankManager;
	
	@Override
	public PaymentCfgListResp queryPaymentCfgList(PaymentCfgListReq req) {
		PaymentCfgListResp resp = new PaymentCfgListResp();
		List<PayCfg> list = paymentManager.list();
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setPayCfgList(list);
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public PaymentCfgBankResp queryCfgBankList(PaymentCfgBankReq req) {
		PaymentCfgBankResp resp = new PaymentCfgBankResp();
		List<Bank> bankList = bankManager.qryBankByPaymentCfgId(req.getPayment_cfg_id());
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setBankList(bankList);
		this.addReturn(req, resp);
		return resp;
	}
	
	@Override
	public PaymentCfgResp queryPaymentCfgById(PaymentCfgReq req) {
		PayCfg payCfg = paymentManager.get(req.getPayment_cfg_id());
		PaymentCfgResp resp = new PaymentCfgResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setPaymentCfg(payCfg);
		this.addReturn(req, resp);
		return resp;
	}

	public IPaymentManager getPaymentManager() {
		return paymentManager;
	}

	public void setPaymentManager(IPaymentManager paymentManager) {
		this.paymentManager = paymentManager;
	}

	public IBankManager getBankManager() {
		return bankManager;
	}

	public void setBankManager(IBankManager bankManager) {
		this.bankManager = bankManager;
	}

}

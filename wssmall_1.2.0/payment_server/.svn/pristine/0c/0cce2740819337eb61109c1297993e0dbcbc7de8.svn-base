package zte.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import services.ServiceBase;
import zte.net.iservice.IPaymentConfigService;
import zte.params.cfg.req.PaymentBankListReq;
import zte.params.cfg.req.PaymentConfigListReq;
import zte.params.cfg.resp.PaymentBankListResp;
import zte.params.cfg.resp.PaymentConfigListResp;

import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.service.IBankManager;
import com.ztesoft.net.mall.core.service.IPaymentManager;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;
import org.apache.commons.lang3.StringUtils;

@ServiceMethodBean(version="1.0")
public class PaymentConfigService extends ServiceBase implements
		IPaymentConfigService {

	@Resource
	private IPaymentManager paymentManager;
	@Resource
	private IBankManager bankManager;
	
	@Override
	@ServiceMethod(method="zte.paymentConfigService.paymentcfg.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PaymentConfigListResp listPaymentCfgList(PaymentConfigListReq req) {
		PaymentConfigListResp resp = new PaymentConfigListResp();
		List<PayCfg> list = null;
		if(StringUtils.isEmpty(req.getPayment_id())){
			list = paymentManager.list();
		}else{
			list = new ArrayList<PayCfg>();
			PayCfg cfg = paymentManager.get(req.getPayment_id());
			list.add(cfg);
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setPaymentCfgList(list);
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ServiceMethod(method="zte.paymentConfigService.bank.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PaymentBankListResp listPaymentBankList(PaymentBankListReq req) {
		PaymentBankListResp resp = new PaymentBankListResp();
		List<Bank> list = null;
		if(!StringUtils.isEmpty(req.getPayment_id()) && StringUtils.isEmpty(req.getBank_id())){
			list = bankManager.qryBankByPaymentCfgId(req.getPayment_id());
		}else{
			list = new ArrayList<Bank>();
			Bank bank = bankManager.getBankByBankId(req.getBank_id());
			list.add(bank);
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setBankList(list);
		this.addReturn(req, resp);
		return resp;
	}

}

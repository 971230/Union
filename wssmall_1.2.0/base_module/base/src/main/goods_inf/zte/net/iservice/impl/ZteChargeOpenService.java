package zte.net.iservice.impl;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.remote.basic.inf.IChargeBasicService;
import com.ztesoft.remote.basic.params.req.CardTrafficRequest;
import com.ztesoft.remote.basic.params.req.ChargeFeeRequest;
import com.ztesoft.remote.basic.params.req.ChargeWalletRequest;
import com.ztesoft.remote.basic.params.req.LbsRechargeReq;
import com.ztesoft.remote.basic.params.resp.CardTrafficReponse;
import com.ztesoft.remote.basic.params.resp.ChargeFeeResponse;
import com.ztesoft.remote.basic.params.resp.ChargeWalletResponse;
import com.ztesoft.remote.basic.params.resp.LbsRechargeResp;
import com.ztesoft.remote.basic.utils.BasicConst;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version="1.0")
public class ZteChargeOpenService {
	
//	@Resource
	private IChargeBasicService chargeBasicService;
	
	private void init() {
		if (null == chargeBasicService) chargeBasicService = ApiContextHolder.getBean("chargeBasicService");
	}
	
	@ServiceMethod(method = BasicConst.API_PREFIX + "chargeFee",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public ChargeFeeResponse chargeFee(ChargeFeeRequest request){
		this.init();
		return chargeBasicService.chargeFee(request);
	}
	
	@ServiceMethod(method = BasicConst.API_PREFIX+"cardTraffic",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public CardTrafficReponse cardTraffic(CardTrafficRequest request){
		this.init();
		return chargeBasicService.cardTraffic(request);
	}
	
	@ServiceMethod(method = BasicConst.API_PREFIX+"chargeWallet",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public ChargeWalletResponse chargeWallet(ChargeWalletRequest request){
		this.init();
		return chargeBasicService.chargeWallet(request);
	}
	
	@ServiceMethod(method = BasicConst.API_PREFIX+"lbsRecharge",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public LbsRechargeResp lbsRecharge(LbsRechargeReq request) {
		this.init();
		return chargeBasicService.lbsRecharge(request);
	}
}

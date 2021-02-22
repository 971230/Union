package com.ztesoft.test.dubbo;

import junit.framework.Assert;
import params.ZteResponse;

import com.ztesoft.api.ZteClient;
import com.ztesoft.remote.basic.params.req.CardTrafficRequest;
import com.ztesoft.remote.basic.params.req.ChargeFeeRequest;
import com.ztesoft.remote.basic.params.req.ChargeWalletRequest;
import com.ztesoft.remote.basic.params.req.LbsRechargeReq;
import com.ztesoft.remote.basic.params.resp.CardTrafficReponse;
import com.ztesoft.remote.basic.params.resp.ChargeFeeResponse;
import com.ztesoft.remote.basic.params.resp.ChargeWalletResponse;
import com.ztesoft.remote.basic.params.resp.LbsRechargeResp;
import com.ztesoft.test.dubbo.base.DubboClientTest;

public class ChargeDubboClientTest extends DubboClientTest{
	public void chargeFee(){
		ChargeFeeRequest chargeFeeRequest=new ChargeFeeRequest();
		chargeFeeRequest.setProduct_id("201209024753");
		chargeFeeRequest.setAccNbr("");//号码
		chargeFeeRequest.setChargeMoney(100.00);
		chargeFeeRequest.setService_code("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(chargeFeeRequest, ChargeFeeResponse.class);
		logger.info("---------"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
	}
	public void cardTraffic(){
		CardTrafficRequest cardTrafficRequest=new CardTrafficRequest();
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(cardTrafficRequest, CardTrafficReponse.class);
		logger.info("---------"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
	}
	public void chargeWallet(){
		ChargeWalletRequest chargeWalletRequest=new ChargeWalletRequest();
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(chargeWalletRequest, ChargeWalletResponse.class);
		logger.info("---------"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
	}
	public void lbsRecharge(){
		LbsRechargeReq lbsRechargeReq=new LbsRechargeReq();
		lbsRechargeReq.setAcc_nbr("");
		lbsRechargeReq.setCharge_type("");
		lbsRechargeReq.setOrd_amount("");
		lbsRechargeReq.setOrd_att("");
		lbsRechargeReq.setOrd_no("");
		lbsRechargeReq.setOrd_time("");
		lbsRechargeReq.setNotify_url("");
		lbsRechargeReq.setProduct_id("");
		lbsRechargeReq.setService_code("");
		lbsRechargeReq.setServerUrl("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(lbsRechargeReq, LbsRechargeResp.class);
		logger.info("---------"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
		
	}
}

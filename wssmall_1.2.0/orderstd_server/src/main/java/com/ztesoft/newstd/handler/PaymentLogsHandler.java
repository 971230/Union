package com.ztesoft.newstd.handler;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderPayBusiRequest;

/**
 * @author licong
 *
 */
public class PaymentLogsHandler implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		OrderPayBusiRequest orderPayBusiRequest = (OrderPayBusiRequest) params.getBusiRequest();
		String value = orderPayBusiRequest.getPay_method();
/*		String pay_method = orderPayBusiRequest.getPay_method();
*/	    String order_from = params.getOrder_from(); // 订单来源
		if (EcsOrderConsts.PAY_METHOD_ZFB.equals(value) && !"10093".equals(order_from)) {
			orderPayBusiRequest.setPay_method(EcsOrderConsts.PAY_METHOD_QEZF);
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setBusiRequest(orderPayBusiRequest);
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
	}

}

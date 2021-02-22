package com.ztesoft.net.attr.hander;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class PayTimeHandler implements IAttrHandler{

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(params.getValue());
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		String paytype =  CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.PAY_TYPE);
		String send_type = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.SENDING_TYPE);
		String payTime = req.getNew_value();
		//支付时间为空并且不是货到付款的时候
		if(StringUtils.isEmpty(payTime)&&!EcsOrderConsts.PAY_TYPE_HDFK.equals(paytype)&&!EcsOrderConsts.SHIPPING_TYPE_XJ.equals(send_type)){
			AttrInstLoadResp resp = new AttrInstLoadResp();
			resp.setField_name(AttrConsts.PAY_TIME);
			resp.setField_desc("支付时间");
			//resp.setCheck_message(resp.getField_desc()+"不能为空。");
			return resp;
		}
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		String paytype =  CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.PAY_TYPE);
		String ship_type = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.SENDING_TYPE);
		String payTime = req.getNew_value();
		//支付时间为空并且不是货到付款的时候
		if(StringUtils.isEmpty(payTime)&&!EcsOrderConsts.PAY_TYPE_HDFK.equals(paytype)&&!EcsOrderConsts.SHIPPING_TYPE_XJ.equals(ship_type)){
			AttrInstLoadResp resp = new AttrInstLoadResp();
			resp.setField_name(AttrConsts.PAY_TIME);
			resp.setField_desc("支付时间");
			//resp.setCheck_message(resp.getField_desc()+"不能为空。");
			return resp;
		}
		return null;
	}

}

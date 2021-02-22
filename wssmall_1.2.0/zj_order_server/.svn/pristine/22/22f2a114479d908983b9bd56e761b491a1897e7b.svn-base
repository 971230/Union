package com.ztesoft.net.attr.hander;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

public class OrderDisfeeHandler implements IAttrHandler{
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		// TODO Auto-generated method stub
		String order_id = params.getOrder_id();// 订单ID
		String value = params.getValue(); // 原始值
		
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(value);
		
		if(value==null||StringUtils.isEmpty(value)){
			resp.setField_value("0");
		}
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

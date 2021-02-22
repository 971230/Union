package com.ztesoft.net.attr.hander;

import java.util.Map;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;

public class SimCardSkuHandler implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		String goods_type = (String) orderAttrValues.get("type_id");
		String specification_code = (String) orderAttrValues.get(AttrConsts.SPECIFICATION_CODE);
		String sim_type = (String) orderAttrValues.get(AttrConsts.SIM_TYPE);
		String white_card_type = (String) orderAttrValues.get(AttrConsts.WHITE_CART_TYPE);
		//String sim_card_sku = CommonDataFactory.getInstance().getNumberSku(order_id,goods_type,specification_code,sim_type,white_card_type);
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(null);
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

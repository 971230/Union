package com.ztesoft.net.attr.hander;

import java.util.Map;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
/**
 * 是否需要发货
 * @author Administrator
 *
 */
public class IsSendGoodsHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		
		String is_send_goods = EcsOrderConsts.NO_DEFAULT_VALUE;
		String sending_type = (String) orderAttrValues.get(AttrConsts.SENDING_TYPE);
		String has_entity_prod = (String) orderAttrValues.get(AttrConsts.IS_PHISICS);
		//配送方式不是自提、有实物货品，需要进行发货
		if(!EcsOrderConsts.SENDING_TYPE_ZT.equals(sending_type) && EcsOrderConsts.IS_DEFAULT_VALUE.equals(has_entity_prod)){
			is_send_goods = has_entity_prod;
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(is_send_goods);
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

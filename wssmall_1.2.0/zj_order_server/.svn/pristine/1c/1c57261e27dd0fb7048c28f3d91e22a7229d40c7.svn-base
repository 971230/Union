package com.ztesoft.net.attr.hander;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.eop.sdk.database.BaseSupport;

public class OrderOriginHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		String order_from = (String) orderAttrValues.get(AttrConsts.ORDER_FROM);
		String order_origin = null;
		List<Map> relations = CommonDataFactory.getInstance().getDictRelationListData(StypeConsts.DIC_ORDER_ORIGIN);
		if(relations!=null && relations.size()>0){
			for(Map relation : relations){
				if(order_from.equals(Const.getStrValue(relation, "field_value"))){
					order_origin = Const.getStrValue(relation, "other_field_value");
					break ;
				}
			}
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(order_origin);
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

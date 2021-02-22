package com.ztesoft.net.attr.hander;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class IsPhysicsHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		Map orderAttrValues = params.getOrderAttrValues();
		String goods_type = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.TYPE_ID);
		String has_entity_product = CommonDataFactory.getInstance().hasEntityProduct(order_id, goods_type);
		if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(has_entity_product)){//如果没有实物货品,判断赠品中是否有实物
			String is_old = (String) orderAttrValues.get(AttrConsts.IS_OLD);
			//号卡、新用户有实物货品
			if(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type) && EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_old)){
				has_entity_product = EcsOrderConsts.IS_DEFAULT_VALUE;
			}
			//判断赠品是否有实物
			List<AttrGiftInfoBusiRequest> gifts = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
			for(AttrGiftInfoBusiRequest gift : gifts){
				if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(gift.getIs_virtual())){
					has_entity_product = EcsOrderConsts.IS_DEFAULT_VALUE;
					break ;
				}
			}
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(has_entity_product);
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

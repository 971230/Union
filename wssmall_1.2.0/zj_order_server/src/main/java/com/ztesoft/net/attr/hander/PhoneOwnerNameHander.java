package com.ztesoft.net.attr.hander;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

public class PhoneOwnerNameHander implements IAttrHandler{
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(params.getValue());
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null; 
		String order_id = req.getOrder_id() ;
		
		String  is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String  goods_type  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		String  phone_owner_name = req.getNew_value();
				//req.getNew_value();
		if(StringUtils.isEmpty(phone_owner_name)&&(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type)||EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type)||EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type)||EcsOrderConsts.IS_OLD_1.equals(is_old))){
			resp = new AttrInstLoadResp();
			resp = new AttrInstLoadResp();
			resp.setField_desc("客户名称");
			resp.setField_value(phone_owner_name);
			resp.setCheck_message(resp.getField_desc()+"不能为空。");
		}
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null; 
		String order_id = req.getOrder_id() ;
		
		String  is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String  goods_type  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		String  phone_owner_name = req.getNew_value();//CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME);
		if(StringUtils.isEmpty(phone_owner_name)&&(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type)||EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type)||EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type)||EcsOrderConsts.IS_OLD_1.equals(is_old))){
			resp = new AttrInstLoadResp();
			resp = new AttrInstLoadResp();
			resp.setField_desc("客户名称");
			resp.setField_value(phone_owner_name);
			resp.setCheck_message(resp.getField_desc()+"不能为空。");
		}
		return resp;
	}

}

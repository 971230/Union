package com.ztesoft.net.attr.hander;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class CertFailureTimeHander implements IAttrHandler{
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = new AttrInstLoadResp();
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(params.getOrder_id(), AttrConsts.GOODS_TYPE);
		
		if(StringUtils.isEmpty(params.getValue())&&!SpecConsts.TYPE_ID_20003.equals(goods_type)){
			resp.setField_value(EcsOrderConsts.DEFAULT_CERTI_FAILURE_TIME);
		}else{
			resp.setField_value(params.getValue());
		}
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null; 
		String order_id = req.getOrder_id() ;
		
		String  is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String  goods_type  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		String  cert_failure_time = req.getNew_value(); // CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_FAILURE_TIME);
		if(StringUtils.isEmpty(cert_failure_time)&&(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type)||EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type)||EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type))&&!EcsOrderConsts.IS_OLD_1.equals(is_old)){
			resp = new AttrInstLoadResp();
			resp.setField_desc("证件有效期");
			resp.setField_value(cert_failure_time);
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
		String  cert_failure_time = req.getNew_value();//CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_FAILURE_TIME);
		if(StringUtils.isEmpty(cert_failure_time)&&(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type)||EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type)||EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type))&&!EcsOrderConsts.IS_OLD_1.equals(is_old)){
			resp = new AttrInstLoadResp();
			resp.setField_desc("证件有效期");
			resp.setField_value(cert_failure_time);
			resp.setCheck_message(resp.getField_desc()+"不能为空。");
		}
		return resp;
	}

}

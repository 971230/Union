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

public class BaiDuIdHander implements IAttrHandler{

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
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
		AttrInstLoadResp resp = null; 
		
		String order_from  = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.ORDER_FROM);
		String baidu_id = req.getNew_value();//CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.BAIDU_ID);
		
		if(EcsOrderConsts.ORDER_FROM_10039.equals(order_from)&&StringUtils.isEmpty(baidu_id)){
			resp = new AttrInstLoadResp();
			resp = new AttrInstLoadResp();
			resp.setField_desc("百度账号");
			resp.setField_value(baidu_id);
			resp.setCheck_message("百度账号不能为空。");
		}
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
        AttrInstLoadResp resp = null; 
		
		String order_from  = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.ORDER_FROM);
		String baidu_id = req.getNew_value();// CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.BAIDU_ID);
		
		if(EcsOrderConsts.ORDER_FROM_10039.equals(order_from)&&StringUtils.isEmpty(baidu_id)){
			resp = new AttrInstLoadResp();
			resp = new AttrInstLoadResp();
			resp.setField_desc("百度账号");
			resp.setField_value(baidu_id);
			resp.setCheck_message("百度账号不能为空。");
		}
		return resp;
	}

}

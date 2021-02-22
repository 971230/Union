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

public class Reserve6Hander implements IAttrHandler{
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
		String order_id = req.getOrder_id() ;
		
		String  order_from  = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.ORDER_FROM);
		String  reserve6 = req.getNew_value();//CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.RESERVE6);
		if(EcsOrderConsts.ORDER_FROM_10039.equals(order_from)&&StringUtils.isEmpty(reserve6)){
			resp = new AttrInstLoadResp();
			resp = new AttrInstLoadResp();
			resp.setField_desc("百度冻结结束时间");
			resp.setField_value(reserve6);
			resp.setCheck_message("百度冻结结束时间不能为空。");
		}
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null; 
		String order_id = req.getOrder_id() ;
		
		String  order_from  = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.ORDER_FROM);
		String  reserve6 = req.getNew_value();//CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.RESERVE6);
		if(EcsOrderConsts.ORDER_FROM_10039.equals(order_from)&&StringUtils.isEmpty(reserve6)){
			resp = new AttrInstLoadResp();
			resp = new AttrInstLoadResp();
			resp.setField_desc("百度冻结结束时间");
			resp.setField_value(reserve6);
			resp.setCheck_message("百度冻结结束时间不能为空。");
		}
		return resp;
	}

}

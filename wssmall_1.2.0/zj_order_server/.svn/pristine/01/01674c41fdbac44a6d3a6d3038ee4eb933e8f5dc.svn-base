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

public class GroupCodeHader implements IAttrHandler{
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
		String customerType = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.CUSTOMER_TYPE);
		String group_code = req.getNew_value();//CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.GROUP_CODE); 
		
		if(EcsOrderConsts.CUSTOMER_CUST_TYPE_JTKH.equals(customerType)&&StringUtils.isEmpty(group_code)){
			resp = new AttrInstLoadResp();
			resp = new AttrInstLoadResp();
			resp.setField_desc("集团编码");
			resp.setField_value(group_code);
			resp.setCheck_message("集团编码不能为空。");
		}
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null; 
		String customerType = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.CUSTOMER_TYPE);
		String group_code = req.getNew_value();// CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.GROUP_CODE); 
		if(EcsOrderConsts.CUSTOMER_CUST_TYPE_JTKH.equals(customerType)&&StringUtils.isEmpty(group_code)){
			resp = new AttrInstLoadResp();
			resp = new AttrInstLoadResp();
			resp.setField_desc("集团编码");
			resp.setField_value(group_code);
			resp.setCheck_message("集团编码不能为空。");
		}
		return resp;
	}

}

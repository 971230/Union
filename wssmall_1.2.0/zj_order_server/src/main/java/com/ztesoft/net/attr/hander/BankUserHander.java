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

public class BankUserHander implements IAttrHandler{

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
		
		String  bill_type  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BILL_TYPE);
		String  bank_user = req.getNew_value();//CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BANK_USER);
		if(EcsOrderConsts.BILL_TYPE_15.equals(bill_type)&&StringUtils.isEmpty(bank_user)){
			resp = new AttrInstLoadResp();
			resp.setField_desc("银行账户名");
			resp.setField_value(bank_user);
			resp.setCheck_message(resp.getField_desc()+"不能为空。");
		}
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null; 
		String order_id = req.getOrder_id() ;
		
		String  bill_type  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BILL_TYPE);
		String  bank_user = req.getNew_value();//CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BANK_USER);
		if(EcsOrderConsts.BILL_TYPE_15.equals(bill_type)&&StringUtils.isEmpty(bank_user)){
			resp = new AttrInstLoadResp();
			resp.setField_desc("银行账户名");
			resp.setField_value(bank_user);
			resp.setCheck_message(resp.getField_desc()+"不能为空。");
		}
		return resp;
	}

}

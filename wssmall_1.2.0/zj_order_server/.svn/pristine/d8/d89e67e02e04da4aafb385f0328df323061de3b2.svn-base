package com.ztesoft.net.attr.hander;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;


/**
 * 稽核状态初始化
 */
public class AuditMoneyStatusAttrHandler implements IAttrHandler {

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_0);
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		 
		//resp.setField_value(ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_0);
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
//		resp.setCheck_message("XXX字段不能为空！");
		return resp;
	}

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.ztesoft.net.attr.hander;

import com.ztesoft.api.ApiBusiException;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;


/**
 * 公共属性转换类
 * @作者 MoChunrun
 * @创建日期 2014-9-23 
 * @版本 V 1.0
 */
public class CommonAttrHandler implements IAttrHandler {

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(params.getValue());
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		 
		//req.getNew_value();
//		resp.setField_value("126");
//		resp.setCheck_message("XXX字段不能为空！");
		
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

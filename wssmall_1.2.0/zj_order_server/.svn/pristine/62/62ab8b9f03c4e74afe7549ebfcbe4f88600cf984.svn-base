package com.ztesoft.net.attr.hander;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import commons.CommonTools;

import consts.ConstsCore;


/**
 * 公共属性转换类
 * @作者 MoChunrun
 * @创建日期 2014-9-23 
 * @版本 V 1.0
 */
public class BssOperAttrHander implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		AttrSyLoadResp resp = new AttrSyLoadResp();
		resp.setError_code(ConstsCore.ERROR_SUCC);
		
		//获取配送方式
		String sending_type = oo.getOuter().getSending_type();
		String tempValue=CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.SHIPPING_TYPE,sending_type);
		if(!StringUtils.isEmpty(tempValue))
			sending_type = tempValue;
		String bss_code = oo.getValue();
		if(EcsOrderConsts.SHIPPING_TYPE_XJ.equals(sending_type) && StringUtils.isEmpty(bss_code)){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			CommonTools.addBusiError(ConstsCore.ERROR_FAIL, "BSS工号不能为空!");
		}
		return resp;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(params.getValue());
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		return resp;
	}
}

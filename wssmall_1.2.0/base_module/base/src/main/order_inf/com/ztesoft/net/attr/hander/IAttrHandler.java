package com.ztesoft.net.attr.hander;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;

public interface IAttrHandler {

	
	/**
	 * 属性同步前置验证
	 * @param req
	 * @return
	 */
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException;
	
	/**
	 * 订单标准化属性值设置
	 * @param params
	 * @return
	 */
	public AttrInstLoadResp handler(AttrSwitchParams params);
	
	
	/**
	 * 页面装载属性数据验证，初始值设置
	 * @param req
	 * @return
	 */
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req);
	
	/**
	 * 页面保存属性实例数据设置
	 * @param req
	 * @return
	 */
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req);
}

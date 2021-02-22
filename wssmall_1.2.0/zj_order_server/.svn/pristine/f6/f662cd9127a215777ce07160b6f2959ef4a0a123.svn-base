package com.ztesoft.net.attr.hander;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderWMSKeyInfoBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;

/**
 * 自动化关键信息表数据初始化
 * @author Rapon
 *
 */
public class WMSKeyInfoHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
//		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		try {
			OrderWMSKeyInfoBusiRequest wmsKeyInfo = new OrderWMSKeyInfoBusiRequest();
			
			wmsKeyInfo.setOrder_id(order_id);
			wmsKeyInfo.setSource_from(ManagerUtils.getSourceFrom());
			
			wmsKeyInfo.setDb_action(ConstsCore.DB_ACTION_INSERT);
			wmsKeyInfo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			wmsKeyInfo.store();
		} catch (Exception e) {
			e.printStackTrace();
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value("");
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
	}

}

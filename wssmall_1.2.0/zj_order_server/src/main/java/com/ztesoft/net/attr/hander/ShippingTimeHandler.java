package com.ztesoft.net.attr.hander;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.utils.AttrUtils;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
/**
 * 收货地址、邮寄地址
 * @author Administrator
 *
 */
public class ShippingTimeHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(params.getValue()); // 原始值);
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		String str = AttrUtils.isShowShipVisiable(req.getOrder_id());
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(req.getOrder_id()).getOrderExtBusiRequest().getFlow_trace_id();
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(str)&&StringUtil.isEmpty(req.getNew_value())&&(EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)||EcsOrderConsts.DIC_ORDER_NODE_F.equals(flow_trace_id)))
		{
			AttrInstLoadResp resp = new AttrInstLoadResp();
			resp.setField_name(AttrConsts.SHIPPING_TIME);
			resp.setCheck_message("配送时间不能为空。");
			return resp;
		}
			return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		String str = AttrUtils.isShowShipVisiable(req.getOrder_id());
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(req.getOrder_id()).getOrderExtBusiRequest().getFlow_trace_id();
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(str)&&StringUtil.isEmpty(req.getNew_value())&&(EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)||EcsOrderConsts.DIC_ORDER_NODE_F.equals(flow_trace_id)))
		{
			AttrInstLoadResp resp = new AttrInstLoadResp();
			resp.setField_name(AttrConsts.SHIPPING_TIME);
			resp.setCheck_message("配送时间不能为空。");
			return resp;
		}
			return null;
	}

}

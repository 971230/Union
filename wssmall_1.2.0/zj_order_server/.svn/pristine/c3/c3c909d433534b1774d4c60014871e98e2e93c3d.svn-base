package com.ztesoft.net.attr.hander;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class SimIdHandler implements IAttrHandler{

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(req.getOrder_id());
		String has = CommonDataFactory.getInstance().hasTypeOfProduct(req.getOrder_id(), SpecConsts.TYPE_ID_20002);
		String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		AttrInstLoadResp resp = null;
		if(StringUtil.isEmpty(req.getNew_value())&&EcsOrderConsts.DEFAULT_STR_ONE.equals(has)&&EcsOrderConsts.DIC_ORDER_NODE_D.equals(trace_id)){
			resp.setField_name(AttrConsts.SIMID);
			resp.setField_desc("SIM卡号");
			resp.setCheck_message(resp.getField_desc()+"不能为空");
		}
		
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(req.getOrder_id());
		String has = CommonDataFactory.getInstance().hasTypeOfProduct(req.getOrder_id(), SpecConsts.TYPE_ID_20002);
		String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		AttrInstLoadResp resp = null;
		if(StringUtil.isEmpty(req.getNew_value())&&EcsOrderConsts.DEFAULT_STR_ONE.equals(has)&&EcsOrderConsts.DIC_ORDER_NODE_D.equals(trace_id)){
			resp.setField_name(AttrConsts.SIMID);
			resp.setField_desc("SIM卡号");
			resp.setCheck_message(resp.getField_desc()+"不能为空");
		}
		
		return resp;
	}

}

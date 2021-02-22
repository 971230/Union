package com.ztesoft.net.attr.hander;

import org.drools.core.util.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;


/**
 * ICCID handler
 * @author duan.shaochu
 *
 */
public class ICCIDAttrHandler implements IAttrHandler {

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(params.getValue());
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		//开户及业务办理环节后向激活订单必填ICCID字段
		String order_id = req.getOrder_id();
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
		if(EcsOrderConsts.DIC_ORDER_NODE_Y.equals(flow_trace_id)){//EcsOrderConsts.DIC_ORDER_NODE_D.equals(flow_trace_id) || 
			//开户、业务办理环节需要校验开户开户流水号
			String is_later_active = EcsOrderConsts.NO_DEFAULT_VALUE;
			OrderRealNameInfoBusiRequest realNameBus = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderRealNameInfoBusiRequest();
			if(null != realNameBus){
				is_later_active = realNameBus.getLater_active_flag();
			}
			String iccid = req.getNew_value();
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_later_active) && StringUtils.isEmpty(iccid)){
				resp = new AttrInstLoadResp();
				resp.setField_desc("ICCID");
				resp.setField_name(AttrConsts.ICCID);
				resp.setCheck_message("请输入ICCID!");
				return resp;
			}
		}
		
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		//开户及业务办理环节后向激活订单必填ICCID字段
		String order_id = req.getOrder_id();
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
		if(EcsOrderConsts.DIC_ORDER_NODE_Y.equals(flow_trace_id)){//EcsOrderConsts.DIC_ORDER_NODE_D.equals(flow_trace_id) || 
			//开户、业务办理环节需要校验开户开户流水号
			String is_later_active = EcsOrderConsts.NO_DEFAULT_VALUE;
			OrderRealNameInfoBusiRequest realNameBus = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderRealNameInfoBusiRequest();
			if(null != realNameBus){
				is_later_active = realNameBus.getLater_active_flag();
			}
			String iccid = req.getNew_value();
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_later_active) && StringUtils.isEmpty(iccid)){
				resp = new AttrInstLoadResp();
				resp.setField_desc("ICCID");
				resp.setField_name(AttrConsts.ICCID);
				resp.setCheck_message("请输入ICCID!");
				return resp;
			}
		}
		return resp;
	}

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

}

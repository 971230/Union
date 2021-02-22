package com.ztesoft.net.attr.hander;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

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
import com.ztesoft.net.model.AttrDef;
/**
 * 实收运费
 * @author Administrator
 *
 */
public class NShippingAmountHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		String goods_id = params.getGoods_id(); // 商品ID 只有子订单或货品单才有值
		String pro_goods_id = params.getPro_goods_id(); // 货品ID 只有货品单才有值
		String order_from = params.getOrder_from(); // 订单来源
		String value = params.getValue(); // 原始值
		AttrDef attrDef = params.getAttrDef(); // 属性定议配置信息
		Map orderAttrValues;// 订单所有属性值
		
		if(StringUtils.isEmpty(value)){
			value = EcsOrderConsts.DEFAULT_FEE_ZERO;
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(value);
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		String str = AttrUtils.isShowShipVisiable(req.getOrder_id());
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(req.getOrder_id()).getOrderExtBusiRequest().getFlow_trace_id();
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(str)&&StringUtil.isEmpty(req.getNew_value())&&EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id))
		{
			AttrInstLoadResp resp = new AttrInstLoadResp();
			resp.setField_name(AttrConsts.N_SHIPPING_AMOUNT);
			resp.setCheck_message("实收运费不能为空。");
			return resp;
		}
			return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		String str = AttrUtils.isShowShipVisiable(req.getOrder_id());
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(req.getOrder_id()).getOrderExtBusiRequest().getFlow_trace_id();
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(str)&&StringUtil.isEmpty(req.getNew_value())&&EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id))
		{
			AttrInstLoadResp resp = new AttrInstLoadResp();
			resp.setField_name(AttrConsts.N_SHIPPING_AMOUNT);
			resp.setCheck_message("实收运费不能为空。");
			return resp;
		}
			return null;
	}

}

package com.ztesoft.net.attr.hander;

import java.util.Map;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.AttrDef;
/**
 * 4G套餐类型
 * @author Administrator
 * DIY-4G DIY套餐，MAIN-4G基本套餐
 */
public class IsDiyPlanHandler implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
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
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		
		//取套餐分类ID
		String cat_id = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.CAT_ID);
		String plan_type = EcsOrderConsts.EMPTY_STR_VALUE;
		if(EcsOrderConsts.PLAN_CAT_ID_DIY.equals(cat_id)){
			plan_type = EcsOrderConsts.OFFER_TYPE_4G_DIY;
		}
		else if(EcsOrderConsts.PLAN_CAT_ID_MAIN.equals(cat_id)){
			plan_type = EcsOrderConsts.OFFER_TYPE_4G_MAIN;
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(plan_type);
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

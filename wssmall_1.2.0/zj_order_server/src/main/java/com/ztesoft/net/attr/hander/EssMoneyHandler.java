package com.ztesoft.net.attr.hander;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;

public class EssMoneyHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
	//	String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		String goods_id = params.getGoods_id(); // 商品ID 只有子订单或货品单才有值
		//String pro_goods_id = params.getPro_goods_id(); // 货品ID 只有货品单才有值
		String order_from = params.getOrder_from(); // 订单来源
		String value = params.getValue(); // 原始值
		//AttrDef attrDef = params.getAttrDef(); // 属性定议配置信息
		//Map orderAttrValues = params.getOrderAttrValues();;// 订单所有属性值
		String cat_id = CommonDataFactory.getInstance().getGoodSpec(order_id, goods_id, SpecConsts.CAT_ID);
		if((EcsOrderConsts.GOODS_CAT_ID_DDWK.equals(cat_id) || EcsOrderConsts.GOODS_CAT_ID_TXWK.equals(cat_id))
				&& StringUtils.isEmpty(value)){
			value = "0";
		}else if((ZjEcsOrderConsts.ORDER_FROM_100032.equals(order_from)||"CRAWLER".equals(order_from))&& StringUtils.isEmpty(value)){
			value = "0";
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(value);
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		AttrInstLoadResp resp = null;
		String value = req.getNew_value();
		if(StringUtils.isEmpty(value)){
			resp = new AttrInstLoadResp();
			resp.setField_desc("ESS营业款");
			resp.setField_value(value);
			resp.setCheck_message("ESS营业款不能为空。");
		}
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		AttrInstLoadResp resp = null;
		String value = req.getNew_value();
		if(StringUtils.isEmpty(value)){
			resp = new AttrInstLoadResp();
			resp.setField_desc("ESS营业款");
			resp.setField_value(value);
			resp.setCheck_message("ESS营业款不能为空。");
		}
		return resp;
	}

}

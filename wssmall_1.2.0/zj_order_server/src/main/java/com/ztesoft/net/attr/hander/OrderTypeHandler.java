package com.ztesoft.net.attr.hander;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.AttrDef;
/**
 * 订单类型
 * @author Administrator
 * 01正常单、02靓号单、03订购、04退订、05变更、06预约、07预售、08换货、09B2B
 */
public class OrderTypeHandler extends BaseSupport implements IAttrHandler {
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
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		
		String order_type = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.ORDER_TYPE, value);
		if(StringUtils.isEmpty(order_type)){
			String is_liang = (String) orderAttrValues.get(AttrConsts.IS_LIANG);
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_liang)){
				order_type = EcsOrderConsts.ORDER_TYPE_02;
			}
			else{
				order_type = EcsOrderConsts.ORDER_TYPE_01;
			}
		}
		//根据小类判断是否上网卡预单约商品->根据小类匹配订单类型
		String cat_id = (String) orderAttrValues.get("pro_type");
		if(StringUtils.isEmpty(cat_id)){
			cat_id = CommonDataFactory.getInstance().getGoodSpec(null, goods_id, SpecConsts.CAT_ID);
		}
		String order_type_from_cat_id = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.CAT_ID_VS_ORDER_TYPE, cat_id);
		if(!StringUtils.isEmpty(order_type_from_cat_id)) {//订单类型不为空,以cat_id匹配出来的为准
			order_type = order_type_from_cat_id;
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(order_type);
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

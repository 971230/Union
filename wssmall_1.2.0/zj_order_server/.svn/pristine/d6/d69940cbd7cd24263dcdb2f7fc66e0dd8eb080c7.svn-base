package com.ztesoft.net.attr.hander;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.AttrDef;
/**
 * 是否需要写卡【白卡需要写卡】
 * @author Administrator
 * 1是、0否
 */
public class IsWriteCardHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		String goods_id = params.getGoods_id(); // 商品ID 只有子订单或商品单才有值
		String pro_goods_id = params.getPro_goods_id(); // 货品ID 只有货品单才有值
		String order_from = params.getOrder_from(); // 订单来源
		String value = params.getValue(); // 原始值
		AttrDef attrDef = params.getAttrDef(); // 属性定议配置信息
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		
		String sim_type =(String) orderAttrValues.get(AttrConsts.SIM_TYPE);
		String is_write_card = EcsOrderConsts.IS_WRITE_CARD_NO;
		if(EcsOrderConsts.SIM_TYPE_BK.equals(sim_type)){
			is_write_card = EcsOrderConsts.IS_WRITE_CARD_YES;
		}
		String goods_type = (String) orderAttrValues.get(AttrConsts.GOODS_TYPE);//商品类型
		logger.info("=================是否需要写卡属性处理器单宽带商品类型："+goods_type);
		if(StringUtils.isNotBlank(goods_type) && EcsOrderConsts.GOODS_TYPE_DKD.equals(goods_type)){//单宽带不需要写卡
			is_write_card = EcsOrderConsts.IS_WRITE_CARD_NO;
			logger.info("=================是否需要写卡属性处理器单宽带是否需要写卡："+is_write_card);
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(is_write_card);
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

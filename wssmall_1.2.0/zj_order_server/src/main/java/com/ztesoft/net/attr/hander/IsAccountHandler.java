package com.ztesoft.net.attr.hander;

import java.util.Map;

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
import com.ztesoft.net.model.AttrDef;
/**
 * 是否ESS开户
 * @author wu.i
 *
 */
public class IsAccountHandler extends BaseSupport implements IAttrHandler {
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
		//上网卡，号卡，合约机需要开户
		String o_sim_type = orderAttrValues.get("sim_type")==null?null:orderAttrValues.get("sim_type").toString();
		String sim_type = CommonDataFactory.getInstance().getSimType(order_id, order_from, o_sim_type, orderAttrValues);
		String goods_type = (String) orderAttrValues.get(SpecConsts.TYPE_ID);
		if(StringUtils.isEmpty(goods_type)){
			goods_id = (String) orderAttrValues.get("goods_id");
			goods_type = CommonDataFactory.getInstance().getGoodSpec(order_id, goods_id, SpecConsts.TYPE_ID);
		}
		if((EcsOrderConsts.SIM_TYPE_BK.equals(sim_type) || EcsOrderConsts.SIM_TYPE_BCK.equals(sim_type)) && (EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type) 
				|| EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type)||EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type))){
			value = EcsOrderConsts.IS_NEED_ACCOUNT;
		}
		else{
			value = EcsOrderConsts.NOT_NEED_ACCOUNT;
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(value);
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

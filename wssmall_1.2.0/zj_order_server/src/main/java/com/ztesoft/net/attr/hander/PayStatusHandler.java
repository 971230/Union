package com.ztesoft.net.attr.hander;

import java.util.Map;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.model.AttrDef;
/**
 * 支付状态
 * @author Administrator
 * 01未支付、02已支付
 */
public class PayStatusHandler extends BaseSupport implements IAttrHandler {
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
		
//		if(!StringUtils.isEmpty(value)){
//			value = CommonDataFactory.getInstance().getOtherDictCodeValue(StypeConsts.PAY_STATUS, value);
//		}
		
	    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	    String orderFrom = cacheUtil.getConfigInfo("orderHandle_orderfrom_payStatus");//10078
		if(!orderFrom.contains(order_from+",")){
			String paytype = (String) orderAttrValues.get(AttrConsts.PAY_TYPE);
			String goods_type  = CommonDataFactory.getInstance().getGoodSpec(order_id, goods_id, SpecConsts.TYPE_ID);
			if(EcsOrderConsts.PAY_TYPE_HDFK.equals(paytype)){//支付类型货到付款，支付状态为未支付
				value = EcsOrderConsts.PAY_STATUS_NOT_PAY;
			}
			else if(EcsOrderConsts.PAY_TYPE_ZXZF.equals(paytype) && !"10093".equals(order_from)){//支付类型为在线支付，支付状态为已支付
				value = EcsOrderConsts.PAY_STATUS_PAYED;
			}
			if(EcsOrderConsts.ORDER_FROM_10070.equals(order_from) && EcsOrderConsts.GOODS_TYPE_DKD.equals(goods_type)){
				value = EcsOrderConsts.PAY_STATUS_NOT_PAY;
			}
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

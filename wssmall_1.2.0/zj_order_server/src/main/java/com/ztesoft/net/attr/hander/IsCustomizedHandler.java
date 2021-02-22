package com.ztesoft.net.attr.hander;

import java.util.Map;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.AttrDef;
/**
 * 是否定制机
 * @author Administrator
 * 1-定制机、0-社会机
 */
public class IsCustomizedHandler extends BaseSupport implements IAttrHandler {
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
		
		String goods_type = (String) orderAttrValues.get(AttrConsts.GOODS_TYPE);
	    String cat_id =	(String) orderAttrValues.get(AttrConsts.GOODS_CAT_ID);
	    String is_customized = null;
	    //合约机、裸机才区分定制机社会机
	    if(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type) || EcsOrderConsts.GOODS_TYPE_PHONE.equals(goods_type)){
	    	is_customized = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_CUSTOMIZED, cat_id);
	        if(StringUtils.isEmpty(is_customized)){
	        	is_customized = EcsOrderConsts.NO_DEFAULT_VALUE;
	        }
	    }
		AttrInstLoadResp attrInstLoadResp=new AttrInstLoadResp();
		attrInstLoadResp.setField_value(is_customized);
		return attrInstLoadResp;
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

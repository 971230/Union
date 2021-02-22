package com.ztesoft.net.attr.hander;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.AttrDef;

public class CertiTypeHandler extends BaseSupport implements IAttrHandler{
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
		
		String certiType = null;
		/*if("1".equalsIgnoreCase(value)){
			certiType = "SFZ18";
    	}else if("2".equalsIgnoreCase(value)){
    		certiType = "SFZ15";
    	}else if("3".equalsIgnoreCase(value)){
    		certiType = "HZB";
    	}else if("4".equalsIgnoreCase(value)){
    		certiType = "JUZ";
    	}else if("6".equalsIgnoreCase(value)){
    		certiType = "HKB";
    	}else if("7".equalsIgnoreCase(value)){
    		certiType = "JGZ";
    	}else if("10".equalsIgnoreCase(value)){
    		certiType = "GOT";
    	}else if("11".equalsIgnoreCase(value)){
    		certiType = "TWT";
    	}else {
    		certiType = value;
		}*/
   
		if(StringUtils.isNotEmpty(value)){
			CommHandler handler = new CommHandler();
			certiType = handler.getkeyByCodea(value, value, StypeConsts.CERTI_TYPE);
		}else{
			certiType = value;
		}
		
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(certiType);
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null; 
		String order_id = req.getOrder_id() ;
		
		String  is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String  goods_type  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		String  cert_type = req.getNew_value();
		if(StringUtils.isEmpty(cert_type)&&(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type)||EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type)||EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type))&&!EcsOrderConsts.IS_OLD_1.equals(is_old)){
			resp = new AttrInstLoadResp();
			resp = new AttrInstLoadResp();
			resp.setField_desc("证件类型");
			resp.setField_value(AttrConsts.CERTI_TYPE);
			resp.setCheck_message("证件类型不能为空。");
			return resp;
		}
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null; 
		String order_id = req.getOrder_id() ;
		
		String  is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String  goods_type  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		String  cert_type = req.getNew_value();
		if(StringUtils.isEmpty(cert_type)&&(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type)||EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type)||EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type))&&!EcsOrderConsts.IS_OLD_1.equals(is_old)){
			resp = new AttrInstLoadResp();
			resp = new AttrInstLoadResp();
			resp.setField_desc("证件类型");
			resp.setField_value(AttrConsts.CERTI_TYPE);
			resp.setCheck_message("证件类型不能为空。");
			return resp;
		}
		return null;
	}

}

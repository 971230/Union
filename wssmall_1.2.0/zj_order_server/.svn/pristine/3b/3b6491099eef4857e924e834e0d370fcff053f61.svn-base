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
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.AttrDef;
/**
 * 卡类型
 * @author Administrator
 * NM普卡、MC微卡、NN纳诺卡
 */
public class WhiteCartTypeHandler extends BaseSupport implements IAttrHandler {
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
		
		value = "NM";//只有三合一卡
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(value);
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		  
		  String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.GOODS_TYPE);
		  String str1 = SpecConsts.TYPE_ID_20000;//号卡
		  String str2 = SpecConsts.TYPE_ID_20001;//上网卡
		  String str3 = SpecConsts.TYPE_ID_20002;//合约机
		  String  is_old = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.IS_OLD);
		  if(!EcsOrderConsts.IS_OLD_1.equals(is_old)&&StringUtils.isEmpty(req.getNew_value())&&(goods_type.equals(str1)||goods_type.equals(str2)||goods_type.equals(str3))){
	        AttrInstLoadResp resp = new AttrInstLoadResp();
			resp.setField_name(AttrConsts.WHITE_CART_TYPE);
			resp.setField_desc("卡类型");
			resp.setCheck_message(resp.getField_desc()+"不能为空。");
			return resp;
		  }
		   return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		  String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.GOODS_TYPE);
		  String str1 = SpecConsts.TYPE_ID_20000;//号卡
		  String str2 = SpecConsts.TYPE_ID_20001;//上网卡
		  String str3 = SpecConsts.TYPE_ID_20002;//合约机
		  String  is_old = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrder_id(), AttrConsts.IS_OLD);
			 
		  if(!EcsOrderConsts.IS_OLD_1.equals(is_old)&&StringUtils.isEmpty(req.getNew_value())&&(goods_type.equals(str1)||goods_type.equals(str2)||goods_type.equals(str3))){
	        AttrInstLoadResp resp = new AttrInstLoadResp();
			resp.setField_name(AttrConsts.WHITE_CART_TYPE);
			resp.setField_desc("卡类型");
			resp.setCheck_message(resp.getField_desc()+"不能为空。");
			return resp;
		  }
		   return null;
	}

}

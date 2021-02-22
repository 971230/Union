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

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.AttrDef;
/**
 * 渠道标记
 * @author Administrator
 * 1-传统营业厅，2-电话营销 ，3-非集团合作直销，4-非集团自有直销，5-集客，6-家客直销，7-宽固代理，8-社会渠道
 * 9-社会微厅，10-网盟，11-异业联盟，12-员工渠道，13-自营渠道，14-自营微厅，15-其他
 */
public class ChannelMarkHandler implements IAttrHandler {

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
		String goods_id = params.getGoods_id(); // 商品ID 只有子订单或商品单才有值
		String pro_goods_id = params.getPro_goods_id(); // 货品ID 只有货品单才有值
		String order_from = params.getOrder_from(); // 订单来源
		String value = params.getValue(); // 原始值
		AttrDef attrDef = params.getAttrDef(); // 属性定议配置信息
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		
		String plat_type = (String) orderAttrValues.get(AttrConsts.PLAT_TYPE);
		boolean isZbOrder = CommonDataFactory.getInstance().isZbOrder(plat_type);

		boolean isTbOrder = CommonDataFactory.getInstance().isTbOrder(order_from);//是否淘宝订单,默认否
		//总部商城、淘宝默认填13自营渠道
		if(isZbOrder || isTbOrder/*EcsOrderConsts.ORDER_FROM_10001.equals(order_from) || EcsOrderConsts.ORDER_FROM_TAOBAO.equals(order_from)*/){
			value = EcsOrderConsts.CHANNEL_MARK_ZYQD;
		}
		else{
			value = (String) orderAttrValues.get("reserve2");
			if(StringUtils.isEmpty(value)){
				String service_remarks = Const.getStrValue(orderAttrValues, AttrConsts.SERVICE_REMARKS);
				String[] strs = service_remarks.trim().split("渠道标记：");
				String channel_mark_name = strs[1];
				CommHandler handler = new CommHandler();
				value = handler.getkeyByName(channel_mark_name, channel_mark_name, StypeConsts.CHANNEL_MARK);
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

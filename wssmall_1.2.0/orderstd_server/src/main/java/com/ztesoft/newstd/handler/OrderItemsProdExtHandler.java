package com.ztesoft.newstd.handler;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.newstd.common.CommonData;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;

/**
 * @author licong
 *
 */
public class OrderItemsProdExtHandler implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		OrderItemProdExtBusiRequest orderItemProdExtBusiRequest = (OrderItemProdExtBusiRequest) params.getBusiRequest();
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		Map goodsSpec = CommonData.getData().getGoodsSpec();

		/**
		 * ------------------------certCardNationHandler----------------------------
		 **/
		String certNation = (String) orderAttrValues.get(AttrConsts.CERT_NATION);
		if (StringUtils.isEmpty(certNation)) {
			orderItemProdExtBusiRequest.setCert_card_nation(EcsOrderConsts.CERT_CARD_NATION);
		} else {
			orderItemProdExtBusiRequest.setCert_card_nation(certNation);
		}

		/** ------------------------certiTypeHandler---------------------------- **/
		String certiType = orderItemProdExtBusiRequest.getCerti_type();
		if (StringUtils.isNotEmpty(certiType)) {
			CommHandler handler = new CommHandler();
			orderItemProdExtBusiRequest
					.setCerti_type(handler.getkeyByCodea(certiType, certiType, StypeConsts.CERTI_TYPE));
		}

		/**
		 * ------------------------certFailureTimeHander----------------------------
		 **/
		String goods_type = CommonData.getData().getOrderTreeBusiRequest().getOrderBusiRequest().getGoods_type();

		String Cert_failure_time = params.getValue();
		if(org.apache.commons.lang.StringUtils.isBlank(Cert_failure_time)
				|| "null".equals(Cert_failure_time))
			Cert_failure_time = String.valueOf(orderAttrValues.get(AttrConsts.CERT_FAILURE_TIME));
		
		if ((StringUtils.isEmpty(Cert_failure_time) || "null".equals(Cert_failure_time)) 
				&& !SpecConsts.TYPE_ID_20003.equals(goods_type)) {
			orderItemProdExtBusiRequest.setCert_failure_time(EcsOrderConsts.DEFAULT_CERTI_FAILURE_TIME);
		} else {
			orderItemProdExtBusiRequest.setCert_failure_time(Cert_failure_time);
		}
		
	    String cert_failure_time = (String) orderAttrValues.get(AttrConsts.CERT_FAILURE_TIME);
		if(!StringUtils.isEmpty(cert_failure_time)){
		    orderItemProdExtBusiRequest.setCert_failure_time(cert_failure_time);
		}
		/** ------------------------certAddressHandler---------------------------- **/
		// String cert_address = orderItemProdExtBusiRequest.getCert_address();
		String cert_address = (String) orderAttrValues.get(AttrConsts.CERT_ADDRESS);
		if (!StringUtils.isEmpty(cert_address) && !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(cert_address)) {
			int count = getChnieseCount(cert_address);
			if (count < 8) {
				cert_address = cert_address + "身份证地址不能少于8个汉字";
			}
		}
		orderItemProdExtBusiRequest.setCert_address(cert_address);

		/** ------------------------whiteCartTypeHandler---------------------------- **/
		orderItemProdExtBusiRequest.setWhite_cart_type("NM");// 只有三合一卡

		/** ------------------------certCardNumHander---------------------------- **/
		if (StringUtils.isNotEmpty(orderItemProdExtBusiRequest.getCert_card_num())) {
			orderItemProdExtBusiRequest.setCert_card_num(lowToUp(orderItemProdExtBusiRequest.getCert_card_num()));
		}

		/** ------------------------isIphonePlanHandler---------------------------- **/
		if (StringUtils.isEmpty(orderItemProdExtBusiRequest.getIs_iphone_plan())) {
			// String cat_id = CommonDataFactory.getInstance().getGoodSpec(order_id,
			// goods_id, SpecConsts.CAT_ID);
			String cat_id = Const.getStrValue(goodsSpec, SpecConsts.CAT_ID);
			orderItemProdExtBusiRequest.setIs_iphone_plan(
					CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_IPHONE_PLAN, cat_id));
		}

		/** ------------------------simCardSkuHandler---------------------------- **/
		orderItemProdExtBusiRequest.setSim_card_sku(null);

		// dingxiaotao 6.7
		// terminalNumHandler设置原值
		orderItemProdExtBusiRequest.setTerminal_num((String) orderAttrValues.get(AttrConsts.TERMINAL_NUM));

		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setBusiRequest(orderItemProdExtBusiRequest);
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

	private int getChnieseCount(String str) {
		String temp = null;
		Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");
		Matcher m = p.matcher(str);
		int count = 0;
		while (m.find()) {
			temp = m.group(0);
			count = count + temp.length();
		}

		return count;
	}

	private String lowToUp(String cert_card_num) {
		if (StringUtils.isNotEmpty(cert_card_num)) {
			return cert_card_num.toUpperCase();
		} else {
			return "";
		}
	}
}

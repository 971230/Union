package com.ztesoft.newstd.handler;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxyNew;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.newstd.common.CommonData;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;

/**
 * @author licong
 *
 */
public class OrderHandler implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		OrderBusiRequest orderBusiRequest = (OrderBusiRequest) params.getBusiRequest();
		String order_id = params.getOrder_id();// 订单ID
		String goods_id = params.getGoods_id(); // 商品ID 只有子订单或货品单才有值
		String order_from = params.getOrder_from(); // 订单来源
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值

		// 从orderAttrValues获取
		String order_city_code = (String) orderAttrValues.get(AttrConsts.ORDER_CITY_CODE);

		// String cat_id = CommonDataFactory.getInstance().getGoodSpec(order_id,
		// goods_id, SpecConsts.CAT_ID);
		Map goodsSpec = CommonData.getData().getGoodsSpec();
		String cat_id = "";
		if (goodsSpec != null && !goodsSpec.isEmpty()) {
			cat_id = Const.getStrValue(goodsSpec, SpecConsts.CAT_ID);
		}

		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxyNew proxy = new DcPublicInfoCacheProxyNew(dcPublicInfoManager);

		/** ------------------------essMoneyHandler---------------------------- **/
		String ess_money = null;
		if (orderBusiRequest.getEss_money() != null)
			String.valueOf(orderBusiRequest.getEss_money());
		if ((EcsOrderConsts.GOODS_CAT_ID_DDWK.equals(cat_id) || EcsOrderConsts.GOODS_CAT_ID_TXWK.equals(cat_id))
				&& StringUtils.isEmpty(ess_money)) {
			ess_money = "0";
		} else if ((ZjEcsOrderConsts.ORDER_FROM_100032.equals(order_from) || "CRAWLER".equals(order_from))
				&& StringUtils.isEmpty(ess_money)) {
			ess_money = "0";
		}
		if (ess_money != null)
			orderBusiRequest.setEss_money(Double.valueOf(ess_money));
		/** ------------------------busiMoneyHandler---------------------------- **/
		
		String busiMoney = null;
		if (orderBusiRequest.getBusi_money() != null)
			String.valueOf(orderBusiRequest.getBusi_money());
		if (("CRAWLER".equals(order_from) || EcsOrderConsts.GOODS_CAT_ID_DDWK.equals(cat_id)
				|| EcsOrderConsts.GOODS_CAT_ID_TXWK.equals(cat_id) || EcsOrderConsts.GOODS_CAT_ID_BDSK.equals(cat_id))
				&& StringUtils.isEmpty(busiMoney)) {
			busiMoney = "0";
		} else if (EcsOrderConsts.GOODS_CAT_ID_MYBK.equals(cat_id) && StringUtils.isEmpty(busiMoney)) {
			busiMoney = "19";// 蚂蚁宝卡初始化为19块
		}
		if (busiMoney != null)
			orderBusiRequest.setBusi_money(Double.valueOf(busiMoney));
		/** ------------------------lanCodeHandler---------------------------- **/
		// orderBusiRequest.setLan_id(CommonDataFactory.getInstance().getLanCode(order_city_code));
		// 优化
		Map region = proxy.getSingRegion(order_city_code);
		orderBusiRequest.setLan_id(Const.getStrValue(region, AttrConsts.LAN_CODE));

		/** ------------------------sendingTypeHandler---------------------------- **/
		// String tempValue =
		// CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.SHIPPING_TYPE,
		// orderBusiRequest.getShipping_type());
		// 修改 ding.xiaotao 6.7原本取的是sendtype字段来设置ship_type
		System.out.println(orderAttrValues.get(AttrConsts.SENDING_TYPE));
		String tempValue = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.SHIPPING_TYPE,
				String.valueOf(orderAttrValues.get(AttrConsts.SENDING_TYPE)));
		if (!StringUtils.isEmpty(tempValue)) {
			orderBusiRequest.setShipping_type(tempValue);
		}else{
	        orderBusiRequest.setShipping_type((String)orderAttrValues.get(AttrConsts.SENDING_TYPE));
		}
		/** ------------------------payStatusHandler---------------------------- **/
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String orderFrom = cacheUtil.getConfigInfo("orderHandle_orderfrom_payStatus");
		if (!orderFrom.contains(order_from)) {
			// String paytype = orderBusiRequest.getPayment_type();
			String paytype = String.valueOf(
					CommonData.getData().getOrderTreeBusiRequest().getOrderPayBusiRequests().get(0).getPay_type());
			String goods_type = orderBusiRequest.getGoods_type();
			// 押金业务特殊处理
			String depositGoodType = cacheUtil.getConfigInfo("BSS_GET_MONEY_DEPOSIT");
			if (depositGoodType.equals(goods_type)) {
				orderBusiRequest.setPay_status(Integer.valueOf(EcsOrderConsts.PAY_STATUS_NOT_PAY));
			}else{
				if (EcsOrderConsts.PAY_TYPE_HDFK.equals(paytype)) {// 支付类型货到付款，支付状态为未支付
					orderBusiRequest.setPay_status(Integer.valueOf(EcsOrderConsts.PAY_STATUS_NOT_PAY));
				} else if (EcsOrderConsts.PAY_TYPE_ZXZF.equals(paytype) && !"10093".equals(order_from)) {// 支付类型为在线支付，支付状态为已支付
					orderBusiRequest.setPay_status(Integer.valueOf(EcsOrderConsts.PAY_STATUS_PAYED));
				}
			} 
			if (EcsOrderConsts.ORDER_FROM_10070.equals(order_from)
					&& EcsOrderConsts.GOODS_TYPE_DKD.equals(goods_type)) {
				orderBusiRequest.setPay_status(Integer.valueOf(EcsOrderConsts.PAY_STATUS_NOT_PAY));
			}
		}
		/** ------------------------orderTypeHandler---------------------------- **/
		String order_type = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.ORDER_TYPE,
				orderBusiRequest.getOrder_type());
		if (StringUtils.isEmpty(order_type)) {
			// 这里需要以来orderextvtlrequest设置完is_liang字段后
			String is_liang = CommonData.getData().getOrderExtvlBusiRequest().getIs_liang();
			if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_liang)) {
				order_type = EcsOrderConsts.ORDER_TYPE_02;
			} else {
				order_type = EcsOrderConsts.ORDER_TYPE_01;
			}
		}
		// 根据小类判断是否上网卡预单约商品->根据小类匹配订单类型
		String order_type_from_cat_id = CommonDataFactory.getInstance()
				.getDictCodeValue(StypeConsts.CAT_ID_VS_ORDER_TYPE, cat_id);
		if (!StringUtils.isEmpty(order_type_from_cat_id)) {// 订单类型不为空,以cat_id匹配出来的为准
			order_type = order_type_from_cat_id;
		}
		orderBusiRequest.setOrder_type(order_type);
		// payTimeHandler设置原值
		orderBusiRequest.setPay_time((String) orderAttrValues.get(AttrConsts.PAY_TIME));
		// goodsamount
		orderBusiRequest.setGoods_amount(
				goodsSpec.get(SpecConsts.PRICE) == null ? 0.0d : (Double) goodsSpec.get(SpecConsts.PRICE));

		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setBusiRequest(orderBusiRequest);
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

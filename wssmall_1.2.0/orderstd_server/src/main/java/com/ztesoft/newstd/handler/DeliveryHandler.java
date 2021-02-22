package com.ztesoft.newstd.handler;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
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
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;

/**
 * @author licong
 *
 */
public class DeliveryHandler implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		OrderDeliveryBusiRequest orderDeliveryBusiRequest = (OrderDeliveryBusiRequest) params.getBusiRequest();
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		String order_id = params.getOrder_id();// 订单ID
		String goods_id = params.getGoods_id(); // 商品ID 只有子订单或货品单才有值
		String order_from = params.getOrder_from(); // 订单来源

		// orderAttrValues中获取
		String sending_type = (String) orderAttrValues.get(AttrConsts.SENDING_TYPE);// 配送方式
		String city_code = (String) orderAttrValues.get(AttrConsts.CITY_CODE);
		String province_code = (String) orderAttrValues.get(AttrConsts.PROVINCE_CODE);
		String area_code = (String) orderAttrValues.get(AttrConsts.AREA_CODE);
		String n_shipping_amount = (String) orderAttrValues.get(AttrConsts.N_SHIPPING_AMOUNT);

		Map goodsSpec = CommonData.getData().getGoodsSpec();
		String cat_id = Const.getStrValue(goodsSpec, SpecConsts.CAT_ID);

		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxyNew proxy = new DcPublicInfoCacheProxyNew(dcPublicInfoManager);
		/** ------------------------cheDeliLogiHandler---------------------------- **/
		/**
		 * 订单同步报文中配送方式为“自提”或“无需配送”时，默认自动生成“物流单号”
		 */
		// String sending_type = (String)
		// orderAttrValues.get(AttrConsts.SENDING_TYPE);// 配送方式
		String tempValue = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.SHIPPING_TYPE, sending_type);
		if (!StringUtils.isEmpty(tempValue)) {
			sending_type = tempValue;
		}
		if (EcsOrderConsts.SHIP_TYPE_ZT.equals(sending_type) || EcsOrderConsts.SHIP_TYPE_WX.equals(sending_type)) {// 自提、自有配送-联通
			orderDeliveryBusiRequest.setLogi_no(getUniqueRandomNum());// 单号生成逻辑
		}

		/**
		 * ------------------------shippingCompanyHandler----------------------------
		 **/
		String shipping_company = orderDeliveryBusiRequest.getShipping_company();
		/**
		 * 订单同步报文中配送方式为“自提”时，默认物流公司名称为“自提”；订单同步报文中配送方式为“无需配送”时，默认物流公司名称为“自有配送-联通”；
		 */
		if (!StringUtils.isEmpty(tempValue)) {
			sending_type = tempValue;
		}
		if (EcsOrderConsts.SHIP_TYPE_ZT.equals(sending_type)) {// 自提
			shipping_company = EcsOrderConsts.LOGI_COMPANY_ZT0002;
		} else if (EcsOrderConsts.SHIP_TYPE_WX.equals(sending_type)) {// 自有配送-联通
			shipping_company = EcsOrderConsts.LOGI_COMPANY_ZY0002;
		}
		/**
		 * 订单同步报文中配送方式为“自提”时，默认物流公司名称为“自提”；订单同步报文中配送方式为“无需配送”时，默认物流公司名称为“自有配送-联通”；
		 */
		/*if (StringUtil.isEmpty(shipping_company)) {
			// String city_code = (String) orderAttrValues.get(AttrConsts.CITY_CODE);
			// 杭州发EMS，其他地市发顺丰
			if ("330100".equals(city_code) || "330700".equals(city_code)) {
				shipping_company = EcsOrderConsts.LOGI_COMPANY_EMS0001;
			} else {
				shipping_company = EcsOrderConsts.LOGI_COMPANY_SFFYZQYF;
			}
		}*/
		String cityCode = (String) orderAttrValues.get(AttrConsts.CITY_CODE);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		shipping_company = cacheUtil.getConfigInfo("EMS_OR_SF_BY_CITYCODE_"+cityCode);
		orderDeliveryBusiRequest.setShipping_company(shipping_company);

		/**
		 * ------------------------nShippingAmountHandler----------------------------
		 **/
		// dingxiaotao修改
		orderDeliveryBusiRequest.setN_shipping_amount(n_shipping_amount);
		if (StringUtils.isEmpty(orderDeliveryBusiRequest.getN_shipping_amount())) {
			orderDeliveryBusiRequest.setN_shipping_amount(EcsOrderConsts.DEFAULT_FEE_ZERO);
		}

		/** ------------------------provinceHandler---------------------------- **/
		// IDcPublicInfoManager dcPublicInfoManager =
		// SpringContextHolder.getBean("dcPublicInfoManager");
		// DcPublicInfoCacheProxy proxy = new
		// DcPublicInfoCacheProxy(dcPublicInfoManager);
		// List<Map> regions = proxy.getReionsList();
		// if (StringUtils.isEmpty(orderDeliveryBusiRequest.getProvince())) {
		// String region_id = (String) orderAttrValues.get(AttrConsts.PROVINCE_CODE);
		// if (regions != null && regions.size() > 0) {
		// for (Map region : regions) {
		// if (!StringUtils.isEmpty(region_id) &&
		// region_id.equals(Const.getStrValue(region, "region_id"))) {
		// orderDeliveryBusiRequest.setProvince(Const.getStrValue(region,
		// "local_name"));
		// break;
		// }
		// }
		// }
		// }
		// 优化性能
		if (StringUtils.isEmpty(orderDeliveryBusiRequest.getProvince())) {
			Map region = proxy.getSingRegion(province_code);
			orderDeliveryBusiRequest.setProvince(Const.getStrValue(region, "local_name"));
		}
		/** ------------------------cityHandler---------------------------- **/
		// if (regions != null && regions.size() > 0) {
		// String region_id = (String) orderAttrValues.get(AttrConsts.CITY_CODE);
		// for (Map region : regions) {
		// if (!StringUtils.isEmpty(region_id) &&
		// region_id.equals(Const.getStrValue(region, "region_id"))) {
		// orderDeliveryBusiRequest.setCity(Const.getStrValue(region, "local_name"));
		// break;
		// }
		// }
		// }
		// 优化性能
		if (StringUtils.isEmpty(orderDeliveryBusiRequest.getCity())) {
			Map region = proxy.getSingRegion(city_code);
			orderDeliveryBusiRequest.setCity(Const.getStrValue(region, "local_name"));
		}

		/** ------------------------districtHandler---------------------------- **/
		// if (StringUtils.isEmpty(orderDeliveryBusiRequest.getRegion())) {
		// String region_id = (String) orderAttrValues.get(AttrConsts.AREA_CODE);
		// if (regions != null && regions.size() > 0) {
		// for (Map region : regions) {
		// if (!StringUtils.isEmpty(region_id) &&
		// region_id.equals(Const.getStrValue(region, "region_id"))) {
		// orderDeliveryBusiRequest.setRegion(Const.getStrValue(region, "local_name"));
		// break;
		// }
		// }
		// }
		// }
		// 优化性能
		if (StringUtils.isEmpty(orderDeliveryBusiRequest.getRegion()) && !StringUtil.isEmpty(area_code)) {
			Map region = proxy.getSingRegion(area_code);
			orderDeliveryBusiRequest.setRegion(Const.getStrValue(region, "local_name"));
		}
		/** ------------------------needReceiptHandler---------------------------- **/
		// String need_receipt = EcsOrderConsts.NO_DEFAULT_VALUE;
		//
		// // 获取后激活订单类型
		// String laterActFlag = EcsOrderConsts.NO_DEFAULT_VALUE;
		// if (orderAttrValues.containsKey(AttrConsts.LATER_ACT_FLAG)) {
		// laterActFlag = orderAttrValues.get(AttrConsts.LATER_ACT_FLAG).toString();
		// }
		//
		// if (EcsOrderConsts.ORDER_FROM_10061.equals(order_from) ||
		// EcsOrderConsts.ORDER_FROM_10062.equals(order_from)
		// || EcsOrderConsts.ORDER_FROM_10012.equals(order_from) ||
		// EcsOrderConsts.ORDER_FROM_10070.equals(order_from)) {
		// need_receipt = EcsOrderConsts.NO_DEFAULT_VALUE;
		// } else if (StringUtils.equals(laterActFlag, EcsOrderConsts.IS_DEFAULT_VALUE))
		// {
		// // 后激活订单 是否回单默认值为“否”
		// need_receipt = EcsOrderConsts.NO_DEFAULT_VALUE;
		// }
		// dingxiaotao 6.7 逻辑上只设置了否
		String need_receipt = EcsOrderConsts.NO_DEFAULT_VALUE;
		orderDeliveryBusiRequest.setNeed_receipt(need_receipt);

		/** ------------------------isProtectHandler---------------------------- **/
		String isProtect = null;
		if (orderDeliveryBusiRequest.getIs_protect() != null) {
			String.valueOf(orderDeliveryBusiRequest.getIs_protect());
		}
		// String cat_id = CommonDataFactory.getInstance().getGoodSpec(order_id,
		// goods_id, SpecConsts.CAT_ID);
		// DT卡不需要保价
		if (EcsOrderConsts.GOODS_CAT_ID_DDWK.equals(cat_id) || EcsOrderConsts.GOODS_CAT_ID_TXWK.equals(cat_id)) {
			isProtect = EcsOrderConsts.NO_DEFAULT_VALUE;
		}
		// 如果为空，默认否
		if (StringUtil.isEmpty(isProtect) || "null".equals(isProtect)) {
			isProtect = EcsOrderConsts.NO_DEFAULT_VALUE;
		}
		orderDeliveryBusiRequest.setIs_protect(Integer.valueOf(isProtect));
		// dingxiaotao 6.7
		// shipZipHandler 设置原值
		orderDeliveryBusiRequest.setShip_zip((String) orderAttrValues.get(AttrConsts.SHIP_ZIP));
		// postFeeHandler 设置原值
		orderDeliveryBusiRequest.setPost_fee((String) orderAttrValues.get(AttrConsts.POST_FEE));
		// shipNameHandler 设置原值
		orderDeliveryBusiRequest.setShip_name((String) orderAttrValues.get(AttrConsts.SHIP_NAME));
		// receiverMobileHandler 设置原值
		orderDeliveryBusiRequest.setShip_mobile((String) orderAttrValues.get(AttrConsts.REVEIVER_MOBILE));
		// shippingTimeHandler 设置原值
		orderDeliveryBusiRequest.setShipping_time((String) orderAttrValues.get(AttrConsts.SHIPPING_TIME));

		/** ------------------------shipAddrHandler---------------------------- **/
		String ship_addr = orderDeliveryBusiRequest.getShip_addr();
		// String orderfrom = (String) orderAttrValues.get(AttrConsts.ORDER_FROM);
		if (EcsOrderConsts.ORDER_FROM_10003.equals(order_from)) {
			String province = orderDeliveryBusiRequest.getProvince();
			String city = orderDeliveryBusiRequest.getCity();
			String district = orderDeliveryBusiRequest.getRegion();
			orderDeliveryBusiRequest.setShip_addr(province + " " + city + " " + district + " " + ship_addr);
		}

		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setBusiRequest(orderDeliveryBusiRequest);
		return resp;
	}

	/**
	 * 得到一个18位唯一的随机数,生成规则为当前时间yyMMddHHmmss+6位随机数
	 *
	 * @return String
	 */
	public static synchronized String getUniqueRandomNum() {
		String date = "";
		try {
			date = DateUtil.getTime5();
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder(date);
		for (int i = 0; i < 6; i++) {
			sb.append((int) (10 * Math.random()));
		}
		return sb.toString();
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

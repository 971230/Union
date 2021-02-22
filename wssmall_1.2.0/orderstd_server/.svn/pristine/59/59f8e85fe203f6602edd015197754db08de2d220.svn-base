package com.ztesoft.newstd.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.newstd.common.CommonData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.utils.SpecUtils;

/**
 * @author licong
 *
 */
public class OrderExtHandler implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		OrderExtBusiRequest orderExtBusiRequest = (OrderExtBusiRequest) params.getBusiRequest();
		String order_id = params.getOrder_id();// 订单ID
		String goods_id = params.getGoods_id(); // 商品ID 只有子订单或货品单才有值
		String order_from = params.getOrder_from();
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值

		// 从orderAttrValues获取属性
		String pro_type = (String) orderAttrValues.get(AttrConsts.PRO_TYPE);
		String plat_type = (String) orderAttrValues.get(AttrConsts.PLAT_TYPE);
		String order_city_code = (String) orderAttrValues.get(AttrConsts.ORDER_CITY_CODE);
		String reserver2 = (String) orderAttrValues.get(AttrConsts.RESERVE2);
		String pay_method = (String) orderAttrValues.get(AttrConsts.PAY_METHOD);
		String activity_list = (String) orderAttrValues.get(AttrConsts.ACTIVITY_LIST);
		String sim_type = (String) orderAttrValues.get(AttrConsts.SIM_TYPE);
		String order_model = (String) orderAttrValues.get(AttrConsts.ORDER_MODEL);
		String out_tid = (String) orderAttrValues.get(AttrConsts.OUT_TID);

		CommHandler commHandler = new CommHandler();

		boolean isTbOrder = Boolean.parseBoolean(CommonData.getData().getIsTbOrder());
		boolean isZbOrder = Boolean.parseBoolean(CommonData.getData().getIsZbOrder());

		Map goodsSpec = CommonData.getData().getGoodsSpec();
		String cat_id = "";
		String type_id = "";
		if (goodsSpec != null && !goodsSpec.isEmpty()) {
			cat_id = Const.getStrValue(goodsSpec, SpecConsts.CAT_ID);
			type_id = Const.getStrValue(goodsSpec, SpecConsts.TYPE_ID);
		}
		List<Goods> products = CommonData.getData().getProducts();
		Map terminalSpec = CommonData.getData().getTerMinalSpec();
		Map contactSpec = CommonData.getData().getContactSpec();
		if (products != null && products.size() > 0) {
			for (Goods prod : products) {
				if (SpecConsts.TYPE_ID_10000.equals(prod.getType_id()) && MapUtils.isEmpty(terminalSpec)) {
					terminalSpec = SpecUtils.getProductSpecMap(prod);
					CommonData.getData().setTerMinalSpec(terminalSpec);
					break;
				} else if (SpecConsts.TYPE_ID_10001.equals(prod.getType_id()) && MapUtils.isEmpty(contactSpec)) {
					contactSpec = SpecUtils.getProductSpecMap(prod);
					CommonData.getData().setContactSpec(contactSpec);
					break;
				}
			}
		}

		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy proxy = new DcPublicInfoCacheProxy(dcPublicInfoManager);

		/** ------------------------bssInfIdHandler---------------------------- **/
		StringBuffer strBuff = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("yyyySSmmss");
		long random = Math.round(Math.random() * 89999 + 10000);
		strBuff.append(format.format(new Date())).append(random);
		orderExtBusiRequest.setBss_inf_id(strBuff.toString());

		/** ------------------------tidCategoryHandler---------------------------- **/
		if (StringUtils.isEmpty(orderExtBusiRequest.getTid_category())) {
			// 从缓存中获取商品
			// goods_id = (String) orderAttrValues.get("goods_id");
			// String cat_id = CommonDataFactory.getInstance().getGoodSpec(order_id,
			// goods_id, SpecConsts.CAT_ID);
			orderExtBusiRequest.setTid_category(proxy.getTidCategory(cat_id));
		}

		/** ------------------------isZHWJHandler---------------------------- **/
		String is_zhwj = EcsOrderConsts.NO_DEFAULT_VALUE;// 默认非智慧沃家
		
		// 含宽带赠品（智慧沃家产品）
		try {
			String json = activity_list;
			if (!StringUtils.isEmpty(json) && !"-1".equals(json)) {
				JSONArray jsonArray = JSONArray.fromObject(json);
				List<Map> list = proxy.getList(StypeConsts.BROADBAND_GIFT);
				String broadband_gift = (String) list.get(0).get("pname");
				/*
				 * 智慧沃家产品活动
				 * 的pmt_code配置在es_dc_publict_ext表pkey及pname字段，每个pmt_code以","隔开;其中pkey首尾不含",",
				 * pname首尾均含","
				 */
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject json1 = (JSONObject) jsonArray.get(i);
					if (json1.containsKey("activity_code")) {
						String activity_code = json1.getString("activity_code");
						if (broadband_gift.contains("," + activity_code + ",")) {
							is_zhwj = EcsOrderConsts.IS_DEFAULT_VALUE;// 是智慧沃家
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		orderExtBusiRequest.setIs_zhwj(is_zhwj);

		/** ------------------------prodCatIdHandler---------------------------- **/
		if (StringUtils.isEmpty(orderExtBusiRequest.getProd_cat_id())) {
			// orderExtBusiRequest.setProd_cat_id(
			// (String) CommonDataFactory.getInstance().getProductSpecMapByGoodsId(goods_id,
			// SpecConsts.TYPE_ID_10001).get(SpecConsts.CAT_ID));
			orderExtBusiRequest.setProd_cat_id(Const.getStrValue(contactSpec, SpecConsts.CAT_ID));
		}
		orderExtBusiRequest.setIs_send_wms(Const.getStrValue(contactSpec,"is_send_wms"));
		/** ------------------------modelCodeHandler---------------------------- **/
		if (StringUtils.isEmpty(orderExtBusiRequest.getModel_code())) {
			// orderExtBusiRequest.setModel_code((String) CommonDataFactory.getInstance()
			// .getProductSpecMapByGoodsId(goods_id,
			// EcsOrderConsts.PRODUCT_TYPE_TERMINAL).get(SpecConsts.GOODS_SN));
			orderExtBusiRequest.setModel_code(Const.getStrValue(terminalSpec, SpecConsts.GOODS_SN));
		}

		/** ------------------------modelNameHandler---------------------------- **/
		if (StringUtils.isEmpty(orderExtBusiRequest.getModel_name())) {
			// orderExtBusiRequest.setModel_name((String) CommonDataFactory.getInstance()
			// .getProductSpecMapByGoodsId(goods_id,
			// EcsOrderConsts.PRODUCT_TYPE_TERMINAL).get(SpecConsts.MACHINE_NAME));
			orderExtBusiRequest.setModel_name(Const.getStrValue(terminalSpec, SpecConsts.MACHINE_NAME));
		}

		/** ------------------------isAopHandler---------------------------- **/
		if("10093".equals(order_from)){
		    orderExtBusiRequest.setIs_aop("2");
		}else{
		    orderExtBusiRequest.setIs_aop(EcsOrderConsts.NO_DEFAULT_VALUE);
		}

		/**
		 * ------------------------auditMoneyStatusAttrHandler----------------------------
		 **/
		orderExtBusiRequest.setAudit_receive_money_status(ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_0);

		/**
		 * ------------------------auditMoneyStatusAttrHandler----------------------------
		 **/
		orderExtBusiRequest.setAudit_busi_money_status(ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_0);

		/**
		 * ------------------------cancelStatusAttrHandler----------------------------
		 **/
		orderExtBusiRequest.setOrder_if_cancel(EcsOrderConsts.BSS_CANCEL_STATUS_0);
		orderExtBusiRequest.setEss_cancel_status(EcsOrderConsts.BSS_CANCEL_STATUS_0);
		orderExtBusiRequest.setBss_cancel_status(EcsOrderConsts.BSS_CANCEL_STATUS_0);

		/** ------------------------orderCityCodeHandler---------------------------- **/
		// 非广东省的地市

		List otherCities = CommonDataFactory.getInstance().listDcPublicData(StypeConsts.OTHER_SHIP_CITY);
		if (otherCities != null && otherCities.size() > 0) {
			for (int i = 0; i < otherCities.size(); i++) {
				Map city = (Map) otherCities.get(i);
				if (!StringUtils.isEmpty(order_city_code) && order_city_code.equals(Const.getStrValue(city, "pkey"))) {
					orderExtBusiRequest.setOrder_city_code(Const.getStrValue(city, "codea"));
					break;
				}
			}
		}

		/**
		 * ------------------------wmIsreservationFromHandler----------------------------
		 **/
		String wmIsreservationFrom = orderExtBusiRequest.getWm_isreservation_from();
		// 属性为空，默认否
		if (StringUtils.isEmpty(wmIsreservationFrom))
			wmIsreservationFrom = EcsOrderConsts.NO_DEFAULT_VALUE;

		// 根据小类判断是否上网卡预单约商品->根据小类匹配订单类型

		if (StringUtils.isEmpty(pro_type)) {
			pro_type = cat_id;
		}
		String order_type_from_cat_id = CommonDataFactory.getInstance()
				.getDictCodeValue(StypeConsts.CAT_ID_VS_ORDER_TYPE, pro_type);
		if (EcsOrderConsts.ORDER_TYPE_06.equals(order_type_from_cat_id)) {// 预约单
			wmIsreservationFrom = EcsOrderConsts.IS_DEFAULT_VALUE;
		}
		orderExtBusiRequest.setWm_isreservation_from(wmIsreservationFrom);

		/** ------------------------packTypeHandler---------------------------- **/
		orderExtBusiRequest
				.setPack_type(CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.PACK_TYPE, type_id));

		/**
		 * ------------------------abnormalStatusHandler----------------------------
		 **/
		String abnormalStatusOld = orderExtBusiRequest.getAbnormal_status();
		String abnormal_status = commHandler.getkeyByName(abnormalStatusOld, abnormalStatusOld,
				StypeConsts.ABNORMAL_STATUS);
		if (StringUtils.isEmpty(abnormalStatusOld) || EcsOrderConsts.ABNORMAL_STATUS_NORMAL_SY.equals(abnormalStatusOld)
				|| EcsOrderConsts.ABNORMAL_STATUS_NORMAL_SY.equals(abnormal_status))
			abnormal_status = EcsOrderConsts.ABNORMAL_STATUS_0;
		orderExtBusiRequest.setAbnormal_status(abnormal_status);

		/**
		 * ------------------------deliveryStatusHandler----------------------------
		 **/
		// String deliveryStatus = orderExtBusiRequest.getO_ship_status();
		// dingxiaotao 6.7修改
		String deliveryStatus = (String) orderAttrValues.get(AttrConsts.DELIVERY_STATUS);
		if ("未发货".equals(deliveryStatus))
			deliveryStatus = EcsOrderConsts.DELVERY_STATUS_00;

		if ("发货到货".equals(deliveryStatus))
			deliveryStatus = EcsOrderConsts.DELVERY_STATUS_01;

		if ("已发货".equals(deliveryStatus))
			deliveryStatus = EcsOrderConsts.DELVERY_STATUS_02;
		orderExtBusiRequest.setO_ship_status(deliveryStatus);

		/** ------------------------contactAddrHandler---------------------------- **/
		// 这个要放在deliveryHandler后面
		if (isTbOrder) {
			// orderExtBusiRequest.setContact_addr(Const.getStrValue(orderAttrValues,
			// AttrConsts.SHIP_ADDR));
			orderExtBusiRequest.setContact_addr(CommonData.getData().getOrderTreeBusiRequest()
					.getOrderDeliveryBusiRequests().get(0).getShip_addr());
		}

		/** ------------------------channelMarkHandler---------------------------- **/
		String channekMark = orderExtBusiRequest.getChannel_mark();
		// 总部商城、淘宝默认填13自营渠道
		if (isZbOrder || isTbOrder/*
									 * EcsOrderConsts.ORDER_FROM_10001.equals(order_from) ||
									 * EcsOrderConsts.ORDER_FROM_TAOBAO.equals(order_from)
									 */) {
			channekMark = EcsOrderConsts.CHANNEL_MARK_ZYQD;
		} else {
			channekMark = reserver2;
			if (StringUtils.isEmpty(channekMark)) {
				String service_remarks = Const.getStrValue(orderAttrValues, AttrConsts.SERVICE_REMARKS);
				String[] strs = service_remarks.trim().split("渠道标记：");
				String channel_mark_name = strs[1];
				channekMark = commHandler.getkeyByName(channel_mark_name, channel_mark_name, StypeConsts.CHANNEL_MARK);
			}
		}
		orderExtBusiRequest.setChannel_mark(channekMark);

		/** ------------------------isSendZbHandler---------------------------- **/
		String is_send_zb = EcsOrderConsts.NO_DEFAULT_VALUE;

		/* ZX add 2015-12-25 修改总商订单默认不与总部交互 (所有情况都不与总部交互) start */
		if (StringUtils.isNotBlank(is_send_zb)) { // 此判断无实际意义，只要能保证if永久成立即可，目的返回不予总部交互
			orderExtBusiRequest.setSend_zb(EcsOrderConsts.NO_DEFAULT_VALUE);
		}

		/** ------------------------payTypeHandler---------------------------- **/
		if (EcsOrderConsts.PAY_METHOD_ZFB.equals(pay_method)) {
			orderExtBusiRequest.setPay_type(EcsOrderConsts.PAY_TYPE_ZXZF);
		}
		/** ------------------------isAccountHandler---------------------------- **/
		// 上网卡，号卡，合约机需要开户
		String o_sim_type = sim_type == null ? null : sim_type;
		String sim_type_new = getSimType(order_id, order_from, o_sim_type, orderAttrValues, goods_id, isTbOrder);
		String goods_type = type_id;
		if (StringUtils.isEmpty(goods_type)) {
			// goods_id = (String) orderAttrValues.get("goods_id");
			goods_type = type_id;
		}
		if ((EcsOrderConsts.SIM_TYPE_BK.equals(sim_type_new) || EcsOrderConsts.SIM_TYPE_BCK.equals(sim_type_new))
				&& (EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type)
						|| EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type)
						|| EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type))) {
			orderExtBusiRequest.setIs_account(EcsOrderConsts.IS_NEED_ACCOUNT);
		} else {
			orderExtBusiRequest.setIs_account(EcsOrderConsts.NOT_NEED_ACCOUNT);
		}

		/** ------------------------shippingQuickHandler---------------------------- **/
		String shipping_quick = EcsOrderConsts.SHIPPING_QUICK_02;
		// 如果是物流生产模式，是闪电送
		if (EcsOrderConsts.OPER_MODE_WL.equals(order_model)) {
			shipping_quick = EcsOrderConsts.SHIPPING_QUICK_01;
		}
		orderExtBusiRequest.setShipping_quick(shipping_quick);

		/** ------------------------zbInfIdHandler---------------------------- **/
		String zb_inf_id = out_tid;
		if (!isZbOrder) {
			Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int date = c.get(Calendar.DATE);
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			int second = c.get(Calendar.SECOND);
			zb_inf_id = year + "" + month + "" + date + "" + hour + "" + minute + "" + second
					+ Math.round(Math.random() * 8999 + 1000);
		}
		orderExtBusiRequest.setZb_inf_id(zb_inf_id);

		/** ------------------------orderFromHandler---------------------------- **/
		if ("CRAWLER".equals(plat_type)) {
			order_from = EcsOrderConsts.ORDER_FROM_100032;
		} else {
			// boolean iszbmall = CommonDataFactory.getInstance().isZbOrder(plat_type);
			if (isZbOrder) {
				order_from = EcsOrderConsts.ORDER_FROM_10003;
			} else {
				boolean iszbmall = CommonDataFactory.getInstance().isZbOrder(order_from);
				if (iszbmall) {
					order_from = EcsOrderConsts.ORDER_FROM_10003;
				}
			}
		}
		/** ------------------------platTypeHandler---------------------------- **/
		if (isZbOrder) {
			orderExtBusiRequest.setPlat_type(EcsOrderConsts.ORDER_FROM_10003);
		}

		/** ------------------------orderOriginHandler---------------------------- **/
		String order_origin = null;
		List<Map> relations = CommonDataFactory.getInstance().getDictRelationListData(StypeConsts.DIC_ORDER_ORIGIN);
		if (relations != null && relations.size() > 0) {
			for (Map relation : relations) {
				if (order_from.equals(Const.getStrValue(relation, "field_value"))) {
					order_origin = Const.getStrValue(relation, "other_field_value");
					break;
				}
			}
		}
		orderExtBusiRequest.setOrder_origin(order_origin);

		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setBusiRequest(orderExtBusiRequest);
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

	public String getSimType(String order_id, String order_from, String value, Map orderAttrValues, String goods_id,
			boolean isTbOrder) {
		String sim_type = EcsOrderConsts.SIM_TYPE_BK;
		// 上网卡-卡类型判断
		Map goodsSpec = CommonData.getData().getGoodsSpec();
		if (MapUtils.isEmpty(goodsSpec)) {
			goodsSpec = CommonDataFactory.getInstance().getGoodsSpecMap(order_id, goods_id);
			CommonData.getData().setGoodsSpec(goodsSpec);
		}
		Map liPinSpec = CommonData.getData().getLiPinSpec();
		if (MapUtils.isEmpty(liPinSpec)) {
			liPinSpec = CommonDataFactory.getInstance().getProductSpecMapByGoodsId(goods_id, SpecConsts.TYPE_ID_10007);
			CommonData.getData().setLiPinSpec(liPinSpec);
		}
		// String is_set = CommonDataFactory.getInstance().getGoodSpec(order_id,
		// goods_id, SpecConsts.SWK_IS_SET);
		String is_set = Const.getStrValue(goodsSpec, SpecConsts.SWK_IS_SET);
		if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_set)) {
			sim_type = EcsOrderConsts.SIM_TYPE_CK;// 成卡
		} else if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_set)) {
			sim_type = EcsOrderConsts.SIM_TYPE_BK;// 白卡
		}
		String type_id = (String) orderAttrValues.get("type_id");
		String hasNumber = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.ISHAVEMSISDN, type_id);
		// String lipin_prod_cat =
		// (String)CommonDataFactory.getInstance().getProductSpecMapByGoodsId(goods_id,
		// SpecConsts.TYPE_ID_10007).get(SpecConsts.CAT_ID);
		String lipin_prod_cat = Const.getStrValue(liPinSpec, SpecConsts.CAT_ID);
		if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(hasNumber) || (EcsOrderConsts.GOODS_TYPE_LIPIN.equals(type_id)
				&& EcsOrderConsts.PRODUCT_CAT_ID_4.equals(lipin_prod_cat))) {
			String is_old = (String) orderAttrValues.get(AttrConsts.IS_OLD);
			String vicecard_no = (String) orderAttrValues.get(AttrConsts.VICECARD_NO);
			// 合约机默认白卡
			if (Consts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)) {
				sim_type = EcsOrderConsts.SIM_TYPE_BK;// 白卡
			}
			// VIP商城默认成卡 checkFieldValueExists("ischengka", order_from)
			if (EcsOrderConsts.ORDER_FROM_10034.equals(order_from)) {
				sim_type = EcsOrderConsts.SIM_TYPE_CK;// 成卡
			}
			// 老用户默认成卡
			if (EcsOrderConsts.IS_OLD_1.equals(is_old) && StringUtils.isEmpty(vicecard_no)) {
				sim_type = EcsOrderConsts.SIM_TYPE_CK;// 成卡
			} else if (EcsOrderConsts.IS_OLD_1.equals(is_old) && !StringUtils.isEmpty(vicecard_no)) {
				sim_type = EcsOrderConsts.SIM_TYPE_BK;// 白卡
			}
			// 如果商城有值,以商城的为准
			if (StringUtils.isNotEmpty(value)) {
				sim_type = value;
			}
		}

		// 淘宝订单(默认取商品配置)
		if (isTbOrder) {
			if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_set)) {
				sim_type = EcsOrderConsts.SIM_TYPE_CK;// 成卡
			} else if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_set)) {
				sim_type = EcsOrderConsts.SIM_TYPE_BK;// 白卡
			}
		}

		if (!(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(type_id) || EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(type_id)
				|| EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id))) {
			sim_type = EcsOrderConsts.SIM_TYPE_CK;// 成卡
		}
		// 宽带默认白卡
		if (EcsOrderConsts.GOODS_TYPE_KD.equals(type_id)) {
			sim_type = EcsOrderConsts.SIM_TYPE_BK;// 白卡
		}
		return sim_type;
	}

}

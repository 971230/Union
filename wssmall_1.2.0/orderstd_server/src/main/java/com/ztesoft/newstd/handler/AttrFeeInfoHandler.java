package com.ztesoft.newstd.handler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.newstd.common.CommonData;

import consts.ConstsCore;
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
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.spec.req.NumberSpecReq;

public class AttrFeeInfoHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		String order_from = params.getOrder_from(); // 订单来源
		List<AttrFeeInfoBusiRequest> attrFeeInfoBusiRequests = (List<AttrFeeInfoBusiRequest>) params.getBusiRequest();
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值

		String type_id = (String) orderAttrValues.get(AttrConsts.TYPE_ID);
		String order_realfee = (String) orderAttrValues.get(AttrConsts.ORDER_REAL_FEE);
		String good_no_fee = (String) orderAttrValues.get(AttrConsts.GOOD_NO_FEE);
		String fee_infos = (String) orderAttrValues.get(AttrConsts.FEE_INFOS);

		boolean isZbOrder = Boolean.parseBoolean(CommonData.getData().getIsZbOrder());
		boolean isTbOrder = Boolean.parseBoolean(CommonData.getData().getIsTbOrder());

		CommHandler commHandler = new CommHandler();
		String source_from = commHandler.getSourceFrom();

		String fee_item_code = "";
		String fee_item_name = "";
		String o_fee_num = "";
		String disacount_fee = "";
		String disacount_reason = "";
		String n_fee_num = "";
		String is_aop_fee = "";
		String fee_category = "";
		String can_cancel_date = "";
		String fee_type = "";
		AttrFeeInfoBusiRequest attrFeeInfo_djyck = new AttrFeeInfoBusiRequest(); // 多缴预存款

		Map goodsSpec = CommonData.getData().getGoodsSpec();
		String cat_id = Const.getStrValue(goodsSpec, SpecConsts.CAT_ID);
		// ES_PREMIUM_GOODS表配置的商品才做溢价处理
		// 优先根据商品从商品参数获取cat_id值，值不为空，则用参数中配置cat_id取值
		/**
		 * 订单标准化的时候如果来源是淘宝的上网卡商品读取参数new_type_id，
		 * new_cat_id和new_prod_brand，如果有值，送老系统的接口更新商品类型prod_offer_type和产品品牌prod_brand。
		 * 根据新的小类new_cat_id，如果是自由组合套餐，按照自由组合套餐的处理方式，费用项增加4001多交预存款费用项
		 */

		/** 4G自由组合套餐：预存款=订单金额-靓号费；临时方案 */
		// String cat_id = CommonDataFactory.getInstance().getGoodSpec(order_id,
		// goods_id, SpecConsts.CAT_ID);
		if (!isZbOrder && !EcsOrderConsts.ORDER_FROM_10003.equals(order_from)) {
			if ("69010206".equals(cat_id) || "69010208".equals(cat_id) || "69010209".equals(cat_id)
					|| "69010210".equals(cat_id)) {

				fee_item_name = "多交预存款费用";
				fee_item_code = EcsOrderConsts.DJYCK_FEE_ITEM_ID;
				disacount_fee = EcsOrderConsts.DEFAULT_FEE_ZERO;
				disacount_reason = EcsOrderConsts.EMPTY_STR_VALUE;
				// String order_totalfee = orderAttrValues.get("order_totalfee") == null ? "0" :
				// orderAttrValues.get("order_totalfee").toString();
				if (StringUtils.isEmpty(order_realfee))
					order_realfee = "0";
				// if (StringUtil.isEmpty(order_realfee)) {
				// order_realfee = order_totalfee;
				// }
				String liang_price = good_no_fee;
				if (StringUtils.isEmpty(liang_price)) {
					liang_price = EcsOrderConsts.DEFAULT_FEE_ZERO;
				}
				double sell_price_d = 0d;
				if (!StringUtils.isEmpty(order_realfee)) {
					sell_price_d = Double.valueOf(order_realfee);
				}
				double djyck = 0D;
				if (sell_price_d > Double.valueOf(liang_price)) {
					djyck = sell_price_d - Double.valueOf(liang_price);
				}
				o_fee_num = n_fee_num = String.valueOf(djyck);
				Map mapValue = new HashMap();
				mapValue.put("fee_inst_id", this.baseDaoSupport.getSequences("seq_attr_fee_info"));
				mapValue.put("order_id", order_id);
				mapValue.put("inst_id", inst_id);
				mapValue.put("fee_item_code", fee_item_code);
				mapValue.put("fee_item_name", fee_item_name);
				mapValue.put("o_fee_num", o_fee_num);
				mapValue.put("discount_fee", disacount_fee);
				mapValue.put("discount_reason", disacount_reason);
				mapValue.put("n_fee_num", n_fee_num);
				mapValue.put("origin_mall", order_from);
				mapValue.put("source_from", source_from);
				try {
					if (StringUtil.isEmpty(attrFeeInfo_djyck.getFee_inst_id())) {
						BeanUtils.copyProperties(attrFeeInfo_djyck, mapValue);
					} else {
						attrFeeInfo_djyck.setO_fee_num(attrFeeInfo_djyck.getO_fee_num()
								+ Double.valueOf(mapValue.get("o_fee_num").toString()));
						attrFeeInfo_djyck.setN_fee_num(attrFeeInfo_djyck.getN_fee_num()
								+ Double.valueOf(mapValue.get("n_fee_num").toString()));
						attrFeeInfo_djyck.setDiscount_fee(attrFeeInfo_djyck.getDiscount_fee()
								+ Double.valueOf(mapValue.get("discount_fee").toString()));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		/** 根据优惠活动计算溢价费用 */
		this.saveYijiaFeeInfo(order_id, inst_id, order_from, orderAttrValues, attrFeeInfo_djyck, source_from, isZbOrder,
				isTbOrder);

		if (!isZbOrder && !EcsOrderConsts.ORDER_FROM_10003.equals(order_from)) {
			if (EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)) {
				this.saveHyjFeeInfo(order_id, inst_id, order_from, orderAttrValues, attrFeeInfoBusiRequests,
						source_from, goodsSpec);
			} else if (EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(type_id)) {
				this.saveHkFeeInfo(order_id, inst_id, order_from, orderAttrValues, attrFeeInfoBusiRequests,
						source_from);
			} else if (EcsOrderConsts.GOODS_TYPE_PHONE.equals(type_id)) {
				this.saveLjFeeInfo(order_id, inst_id, order_from, orderAttrValues, attrFeeInfoBusiRequests, source_from,
						goodsSpec);
			}
		}

		if (!StringUtils.isEmpty(fee_infos) && !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(fee_infos)) {
			JSONArray jsonArray = JSONArray.fromObject(fee_infos);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				fee_item_code = jsonObject.get("feeID") == null ? "" : jsonObject.get("feeID").toString();
				fee_item_name = jsonObject.get("feeDes") == null ? "" : jsonObject.get("feeDes").toString();
				o_fee_num = jsonObject.get("origFee") == null ? "" : jsonObject.get("origFee").toString();
				disacount_fee = jsonObject.get("reliefFee") == null ? "" : jsonObject.get("reliefFee").toString();
				disacount_reason = jsonObject.get("reliefResult") == null ? ""
						: jsonObject.get("reliefResult").toString();
				n_fee_num = jsonObject.get("realFee") == null ? "" : jsonObject.get("realFee").toString();
				is_aop_fee = jsonObject.get("is_aop_fee") == null ? "" : jsonObject.get("is_aop_fee").toString();
				fee_category = jsonObject.get("fee_rule_id") == null ? "" : jsonObject.get("fee_rule_id").toString();
				can_cancel_date = jsonObject.get("can_cancel_date") == null ? "" : jsonObject.get("can_cancel_date").toString();
				fee_type = jsonObject.get("fee_type") == null ? "" : jsonObject.get("fee_type").toString();
				double o_fee_num_d = 0d;
				double disacount_fee_d = 0d;
				double n_fee_num_d = 0d;
				if (StringUtils.isNotEmpty(o_fee_num)) {
					o_fee_num_d = Double.valueOf(o_fee_num);
				}
				if (StringUtils.isNotEmpty(disacount_fee)) {
					disacount_fee_d = Double.valueOf(disacount_fee);
				}
				if (StringUtils.isNotEmpty(n_fee_num)) {
					n_fee_num_d = Double.valueOf(n_fee_num);
				}

				Map mapValue = new HashMap();
				mapValue.put("fee_inst_id", this.baseDaoSupport.getSequences("seq_attr_fee_info"));
				mapValue.put("order_id", order_id);
				mapValue.put("inst_id", inst_id);
				mapValue.put("fee_item_code", fee_item_code);
				mapValue.put("fee_item_name", fee_item_name);
				mapValue.put("o_fee_num", o_fee_num_d);
				mapValue.put("discount_fee", disacount_fee_d);
				mapValue.put("discount_reason", disacount_reason);
				mapValue.put("n_fee_num", n_fee_num_d);
				mapValue.put("origin_mall", order_from);
				mapValue.put("fee_category", fee_category);
				mapValue.put("fee_type", fee_type);
				if (!StringUtil.isEmpty(is_aop_fee)) {
					mapValue.put("is_aop_fee", is_aop_fee);
				}
				if (!StringUtil.isEmpty(can_cancel_date)) {
					mapValue.put("can_cancel_date", can_cancel_date);
				}

				mapValue.put("source_from", source_from);

				if (!EcsOrderConsts.DJYCK_FEE_ITEM_ID.equals(fee_item_code)) { // 非多缴预存款
					AttrFeeInfoBusiRequest attrFeeInfoBusiRequest = new AttrFeeInfoBusiRequest();
					try {
						BeanUtils.copyProperties(attrFeeInfoBusiRequest, mapValue);
						attrFeeInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
						attrFeeInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						attrFeeInfoBusiRequests.add(attrFeeInfoBusiRequest);
						// attrFeeInfoBusiRequest.store();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else { // 多缴预存款
					try {
						if (StringUtil.isEmpty(attrFeeInfo_djyck.getFee_inst_id())) {
							BeanUtils.copyProperties(attrFeeInfo_djyck, mapValue);
						} else {
							attrFeeInfo_djyck.setO_fee_num(attrFeeInfo_djyck.getO_fee_num()
									+ Double.valueOf(mapValue.get("o_fee_num").toString()));
							attrFeeInfo_djyck.setN_fee_num(attrFeeInfo_djyck.getN_fee_num()
									+ Double.valueOf(mapValue.get("n_fee_num").toString()));
							attrFeeInfo_djyck.setDiscount_fee(attrFeeInfo_djyck.getDiscount_fee()
									+ Double.valueOf(mapValue.get("discount_fee").toString()));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		// 统一保存已经计算好的多缴预存款费用项
		if (!StringUtil.isEmpty(attrFeeInfo_djyck.getFee_inst_id())) {
			try {
				attrFeeInfo_djyck.setDb_action(ConstsCore.DB_ACTION_INSERT);
				attrFeeInfo_djyck.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				// attrFeeInfo_djyck.store();
				attrFeeInfoBusiRequests.add(attrFeeInfo_djyck);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		CommonData.getData().getOrderTreeBusiRequest().setFeeInfoBusiRequests(attrFeeInfoBusiRequests);
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		return null;
	}

	/**
	 * 根据优惠活动取溢价费用(activityList)
	 *
	 * @param order_id
	 * @param inst_id
	 * @param order_from
	 * @param orderAttrValues
	 */
	public void saveYijiaFeeInfo(String order_id, String inst_id, String order_from, Map orderAttrValues,
			AttrFeeInfoBusiRequest attrFeeInfo_djyck, String source_from, boolean isZbOrder, boolean isTbOrder) {
		// boolean isTbOrder = false;// 是否淘宝订单,默认否
		// java.util.List<Map> relations =
		// CommonDataFactory.getInstance().getDictRelationListData(StypeConsts.ISTBORDER);
		// if (relations != null && relations.size() > 0) {
		// for (Map relation : relations) {
		// if
		// (order_from.equals(com.ztesoft.ibss.common.util.Const.getStrValue(relation,
		// "other_field_value"))) {
		// isTbOrder = true;
		// break;
		// }
		// }
		// }

		BigDecimal fee_num = new BigDecimal("0.00");
		boolean flag = false;

		// 這裏只有新商城的,需要添加上總部商城和淘寶商城的(RESERVE9)
		if (isZbOrder || isTbOrder) {
			String reserve9 = (String) orderAttrValues.get(AttrConsts.RESERVE9);
			reserve9 = reserve9 == null ? "" : reserve9;
			logger.info("feeInfoHanlder:" + order_id + "  reserve9 : " + reserve9);

			String activity_list = (String) orderAttrValues.get(AttrConsts.ACTIVITY_LIST);
			logger.info("feeInfoHanlder:" + order_id + "  activitylist: " + activity_list);
			// if(null==reserve9 || "".equalsIgnoreCase(reserve9)) return;

			String[] pmt_codess = reserve9.split(",");
			for (String pmt_code : pmt_codess) {
				String activity_type = CommonDataFactory.getInstance().getActivitySpec(pmt_code,
						SpecConsts.ACTIVITY_TYPE);
				if (StringUtils.equals(Consts.PMT_TYPE_OVERPRICE, activity_type)) { // 溢价
					flag = true;
					String disacount_num = CommonDataFactory.getInstance().getActivitySpec(pmt_code,
							SpecConsts.DISACOUNT_NUM);
					fee_num = fee_num.add(new BigDecimal(disacount_num).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}
		} else {
			// 這下面屬於新商城的判斷
			String activity_list = (String) orderAttrValues.get(AttrConsts.ACTIVITY_LIST);
			if (!StringUtils.isEmpty(activity_list) && !"-1".equals(activity_list)) {
				JSONArray jsonArray = JSONArray.fromObject(activity_list);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
					String activity_code = jsonObject.get("activity_code") == null ? ""
							: jsonObject.get("activity_code").toString();
					String activity_type = CommonDataFactory.getInstance().getActivitySpec(activity_code,
							SpecConsts.ACTIVITY_TYPE);
					if (StringUtils.equals(Consts.PMT_TYPE_OVERPRICE, activity_type)) { // 溢价
						flag = true;
						String disacount_num = CommonDataFactory.getInstance().getActivitySpec(activity_code,
								SpecConsts.DISACOUNT_NUM);
						fee_num = fee_num.add(new BigDecimal(disacount_num).setScale(2, BigDecimal.ROUND_HALF_UP));
					}
				}
			}
		}

		// 只要有一個活動是屬於溢價的,就需要保存溢價記錄
		if (flag) {
			Map mapValue = new HashMap();
			mapValue.put("fee_inst_id", this.baseDaoSupport.getSequences("seq_attr_fee_info"));
			mapValue.put("order_id", order_id);
			mapValue.put("inst_id", inst_id);
			mapValue.put("fee_item_code", EcsOrderConsts.DJYCK_FEE_ITEM_ID);
			mapValue.put("fee_item_name", "多交预存款费用");
			mapValue.put("o_fee_num", fee_num);
			mapValue.put("discount_fee", EcsOrderConsts.DEFAULT_FEE_ZERO);
			mapValue.put("discount_reason", EcsOrderConsts.EMPTY_STR_VALUE);
			mapValue.put("n_fee_num", fee_num);
			mapValue.put("origin_mall", order_from);
			mapValue.put("source_from", source_from);

			try {
				if (StringUtil.isEmpty(attrFeeInfo_djyck.getFee_inst_id())) {
					BeanUtils.copyProperties(attrFeeInfo_djyck, mapValue);
				} else {
					attrFeeInfo_djyck.setO_fee_num(
							attrFeeInfo_djyck.getO_fee_num() + Double.valueOf(mapValue.get("o_fee_num").toString()));
					attrFeeInfo_djyck.setN_fee_num(
							attrFeeInfo_djyck.getN_fee_num() + Double.valueOf(mapValue.get("n_fee_num").toString()));
					attrFeeInfo_djyck.setDiscount_fee(attrFeeInfo_djyck.getDiscount_fee()
							+ Double.valueOf(mapValue.get("discount_fee").toString()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 号卡费用项
	 *
	 * @param order_id
	 * @param inst_id
	 */
	public void saveHkFeeInfo(String order_id, String inst_id, String order_from, Map orderAttrValues,
			List<AttrFeeInfoBusiRequest> attrFeeInfoBusiRequests, String source_from) {
		Map mapValue = new HashMap();
		mapValue.put("fee_inst_id", this.baseDaoSupport.getSequences("seq_attr_fee_info"));
		mapValue.put("order_id", order_id);
		mapValue.put("inst_id", inst_id);
		mapValue.put("fee_item_code", "1001");
		mapValue.put("fee_item_name", "USIM卡费用");
		mapValue.put("o_fee_num", "0");
		mapValue.put("discount_fee", "0");
		mapValue.put("discount_reason", "");
		mapValue.put("n_fee_num", "0");
		mapValue.put("origin_mall", order_from);
		mapValue.put("source_from", source_from);

		AttrFeeInfoBusiRequest attrFeeInfoBusiRequest = new AttrFeeInfoBusiRequest();
		try {
			BeanUtils.copyProperties(attrFeeInfoBusiRequest, mapValue);
			attrFeeInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			attrFeeInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			// attrFeeInfoBusiRequest.store();
			attrFeeInfoBusiRequests.add(attrFeeInfoBusiRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String good_no_fee = null;
		String phone_num = (String) orderAttrValues.get("phone_num");
		String liang_price_mall = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.LIANG_PRICE_MALL,
				order_from);
		if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(liang_price_mall)) {
			good_no_fee = orderAttrValues.get("good_no_fee") == null ? "0"
					: "".equals(orderAttrValues.get("good_no_fee").toString()) ? "0"
							: orderAttrValues.get("good_no_fee").toString();
		} else if (!StringUtils.isEmpty(phone_num)) {
			NumberSpecReq req = new NumberSpecReq();
			req.setDn_no(phone_num);
			String deposit = CommonDataFactory.getInstance().getNumberSpec(phone_num, "deposit");
			good_no_fee = StringUtils.isEmpty(deposit) ? "0" : deposit;
		}
		mapValue = new HashMap();
		mapValue.put("fee_inst_id", this.baseDaoSupport.getSequences("seq_attr_fee_info"));
		mapValue.put("order_id", order_id);
		mapValue.put("inst_id", inst_id);
		mapValue.put("fee_item_code", "2001");
		mapValue.put("fee_item_name", "靓号预存款");
		mapValue.put("o_fee_num", good_no_fee);
		mapValue.put("discount_fee", "0");
		mapValue.put("discount_reason", "");
		mapValue.put("n_fee_num", good_no_fee);
		mapValue.put("origin_mall", order_from);
		mapValue.put("source_from", source_from);

		AttrFeeInfoBusiRequest liangFee = new AttrFeeInfoBusiRequest();
		try {
			BeanUtils.copyProperties(liangFee, mapValue);
			liangFee.setDb_action(ConstsCore.DB_ACTION_INSERT);
			liangFee.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			// liangFee.store();
			attrFeeInfoBusiRequests.add(liangFee);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mapValue = new HashMap();
		mapValue.put("fee_inst_id", this.baseDaoSupport.getSequences("seq_attr_fee_info"));
		mapValue.put("order_id", order_id);
		mapValue.put("inst_id", inst_id);
		mapValue.put("fee_item_code", "2002");
		mapValue.put("fee_item_name", "合约费用");
		mapValue.put("o_fee_num", orderAttrValues.get("deposit_fee"));
		mapValue.put("discount_fee", "0");
		mapValue.put("discount_reason", "");
		mapValue.put("n_fee_num", orderAttrValues.get("deposit_fee"));
		mapValue.put("origin_mall", order_from);
		mapValue.put("source_from", source_from);

		AttrFeeInfoBusiRequest contractFee = new AttrFeeInfoBusiRequest();
		try {
			BeanUtils.copyProperties(contractFee, mapValue);
			contractFee.setDb_action(ConstsCore.DB_ACTION_INSERT);
			contractFee.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			attrFeeInfoBusiRequests.add(contractFee);
			// contractFee.store();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 裸机费用项
	 *
	 * @param order_id
	 * @param inst_id
	 */
	public void saveLjFeeInfo(String order_id, String inst_id, String order_from, Map orderAttrValues,
			List<AttrFeeInfoBusiRequest> attrFeeInfoBusiRequests, String source_from, Map goodsSpec) {
		// String price = CommonDataFactory.getInstance().getGoodSpec(order_id, null,
		// SpecConsts.PRICE);
		String price = Const.getStrValue(goodsSpec, SpecConsts.PRICE);
		Map mapValue = new HashMap();
		mapValue.put("fee_inst_id", this.baseDaoSupport.getSequences("seq_attr_fee_info"));
		mapValue.put("order_id", order_id);
		mapValue.put("inst_id", inst_id);
		mapValue.put("fee_item_code", "1002");
		mapValue.put("fee_item_name", "终端费用");
		mapValue.put("o_fee_num", price);
		mapValue.put("discount_fee", "0");
		mapValue.put("discount_reason", "");
		mapValue.put("n_fee_num", price);
		mapValue.put("origin_mall", order_from);
		mapValue.put("source_from", source_from);

		AttrFeeInfoBusiRequest attrFeeInfoBusiRequest = new AttrFeeInfoBusiRequest();
		try {
			BeanUtils.copyProperties(attrFeeInfoBusiRequest, mapValue);
			attrFeeInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			attrFeeInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			attrFeeInfoBusiRequests.add(attrFeeInfoBusiRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 合约机费用项
	 *
	 * @param order_id
	 * @param inst_id
	 */
	public void saveHyjFeeInfo(String order_id, String inst_id, String order_from, Map orderAttrValues,
			List<AttrFeeInfoBusiRequest> attrFeeInfoBusiRequests, String source_from, Map goodsSpec) {
		Map mapValue = new HashMap();
		mapValue.put("fee_inst_id", this.baseDaoSupport.getSequences("seq_attr_fee_info"));
		mapValue.put("order_id", order_id);
		mapValue.put("inst_id", inst_id);
		mapValue.put("fee_item_code", "1001");
		mapValue.put("fee_item_name", "USIM卡费用");
		mapValue.put("o_fee_num", "0");
		mapValue.put("discount_fee", "0");
		mapValue.put("discount_reason", "");
		mapValue.put("n_fee_num", "0");
		mapValue.put("origin_mall", order_from);
		mapValue.put("source_from", source_from);

		AttrFeeInfoBusiRequest attrFeeInfoBusiRequest = new AttrFeeInfoBusiRequest();
		try {
			BeanUtils.copyProperties(attrFeeInfoBusiRequest, mapValue);
			attrFeeInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			attrFeeInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			attrFeeInfoBusiRequests.add(attrFeeInfoBusiRequest);
			// attrFeeInfoBusiRequest.store();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// String price = CommonDataFactory.getInstance().getGoodSpec(order_id, null,
		// SpecConsts.MOBILE_PRICE);
		String price = Const.getStrValue(goodsSpec, SpecConsts.MOBILE_PRICE);
		mapValue = new HashMap();
		mapValue.put("fee_inst_id", this.baseDaoSupport.getSequences("seq_attr_fee_info"));
		mapValue.put("order_id", order_id);
		mapValue.put("inst_id", inst_id);
		mapValue.put("fee_item_code", "1002");
		mapValue.put("fee_item_name", "终端费用");
		mapValue.put("o_fee_num", price);
		mapValue.put("discount_fee", "0");
		mapValue.put("discount_reason", "");
		mapValue.put("n_fee_num", price);
		mapValue.put("origin_mall", order_from);
		mapValue.put("source_from", source_from);

		AttrFeeInfoBusiRequest terminalFee = new AttrFeeInfoBusiRequest();
		try {
			BeanUtils.copyProperties(attrFeeInfoBusiRequest, mapValue);
			attrFeeInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			attrFeeInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			attrFeeInfoBusiRequests.add(terminalFee);
			// terminalFee.store();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String good_no_fee = null;
		String phone_num = (String) orderAttrValues.get("phone_num");
		String liang_price_mall = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.LIANG_PRICE_MALL,
				order_from);
		if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(liang_price_mall)) {
			good_no_fee = orderAttrValues.get("good_no_fee") == null ? "0"
					: "".equals(orderAttrValues.get("good_no_fee").toString()) ? "0"
							: orderAttrValues.get("good_no_fee").toString();
		} else if (!StringUtils.isEmpty(phone_num)) {
			NumberSpecReq req = new NumberSpecReq();
			req.setDn_no(phone_num);
			String deposit = CommonDataFactory.getInstance().getNumberSpec(phone_num, "deposit");
			good_no_fee = StringUtils.isEmpty(deposit) ? "0" : deposit;
		}
		mapValue = new HashMap();
		mapValue.put("fee_inst_id", this.baseDaoSupport.getSequences("seq_attr_fee_info"));
		mapValue.put("order_id", order_id);
		mapValue.put("inst_id", inst_id);
		mapValue.put("fee_item_code", "2001");
		mapValue.put("fee_item_name", "靓号预存款");
		mapValue.put("o_fee_num", good_no_fee);
		mapValue.put("discount_fee", "0");
		mapValue.put("discount_reason", "");
		mapValue.put("n_fee_num", good_no_fee);
		mapValue.put("origin_mall", order_from);
		mapValue.put("source_from", source_from);

		AttrFeeInfoBusiRequest liangFee = new AttrFeeInfoBusiRequest();
		try {
			BeanUtils.copyProperties(attrFeeInfoBusiRequest, mapValue);
			attrFeeInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			attrFeeInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			attrFeeInfoBusiRequests.add(liangFee);
			// liangFee.store();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mapValue = new HashMap();
		mapValue.put("fee_inst_id", this.baseDaoSupport.getSequences("seq_attr_fee_info"));
		mapValue.put("order_id", order_id);
		mapValue.put("inst_id", inst_id);
		mapValue.put("fee_item_code", "2002");
		mapValue.put("fee_item_name", "合约费用");
		mapValue.put("o_fee_num", orderAttrValues.get("deposit_fee"));
		mapValue.put("discount_fee", "0");
		mapValue.put("discount_reason", "");
		mapValue.put("n_fee_num", orderAttrValues.get("deposit_fee"));
		mapValue.put("origin_mall", order_from);
		mapValue.put("source_from", source_from);

		AttrFeeInfoBusiRequest contractFee = new AttrFeeInfoBusiRequest();
		try {
			BeanUtils.copyProperties(contractFee, mapValue);
			contractFee.setDb_action(ConstsCore.DB_ACTION_INSERT);
			contractFee.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			attrFeeInfoBusiRequests.add(contractFee);
			// contractFee.store();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

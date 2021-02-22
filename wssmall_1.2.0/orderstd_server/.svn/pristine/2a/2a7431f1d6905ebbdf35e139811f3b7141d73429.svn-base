package com.ztesoft.newstd.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.newstd.common.CommonData;

import consts.ConstsCore;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.AttrDiscountInfoBusiRequest;
import zte.net.ecsord.params.spec.req.ActivitySpecReq;
import zte.net.ecsord.params.spec.resp.ActivitySpecResp;

public class AttrDiscountInfoHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		List<AttrDiscountInfoBusiRequest> attrDiscountInfoBusiRequests = (List<AttrDiscountInfoBusiRequest>) params
				.getBusiRequest();

		// 从orderAttrValues获取的属性
		String discountInfos = (String) orderAttrValues.get(AttrConsts.DISCOUNT_INFOS);
		String reserve9 = (String) orderAttrValues.get(AttrConsts.RESERVE9);
		String couponsList = (String) orderAttrValues.get(AttrConsts.COUPONS_LIST);
		String activity_list = (String) orderAttrValues.get(AttrConsts.ACTIVITY_LIST);

		CommHandler commHandler = new CommHandler();
		String source_from = commHandler.getSourceFrom();
		boolean isTbOrder = Boolean.parseBoolean(CommonData.getData().getIsTbOrder());
		boolean isZbOrder = Boolean.parseBoolean(CommonData.getData().getIsZbOrder());
		if (isZbOrder) {
			if (!StringUtils.isEmpty(discountInfos) && !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(discountInfos)) {
				saveZBDiscountInfo(order_id, inst_id, discountInfos, attrDiscountInfoBusiRequests, source_from);
			}
		} else if (isTbOrder) {// 淘宝优惠信息
			saveTbDiscountInfo(order_id, inst_id, reserve9, attrDiscountInfoBusiRequests, source_from);
		} else {
			saveDiscountInfo(order_id, inst_id, discountInfos, attrDiscountInfoBusiRequests, source_from);
		}

		if (!StringUtils.isEmpty(couponsList) && !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(couponsList)) {
			saveCouponslist(order_id, inst_id, couponsList, source_from, attrDiscountInfoBusiRequests);
		}

		if (!StringUtils.isEmpty(activity_list) && !"-1".equals(activity_list)) {
			saveActivityList(order_id, inst_id, activity_list, source_from, attrDiscountInfoBusiRequests);
		}

		CommonData.getData().getOrderTreeBusiRequest().setDiscountInfoBusiRequests(attrDiscountInfoBusiRequests);
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

	public void saveTbDiscountInfo(String order_id, String inst_id, String pmt_code,
			List<AttrDiscountInfoBusiRequest> attrDiscountInfoBusiRequests, String source_from) {
		ActivitySpecReq req = new ActivitySpecReq();
		req.setActivity_code(pmt_code);
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		ActivitySpecResp response = client.execute(req, ActivitySpecResp.class);
		Map specs = response.getSpecs();
		if (specs != null && !specs.isEmpty()) {
			AttrDiscountInfoBusiRequest attrDiscountInfoBusiRequest = new AttrDiscountInfoBusiRequest();
			Map mapValue = new HashMap();
			mapValue.put("discount_inst_id", this.baseDaoSupport.getSequences("SEQ_ATTR_DISCOUNT_INFO"));
			mapValue.put("order_id", order_id);
			mapValue.put("inst_id", inst_id);
			mapValue.put("activity_code", Const.getStrValue(specs, "activity_code"));
			mapValue.put("activity_name", Const.getStrValue(specs, "activity_name"));
			mapValue.put("activity_desc", Const.getStrValue(specs, "activity_desc"));
			mapValue.put("activity_type", "3");
			mapValue.put("discount_range", "");
			String disacount_num = Const.getStrValue(specs, "disacount_num");
			if (StringUtils.isEmpty(disacount_num))
				disacount_num = "0";
			mapValue.put("discount_num", disacount_num);
			mapValue.put("discount_unit", "02");
			mapValue.put("source_from", source_from);
			try {
				BeanUtils.copyProperties(attrDiscountInfoBusiRequest, mapValue);
				attrDiscountInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
				attrDiscountInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				attrDiscountInfoBusiRequests.add(attrDiscountInfoBusiRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// this.baseDaoSupport.insert("es_attr_discount_info", mapValue);
	}

	/**
	 * 保存总部优惠信息到业务表，优惠信息取总部同步过来的优惠信息
	 * 
	 * @param order_id
	 * @param inst_id
	 */
	public void saveZBDiscountInfo(String order_id, String inst_id, String discountInfos,
			List<AttrDiscountInfoBusiRequest> attrDiscountInfoBusiRequests, String source_from) {

		JSONArray jsonArray = JSONArray.fromObject(discountInfos);
		String discountType = "";
		String discountID = "";
		String discountName = "";
		String discountValue = "";
		String disacount_range = "";
		String disacount_unit = "02";
		for (int i = 0; i < jsonArray.size(); i++) {
			AttrDiscountInfoBusiRequest attrDiscountInfoBusiRequest = new AttrDiscountInfoBusiRequest();
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			discountType = jsonObject.get("discountType") == null ? "" : jsonObject.get("discountType").toString();
			discountID = jsonObject.get("discountID") == null ? "" : jsonObject.get("discountID").toString();
			discountName = jsonObject.get("discountName") == null ? "" : jsonObject.get("discountName").toString();
			discountValue = jsonObject.get("discountValue") == null ? "" : jsonObject.get("discountValue").toString();

			Map mapValue = new HashMap();
			mapValue.put("discount_inst_id", this.baseDaoSupport.getSequences("SEQ_ATTR_DISCOUNT_INFO"));
			mapValue.put("order_id", order_id);
			mapValue.put("inst_id", inst_id);
			mapValue.put("activity_code", discountID);
			mapValue.put("activity_name", discountName);
			mapValue.put("activity_desc", discountName);
			mapValue.put("activity_type", discountType);
			mapValue.put("discount_range", disacount_range);
			mapValue.put("discount_num", discountValue);
			mapValue.put("discount_unit", disacount_unit);
			mapValue.put("source_from", source_from);
			try {
				BeanUtils.copyProperties(attrDiscountInfoBusiRequest, mapValue);
				attrDiscountInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
				attrDiscountInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				attrDiscountInfoBusiRequests.add(attrDiscountInfoBusiRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 保存优惠信息到业务表
	 * 
	 * @param order_id
	 * @param inst_id
	 */
	public void saveDiscountInfo(String order_id, String inst_id, String discountInfos,
			List<AttrDiscountInfoBusiRequest> attrDiscountInfoBusiRequests, String source_from) {
		String discountType = "";
		String discountID = "";
		String discountName = "";
		String discountDesc = "";
		String discountValue = "";
		String disacount_range = "";
		String disacount_unit = "02";
		if (StringUtils.isNotEmpty(discountInfos)) {
			JSONArray jsonArray = JSONArray.fromObject(discountInfos);
			for (int i = 0; i < jsonArray.size(); i++) {
				AttrDiscountInfoBusiRequest attrDiscountInfoBusiRequest = new AttrDiscountInfoBusiRequest();
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				discountID = jsonObject.get("activity_code") == null ? "" : jsonObject.get("activity_code").toString();
				discountName = jsonObject.get("activity_name") == null ? ""
						: jsonObject.get("activity_name").toString();
				discountDesc = jsonObject.get("activity_desc") == null ? ""
						: jsonObject.get("activity_desc").toString();
				discountType = jsonObject.get("activity_type") == null ? ""
						: jsonObject.get("activity_type").toString();
				discountValue = jsonObject.get("discount_amount") == null ? ""
						: jsonObject.get("discount_amount").toString();

				Map mapValue = new HashMap();
				mapValue.put("discount_inst_id", this.baseDaoSupport.getSequences("SEQ_ATTR_DISCOUNT_INFO"));
				mapValue.put("order_id", order_id);
				mapValue.put("inst_id", inst_id);
				mapValue.put("activity_code", discountID);
				mapValue.put("activity_name", discountName);
				mapValue.put("activity_desc", discountDesc);
				mapValue.put("activity_type", discountType);
				mapValue.put("discount_range", disacount_range);
				mapValue.put("discount_num", discountValue);
				mapValue.put("discount_unit", disacount_unit);
				mapValue.put("source_from", source_from);
				try {
					BeanUtils.copyProperties(attrDiscountInfoBusiRequest, mapValue);
					attrDiscountInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrDiscountInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrDiscountInfoBusiRequests.add(attrDiscountInfoBusiRequest);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 新商城优惠券列表
	 * 
	 * @param order_id
	 * @param inst_id
	 * @param couponsList
	 */
	public void saveCouponslist(String order_id, String inst_id, String couponsList, String source_from,
			List<AttrDiscountInfoBusiRequest> attrDiscountInfoBusiRequests) {
		JSONArray jsonArray = JSONArray.fromObject(couponsList);
		String discountType = "";
		String discountID = "";
		String discountName = "";
		String discountValue = "";
		String disacount_range = "";
		String disacount_unit = "02";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			AttrDiscountInfoBusiRequest attrDiscountInfoBusiRequest = new AttrDiscountInfoBusiRequest();
			discountType = jsonObject.get("coupons_type") == null ? "" : jsonObject.get("coupons_type").toString();
			discountID = jsonObject.get("coupons_id") == null ? "" : jsonObject.get("coupons_id").toString();
			discountName = jsonObject.get("coupons_name") == null ? "" : jsonObject.get("coupons_name").toString();
			discountValue = jsonObject.get("coupons_fee") == null ? "" : jsonObject.get("coupons_fee").toString();

			Map mapValue = new HashMap();
			mapValue.put("discount_inst_id", this.baseDaoSupport.getSequences("SEQ_ATTR_DISCOUNT_INFO"));
			mapValue.put("order_id", order_id);
			mapValue.put("inst_id", inst_id);
			mapValue.put("activity_code", discountID);
			mapValue.put("activity_name", discountName);
			mapValue.put("activity_desc", discountName);
			mapValue.put("activity_type", discountType);
			mapValue.put("discount_range", disacount_range);
			mapValue.put("discount_num", discountValue);
			mapValue.put("discount_unit", disacount_unit);
			mapValue.put("source_from", source_from);
			// this.baseDaoSupport.insert(" es_attr_discount_inf",mapValue);
			try {
				BeanUtils.copyProperties(attrDiscountInfoBusiRequest, mapValue);
				attrDiscountInfoBusiRequests.add(attrDiscountInfoBusiRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 保存商城优惠信息到业务表，优惠信息从活动表获取
	 * 
	 * @param order_id
	 * @param inst_id
	 */
	public void saveActivityList(String order_id, String inst_id, String activity_list, String source_from,
			List<AttrDiscountInfoBusiRequest> attrDiscountInfoBusiRequests) {
		JSONArray jsonArray = JSONArray.fromObject(activity_list);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			AttrDiscountInfoBusiRequest attrDiscountInfoBusiRequest = new AttrDiscountInfoBusiRequest();
			// 取优惠活动编码
			String activity_code = jsonObject.get("activity_code") == null ? ""
					: jsonObject.get("activity_code").toString();

			ActivitySpecReq req = new ActivitySpecReq();
			req.setActivity_code(activity_code);
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			ActivitySpecResp response = client.execute(req, ActivitySpecResp.class);
			Map specs = response.getSpecs();

			Map mapValue = new HashMap();
			mapValue.put("discount_inst_id", this.baseDaoSupport.getSequences("SEQ_ATTR_DISCOUNT_INFO"));
			mapValue.put("order_id", order_id);
			mapValue.put("inst_id", inst_id);
			mapValue.put("activity_code", Const.getStrValue(specs, "activity_code"));
			mapValue.put("activity_name", Const.getStrValue(specs, "activity_name"));
			mapValue.put("activity_desc", Const.getStrValue(specs, "activity_desc"));
			mapValue.put("activity_type", "3");
			mapValue.put("discount_range", "");
			String pmt_solution = Const.getStrValue(specs, "disacount_num");
			if (StringUtils.isEmpty(pmt_solution))
				pmt_solution = "0";
			mapValue.put("discount_num", pmt_solution);
			mapValue.put("discount_unit", "02");
			mapValue.put("source_from", source_from);
			try {
				BeanUtils.copyProperties(attrDiscountInfoBusiRequest, mapValue);
				attrDiscountInfoBusiRequests.add(attrDiscountInfoBusiRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

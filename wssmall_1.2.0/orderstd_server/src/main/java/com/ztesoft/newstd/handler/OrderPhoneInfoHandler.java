package com.ztesoft.newstd.handler;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.newstd.common.CommonData;

import consts.ConstsCore;
import net.sf.json.JSONObject;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;

public class OrderPhoneInfoHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		String order_from = params.getOrder_from(); // 订单来源
		OrderPhoneInfoBusiRequest orderPhoneInfoBusiRequest;
		if (params.getBusiRequest() == null)
			orderPhoneInfoBusiRequest = new OrderPhoneInfoBusiRequest();
		else
			orderPhoneInfoBusiRequest = (OrderPhoneInfoBusiRequest) params.getBusiRequest();

		CommHandler commHandler = new CommHandler();
		String source_from = commHandler.getSourceFrom();
		String phone_num = orderAttrValues.get(AttrConsts.PHONE_NUM) == null ? ""
				: orderAttrValues.get(AttrConsts.PHONE_NUM).toString();
		String id = this.baseDaoSupport.getSequences("ES_ORDER_PHONE_INFO_SEQ");
		orderPhoneInfoBusiRequest.setId(id);
		orderPhoneInfoBusiRequest.setOrder_id(order_id);
		orderPhoneInfoBusiRequest.setOrder_from(order_from);
		orderPhoneInfoBusiRequest.setPhone_num(phone_num);
		String phoneinfo = (String) orderAttrValues.get(AttrConsts.PHONE_INFO);
		if (!StringUtils.isEmpty(phone_num)) {// 号码不为空的时候做预占
			orderPhoneInfoBusiRequest = this.setPhoneInfo(orderPhoneInfoBusiRequest, phoneinfo, phone_num);// 保存报文传过来的信息
		} else {
			orderPhoneInfoBusiRequest.setOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_0);
			orderPhoneInfoBusiRequest.setOperatorState("");
		}
		orderPhoneInfoBusiRequest.setSource_from(source_from);
		orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
		orderPhoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		CommonData.getData().getOrderTreeBusiRequest().setPhoneInfoBusiRequest(orderPhoneInfoBusiRequest);
		// orderPhoneInfoBusiRequest.store();
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

	public OrderPhoneInfoBusiRequest setPhoneInfo(OrderPhoneInfoBusiRequest phoneInfoReq, String phoneinfo,
			String phone_num) {
		Map mapValue = new HashMap<String, String>();
		if (!StringUtils.isEmpty(phoneinfo) && !"null".equals(phoneinfo)
				&& !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(phoneinfo)) {
			phoneinfo = phoneinfo.substring(1, phoneinfo.length() - 1);
			JSONObject jsonObject = JSONObject.fromObject(phoneinfo);
			JSONObject niceinfo = jsonObject.getJSONObject("niceInfo");
			String classId_str = niceinfo.get("classId") == null ? "" : niceinfo.get("classId").toString();
			String changeTagChe_str = niceinfo.get("changeTagChe") == null ? ""
					: niceinfo.get("changeTagChe").toString();
			String cancelTagChe_str = niceinfo.get("cancelTagChe") == null ? ""
					: niceinfo.get("cancelTagChe").toString();
			String changeTagPro_str = niceinfo.get("changeTagPro") == null ? ""
					: niceinfo.get("changeTagPro").toString();
			String cancelTagPro_str = niceinfo.get("cancelTagPro") == null ? ""
					: niceinfo.get("cancelTagPro").toString();

			int classId = 0;
			int changeTagChe = 0;
			int cancelTagChe = 0;
			int changeTagPro = 0;
			int cancelTagPro = 0;

			if (StringUtils.isNotEmpty(classId_str)) {
				classId = Integer.valueOf(classId_str);
			} else {
				classId = 9;
			}
			if (StringUtils.isNotEmpty(changeTagChe_str)) {
				changeTagChe = Integer.valueOf(changeTagChe_str);
			}
			if (StringUtils.isNotEmpty(cancelTagChe_str)) {
				cancelTagChe = Integer.valueOf(cancelTagChe_str);
			}
			if (StringUtils.isNotEmpty(changeTagPro_str)) {
				changeTagPro = Integer.valueOf(changeTagPro_str);
			}
			if (StringUtils.isNotEmpty(cancelTagPro_str)) {
				cancelTagPro = Integer.valueOf(cancelTagChe_str);
			}
			// int类型
			mapValue.put("classId", classId);// 默认 9 是普通号码
			mapValue.put("changeTagChe", changeTagChe);
			mapValue.put("cancelTagChe", cancelTagChe);
			mapValue.put("changeTagPro", changeTagPro);
			mapValue.put("cancelTagPro", cancelTagPro);
			// str类型
			String a = "";
			mapValue.put("phone_num", phone_num);

			String operatorState = jsonObject.get("operatorState") == null ? ""
					: jsonObject.get("operatorState").toString();
			String occupiedFlag = EcsOrderConsts.OCCUPIEDFLAG_0;
			String bssOccupiedFlag = EcsOrderConsts.OCCUPIEDFLAG_0;
			if (!StringUtils.isEmpty(operatorState)) {
				if (EcsOrderConsts.OPERATOR_STATE_0.equals(operatorState)) {
					occupiedFlag = EcsOrderConsts.OCCUPIEDFLAG_1;
					bssOccupiedFlag = EcsOrderConsts.BSS_OCCUPIEDFLAG_1;
				} else if (EcsOrderConsts.OPERATOR_STATE_2.equals(operatorState)) {
					occupiedFlag = EcsOrderConsts.OCCUPIEDFLAG_3;
					bssOccupiedFlag = EcsOrderConsts.BSS_OCCUPIEDFLAG_1;
				} else if (EcsOrderConsts.OPERATOR_STATE_4.equals(operatorState)) {
					occupiedFlag = EcsOrderConsts.OCCUPIEDFLAG_2;
					bssOccupiedFlag = EcsOrderConsts.BSS_OCCUPIEDFLAG_1;
				}
			}
			mapValue.put("operatorState", operatorState);
			mapValue.put("occupiedFlag", occupiedFlag);
			mapValue.put("bssOccupiedFlag", bssOccupiedFlag);

			mapValue.put("proKey", jsonObject.get("proKey") == null ? "" : jsonObject.get("proKey").toString());
			mapValue.put("occupiedTime",
					jsonObject.get("occupiedTime") == null ? "" : jsonObject.get("occupiedTime").toString());
			mapValue.put("proKeyMode",
					jsonObject.get("proKeyMode") == null ? "" : jsonObject.get("proKeyMode").toString());

			mapValue.put("advancePay",
					niceinfo.get("advancePay") == null ? "0" : niceinfo.get("advancePay").toString());
			mapValue.put("advanceCom", niceinfo.get("advanceCom") == null ? "" : niceinfo.get("advanceCom").toString());
			mapValue.put("advanceSpe", niceinfo.get("advanceSpe") == null ? "" : niceinfo.get("advanceSpe").toString());
			mapValue.put("numThawTim", niceinfo.get("numThawTim") == null ? "" : niceinfo.get("numThawTim").toString());
			mapValue.put("numThawPro", niceinfo.get("numThawPro") == null ? "" : niceinfo.get("numThawPro").toString());
			mapValue.put("lowCostChe", niceinfo.get("lowCostChe") == null ? "" : niceinfo.get("lowCostChe").toString());
			mapValue.put("timeDurChe", niceinfo.get("timeDurChe") == null ? "" : niceinfo.get("timeDurChe").toString());
			mapValue.put("bremonChe", niceinfo.get("bremon_che") == null ? "" : niceinfo.get("bremon_che").toString());
			mapValue.put("lowCostPro", niceinfo.get("lowCostPro") == null ? "" : niceinfo.get("lowCostPro").toString());
			mapValue.put("timeDurPro", niceinfo.get("timeDurPro") == null ? "" : niceinfo.get("timeDurPro").toString());
			mapValue.put("broMonPro", niceinfo.get("broMonPro") == null ? "" : niceinfo.get("broMonPro").toString());
		} else {
			mapValue.put("occupiedFlag", EcsOrderConsts.OCCUPIEDFLAG_0);
		}
		try {
			BeanUtils.copyProperties(phoneInfoReq, mapValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return phoneInfoReq;
	}
}

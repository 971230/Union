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
import zte.net.ecsord.params.busi.req.OrderSubOtherInfoBusiRequest;

public class OrderSubOtherInfoNewHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值

		OrderSubOtherInfoBusiRequest orderSubOtherInfoBusiRequest;
		if (params.getBusiRequest() == null)
			orderSubOtherInfoBusiRequest = new OrderSubOtherInfoBusiRequest();
		else
			orderSubOtherInfoBusiRequest = (OrderSubOtherInfoBusiRequest) params.getBusiRequest();

		String subotherinfo = String.valueOf(orderAttrValues.get(AttrConsts.SUBOTHER_INFO));

		if (!StringUtils.isEmpty(subotherinfo) && !"null".equals(subotherinfo)
				&& !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(subotherinfo)) {
			if (orderSubOtherInfoBusiRequest == null)
				orderSubOtherInfoBusiRequest = new OrderSubOtherInfoBusiRequest();
			JSONObject jsonObject = JSONObject.fromObject(subotherinfo);
			String user_group = jsonObject.get("user_group") == null ? "" : jsonObject.getString("user_group");
			String lan_school = jsonObject.get("lan_school") == null ? "" : jsonObject.getString("lan_school");
			String complex_grid_id = jsonObject.get("complex_grid_id") == null ? ""
					: jsonObject.getString("complex_grid_id");
			String cust_building_id = jsonObject.get("cust_building_id") == null ? ""
					: jsonObject.getString("cust_building_id");
			String grp_grid_id = jsonObject.get("grp_grid_id") == null ? "" : jsonObject.getString("grp_grid_id");
			String grpuser_ope_model = jsonObject.get("grpuser_ope_model") == null ? ""
					: jsonObject.getString("grpuser_ope_model");
			String group_code = jsonObject.get("group_code") == null ? "" : jsonObject.getString("group_code");
			String cust_id = jsonObject.get("cust_id") == null ? "" : jsonObject.getString("cust_id");
			String certify_flag = jsonObject.get("certify_flag") == null ? "" : jsonObject.getString("certify_flag");
			String certify_cert_type = jsonObject.get("certify_cert_type") == null ? ""
					: jsonObject.getString("certify_cert_type");
			String certify_cert_num = jsonObject.get("certify_cert_num") == null ? ""
					: jsonObject.getString("certify_cert_num");
			String certify_cust_name = jsonObject.get("certify_cust_name") == null ? ""
					: jsonObject.getString("certify_cust_name");
			String certify_cert_addr = jsonObject.get("certify_cert_addr") == null ? ""
					: jsonObject.getString("certify_cert_addr");

			Map mapValue = new HashMap();
			mapValue.put("sub_other_info_inst_id", this.baseDaoSupport.getSequences("seq_sub_other_info"));
			mapValue.put("order_id", order_id);
			mapValue.put("user_group", user_group);
			mapValue.put("lan_school", lan_school);
			mapValue.put("complex_grid_id", complex_grid_id);
			mapValue.put("cust_building_id", cust_building_id);
			mapValue.put("grp_grid_id", grp_grid_id);
			mapValue.put("grpuser_ope_model", grpuser_ope_model);
			mapValue.put("group_code", group_code);
			mapValue.put("cust_id", cust_id);
			mapValue.put("certify_flag", certify_flag);
			mapValue.put("certify_cert_type", certify_cert_type);
			mapValue.put("certify_cert_num", certify_cert_num);
			mapValue.put("certify_cust_name", certify_cust_name);
			mapValue.put("certify_cert_addr", certify_cert_addr);

			try {
				BeanUtils.copyProperties(orderSubOtherInfoBusiRequest, mapValue);
				orderSubOtherInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
				orderSubOtherInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				CommonData.getData().getOrderTreeBusiRequest()
						.setOrderSubOtherInfoBusiRequest(orderSubOtherInfoBusiRequest);
				// orderSubOtherInfoBusiRequest.store();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
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
}

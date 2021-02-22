package com.ztesoft.newstd.handler;

import java.util.HashMap;
import java.util.List;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderSubProdInfoBusiRequest;

public class OrderSubProdInfoNewHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		List<OrderSubProdInfoBusiRequest> orderSubProdInfoBusiRequests = (List<OrderSubProdInfoBusiRequest>) params
				.getBusiRequest();

		String subprodinfo = String.valueOf(orderAttrValues.get(AttrConsts.SUBPROD_INFO));
		if (!StringUtils.isEmpty(subprodinfo) && !"null".equals(subprodinfo)
				&& !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(subprodinfo)) {
			JSONArray jsonArray = JSONArray.fromObject(subprodinfo);
			OrderSubProdInfoBusiRequest orderSubProdInfoBusiRequest = new OrderSubProdInfoBusiRequest();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				String sub_prod_name = jsonObject.get("sub_prod_name") == null ? ""
						: jsonObject.getString("sub_prod_name");
				String sub_prod_code = jsonObject.get("sub_prod_code") == null ? ""
						: jsonObject.getString("sub_prod_code");
				Map mapValue = new HashMap();
				mapValue.put("sub_prod_info_id", this.baseDaoSupport.getSequences("seq_sub_prod_info"));
				mapValue.put("order_id", order_id);
				mapValue.put("sub_prod_name", sub_prod_name);
				mapValue.put("sub_prod_code", sub_prod_code);

				try {
					BeanUtils.copyProperties(orderSubProdInfoBusiRequest, mapValue);
					orderSubProdInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					orderSubProdInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderSubProdInfoBusiRequests.add(orderSubProdInfoBusiRequest);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			CommonData.getData().getOrderTreeBusiRequest().setOrderSubProdInfoBusiRequest(orderSubProdInfoBusiRequests);
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

package com.ztesoft.newstd.handler;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.newstd.common.CommonData;

import consts.ConstsCore;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;

public class OrderRealNameInfoHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值

		// 获取后激活订单类型
		String laterActFlag = EcsOrderConsts.NO_DEFAULT_VALUE;
		if (orderAttrValues.containsKey(AttrConsts.LATER_ACT_FLAG)) {
			laterActFlag = orderAttrValues.get(AttrConsts.LATER_ACT_FLAG).toString();
		}

		OrderRealNameInfoBusiRequest orderRealNameInfoBusiRequest = new OrderRealNameInfoBusiRequest();
		Map<String, String> fieldsOrder = new HashMap<String, String>();
		fieldsOrder.put("order_id", order_id);
		fieldsOrder.put("later_active_flag", laterActFlag);
		fieldsOrder.put("source_from", ManagerUtils.getSourceFrom());
		fieldsOrder.put("active_flag", "0");
		fieldsOrder.put("cancel_flag", "0");
		try {
			BeanUtils.copyProperties(orderRealNameInfoBusiRequest, fieldsOrder);
			orderRealNameInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			orderRealNameInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			// realNameRequest.store();
			CommonData.getData().getOrderTreeBusiRequest()
					.setOrderRealNameInfoBusiRequest(orderRealNameInfoBusiRequest);
		} catch (Exception e) {
			logger.info("订单order_id[" + order_id + "]OrderRealNameHandler处理失败," + e.getMessage());
			e.printStackTrace();
		}
		return null;
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

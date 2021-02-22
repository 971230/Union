package com.ztesoft.net.attr.hander;

import java.util.HashMap;
import java.util.Map;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;

/**
 * 订单实名制信息表入库
 * @author duan.shaochu
 *
 */
public class OrderRealNameHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		
		//获取后激活订单类型
		String laterActFlag = EcsOrderConsts.NO_DEFAULT_VALUE;
		if(orderAttrValues.containsKey(AttrConsts.LATER_ACT_FLAG)){
			laterActFlag = orderAttrValues.get(AttrConsts.LATER_ACT_FLAG).toString();
		}
		
		OrderRealNameInfoBusiRequest realNameRequest = new OrderRealNameInfoBusiRequest();
		Map<String,String> fieldsOrder = new HashMap<String, String>();
		fieldsOrder.put("order_id", order_id);
		fieldsOrder.put("later_active_flag", laterActFlag);
		fieldsOrder.put("source_from", ManagerUtils.getSourceFrom());
		fieldsOrder.put("active_flag", "0");
		fieldsOrder.put("cancel_flag", "0");
		try {
			BeanUtils.copyProperties(realNameRequest, fieldsOrder);
			realNameRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			realNameRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			realNameRequest.store();
		} catch (Exception e) {
			logger.info("订单order_id["+order_id+"]OrderRealNameHandler处理失败," + e.getMessage());
			e.printStackTrace();
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(null);
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

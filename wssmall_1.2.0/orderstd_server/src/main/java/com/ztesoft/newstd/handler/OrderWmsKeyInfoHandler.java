package com.ztesoft.newstd.handler;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.newstd.common.CommonData;

import consts.ConstsCore;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderWMSKeyInfoBusiRequest;

public class OrderWmsKeyInfoHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
        String order_id = params.getOrder_id();// 订单ID
        Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
        OrderWMSKeyInfoBusiRequest orWmsKeyInfoBusiRequest = new OrderWMSKeyInfoBusiRequest();
        Map<String, String> fieldsOrder = new HashMap<String, String>();
        fieldsOrder.put("order_id", order_id);
        fieldsOrder.put("source_from", ManagerUtils.getSourceFrom());
        try {
            BeanUtils.copyProperties(orWmsKeyInfoBusiRequest, fieldsOrder);
            orWmsKeyInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
            orWmsKeyInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
            CommonData.getData().getOrderTreeBusiRequest()
                    .setOrderWMSKeyInfoBusiRequest(orWmsKeyInfoBusiRequest);
        } catch (Exception e) {
            logger.info("订单order_id[" + order_id + "]OrderRealNameHandler处理失败," + e.getMessage());
            e.printStackTrace();
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

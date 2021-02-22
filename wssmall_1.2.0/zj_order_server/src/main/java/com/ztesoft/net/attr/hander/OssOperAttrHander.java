package com.ztesoft.net.attr.hander;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import commons.CommonTools;

import consts.ConstsCore;


/**
 * 公共属性转换类
 * @作者 MoChunrun
 * @创建日期 2014-9-23 
 * @版本 V 1.0
 */
public class OssOperAttrHander implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		
		AttrSyLoadResp resp = new AttrSyLoadResp();
		resp.setError_code(ConstsCore.ERROR_SUCC);
		String oss_oper = oo.getValue();
		//获取配送方式
		String sending_type = oo.getOuter().getSending_type();
		String tempValue=CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.SHIPPING_TYPE,sending_type);
		if(!StringUtils.isEmpty(tempValue))
			sending_type = tempValue;
		
		String ess_code = oo.getValue();
		if(EcsOrderConsts.SHIPPING_TYPE_XJ.equals(sending_type) && StringUtils.isEmpty(ess_code)){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			CommonTools.addBusiError(ConstsCore.ERROR_FAIL, "ESS工号不能为空!");
		}
		return resp;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(params.getValue());
//		String order_id = params.getOrder_id();// 订单ID
//		Map orderAttrValues = params.getOrderAttrValues();
//		String sending_type = (String) orderAttrValues.get(AttrConsts.SENDING_TYPE);
//		if(!StringUtils.isEmpty(params.getValue()) && "XJ".equals(sending_type) ){ //add by wui不为空，则设置订单锁定人
//			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
//			OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
//			orderExtBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
//			try {
//				orderExtBusiRequest.setLock_time(DateUtil.getTime2());
//			} catch (FrameException e) {
//				e.printStackTrace();
//			}
//			orderExtBusiRequest.setLock_user_id(params.getValue());
//			//		orderExtBusiRequest.setLock_user_name(ManagerUtils.getAdminUser().getUsername());
//			orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
//			orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
//			orderExtBusiRequest.store();
//		}
		
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		 
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		//设置锁定人、锁定时间、锁定状态
		AttrInstLoadResp resp = new AttrInstLoadResp();
		return resp;
	}
}

package com.ztesoft.net.attr.hander;

import java.util.Map;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.AttrTmResourceInfoBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AttrDef;

import consts.ConstsCore;

public class TerminalHandler extends BaseSupport implements IAttrHandler{
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		   return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		String goods_id = params.getGoods_id(); // 商品ID 只有子订单或货品单才有值
		String pro_goods_id = params.getPro_goods_id(); // 货品ID 只有货品单才有值
		String order_from = params.getOrder_from(); // 订单来源
		String value = params.getValue(); // 原始值
		AttrDef attrDef = params.getAttrDef(); // 属性定议配置信息
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		
		Object terinfo = orderAttrValues.get("terminalInfo");
		
		if(terinfo!=null){
			try {
				String terinfoStr = orderAttrValues.get("terminalInfo").toString();
				Class<?> clazz = Class.forName("zte.net.ecsord.params.busi.req.AttrTmResourceInfoBusiRequest");
				AttrTmResourceInfoBusiRequest oldvo =  (AttrTmResourceInfoBusiRequest) JsonUtil.fromJson(terinfoStr,clazz);
				if(oldvo!=null){
					oldvo.setTm_resource_id(this.daoSupport.getSequences("ES_ATTR_TERMINAL_EXT_SEQ", "1", 18));
					oldvo.setSource_from(ManagerUtils.getSourceFrom());
					oldvo.setOrder_id(order_id);
					oldvo.setDb_action(ConstsCore.DB_ACTION_INSERT);
					oldvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					oldvo.store();			
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			AttrTmResourceInfoBusiRequest vo = new AttrTmResourceInfoBusiRequest();
			vo.setTm_resource_id(this.daoSupport.getSequences("ES_ATTR_TERMINAL_EXT_SEQ", "1", 18));
			vo.setOrder_id(order_id);
			vo.setSource_from(ManagerUtils.getSourceFrom());
			vo.setOperation_status(EcsOrderConsts.OCCUPIEDFLAG_0);
			vo.setDb_action(ConstsCore.DB_ACTION_INSERT);
			vo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			vo.store();
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

package com.ztesoft.net.attr.hander;


import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.service.impl.EcsOrdManager;
import com.ztesoft.net.sqls.SF;
/**
 * 物流单号
 * @author Administrator
 *
 */
public class CheDeliLogiHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		String logi_no = params.getValue();
		/**
		 * 订单同步报文中配送方式为“自提”或“无需配送”时，默认自动生成“物流单号”
		 */
		String sending_type =  (String) orderAttrValues.get(AttrConsts.SENDING_TYPE);//配送方式
		String tempValue=CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.SHIPPING_TYPE,sending_type);
		if(!StringUtils.isEmpty(tempValue)){
			sending_type=tempValue;
		}
		if(EcsOrderConsts.SHIP_TYPE_ZT.equals(sending_type)||EcsOrderConsts.SHIP_TYPE_WX.equals(sending_type)){//自提、自有配送-联通
			logi_no = EcsOrdManager.getUniqueRandomNum();//单号生成逻辑
		}
		/**
		 * 订单同步报文中配送方式为“自提”或“无需配送”时，默认自动生成“物流单号”
		 */
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(logi_no);
		return resp;
	}


	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		AttrInstLoadResp resp = null;		
		OrderTreeBusiRequest tree = req.getOrderTree();
		String trace_id = tree.getOrderExtBusiRequest().getFlow_trace_id();
		if(EcsOrderConsts.DIC_ORDER_NODE_F.equals(trace_id)||EcsOrderConsts.DIC_ORDER_NODE_H.equals(trace_id)||EcsOrderConsts.DIC_ORDER_NODE_J.equals(trace_id)){
			OrderDeliveryBusiRequest delivery = CommonDataFactory.getInstance().getDeliveryBusiRequest(req.getOrder_id(), EcsOrderConsts.LOGIS_NORMAL);
			String logi_no = StringUtils.isBlank(req.getNew_value())?delivery.getLogi_no():req.getNew_value();
			if(StringUtils.isBlank(logi_no)) {
				resp = new AttrInstLoadResp();
				resp.setField_desc("物流单号");
				resp.setField_value(logi_no);
				resp.setCheck_message("物流单号不能为空。");	
				return resp;
			}
			String order_id = req.getOrder_id();
			String sql = SF.ecsordSql("ORDER_COUNT_BY_LOGI_NO");
			ArrayList para = new ArrayList();
			para.add(logi_no);
			para.add(order_id);
			para.add(logi_no);
			para.add(order_id);
			int count = this.baseDaoSupport.queryForInt(sql, para.toArray());
			if(count>0) {
				if(StringUtils.isNotEmpty(logi_no)) {
					resp = new AttrInstLoadResp();
					resp.setField_desc("物流单号");
					resp.setField_value(logi_no);
					resp.setCheck_message("物流单号"+logi_no+"已被使用过。");				
				}
			}
		}
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		AttrInstLoadResp resp = null;		
		OrderTreeBusiRequest tree = req.getOrderTree();
		String trace_id = tree.getOrderExtBusiRequest().getFlow_trace_id();
		if(EcsOrderConsts.DIC_ORDER_NODE_F.equals(trace_id)||EcsOrderConsts.DIC_ORDER_NODE_H.equals(trace_id)||EcsOrderConsts.DIC_ORDER_NODE_J.equals(trace_id)){
			String logi_no = req.getNew_value();
			if(StringUtils.isBlank(logi_no)) {
				resp = new AttrInstLoadResp();
				resp.setField_desc("物流单号");
				resp.setField_value(logi_no);
				resp.setCheck_message("物流单号不能为空。");	
				return resp;
			}
			String order_id = req.getOrder_id();
			String sql = SF.ecsordSql("ORDER_COUNT_BY_LOGI_NO");
			ArrayList para = new ArrayList();
			para.add(logi_no);
			para.add(order_id);
			para.add(logi_no);
			para.add(order_id);
			int count = this.baseDaoSupport.queryForInt(sql, para.toArray());
			if(count>0) {
				if(StringUtils.isNotEmpty(logi_no)) {
					resp = new AttrInstLoadResp();
					resp.setField_desc("物流单号");
					resp.setField_value(logi_no);
					resp.setCheck_message("物流单号"+logi_no+"已被使用过。");				
				}
			}
		}
		return resp;
	}

}

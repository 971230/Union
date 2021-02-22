package com.ztesoft.net.attr.hander;

import java.util.Map;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiBusiException; 
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.service.IEcsOrdManager;

import consts.ConstsCore;
/**
 * 物流公司名称
 * @author Administrator
 *
 */
public class ShippingCompanyHandler extends BaseSupport implements IAttrHandler {
	private IEcsOrdManager  ecsOrdManager;
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		String shipping_company = params.getValue();
		String order_id = params.getOrder_id();// 订单ID
		/**
		 * 订单同步报文中配送方式为“自提”时，默认物流公司名称为“自提”；订单同步报文中配送方式为“无需配送”时，默认物流公司名称为“自有配送-联通”；
		 */
		String sending_type =  (String) orderAttrValues.get(AttrConsts.SENDING_TYPE);//配送方式
		String tempValue=CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.SHIPPING_TYPE,sending_type);
		if(!StringUtils.isEmpty(tempValue)){
			sending_type=tempValue;
		}
		if(EcsOrderConsts.SHIP_TYPE_ZT.equals(sending_type)){//自提
			shipping_company = EcsOrderConsts.LOGI_COMPANY_ZT0002;
		}else if(EcsOrderConsts.SHIP_TYPE_WX.equals(sending_type)){//自有配送-联通
			shipping_company = EcsOrderConsts.LOGI_COMPANY_ZY0002;
		}
		/**
		 * 订单同步报文中配送方式为“自提”时，默认物流公司名称为“自提”；订单同步报文中配送方式为“无需配送”时，默认物流公司名称为“自有配送-联通”；
		 */
		/*
		 * TODO:
		 * 原逻辑 杭州金华发EMS，其他地市发顺丰
		 * 修改：默认全为EMS EcsOrderConsts.LOGI_COMPANY_EMS0001，然后可以根据地市来配置 EcsOrderConsts.LOGI_COMPANY_SFFYZQYF
		 */
		String cityCode = (String) orderAttrValues.get(AttrConsts.CITY_CODE);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		shipping_company = cacheUtil.getConfigInfo("EMS_OR_SF_BY_CITYCODE_"+cityCode);
//		if(StringUtil.isEmpty(shipping_company)){
//			String city_code = (String) orderAttrValues.get(AttrConsts.CITY_CODE);
//			if("330100".equals(city_code)||"330700".equals(city_code)){
//				shipping_company = EcsOrderConsts.LOGI_COMPANY_EMS0001;
//			}
//			else{
//				shipping_company = EcsOrderConsts.LOGI_COMPANY_SFFYZQYF;
//			}
//			//shipping_company = EcsOrderConsts.LOGI_COMPANY_SFFYZQYF;
//		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(shipping_company);
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		OrderTreeBusiRequest  orderTree = CommonDataFactory.getInstance().getOrderTree(req.getOrder_id());
		
		String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		String ship_quick = orderTree.getOrderExtBusiRequest().getShipping_quick();
		if(EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)&&EcsOrderConsts.SHIPPING_QUICK_01.equals(ship_quick)){
			String shipping_company =req.getNew_value();
			if(!(EcsOrderConsts.LOGI_COMPANY_SF0001.equals(shipping_company)||EcsOrderConsts.LOGI_COMPANY_SF0002.equals(shipping_company)||EcsOrderConsts.LOGI_COMPANY_SF0003.equals(shipping_company)||EcsOrderConsts.LOGI_COMPANY_ND0001.equals(shipping_company)||EcsOrderConsts.LOGI_COMPANY_ND0002.equals(shipping_company))){
				AttrInstLoadResp resp = new AttrInstLoadResp();
				resp.setCheck_message("闪电送状态的订单物流公司只能是顺丰或者南都");
				resp.setField_name(AttrConsts.SHIPPING_COMPANY);
				return resp;
			}
		}
		String order_model = orderTree.getOrderExtBusiRequest().getOrder_model();
		if(EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)&&EcsOrderConsts.ORDER_MODEL_01.equals(order_model)){
			String shipping_company =req.getNew_value();
			if(StringUtil.isEmpty(shipping_company)||ConstsCore.NULL_VAL.equals(shipping_company)){
				AttrInstLoadResp resp = new AttrInstLoadResp();
				resp.setCheck_message("自动化生产模式物流公司不能为空");
				resp.setField_name(AttrConsts.SHIPPING_COMPANY);
				return resp;
			}
		}
		return null;

	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
        OrderTreeBusiRequest  orderTree = CommonDataFactory.getInstance().getOrderTree(req.getOrder_id());
		
		String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		String ship_quick = orderTree.getOrderExtBusiRequest().getShipping_quick();
		
		if(EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)&&EcsOrderConsts.SHIPPING_QUICK_01.equals(ship_quick)){
			String shipping_company = req.getNew_value();
			if(!(EcsOrderConsts.LOGI_COMPANY_SF0001.equals(shipping_company)||EcsOrderConsts.LOGI_COMPANY_SF0002.equals(shipping_company)||EcsOrderConsts.LOGI_COMPANY_SF0003.equals(shipping_company)||EcsOrderConsts.LOGI_COMPANY_ND0001.equals(shipping_company)||EcsOrderConsts.LOGI_COMPANY_ND0002.equals(shipping_company))){
				AttrInstLoadResp resp = new AttrInstLoadResp();
				resp.setCheck_message("闪电送状态的订单物流公司只能是顺丰或者南都");
				resp.setField_name(AttrConsts.SHIPPING_COMPANY);
				return resp;
			}
		}
		String order_model = orderTree.getOrderExtBusiRequest().getOrder_model();
		if(EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)&&EcsOrderConsts.ORDER_MODEL_01.equals(order_model)){
			String shipping_company =req.getNew_value();
			if(StringUtil.isEmpty(shipping_company)||ConstsCore.NULL_VAL.equals(shipping_company)){
				AttrInstLoadResp resp = new AttrInstLoadResp();
				resp.setCheck_message("自动化生产模式物流公司不能为空");
				resp.setField_name(AttrConsts.SHIPPING_COMPANY);
				return resp;
			}
		}
		return null;
	}
	public IEcsOrdManager getEcsOrdManager() {
		return ecsOrdManager;
	}
	public void setEcsOrdManager(IEcsOrdManager ecsOrdManager) {
		this.ecsOrdManager = ecsOrdManager;
	}

}

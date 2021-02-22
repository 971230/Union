package com.ztesoft.net.attr.hander;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.drools.core.util.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 公共属性转换类
 * @作者 MoChunrun
 * @创建日期 2014-9-23 
 * @版本 V 1.0
 */
public class ActiveNoHandler implements IAttrHandler {

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(params.getValue());
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null;
		String order_id = req.getOrder_id();
		
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
		if(EcsOrderConsts.DIC_ORDER_NODE_Y.equals(flow_trace_id)){//开户、业务办理环节需要校验开户开户流水号 EcsOrderConsts.DIC_ORDER_NODE_D.equals(flow_trace_id) || 
			String active_no = req.getNew_value();
			//开户及业务办理环节后向激活订单必填开户流水
			String is_later_active = EcsOrderConsts.NO_DEFAULT_VALUE;
			OrderRealNameInfoBusiRequest realNameBus = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderRealNameInfoBusiRequest();
			if(null != realNameBus){
				is_later_active = realNameBus.getLater_active_flag();
			}
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_later_active) && StringUtils.isEmpty(active_no)){
				resp = new AttrInstLoadResp();
				resp.setField_desc("总部开户访问流水");
				resp.setField_name(AttrConsts.ACTIVE_NO);
				resp.setCheck_message("请输入总部开户访问流水!");
				return resp;
			}
			boolean is_num = true;//默认符合数字
			Pattern pattern = Pattern.compile("[0-9]*");
			if(null!=active_no){//避免空指针异常
				Matcher matcher = pattern.matcher(active_no);
				is_num = matcher.matches();
			}
			if(!is_num){//不是数字
				resp = new AttrInstLoadResp();
				resp.setField_desc("总部开户访问流水");
				resp.setField_name(AttrConsts.ACTIVE_NO);
				resp.setCheck_message("请输入正确的流水号，总部开户访问流水只能为数字");

			}
		}
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null;
		String order_id = req.getOrder_id();
		
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
		//if(EcsOrderConsts.DIC_ORDER_NODE_D.equals(flow_trace_id) || EcsOrderConsts.DIC_ORDER_NODE_Y.equals(flow_trace_id)){//开户、业务办理环节需要校验开户开户流水号
		if(EcsOrderConsts.DIC_ORDER_NODE_Y.equals(flow_trace_id)){
			String active_no = req.getNew_value();
			//开户及业务办理环节后向激活订单必填开户流水
			String is_later_active = EcsOrderConsts.NO_DEFAULT_VALUE;
			OrderRealNameInfoBusiRequest realNameBus = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderRealNameInfoBusiRequest();
			if(null != realNameBus){
				is_later_active = realNameBus.getLater_active_flag();
			}
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_later_active) && StringUtils.isEmpty(active_no)){
				resp = new AttrInstLoadResp();
				resp.setField_desc("总部开户访问流水");
				resp.setField_name(AttrConsts.ACTIVE_NO);
				resp.setCheck_message("请输入总部开户访问流水");
				return resp;
			}
			boolean is_num = true;//默认符合数字
			Pattern pattern = Pattern.compile("[0-9]*");
			if(null!=active_no){//避免空指针异常
				Matcher matcher = pattern.matcher(active_no);
				is_num = matcher.matches();
			}
			if(!is_num){//不是数字
				resp = new AttrInstLoadResp();
				resp.setField_desc("总部开户访问流水");
				resp.setField_name(AttrConsts.ACTIVE_NO);
				resp.setCheck_message("请输入正确的流水号，总部开户访问流水只能为数字");

			}
		}
		return resp;
	}

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

}

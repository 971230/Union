package com.ztesoft.net.attr.hander;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 收货邮编、邮政编码
 */
public class ShipZipHandler implements IAttrHandler {

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
		String sending_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
		if(EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)&&!EcsOrderConsts.SENDING_TYPE_XJ.equals(sending_type)){//预处理环节非现交订单校验邮政编码6位数字
			String ship_zip = req.getNew_value();
			boolean is_right = true;//默认符合6位数字
			Pattern pattern = Pattern.compile("1{0}|[0-9]{6}");
			if(null!=ship_zip){//避免空指针异常
				Matcher matcher = pattern.matcher(ship_zip);
				is_right = matcher.matches();
			}
			if(!is_right){//不是6位数字
				resp = new AttrInstLoadResp();
				resp.setField_desc("收货邮编、邮政编码");
				resp.setField_name(AttrConsts.SHIP_ZIP);
				resp.setCheck_message("邮政编码须为6位数字。");

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
		String sending_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
		if(EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)&&!EcsOrderConsts.SENDING_TYPE_XJ.equals(sending_type)){//预处理环节非现交订单校验邮政编码6位数字
			String ship_zip = req.getNew_value();
			boolean is_right = true;//默认符合6位数字
			Pattern pattern = Pattern.compile("1{0}|[0-9]{6}");
			if(null!=ship_zip){//避免空指针异常
				Matcher matcher = pattern.matcher(ship_zip);
				is_right = matcher.matches();
			}
			if(!is_right){//不是6位数字
				resp = new AttrInstLoadResp();
				resp.setField_desc("收货邮编、邮政编码");
				resp.setField_name(AttrConsts.SHIP_ZIP);
				resp.setCheck_message("邮政编码须为6位数字。");

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

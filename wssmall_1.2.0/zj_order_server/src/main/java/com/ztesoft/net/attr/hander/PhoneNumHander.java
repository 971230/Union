package com.ztesoft.net.attr.hander;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

public class PhoneNumHander implements IAttrHandler{
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(params.getValue());
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null; 
		String order_id = req.getOrder_id() ;
		
		String  goods_type  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		String  phone_num = req.getNew_value();//CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
		final String synOrdZb = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYN_ORD_ZB);
		final String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
		final String isOld = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		final String isAop = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_AOP);
		resp = new AttrInstLoadResp();
		resp.setDisabled("enabled");//可修改
		if(StringUtils.equals(isAop,EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP)||StringUtils.equals(isAop,EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS)){
			if(org.apache.commons.lang3.StringUtils.equals(isOld, EcsOrderConsts.IS_OLD_1)){
				resp.setField_name(AttrConsts.PHONE_NUM);
				resp.setDisabled("disabled");
			}
		}else{
			if(!EcsOrderConsts.SYN_ORD_ZB_2.equals(synOrdZb)&&!SpecConsts.TYPE_ID_20001.equals(goods_type)){
				resp.setField_name(AttrConsts.PHONE_NUM);
				resp.setDisabled("disabled");
			}
			//总部的非上网卡的号码不可以编辑
			if(EcsOrderConsts.ORDER_FROM_10003.equals(order_from)&&!SpecConsts.TYPE_ID_20001.equals(goods_type)){
				resp.setField_name(AttrConsts.PHONE_NUM);
				resp.setDisabled("disabled");
			}
			//非总部的非上网卡、非号卡、非合约机不可编辑
			if(!EcsOrderConsts.ORDER_FROM_10003.equals(order_from)&&!SpecConsts.TYPE_ID_20000.equals(goods_type)
					&&!SpecConsts.TYPE_ID_20001.equals(goods_type)&&!SpecConsts.TYPE_ID_20002.equals(goods_type)){
				resp.setField_name(AttrConsts.PHONE_NUM);
				resp.setDisabled("disabled");
			}
			
			//淘宝单且未送总部或送总部失败则可以修改
			boolean isTbOrder = CommonDataFactory.getInstance().isTbOrder(order_from);//是否淘宝订单,默认否
			if(isTbOrder/*EcsOrderConsts.ORDER_FROM_10001.equals(order_from)*/&&!EcsOrderConsts.SYN_ORD_ZB_1.equals(synOrdZb)){
				resp.setDisabled("enabled");
			}
		}
		//宽带、裸机和配件没有用户号码、上网卡校验放在开启业务办理环节 
		if(!EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type)
				&&!EcsOrderConsts.GOODS_TYPE_PHONE.equals(goods_type)
				&&!EcsOrderConsts.GOODS_TYPE_ADJUNCT.equals(goods_type)
				&&!EcsOrderConsts.GOODS_TYPE_KD.equals(goods_type)
				&&StringUtils.isEmpty(phone_num)){
			if(null == resp) resp = new AttrInstLoadResp();
			resp.setField_desc("用户号码");
			resp.setField_value(phone_num);
			resp.setCheck_message(resp.getField_desc()+"不能为空。");
		}
		//开户、业务办理环节，上网卡，号码（可修改、并必填）20150525
		String flow_trace_id = req.getOrderTree().getOrderExtBusiRequest().getFlow_trace_id();
		if((StringUtils.equals(flow_trace_id, EcsOrderConsts.DIC_ORDER_NODE_D)||StringUtils.equals(flow_trace_id, EcsOrderConsts.DIC_ORDER_NODE_Y))&&//开户、业务办理环节
				StringUtils.equals(goods_type, EcsOrderConsts.GOODS_TYPE_WIFI_CARD)){//上网卡
			resp.setDisabled("enabled");//可修改
			if(StringUtils.isEmpty(phone_num)){//必填
				resp.setField_desc("用户号码");
				resp.setField_value(phone_num);
				resp.setCheck_message(resp.getField_desc()+"不能为空。");
			}
		}
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null; 
		String order_id = req.getOrder_id() ;
		
		String  goods_type  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		String  phone_num = req.getNew_value();//CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
		//宽带、裸机和配件没有用户号码、上网卡校验放在开启业务办理环节
		if(!EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type)&&!EcsOrderConsts.GOODS_TYPE_PHONE.equals(goods_type)&&!EcsOrderConsts.GOODS_TYPE_ADJUNCT.equals(goods_type)&&!EcsOrderConsts.GOODS_TYPE_KD.equals(goods_type)&&StringUtils.isEmpty(phone_num)){
			resp = new AttrInstLoadResp();
			resp.setField_desc("用户号码");
			resp.setField_value(phone_num);
			resp.setCheck_message(resp.getField_desc()+"不能为空。");
		}
		//开户、业务办理环节，上网卡，号码（可修改、并必填）20150525
		String flow_trace_id = req.getOrderTree().getOrderExtBusiRequest().getFlow_trace_id();
		if((StringUtils.equals(flow_trace_id, EcsOrderConsts.DIC_ORDER_NODE_D)||StringUtils.equals(flow_trace_id, EcsOrderConsts.DIC_ORDER_NODE_Y))&&//开户、业务办理环节
				StringUtils.equals(goods_type, EcsOrderConsts.GOODS_TYPE_WIFI_CARD)){//上网卡
			resp.setDisabled("enabled");//可修改
			if(StringUtils.isEmpty(phone_num)){//必填
				resp.setField_desc("用户号码");
				resp.setField_value(phone_num);
				resp.setCheck_message(resp.getField_desc()+"不能为空。");
			}
		}
		return resp;
	}

}

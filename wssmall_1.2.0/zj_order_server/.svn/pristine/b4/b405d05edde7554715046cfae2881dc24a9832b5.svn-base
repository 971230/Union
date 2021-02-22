package com.ztesoft.net.attr.hander;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class CertAddressHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String value = params.getValue(); // 原始值
		String cert_address = "";
		
		if (!StringUtils.isEmpty(value) && !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(value)) {
			cert_address = value;
			int count = getChnieseCount(cert_address);
			logger.info("order_id:"+order_id+",身份证地址-1："+cert_address+" ,汉字长度:"+count);
			if (count < 8) {
				cert_address = cert_address+"身份证地址不能少于8个汉字";
			}
		} else {
			cert_address = value;
			logger.info("order_id:"+order_id+",身份证地址-2："+cert_address);
		}

		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(cert_address);
		return resp;
		

	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null; 
		String order_id = req.getOrder_id() ;

		String certi_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);//证件类型
		String is_account  = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getIs_account();//是否需要开户
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
		String goods_type =  CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);//商品类型
		boolean flag = EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)&&SpecConsts.TYPE_ID_20001.equals(goods_type);
		if(flag||(EcsOrderConsts.CERTI_TYPE_SFZ15.equals(certi_type) || 
				EcsOrderConsts.CERTI_TYPE_SFZ18.equals(certi_type))//15位身份证、18位身份证才要校验身份证地址20150327
				&&EcsOrderConsts.IS_NEED_ACCOUNT.equals(is_account)){//需要开户才要校验身份证地址20150327
			String  cert_address = req.getNew_value();
			if(StringUtils.isEmpty(cert_address)){
				resp = new AttrInstLoadResp();
				resp.setField_desc("证件地址");
				resp.setField_name(AttrConsts.CERT_ADDRESS);
				resp.setCheck_message("证件地址不能为空。");
			}else{
				int count = getChnieseCount(cert_address);
				if(count<8){
					resp = new AttrInstLoadResp();
					resp.setField_desc("证件地址");
					resp.setField_name(AttrConsts.CERT_ADDRESS);
					resp.setCheck_message("开户证件地址需要确保至少8个汉字。");
				}
			}
		}
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		AttrInstLoadResp resp = null; 
		String order_id = req.getOrder_id() ;

		String certi_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);//证件类型
		String is_account  = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getIs_account();//是否需要开户
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
		String goods_type =  CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);//商品类型
		boolean flag = EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)&&SpecConsts.TYPE_ID_20001.equals(goods_type);
		if(flag||(EcsOrderConsts.CERTI_TYPE_SFZ15.equals(certi_type) || 
				EcsOrderConsts.CERTI_TYPE_SFZ18.equals(certi_type))//15位身份证、18位身份证才要校验身份证地址20150327
				&&EcsOrderConsts.IS_NEED_ACCOUNT.equals(is_account)){//需要开户才要校验身份证地址20150327
			String  cert_address = req.getNew_value();
			if(StringUtils.isEmpty(cert_address)){
				resp = new AttrInstLoadResp();
				resp.setField_desc("证件地址");
				resp.setField_name(AttrConsts.CERT_ADDRESS);
				resp.setCheck_message("证件地址不能为空。");
			}else{
				int count = getChnieseCount(cert_address);
				if(count<8){
					resp = new AttrInstLoadResp();
					resp.setField_desc("证件地址");
					resp.setField_name(AttrConsts.CERT_ADDRESS);
					resp.setCheck_message("开户证件地址需要确保至少8个汉字。");
				}
			}
		}
		return resp;
	}
	public int  getChnieseCount(String str) {  
		String temp = null;  
		Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");  
		Matcher m = p.matcher(str);  
		int count = 0;
		while (m.find())  
		{  
			temp = m.group(0);  
			count = count+temp.length();
		}  

		return count;
	}
}



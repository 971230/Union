package com.ztesoft.net.attr.hander;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;

import com.powerise.ibss.framework.Const;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class CommHandler  {

	public String getNameBykey (String oldValue,String key,String sType){
		String value="";
		String pkey= null;
		String pname= null;
		List<Map<String,String>> staticList=CommonDataFactory.getInstance().listDcPublicData(sType);
		value=oldValue;
		for (Map<String,String> map : staticList) {
			pkey= Const.getStrValue(map, "pkey");
			pname= Const.getStrValue(map, "pname");
			if(pkey.equals(key)){
				value=pname;
				break;
			}
		}
		return value;
	}
	
	public String getkeyByName (String oldValue,String name,String sType){
		String value="";
		String pkey= null;
		String pname= null;
		List<Map<String,String>> staticList=CommonDataFactory.getInstance().listDcPublicData(sType);
		value=oldValue;
		for (Map<String,String> map : staticList) {
			pkey= Const.getStrValue(map, "pkey");
			pname= Const.getStrValue(map, "pname");
			if(pname.equalsIgnoreCase(name)){
				value=pkey;
			}
		}
		if(StypeConsts.INVOICE_PRINT_TYPE.equals(sType) && EcsOrderConsts.INVOICE_PRINT_TYPE_ONCE_2.equals(name)){
			value = EcsOrderConsts.INVOICE_PRINT_TYPE_ONCE_1;
		}
		return value;
	}
	
	public String getkeyByCodea (String oldValue,String name,String sType){
		String value="";
		String pkey= null;
		String codea= null;
		List<Map<String,String>> staticList=CommonDataFactory.getInstance().listDcPublicData(sType);
		value=oldValue;
		for (Map<String,String> map : staticList) {
			pkey= Const.getStrValue(map, "pkey");
			codea= Const.getStrValue(map, "codea");
			if(codea.equalsIgnoreCase(name)){
				value=pkey;
			}
		}
		return value;
	}
}

package com.ztesoft.newstd.handler;

import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.newstd.common.CommonData;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;

public class CommHandler {
	
	IDcPublicInfoManager dcPublicInfoManager = null;
	DcPublicInfoCacheProxy proxy=null;
	private void inti(){
		if(dcPublicInfoManager==null){
			dcPublicInfoManager=SpringContextHolder.getBean("dcPublicInfoManager");
		}
		if(proxy==null){
			proxy = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		}
	}
	public String getNameBykey(String oldValue, String key, String sType) {
		String value = "";
		String pkey = null;
		String pname = null;
		List<Map<String, String>> staticList = CommonDataFactory.getInstance().listDcPublicData(sType);
		value = oldValue;
		for (Map<String, String> map : staticList) {
			pkey = Const.getStrValue(map, "pkey");
			pname = Const.getStrValue(map, "pname");
			if (pkey.equals(key)) {
				value = pname;
				break;
			}
		}
		return value;
	}

	public String getkeyByName(String oldValue, String name, String sType) {
		String value = "";
		String pkey = null;
		String pname = null;
		List<Map<String, String>> staticList = CommonDataFactory.getInstance().listDcPublicData(sType);
		value = oldValue;
		for (Map<String, String> map : staticList) {
			pkey = Const.getStrValue(map, "pkey");
			pname = Const.getStrValue(map, "pname");
			if (pname.equalsIgnoreCase(name)) {
				value = pkey;
			}
		}
		if (StypeConsts.INVOICE_PRINT_TYPE.equals(sType) && EcsOrderConsts.INVOICE_PRINT_TYPE_ONCE_2.equals(name)) {
			value = EcsOrderConsts.INVOICE_PRINT_TYPE_ONCE_1;
		}
		return value;
	}

	/**
	 * 通过stype_id查询es_dc_public_ext，并且获取对应字典值
	 * 
	 * @param oldValue
	 * @param name
	 * @param sType
	 * @return
	 */
	public String getValueBySTypeId(String oldValue, String name, String stype) {
		inti();
		String value = "";
		String pkey = null;
		String pname = null;
		List<Map<String, String>> staticList = proxy.getdcPublicExtListByStypeId(stype);
		// List<Map<String,String>>
		// staticList=CommonDataFactory.getInstance().listDcPublicData(sType);
		value = oldValue;
		for (Map<String, String> map : staticList) {
			pkey = Const.getStrValue(map, "pkey");
			pname = Const.getStrValue(map, "pname");
			if (pname.equalsIgnoreCase(name)) {
				value = pkey;
			}
		}
		if (StypeConsts.INVOICE_PRINT_TYPE.equals(stype) && EcsOrderConsts.INVOICE_PRINT_TYPE_ONCE_2.equals(name)) {
			value = EcsOrderConsts.INVOICE_PRINT_TYPE_ONCE_1;
		}
		return value;
	}

	public String getkeyByCodea(String oldValue, String name, String sType) {
		String value = "";
		String pkey = null;
		String codea = null;
		List<Map<String, String>> staticList = CommonDataFactory.getInstance().listDcPublicData(sType);
		value = oldValue;
		for (Map<String, String> map : staticList) {
			pkey = Const.getStrValue(map, "pkey");
			codea = Const.getStrValue(map, "codea");
			if (codea.equalsIgnoreCase(name)) {
				value = pkey;
			}
		}
		return value;
	}

	public String getSourceFrom() {
		String sourceFrom = CommonData.getData().getSourceFrom();
		if (StringUtil.isEmpty(sourceFrom)) {
			sourceFrom = ManagerUtils.getSourceFrom();
			CommonData.getData().setSourceFrom(sourceFrom);
		}
		return sourceFrom;
	}

}

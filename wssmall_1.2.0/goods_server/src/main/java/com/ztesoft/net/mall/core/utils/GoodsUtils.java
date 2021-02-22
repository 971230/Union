package com.ztesoft.net.mall.core.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.mall.core.model.GoodsParam;
import com.ztesoft.net.mall.core.model.support.ParamGroup;
import com.ztesoft.net.mall.core.model.support.SpecJson;
import com.ztesoft.net.mall.plugin.standard.type.GoodsTypeUtil;

public abstract class GoodsUtils {
	
	
	public static List getSpecList(String specString){
		if(StringUtils.isBlank(specString) || specString.equals("[]") ){
			return new ArrayList();
		}
        List<SpecJson> list= JSON.parseArray(specString,SpecJson.class);

		return list;
	}
	
	public static List<ParamGroup> getParamList(String paramString){
		if(StringUtils.isBlank(paramString) || paramString.equals("[]") ){
			return new ArrayList();
		}
        List<ParamGroup> list= null;
        try{
        	list = JSON.parseArray(paramString,ParamGroup.class);
        }catch(Exception e){
        	e.printStackTrace();
        	list = new ArrayList<ParamGroup>();
        }
		return list;
	}
	
	public static Map params2Map(String paramString){
		if(StringUtils.isBlank(paramString) || paramString.equals("[]") ){
			return new HashMap();
		}
		Map specMap = new HashMap();
		ParamGroup[] paramAr = GoodsTypeUtil.converFormString(paramString);
		for(int i=0;i<paramAr.length;i++){
			ParamGroup group = paramAr[i];
			List<GoodsParam> paramList = group.getParamList();
			if(paramList!=null && paramList.size()>0){
				for(GoodsParam param : paramList){
					String ename = param.getEname();
					String value = param.getValue();
					specMap.put(ename, value);
				}
			}
		}
		return specMap;
	}
}

package com.ztesoft.net.attr.hander;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import com.ztesoft.net.mall.core.model.GoodsParam;
import com.ztesoft.net.mall.core.model.support.ParamGroup;

public class JsonStrHandler {
	
	public static ParamGroup[] converFormString(String params) {
		if (params == null || "".equals(params) || "[]".equals(params)||"null".equals(params) || "<CLOB>".equals(params))
			return null;
		Map<String,Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("paramList", GoodsParam.class);
		JSONArray jsonArray = JSONArray.fromObject(params);

		Object obj = JSONArray.toArray(jsonArray, ParamGroup.class, classMap);

		if (obj == null)
			return null;

		return (ParamGroup[]) obj;
	}
}

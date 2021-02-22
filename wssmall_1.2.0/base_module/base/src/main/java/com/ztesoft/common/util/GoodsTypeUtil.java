package com.ztesoft.common.util;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品类型工具类
 * @author kingapex
 * 2010-1-10下午06:30:10
 */
public class GoodsTypeUtil {
	private GoodsTypeUtil(){};
	
	public static ParamGroup[] converFormString(String params) {
		if (params == null || "".equals(params) || "[]".equals(params)||"null".equals(params))
			return null;
		Map<String,Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("paramList", GoodsParam.class);
		JSONArray jsonArray = JSONArray.fromObject(params);

		Object obj = JSONArray.toArray(jsonArray, ParamGroup.class, classMap);

		if (obj == null)
			return null;

		return (ParamGroup[]) obj;
	}
	
	/**
	 * 将一个json字串转为list
	 * @param props
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Attribute> converAttrFormString(String props){
		if (props == null || props.equals(""))
			return new ArrayList<Attribute>();

		JSONArray jsonArray;
		try {
			jsonArray = JSONArray.fromObject(props);
		} catch (RuntimeException e) {
			return new ArrayList<Attribute>();
		}
		List<Attribute> list = (List<Attribute>) JSONArray.toCollection(jsonArray,
				Attribute.class);
		return list;
	}
	
}

package com.ztesoft.net.mall.plugin.standard.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.ztesoft.net.mall.core.model.Attribute;
import com.ztesoft.net.mall.core.model.GoodsParam;
import com.ztesoft.net.mall.core.model.support.ParamGroup;

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
		Object obj = null;
		try{
			JSONArray jsonArray = JSONArray.fromObject(params);

			obj = JSONArray.toArray(jsonArray, ParamGroup.class, classMap);

			if (obj == null)
				return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		ParamGroup[] g = (ParamGroup[]) obj;
		return (ParamGroup[]) obj;
		
	}
	
	/**
	 * 将一个json字串转为list
	 * @param props
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<ParamGroup> converGroupFromString(String props){
		if (props == null || props.equals(""))
			return new ArrayList<ParamGroup>();

		JSONArray jsonArray;
		try {
			jsonArray = JSONArray.fromObject(props);
		} catch (RuntimeException e) {
			return new ArrayList<ParamGroup>();
		}
		List<ParamGroup> list = (List<ParamGroup>) JSONArray.toCollection(jsonArray,
				ParamGroup.class);
		return list;
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

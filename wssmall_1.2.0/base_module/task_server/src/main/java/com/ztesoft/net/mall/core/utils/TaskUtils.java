package com.ztesoft.net.mall.core.utils;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.ztesoft.net.mall.core.model.ConditionAttr;
import com.ztesoft.net.mall.core.model.ElementsObjectAttr;

public class TaskUtils {

	public static List<ConditionAttr> converConditionAttrFormString(String props){
		if (props == null || props.equals(""))
			return new ArrayList();

		JSONArray jsonArray = JSONArray.fromObject(props);
		List<ConditionAttr> extAtts = new ArrayList<ConditionAttr>();
		List<ConditionAttr> lists = (List) JSONArray.toCollection(jsonArray,ConditionAttr.class);
		
		return lists;
	}
	
	public static List<ElementsObjectAttr> converElementsObjectAttrFormString(String props){
		if (props == null || props.equals(""))
			return new ArrayList();

		JSONArray jsonArray = JSONArray.fromObject(props);
		List<ElementsObjectAttr> extAtts = new ArrayList<ElementsObjectAttr>();
		List<ElementsObjectAttr> lists = (List) JSONArray.toCollection(jsonArray,ElementsObjectAttr.class);
		
		return lists;
	}
}

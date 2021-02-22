package com.ztesoft.net.mall.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.mall.core.model.ConditionAttr;
import com.ztesoft.net.mall.core.model.ElementsObjectAttr;


public class AlarmUtils {

	public static List<ConditionAttr> converConditionAttrFormString(String props){
		if (StringUtils.isBlank(props)){
            return new ArrayList();
        }
        List<ConditionAttr> lists = JSON.parseArray(props,ConditionAttr.class);
		return lists;
	}
	
	public static List<ElementsObjectAttr> converElementsObjectAttrFormString(String props){
		if (StringUtils.isBlank(props)){
            return new ArrayList();
        }
        List<ElementsObjectAttr> lists =JSON.parseArray(props,ElementsObjectAttr.class);
        return lists;
	}
	
	
}

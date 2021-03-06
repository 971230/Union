package com.ztesoft.net.mall.core.utils;

import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.mall.core.model.GoodsRelAttConvert;
import com.ztesoft.net.mall.core.model.support.AdjunctGroup;

@Deprecated
public abstract class GoodsManagerUtils {

	/**
	 * 将一个
	 * 
	 * @param adjString
	 * @return
	 */
	public static AdjunctGroup converAdjFormString(String adjString) {
		if (adjString == null) {
			return null;
		}
        AdjunctGroup adjunct = JSON.parseObject(adjString,AdjunctGroup.class);
		return adjunct;
	}

	public static List<GoodsRelAttConvert> converSetContractFormString(String props){
		if (props == null || props.equals(""))
			return new ArrayList();

		JSONArray jsonArray = JSONArray.fromObject(props);
		List<GoodsRelAttConvert> extAtts = new ArrayList<GoodsRelAttConvert>();
		List<GoodsRelAttConvert> lists = (List) JSONArray.toCollection(jsonArray,GoodsRelAttConvert.class);

		return lists;
	}
	
	/**
	 * 根据数组转换成字符串，中间用splitFlg隔开（末尾没有splitFlg）
	 * @param paramLst  要转换
	 * @param splitFlg  分隔符
	 * @return
	 */
	public static String convertStrFromLst(List<String> paramLst, String splitFlg) {
		
		String rtnStr = "";
		for (int i = 0; paramLst != null && i < paramLst.size(); i++) {
			
			if (i == 0) {
				rtnStr = paramLst.get(i);
			} else {
				rtnStr += splitFlg + paramLst.get(i);
			}
		}
		
		return rtnStr;
	}
}

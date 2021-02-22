package com.ztesoft.ibss.common.util;

import java.util.ArrayList;
import java.util.List;

public final class ListUtil {

	
	public static boolean isEmpty(List list){
		if(list==null)
			return true;
		return list.size()==0;		
	}
	
	/**
	 * 取A和B两个列表的交集List
	 * @param listA
	 * @param listB
	 */
	public static List mixedList(List listA,List listB){
		List mixedList = new ArrayList();
		if(!isEmpty(listA)&&!isEmpty(listB)){
			for(int i = 0;i<listA.size();i++){
				Object objA  = listA.get(i);
				if(listB.contains(objA)){
					mixedList.add(objA);
				}
			}
		}
		return mixedList;
	}
	
	/**
	 * 取在A中而不在B中的数据
	 * @param listA
	 * @param listB
	 */
	public static List notContainList(List listA,List listB){
		List NotContainList = new ArrayList();
		if(!isEmpty(listA)){
			if(isEmpty(listB))
				return listA;
			for(int i = 0;i<listA.size();i++){
				Object objA  = listA.get(i);
				if(!listB.contains(objA)){
					NotContainList.add(objA);
				}
			}
		}
		return NotContainList;
	}
	

	/**
	 * 取A和B两个列表的并集List
	 * @param listA
	 * @param listB
	 */
	public static List mergeList(List listA,List listB){
		List res = new ArrayList();
		//将 两个列表做合并
		if (listA == null && listB==null)
			return res;
		if(ListUtil.isEmpty(listA)){ 
			return listB;
		}else{
			if(ListUtil.isEmpty(listB)){
				return listA;
			}else{
				for(int i =0;i<listB.size();i++){
					Object obj = listB.get(i);
					if(listA.contains(obj))continue;
					listA.add(obj);
				}
				return listA;
			}  
		}
	}
	
	
	/**
	 * 将str根据seperator 拆分组织成 列表
	 * @param seperator
	 * @param str
	 * @return
	 */
	public static List strToList(String seperator,String str ){
		List resList = new ArrayList();
		if(StrTools.isEmpty(str)){
			return resList;
		}
		String[] strArray = str.split(seperator);
		if(strArray.length==0)
			return resList;
		for(int i = 0;i<resList.size();i++){
			String strCell = strArray[i];
			if(!resList.contains(strCell))
				resList.add(strCell); 
		}
		return resList;
	}
}

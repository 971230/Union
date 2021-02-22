package com.ztesoft.component.ui.taglib;

public class MyUtil {
	public static int NEED = 1 ;//是否需要引号
	public static int NO_NEED = 0 ;//不需要引号
	
	private MyUtil(){
		
	}

	public static void wrapFieldAttr(StringBuffer d , String attrName , String attrValue ){
			wrapFieldAttr( d ,  attrName ,  attrValue , NEED) ;
	}
	
	public static void wrapFieldAttr(StringBuffer d , String attrName , String attrValue , int type){
		if(type == NEED)
			d.append(attrName+":'"+ attrValue+"',") ;
		else
			d.append(attrName+":"+ attrValue+",") ;
	}
	
	
	public static void wrapFieldAttrWithCheck(StringBuffer d , String attrName , 
			String attrValue, String ndefaultValue ){
		wrapFieldAttrWithCheck( d ,  attrName ,  attrValue ,ndefaultValue , NEED) ;
	}
	
	public static void wrapFieldAttrWithCheck(StringBuffer d , String attrName , 
			String attrValue  , String ndefaultValue, int type){
		if(attrValue == null || "".equals(attrValue.trim()) || 
				ndefaultValue.equalsIgnoreCase(attrValue))
			return ;
		if(type == NEED)
			d.append(attrName+":'"+ attrValue+"',") ;
		else
			d.append(attrName+":"+ attrValue+",") ;
	}
	
}

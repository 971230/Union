package com.ztesoft.common.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;


/**
 * 
 * ServiceManager 结果进行类型转化 
 * @author easonwu 2010-01-10
 * 
 *
 */
public class DataTranslate {
	
//	private Map typeMap = null ;
	
	static class SingletonHolder{
		static DataTranslate dataTranslate = new  DataTranslate();
	}
	
	public DataTranslate getInstance(){
		return SingletonHolder.dataTranslate ;
	}
	
	public static final String TYPE_int ="int" ;
	public static final String TYPE_Integer ="Integer" ;
	

	public static final String TYPE_short ="short" ;
	public static final String TYPE_Short ="Short" ;
	

	public static final String TYPE_byte ="byte" ;
	public static final String TYPE_Byte ="Byte" ;
	
	public static final String TYPE_long ="long" ;
	public static final String TYPE_Long ="Long" ;
	
	public static final String TYPE_boolean ="boolean" ;
	public static final String TYPE_Boolean ="long" ;
	
	public static final String TYPE_Double ="Double" ;
	public static final String TYPE_double ="double" ;
	

	public static final String TYPE_Float ="Float" ;
	public static final String TYPE_float ="float" ;
	
	public static final String TYPE_String ="String" ;
	
	public static final String TYPE_LIST ="List" ;
	public static final String TYPE_Map ="Map" ;
	
	public static final String TYPE_PageModel="PageModel" ;
	
	public static final String TYPE_blak ="blak" ;
	
	
	
	private DataTranslate(){
//		typeMap = new HashMap() ;
	}
	
	public static int translate(Object obj){
		return ((Integer)obj).intValue() ;
	}
	
	public static int _int(Object obj){
		return ((Integer)obj).intValue() ;
	}
	
	public static Integer _Integer(Object obj){
		return ((Integer)obj) ;
	}
	
	public static short _short(Object obj){
		return ((Short)obj).shortValue() ;
	}
	
	public static Short _Short(Object obj){
		return ((Short)obj) ;
	}
	
	public static short _byte(Object obj){
		return ((Byte)obj).byteValue() ;
	}
	
	public static Byte _Byte(Object obj){
		return ((Byte)obj) ;
	}
	
	public static long _long(Object obj){
		return ((Long)obj).longValue() ;
	}
	
	public static Long _Long(Object obj){
		return ((Long)obj) ;
	}
	
	public static boolean _boolean(Object obj){
		return ((Boolean)obj).booleanValue() ;
	}
	
	public static Boolean _Boolean(Object obj){
		return ((Boolean)obj) ;
	}
	
	public static double _double(Object obj){
		return ((Double)obj).doubleValue() ;
	}
	
	public static Double _Double(Object obj){
		return ((Double)obj) ;
	}
	
	public static float _float(Object obj){
		return ((Float)obj).floatValue() ;
	}
	
	public static Float _Float(Object obj){
		return ((Float)obj) ;
	}
	

	public static String _String(Object obj){
		return obj != null ? (String)obj : "" ;
	}
	
	
	public static List _List(Object obj){
		return obj != null ? (List)obj : new ArrayList() ;
	}
	
	public static Map _Map(Object obj){
		return obj != null ? (Map)obj : new HashMap() ;
	}
	
	public static PageModel _PageModel(Object obj){
		return obj != null ? (PageModel)obj : new PageModel() ;
	}
	
	public static VO _VO(Object obj){
		return obj != null ? (VO)obj : null ;
	}
	

}

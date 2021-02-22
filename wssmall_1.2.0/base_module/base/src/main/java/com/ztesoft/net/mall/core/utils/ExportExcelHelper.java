package com.ztesoft.net.mall.core.utils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.log4j.Logger;



public class ExportExcelHelper{
	private static Logger logger = Logger.getLogger(ExportExcelHelper.class);
	
	/** 
	* 获取对象属性，返回一个字符串数组     
	*  
	* @param  o 对象 
	* @return String[] 字符串数组 
	*/  
	public String[] getFiledName(Object o)  
	{    
	try   
	{  
	Field[] fields = o.getClass().getDeclaredFields();  
	String[] fieldNames = new String[fields.length];    
	for (int i=0; i < fields.length; i++)  
	{    
	    fieldNames[i] = fields[i].getName();    
	}    
	return fieldNames;  
	} catch (SecurityException e)   
	{  
	e.printStackTrace();  
	logger.info(e.toString());  
	}  
	    return null;  
	}    
	  
	/** 
	* 使用反射根据属性名称获取属性值  
	*  
	* @param  fieldName 属性名称 
	* @param  o 操作对象 
	* @return Object 属性值 
	*/  
	  
	public Object getFieldValueByName(String fieldName, Object o)   
	{      
	   try   
	   {      
		   if (o instanceof Map) {
			   Map t = (Map)o; 
			   return t.get(fieldName);
		   } else {
		       String firstLetter = fieldName.substring(0, 1).toUpperCase();      
		       String getter = "get" + firstLetter + fieldName.substring(1);      
		       Method method = o.getClass().getMethod(getter, new Class[] {});      
		       Object value = method.invoke(o, new Object[] {});
		       return value;      
		   }
	   } catch (Exception e)   
	   {      
	       logger.info("属性不存在");      
	       return null;      
	   }      
	}    
	
}
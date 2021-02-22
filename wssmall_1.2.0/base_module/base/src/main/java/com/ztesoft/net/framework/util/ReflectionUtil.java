package com.ztesoft.net.framework.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.ztesoft.net.framework.database.NotDbField;

public class ReflectionUtil {
		
	public static Object invokeMethod(String className, String methodName,
			Object[] args) {

		try {

			Class serviceClass = Class.forName(className);
			Object service = serviceClass.newInstance();

			Class[] argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {
				argsClass[i] = args[i].getClass();
			}

			Method method = serviceClass.getMethod(methodName, argsClass);
			return method.invoke(service, args);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static Object newInstance(String className,Object... _args ){

		try {
			  Class[] argsClass = new Class[_args.length];                                  
			                                                                                 
			   for (int i = 0, j = _args.length; i < j; i++) {   
					   
				   if(_args[i]==null){
					   argsClass[i]=null;
				   }
				   else{
					    
					   argsClass[i] = _args[i].getClass();
				   }
			    }      
			   
			   
			 Class newoneClass  = Class.forName(className);
			 Constructor cons = newoneClass.getConstructor(argsClass);                    
             
			 Object obj= cons.newInstance(_args);
			 return obj;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
	
		 
		 return null;
	 
	}

	/**
	 * 将po对象中有属性和值转换成map
	 * 
	 * @param po
	 * @return
	 */
	public static Map po2Map(Object po) {
		Map poMap = new HashMap();
		Map map = new HashMap();
		try {
			if(!"java.util.HashMap".equals(po.getClass().getName())){
				map = BeanUtils.describe(po);
			}
			else{
				map = (Map) po;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Object[] keyArray = map.keySet().toArray();
		for (int i = 0; i < keyArray.length; i++) {
			String str = keyArray[i].toString();
			if (str != null && !str.equals("class")) {
				if (map.get(str) != null) {
					poMap.put(str, map.get(str));
				}
			}
		}

		Method[] ms =po.getClass().getMethods();
		
		Class parentClass = po.getClass().getSuperclass();
		for(Method m:ms){
			String name = m.getName();
			if(name.startsWith("get") || name.startsWith("is") ){
				//删除父类继承过来的NotDbField属性
				try{
					Method parent = parentClass.getMethod(name);
					if(parent.getAnnotation(NotDbField.class) != null){
						poMap.remove(getFieldName(name));
						continue;
					}
				}catch(Exception e){}
				
				if(m.getAnnotation(NotDbField.class)!=null){
					poMap.remove(getFieldName(name)); 
				}
			}
		}
		return poMap;
	}

	/**
	 * 将po对象中有属性和值转换成map
	 * 
	 * @param po
	 * @return
	 */
	public static Map poZteBusi2Map(Object po) {
		Map poMap = new HashMap();
		Map map = new HashMap();
		try {
			map = BeanUtils.describe(po);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Object[] keyArray = map.keySet().toArray();
		for (int i = 0; i < keyArray.length; i++) {
			String str = keyArray[i].toString();
			if (str != null && !str.equals("class")) {
				if (map.get(str) != null) {
					poMap.put(str, map.get(str));
				}
			}
		}

		Method[] ms =po.getClass().getMethods();
		
		Class parentClass = po.getClass().getSuperclass();
		for(Method m:ms){
			String name = m.getName();
			if(name.startsWith("get") || name.startsWith("is") ){
				//删除父类继承过来的NotDbField属性
				try{
					Method parent = parentClass.getMethod(name);
					if(parent.getAnnotation(NotDbField.class) != null){
						poMap.remove(getFieldName(name));
						
						continue;
					}
					
				}catch(Exception e){}
				
				if(m.getAnnotation(NotDbField.class)!=null){
					poMap.remove(getFieldName(name)); 
				}
			}
		}
		return poMap;
	}
	
	private static String getFieldName(String methodName){
		if(methodName.startsWith("is")){
			methodName = methodName.substring(2,methodName.length());
		}
		methodName = methodName.replaceAll("get", "");
		methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
		return methodName;
	}
	
	/**
	 * 将pojo对象中有属性和值转换成map,不会将属性值转化为字符串
	 * @param po
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getPoJoFieldMap(Object po) throws IllegalArgumentException, IllegalAccessException, 
		InvocationTargetException, NoSuchMethodException {
		//取pojo对象的属性
		Map poMap = new HashMap();
		Map map = new HashMap();
		
		try {
			if(!"java.util.HashMap".equals(po.getClass().getName())){
				map = PropertyUtils.describe(po);
			}
			else{
				map = (Map) po;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Object[] keyArray = map.keySet().toArray();
		
		for (int i = 0; i < keyArray.length; i++) {
			String str = keyArray[i].toString();
			if (str != null && !str.equals("class")) {
				if (map.get(str) != null) {
					poMap.put(str, map.get(str));
				}
			}
		}

		Method[] ms =po.getClass().getMethods();
		
		Class parentClass = po.getClass().getSuperclass();
		
		for(Method m:ms){
			
			String name = m.getName();
			
			if(name.startsWith("get") || name.startsWith("is") ){
				//删除父类继承过来的NotDbField属性
				try{
					Method parent = parentClass.getMethod(name);
					if(parent.getAnnotation(NotDbField.class) != null){
						poMap.remove(getFieldName(name));
						continue;
					}
				}catch(Exception e){}
				
				if(m.getAnnotation(NotDbField.class)!=null){
					poMap.remove(getFieldName(name)); 
				}
			}
		}
		
		return poMap;
	}
}

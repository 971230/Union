package com.ztesoft.inf.framework.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ClassUtils {

	private static String DC_SQL="";
	
	public static Object newInstance(String className) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clazz;
		try {
			clazz = loader.loadClass(className);
			Object obj = clazz.newInstance();
			return obj;
		} catch (Exception e) {
			throw new RuntimeException("根据类名创建实例失败," + e.getMessage(), e);
		}
	}

	public static Object invoke(String op_code,Map data)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		
		String clsName=""; 
		String methodName="";
		Class cls = Class.forName(clsName);
		Object obj = cls.newInstance();
		Method method = cls.getMethod(methodName, new Class[] { Map.class });
		Object ret = method.invoke(obj, new Map[] { data });
		return ret;
	}
}


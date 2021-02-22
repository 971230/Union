package com.powerise.ibss.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Reason
 * @version Created Nov 7, 2011
 * 类Util
 */
public class ClassUtil {


	public static Object newInstanceFromClassName(String sNamclassNamee)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class clazz = Class.forName(sNamclassNamee);
		return clazz.newInstance();
	}

	public static void setSimpleProperty(Object object, String propertyName,
			Object value) throws IllegalAccessException,
			InvocationTargetException {
		Method method = findMethod(object.getClass(), propertyName);
		invokeMethod(method, object, value);
	}

	private static Method findMethod(Class beanClass, String propertyName) {
		Method[] methods = beanClass.getMethods();
		String methodName = "set" + capitalizePropertyName(propertyName);
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method == null) {
				continue;
			}
			if (method.getName().equals(methodName))
				return method;
		}
		return null;
	}

	private static Object invokeMethod(Method method, Object bean, Object value)
			throws IllegalAccessException, InvocationTargetException {
		try {

			Object[] values = new Object[] { value };
			return method.invoke(bean, values);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Cannot invoke "
					+ method.getDeclaringClass().getName() + "."
					+ method.getName() + " - " + e.getMessage());
		}
	}

	/**
	 * <p>
	 * 对一个特定字符串的第一个字母格式化成大写。
	 * </p>
	 * 
	 * @param s需要格式化的字符串。
	 * @return 返回格式化后的字符串
	 */
	private static String capitalizePropertyName(String s) {
		if (s.length() == 0) {
			return s;
		}
		char chars[] = s.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);

		return new String(chars);
	}


}

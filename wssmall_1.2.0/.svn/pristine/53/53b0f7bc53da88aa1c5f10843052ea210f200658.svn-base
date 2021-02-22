package com.ztesoft.rule.core.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClazzUtil {

	public static final String PREFIX = "ClazzCache_";

	/**
	 * Class缓存
	 * @param name
	 * @return
	 */
	public static Class getClassByCache(String name) {
		Class c = (Class) LocalCache.get(PREFIX + name);
		if (c == null) {
			c = ClazzUtil.getClassByName(name);
			LocalCache.put(PREFIX + name, c);
		}

		return c;

	}

	public static Object newInstanceFromClassName(String sNamclassNamee) {
		Object inst = null;
		try {
			Class clazz = getClassByCache(sNamclassNamee);
			inst = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inst;
	}

	public static Class getClassByName(String sNamclassNamee) {
		Class clazz = null;
		try {
			clazz = Class.forName(sNamclassNamee);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
		return clazz;
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

			Object[] values = null;
			if (value != null) {
				values = new Object[] { value };
			}
			return method.invoke(bean, values);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Cannot invoke "
					+ method.getDeclaringClass().getName() + "."
					+ method.getName() + " - " + e.getMessage());
		}
	}

	public static Object invokeMethod(Object bean, String methodName,
			Object value) throws IllegalAccessException,
			InvocationTargetException {
		Method[] methods = bean.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method == null) {
				continue;
			}
			if (method.getName().equals(methodName)) {
				return invokeMethod(method, bean, value);
			}
		}
		return null;

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

	/**
	 * 
	 * 
	 * 根据接口，查找所有实现类
	 * @param c
	 * @return
	 * 
	 */
	public static List<Class> getAllClassByInterface(Class c) {

		// 给一个接口，返回这个接口的所有实现类   
		List<Class> returnClassList = new ArrayList<Class>();// 返回结果   
		// 如果不是一个接口，则不做处理   
		if (c.isInterface()) {
			String packageName = c.getPackage().getName();// 获得当前包名   
			try {
				List<Class> allClass = getClasses(packageName);// 获得当前包下以及包下的所有类   
				for (int i = 0; i < allClass.size(); i++) {
					if (c.isAssignableFrom(allClass.get(i))) {// 判断是不是一个接口   
						if (!c.equals(allClass.get(i))) {// 本身加不进去   
							returnClassList.add(allClass.get(i));

						}
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block   
				e.printStackTrace();
			} catch (IOException e) {
				// TODO: handle exception   
				e.printStackTrace();
			}
		}
		return returnClassList;

	}

	// 从一个包中查找出所有类,在jar包中不能查找   
	private static List<Class> getClasses(String packageName)
			throws IOException, ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}

	private static List<Class> findClasses(File directory, String packageName)
			throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + '.'
						+ file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName
						+ "."
						+ file.getName().substring(0,
								file.getName().length() - 6)));
			}
		}
		return classes;
	}

}

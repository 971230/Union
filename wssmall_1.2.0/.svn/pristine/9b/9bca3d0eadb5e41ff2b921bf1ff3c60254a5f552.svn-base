package com.ztesoft.common.util.convert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.util.ListUtil;
import com.ztesoft.common.util.MapUtil;
import com.ztesoft.common.util.StringUtils;

public final class Converts {
	private static Logger logger = Logger.getLogger(Converts.class);
	/**
	 * 对象值转成HashMap
	 * */
	public static Map<String, Object> toHashMap(Object fromObject)
			throws RuntimeException {

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		Class<?> clazz = fromObject.getClass();
		@SuppressWarnings("rawtypes")
		List<Class> clazzs = new ArrayList<Class>();
		try {
			/**
			 * 递归寻找父类列表
			 * */
			do {
				clazzs.add(clazz);
				clazz = clazz.getSuperclass();
			} while (!clazz.equals(Object.class));
			/** 设置字段数据 **/
			for (Class<?> iClazz : clazzs) {
				Field[] fields = iClazz.getDeclaredFields();
				for (Field field : fields) {
					Object fieldVal = null;
					field.setAccessible(true);
					fieldVal = field.get(fromObject);
					hashMap.put(field.getName(), fieldVal);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return hashMap;
	}

	/**
	 * 将MAP LIST转换成单行MAP，同时转换成对象 list 里面存放的是MAP数据 cls对象类型 keyName
	 * */
	public static Object toObject(List<Object> list, Class<?> cls,
			String keyName, String keyValueNmae) {
		Object obj = null;
		try {
			obj = cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = toMapByListMap(list, keyName, keyValueNmae);
		return toObject(map, cls);
	}

	/**
	 * 将LIST MAP转换成当行MAP对象，需要keyName,keyValueNmae,用值作为新MAP的key和value
	 * */
	public static Map toMapByListMap(List<Object> list, String keyName,
			String keyValueNmae) {

		Map<String, Object> map = new HashMap<String, Object>();

		for (Object object : list) {

			Map<String, Object> record = (Map) object;

			String newKeyName = String.valueOf(record.get(keyName));
			Object newKeyValue = record.get(keyValueNmae);
			if (!StringUtils.isEmpty(newKeyName))
				map.put(newKeyName.toLowerCase(), newKeyValue);

		}

		return map;

	}

	public static <T> T toObject(Map<?, Object> map, Class<T> cls)
			throws RuntimeException {
		Object obj = null;
		try {
			obj = cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 取出bean里的所有方法
		Method[] methods = cls.getMethods();
		for (int i = 0; i < methods.length; i++) {
			// 取方法名
			String method = methods[i].getName();
			// 取出方法的类型
			@SuppressWarnings("rawtypes")
			Class[] cc = methods[i].getParameterTypes();
			if (cc.length != 1)
				continue;

			// 如果方法名没有以set开头的则退出本次for
			if (method.indexOf("set") < 0)
				continue;
			// 类型
			String type = cc[0].getSimpleName();

			try {
				// 转成小写
				Object value = method.substring(3, 4).toLowerCase()
						+ method.substring(4);
				// 如果map里有该key
				if (StringUtils.containMapKey(map, value)
						&& StringUtils.getObjectFromMap(map, value) != null) {
					// 调用设值方法
					setValue(type, StringUtils.getObjectFromMap(map, value), i,
							methods, obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		if (obj instanceof IConvert) {
			((IConvert) obj).initObject(map);
		}

		return (T) obj;
	}

	public static void toObject(Map<?, Object> map, Object descObj)
			throws RuntimeException {

		// 取出bean里的所有方法
		Method[] methods = descObj.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			// 取方法名
			String method = methods[i].getName();
			// 取出方法的类型
			@SuppressWarnings("rawtypes")
			Class[] cc = methods[i].getParameterTypes();
			if (cc.length != 1)
				continue;

			// 如果方法名没有以set开头的则退出本次for
			if (method.indexOf("set") < 0)
				continue;
			// 类型
			String type = cc[0].getSimpleName();

			try {
				// 转成小写
				Object value = method.substring(3, 4).toLowerCase()
						+ method.substring(4);
				// 如果map里有该key
				if (map.containsKey(value) && map.get(value) != null) {
					// 调用设值方法
					setValue(type, map.get(value), i, methods, descObj);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	/***
	 * 调用设值方法
	 */
	private static void setValue(String type, Object value, int i,
			Method[] method, Object bean) throws RuntimeException {

		if (value == null || "".equals(value))
			return;

		try {
			try {
				if (type.equals("String")) {
					method[i].invoke(bean, new Object[] { value });
				} else if (type.equals("int") || type.equals("Integer")) {
					method[i].invoke(bean, new Object[] { new Integer(""
							+ value) });
				} else if (type.equals("long") || type.equals("Long")) {
					method[i].invoke(bean,
							new Object[] { new Long("" + value) });
				} else if (type.equals("boolean") || type.equals("Boolean")) {
					method[i].invoke(bean,
							new Object[] { Boolean.valueOf("" + value) });
				} else {
					method[i].invoke(bean, new Object[] { value });
				}
			} catch (Exception e) {
				method[i].invoke(bean, new Object[] { value.toString() });
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("method[" + i + "]=" + method[i] + ",bean="
					+ bean + ",value" + value.getClass() + ",value=" + value);
			throw new RuntimeException(e.getMessage()
					+ " 将HashMap 或 HashTable 里的值填充到JAVA对象时出错,请检查!");
		}
	}

	public static void copy(Object dest, Object orgi) {
		try {
			PropertyUtils.copyProperties(dest, orgi);
		} catch (IllegalAccessException e) {

			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/***
	 * 将查询结果转换成指定的CLASS类型
	 * */
	public static List copyList(List<Object> list, Class cls) {

		List result = new ArrayList();
		for (Object orgObj : list) {
			Object desObj = null;
			try {
				desObj = cls.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			copy(desObj, orgObj);
			result.add(desObj);
		}
		return result;
	}

	public static <T> List<T> toObjList(Class<T> resultClass, List<Map> list) {
		List<T> result = new ArrayList();
		for (Map resultMap : (List<Map>) ListUtil.safe(list)) {
			try {

				T obj = (T) Converts.toObject(resultMap, resultClass);

				result.add(obj);

			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOSystemException(e);
			}
		}

		return result;
	}

	/**
	 * 将list中map的key转为小写
	 * 
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> listMapKeyToLowerCase(
			List<Map<String, Object>> list) {
		List<Map<String, Object>> listNew = new ArrayList<Map<String, Object>>();
		for (Map map : list) {
			listNew.add(MapUtil.mapKeyToLowerCase(map));
		}
		return listNew;
	}

	/**
	 * 将list中map的key转为大写
	 * 
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> listMapKeyToUpperCase(
			List<Map<String, Object>> list) {
		List<Map<String, Object>> listNew = new ArrayList<Map<String, Object>>();
		for (Map map : list) {
			listNew.add(MapUtil.mapKeyToUpperCase(map));
		}
		return listNew;
	}
}

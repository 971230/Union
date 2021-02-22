package com.ztesoft.common.util.convert;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.omg.CORBA.portable.UnknownException;

public class ObjectUtils {

	public static Map toMap(Object... pairs) {
		Map map = new HashMap();
		for (int i = 0; i < pairs.length; i += 2) {
			map.put(pairs[i], pairs[i + 1]);
		}
		return map;
	}

	public static String toString(Object object) {
		if (object == null)
			return "";
		if (object.getClass().isPrimitive()) {
			return object.toString();
		}
		String str = "";

		if (object instanceof Collection) {
			Collection c = (Collection) object;
			for (Object o : c) {
				str += toString(o);
			}
		} else if (object.getClass().isArray()) {
			Object[] c = (Object[]) object;
			for (Object o : c) {
				str += toString(o);
			}
		} else if (object instanceof Map) {
			return object.toString();
		} else {
			try {
				str = BeanUtils.describe(object).toString();
			} catch (Exception e) {
				return "";
			}
		}
		return str;
	}

	public static Object nvl(Object message, Object replace) {
		return message == null ? replace : message;
	}

	public static String getStackTraceAsString(Throwable e) {
		if (e == null)
			return "";
		if (e instanceof UnknownException) {
			e = ((UnknownException) e).originalEx;
		}
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		return stringWriter.getBuffer().toString();
	}

}

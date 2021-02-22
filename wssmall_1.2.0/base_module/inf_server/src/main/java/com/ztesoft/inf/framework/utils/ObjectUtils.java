package com.ztesoft.inf.framework.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.omg.CORBA.portable.UnknownException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObjectUtils {

	public static Map toMap(Object[] pairs) {
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
//			for (Object o : c) {
			for (Iterator iterator = c.iterator(); iterator.hasNext();) {
				Object o = iterator.next();
				str += toString(o);
			}
		} else if (object.getClass().isArray()) {
			Object[] c = (Object[]) object;
			for (int i = 0; i < c.length; i++) {
//			for (Object o : c) {
				Object o=c[i];
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
		return message==null?replace:message;
	}
	
    public static String getStackTraceAsString(Throwable e) {
		if (e == null)
			return "";
		if (e instanceof UnknownException) {
			e = ((UnknownException) e).originalEx;
		}
		PrintWriter printWriter = null;
		StringWriter stringWriter = null;
		String expMsg = "";
		try {
			stringWriter = new StringWriter();
			printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			expMsg = stringWriter.getBuffer().toString();
		} finally {
			printWriter.close();
			try {
				stringWriter.close();
			} catch (IOException e1) {

			}
		}

		return expMsg;
	}
	
}


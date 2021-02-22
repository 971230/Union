package com.ztesoft.inf.framework.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CollectionUtils extends org.springframework.util.CollectionUtils {

	public static List list(Object[] values) {
		List list = new ArrayList();
//		for (Object value : values)
		for (int i = 0; i < values.length; i++) {
			Object value=values[i];
			list.add(value);
		}
		return list;
	}
	public static Object unique(List list, String errorMessage) {
		if (list == null || list.size() == 0)
			return null;
		if (list.size() > 1)
			throw new RuntimeException(errorMessage);
		else
			return list.get(0);
	}
	public static Object unique(List list) {
		return unique(list, "列表包含多个值!");
	}
	public static  List safe(List list) {
		if (list == null)
			return Collections.EMPTY_LIST;
		return list;
	}
}


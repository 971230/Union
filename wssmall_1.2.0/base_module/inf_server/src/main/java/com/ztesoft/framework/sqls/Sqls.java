package com.ztesoft.framework.sqls;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Sqls {

	protected Map<String, String> sqls = new HashMap<String, String>();

	public  String getSql(String name) {

		return sqls.get(name);
	}

	public Sqls() {

	}

	public Sqls(Class clazz) {
		super();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field f : fields) {
				sqls.put(f.getName(), (String) f.get(this));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}

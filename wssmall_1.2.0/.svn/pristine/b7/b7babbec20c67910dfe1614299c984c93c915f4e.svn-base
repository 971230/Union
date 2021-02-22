package com.ztesoft.common.util.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ztesoft.common.util.StringUtils;

public class CacheProvider {

	private static CacheProvider provider = null;

	private Map<String, Object> cachemap = null;
	private static Object keyObj1 = new Object();
	private static Object keyObj2 = new Object();

	private CacheProvider() {
		cachemap = new HashMap<String, Object>();
	}

	public static CacheProvider getInstance() {
		if (provider == null) {
			synchronized (CacheProvider.class) {
				if (provider == null) {

					provider = new CacheProvider();

				}
			}

		}
		return provider;
	}

	public Object getObject(String key) {
		if (cachemap == null || cachemap.isEmpty()) {
			return null;
		}
		return cachemap.get(key);
	}

	public void pubObject(String key, Object obj) {
		if (cachemap == null) {
			synchronized (keyObj1) {
				if (cachemap == null) {
					cachemap = new HashMap<String, Object>();
				}
			}
		}
		synchronized (keyObj2) {
			cachemap.put(key, obj);
		}

	}

	/**
	 * 清除缓存
	 * @param staff
	 */
	public void removeCache(String staff) {
		if(StringUtils.isEmpty(staff)) {
			return;
		}
		if (cachemap == null || cachemap.isEmpty())
			return;
		try {
			Set<String> setKey = cachemap.keySet();
			Iterator<String> it = setKey.iterator();
			List<String> cacheKey = new ArrayList<String>();
			while (it.hasNext()) {
				String key = it.next();
				if (StringUtils.isEmpty(key)) {
					continue;
				}
				//
				if (key.indexOf(staff) > 0) {
					cacheKey.add(key);
				}
			}
			for (String key : cacheKey) {
				cachemap.remove(key);
			}
		} catch (Exception ex) {

		}
	}
}

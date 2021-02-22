package com.ztesoft.inf.framework.cache;

import java.util.Date;

public class Cache<K, V> {

	private static final long serialVersionUID = 1L;
	protected BoundedMap<K, V> backed;
	private Class<? extends BoundedMap> backedMapClass;
	private static int DEFAULT_MAXSIZE = -1;
	private int maxSize;
	private int period;
	private String cacheKey;
	private Date lastRefresh;

	public Date getLastRefresh() {
		return lastRefresh;
	}
	public void setLastRefresh(Date lastRefresh) {
		this.lastRefresh = lastRefresh;
	}
	public String getCacheKey() {
		return cacheKey;
	}
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	void setBackedClass(Class<? extends BoundedMap> backedMapClass) {
		if (backed.size() > 0) {
			throw new RuntimeException("");
		}
		this.backedMapClass = backedMapClass;
	}
	public Cache() {
		this(DEFAULT_MAXSIZE, FIFOMap.class);
	}
	public Cache(int maxSize, Class<? extends BoundedMap> backedMapClass) {
		this.backedMapClass = backedMapClass;
		this.maxSize = maxSize;
		backed = newBackedMap();
	}
	public Cache(int capacity) {
		this(capacity, FIFOMap.class);
	}
	protected BoundedMap newBackedMap() {
		BoundedMap map;
		try {
			map = backedMapClass.newInstance();
			map.setMaxSize(maxSize);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return map;
	}
	public synchronized void clear() {
		backed.clear();
	}
	public  V get(K key, CacheItemCreateCallback<V> callback)
		throws Exception {
		backed = getBackedMap();
		V item = backed.get(key);
		if (item == null) {
			item = callback.create();
			backed.put(key, item);
		}else{
			item = callback.create();
		}
		return item;
	}
	private BoundedMap<K, V> getBackedMap() {
		if (backed == null) {
			backed = newBackedMap();
		}
		return backed;
	}
	public synchronized V put(K key, V value) {
		backed = getBackedMap();
		return backed.put(key, value);
	};
	public int size() {
		return backed.size();
	}
}

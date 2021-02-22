package com.ztesoft.inf.framework.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class FIFOMap<K, V> extends LinkedHashMap<K, V> implements BoundedMap<K, V> {

	private static final long serialVersionUID = 1L;
	private int maxSize = -1;

	protected FIFOMap(int capacity, float factor, boolean accessOrder) {
		super(capacity, factor, accessOrder);
	}
	public FIFOMap() {
		super();
	}
	@Override
	public void setMaxSize(int newMaxSize) {
		if ((maxSize < 0 || newMaxSize < maxSize) && size() > newMaxSize) {
			int i = size() - newMaxSize;
			Iterator<K> iter = keySet().iterator();
			List<K> keys = new ArrayList<K>(i);
			while (iter.hasNext() && i > 0) {
				i--;
				keys.add(iter.next());
			}
			for (K k : keys) {
				remove(k);
			}
		}
		this.maxSize = newMaxSize;
	}
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return maxSize > 0 && size() > maxSize;
	}
}

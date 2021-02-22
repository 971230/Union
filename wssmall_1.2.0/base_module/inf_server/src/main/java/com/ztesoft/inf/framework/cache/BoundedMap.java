package com.ztesoft.inf.framework.cache;

import java.util.Map;


public interface BoundedMap<K,V> extends Map<K,V>{

	void setMaxSize(int capacity);
	
}

package com.ztesoft.inf.framework.commons;

import java.util.HashMap;
import java.util.Map;

public class TransactionalBeanManager {

	private static Map<Class, Object> transactionalBoCache = new HashMap();

	public static synchronized <T> T getTransactional(Class<T> clz) {
		T bo = (T) transactionalBoCache.get(clz);
		if (bo == null) {
			bo = createTransactional(clz);
			transactionalBoCache.put(clz, bo);
		}
		return bo;
	}

	public static <T> T createTransactional(Class<T> clz) {
		return ProxyCreator.newInstance(TransactionProxy.class, clz);
	}
	
	public static <T> T createCommitTransactional(Class<T> clz) {
		return ProxyCreator.newInstance(TransactionCommitProxy.class, clz);
	}
	
}

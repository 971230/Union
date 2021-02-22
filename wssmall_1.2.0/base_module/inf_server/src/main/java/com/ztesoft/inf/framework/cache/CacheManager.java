package com.ztesoft.inf.framework.cache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.inf.communication.client.CrmRemoteCallClient;
import com.ztesoft.inf.framework.commons.CodedException;
import com.ztesoft.inf.framework.commons.TransactionalBeanManager;

@SuppressWarnings("unchecked")
public class CacheManager {

	private static final Class<? extends Cache> DEFAULT_CACHETYPE = Cache.class;
	private static final int RUNNING = 0;
	private static final int SHUTDOWNED = 1;
	private static Map<String, Cache> cacheMap = new HashMap();
	private static List<Cache> cacheStore = new ArrayList<Cache>();
	private static Thread refreshThread;
	private static int state;
	private static int DEFAULT_CACHE_TIME = 30;

	static {
		startup();
	}

	public static class CacheRefresher {
		public void refresh(Cache c) {
			String key = c.getCacheKey();
			if (key != null && cacheMap.containsKey(key)) {
				int period = c.getPeriod();
				Date lastRefresh = c.getLastRefresh();
				if (period > 0
						&& new Date().after(DateUtil.add(lastRefresh,
								Calendar.MINUTE, period))) {
					c.clear();
					c.setLastRefresh(new Date());
				}
			}
		}
	}

	public synchronized static void startup() {
		state = RUNNING;
		refreshThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					CacheRefresher refresher = TransactionalBeanManager
							.createTransactional(CacheRefresher.class);
					while (true) {
						List<Cache> store = new ArrayList(cacheStore);
						for (Cache c : store) {
							refresher.refresh(c);
						}
						// ��Ϊ��Ч����add by xiaof 110904
						store = null;
						if (state == SHUTDOWNED)
							break;
						try {
							Thread.sleep(60 * 2 * 1000L);
						} catch (InterruptedException e) {
							break;
						}
						if (state == SHUTDOWNED)
							break;
					}
				} catch (Exception e) {
				}
			}
		});
		refreshThread.start();
	}

	public synchronized static void shutdown() {
		state = SHUTDOWNED;
		if (refreshThread != null)
			refreshThread.interrupt();
	}

	public  static void refreshCache(String cacheKey,
			String cacheCode) throws Exception {
		List<Cache> store = new ArrayList(cacheStore);
		for (Cache cache : store) {
			if (cache == null || !cacheKey.equals(cache.getCacheKey())) {
				continue;
			}
			Object cacheValue = cache.get(cacheCode,
					new CacheItemCreateCallback() {
						@Override
						public Object create() throws Exception {
							return null;
						}
					});

			if (cacheValue != null) {
				cache.clear();
				cache.setLastRefresh(new Date());
			}
		}
	}

	public static void refreshAllCache() throws Exception {
		/*
		 * modify by xiaof 110708 synchronized (cacheMap) { cacheMap.clear();
		 * 
		 * 
		 * }
		 */

		synchronized (cacheStore) {
			List<Cache> store = new ArrayList(cacheStore);
			for (Cache cache : store) {
				cache.clear();
				cache.setLastRefresh(new Date());
			}
			// cacheStore.clear();������cacheStore��ͬʱprivate createCache()
		}
	}

	public  static void refreshAll() {
		CrmRemoteCallClient client = new CrmRemoteCallClient();

		String operationCode = "crm.CrmRemoteCall";

		try {
			String rets = getPorts();

			if ("".equals(rets) || null == rets || rets.split(",").length == 0) {
				return;
			}

			String[] arrs = rets.split(",");
			for (int i = 0; i < arrs.length; i++) {
				String arr = arrs[i];
				try {
					client.remoteCall(operationCode + arr.trim(),
							"com.ztesoft.inf.framework.cache.CacheManager",
							"refreshAllCache", new Object[] {}, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  static void refreshAll(String cacheKey, String cacheCode) {

		CrmRemoteCallClient client = new CrmRemoteCallClient();

		String operationCode = "crm.CrmRemoteCall";

		try {
			String rets = getPorts();

			if ("".equals(rets) || null == rets || rets.split(",").length == 0) {
				return;
			}

			String[] arrs = rets.split(",");
			for (int i = 0; i < arrs.length; i++) {
				String arr = arrs[i];
				try {
					client.remoteCall(operationCode + arr.trim(),
							"com.ztesoft.inf.framework.cache.CacheManager",
							"refreshCache",
							new Object[] { cacheKey, cacheCode }, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String getPorts() {
		String value = "";
		try {
//			value = ServiceFactory.invokeServiceWithParams(
//					"AtomDcSystemParamImpl", "getValueByCode",
//					"SYS_SERVER_PORTS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public static void main(String[] args) {
		// refreshAll(CacheConstants.WSCLIENT_INVOKER,"balance.OweInfoQryRequest");
		refreshAll();
	}

	public static <K, V> Cache<K, V> getCache(String cacheId) {
		return getCache(cacheId, DEFAULT_CACHE_TIME);
	}

	public static <K, V> Cache<K, V> getCache(String cacheId, int period) {
		return getCache(cacheId, period, FIFOMap.class);
	}

	public static <K, V> Cache<K, V> getCache(String cacheId,
			Class<? extends BoundedMap> backedMapClass) {
		return getCache(cacheId, DEFAULT_CACHE_TIME, backedMapClass);
	}

	public static <K, V> Cache<K, V> getCache(String cacheId, int period,
			Class<? extends BoundedMap> backedMapClass) {
		Cache<K, V> cache = null;
			if (!cacheMap.containsKey(cacheId)) {
				cache = createCache(period, backedMapClass);
				cache.setCacheKey(cacheId);
				cacheMap.put(cacheId, cache);
			} else
				cache = cacheMap.get(cacheId);
			return cache;
	}

	public static <K, V> Cache<K, V> createCache(int period) {
		return createCache(period, DEFAULT_CACHETYPE, FIFOMap.class);
	}

	public static <K, V> Cache<K, V> createCache(int period,
			Class<? extends BoundedMap> backedMapClass) {
		return createCache(period, DEFAULT_CACHETYPE, backedMapClass);
	}

	public static <K, V> Cache<K, V> createCache(
			Class<? extends Cache> cacheClz,
			Class<? extends BoundedMap> backedMapClass) {
		return createCache(DEFAULT_CACHE_TIME, cacheClz, backedMapClass);
	}

	public static  <K, V> Cache<K, V> createCache(int period,
			Class<? extends Cache> cacheClz,
			Class<? extends BoundedMap> backedMapClass) {
		Cache instance;
		try {
			instance = cacheClz.newInstance();
			instance.setBackedClass(backedMapClass);
			instance.setPeriod(period);
			instance.setLastRefresh(new Date());
			cacheStore.add(instance);
			return instance;
		} catch (Exception e) {
			throw new CodedException("", "", e);
		}
	}

}

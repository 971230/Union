package com.ztesoft.net.cache.ehcache;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.common.INetCache;

/**
 * @author Reason.Yea
 * @version 创建时间：Jan 8, 2014
 */
public class EhCacheImpl implements INetCache {
	private static Logger logger = Logger.getLogger(EhCacheImpl.class);

	private EhCacheImpl() {
	}

	private static final EhCacheImpl inst = new EhCacheImpl();

	public static EhCacheImpl getCache() {
		return inst;
	}

	private Cache getCacheByNameSpace(String nameSpace) {
		Cache cache = null;
		try {
			CacheManager manager = CacheManager.getInstance();
			cache = manager.getCache(nameSpace);
			if (cache == null) {
				manager.addCache(nameSpace);
				cache = manager.getCache(nameSpace);
			}
		} catch (Exception e) {
			throw new CacheException(e);
		}
		return cache;
	}

	@Override
	public void clear() {
		clear(defaltNameSpace);
	}

	@Override
	public void clear(int nameSpace) {
		Cache cache = getCacheByNameSpace(nameSpace + "");
		try {
			cache.removeAll();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		throw new CacheException("method not implemented!");
	}

	@Override
	public void delete(String key) {
		delete(defaltNameSpace, key);
	}

	@Override
	public void delete(int nameSpace, String key) {
		Cache cache = getCacheByNameSpace(nameSpace + "");
		try {
			cache.remove(key);
		} catch (CacheException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Serializable get(String key) {
		return get(defaltNameSpace, key);
	}

	@Override
	public Serializable get(int nameSpace, String key) {
		Cache cache = getCacheByNameSpace(nameSpace + "");
		Object obj = null;
		try {
			if (key != null) {
				Element element = cache.get(key);
				if (element != null) {
					obj = element.getValue();
				}
			}
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return (Serializable) obj;
	}

	@Override
	public void set(String key, Serializable value) {
		set(defaltNameSpace, key, value);
	}

	@Override
	public void set(int nameSpace, String key, Serializable value) {
		Cache cache = getCacheByNameSpace(nameSpace + "");
		try {
			Element element = new Element(key,
					value);
			cache.put(element);
		} catch (Exception e) {
			throw new CacheException(e);
		}

	}

	@Override
	public void set(int nameSpace, String key, Serializable value,
			int expireTime) {
		set(nameSpace, key, value);
	}
	
	@Override
	public boolean add(int nameSpace, String key, Serializable value,
			int expireTime) {
		// TODO Auto-generated method stub
		return false;
	}

	// /**
	// * Gets a value of an element which matches the given key.
	// * @param key the key of the element to return.
	// * @return The value placed into the cache with an earlier put, or null if
	// not found or expired
	// * @throws CacheException
	// */
	// public Object get(Object key){
	//    	
	// Object obj = null;
	// try {
	// if (key!= null) {
	// Element element = cache.get((Serializable) key);
	// if (element!=null) {
	// obj = element.getValue();
	// }
	// }
	// } catch (net.sf.ehcache.CacheException e) {
	// e.printStackTrace();
	// }
	// return obj;
	// }
	//
	//
	// /**
	// * Puts an object into the cache.
	// * @param key a {@link Serializable} key
	// * @param value a {@link Serializable} value
	// * @throws CacheException if the parameters are not {@link Serializable},
	// the {@link CacheManager}
	// * is shutdown or another {@link Exception} occurs.
	// */
	// public void put(Object key, Object value){
	// try {
	// Element element = new Element((Serializable) key, (Serializable) value);
	// cache.put(element);
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (IllegalStateException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * Removes the element which matches the key.
	// * <p>
	// * If no element matches, nothing is removed and no Exception is thrown.
	// * @param key the key of the element to remove
	// * @throws CacheException
	// */
	// public void remove(Object key){
	// try {
	// cache.remove((Serializable) key);
	// } catch (ClassCastException e) {
	// e.printStackTrace();
	// } catch (IllegalStateException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * Remove all elements in the cache, but leave the cache
	// * in a useable state.
	// * @throws CacheException
	// */
	// public void clear(){
	// try {
	// //cache.remove(arg0)
	// cache.removeAll();
	// } catch (IllegalStateException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public static void main(String[] args){
	// EhCacheImpl cache = new EhCacheImpl("queryCache");
	// // cache.put("test","fjdkafjsdkajd");
	// logger.info(cache.get("test"));
	// }

}

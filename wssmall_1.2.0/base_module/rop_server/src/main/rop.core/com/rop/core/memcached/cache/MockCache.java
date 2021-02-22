package com.rop.core.memcached.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//未采用memcached实现
public class MockCache implements INetCache {
	private Map cacheData = new ConcurrentHashMap() ;


	private MockCache(){}
    private static class MockCacheClassLoader{
        private static MockCache instance = new MockCache();
    }
	
	public static MockCache getInstance(){
		return MockCacheClassLoader.instance ;
	}

	public Map getCacheData() {
		return cacheData;
	}


	public void setCacheData(Map cacheData) {
		this.cacheData = cacheData;
	}


	@Override
	public void delete(String key) {
		cacheData.remove(key ) ;
	}

	@Override
	public void deleteWithNoReply(String key) {
		cacheData.remove(key ) ;
	}

	@Override
	public void flush() {
		cacheData.clear() ;
	}

	@Override
	public IBean getBean(String key) {
		return (IBean)cacheData.get(key);
	}

	@Override
	public Serializable getObj(String key) {
		return (Serializable)cacheData.get(key);
	}

	@Override
	public void setBean(String key, int cacheTime, IBean value) {
		cacheData.put(key, value) ;
	}

	@Override
	public void setObj(String key, int cacheTime, Serializable value) {
		cacheData.put(key, value) ;
	}

    @Override
    public void update(String key, int time, Serializable value) {
        cacheData.put(key, value) ;
    }

}

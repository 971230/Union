package com.ztesoft.net.cache.tair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.taobao.tair.DataEntry;
import com.taobao.tair.Result;
import com.ztesoft.net.cache.common.CacheException;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.cache.conf.CacheValues;

/**
 * @author Reason.Yea 
 * @version 创建时间：Jan 11, 2014
 */
public class TairCache implements INetCache {
	public static Logger logger = Logger.getLogger(TairCache.class);
	private static TairCache inst = new TairCache();
	protected ZTairManager tairManager = null;
	private int default_version = 1;
	private static int defaulNameSpace = CacheValues.CORE_CACHE_DEFAULT_NAMESPACE;
	public static TairCache getCache(){
		return inst;
	}
	
	private TairCache(){
//		List<String> confServers = new ArrayList<String>();
//		confServers.add("10.45.47.168:5198");
//		// confServers.add("10.45.47.168:5160"); // 可选
//		ZTairManager tairManager = new ZTairManager();
//		tairManager.setConfigServerList(confServers);
//		tairManager.setGroupName("group_1");
//		tairManager.setCharset("utf-8");
//		tairManager.setMaxWaitThread(90000);
//		tairManager.initTair();
		List<String> confServers = new ArrayList<String>();
		String servList[] = CacheValues.TAIR_MANAGE_SERVERS.split(",");
		for (int i = 0; i < servList.length; i++) {
			confServers.add(servList[i]);
		}
		tairManager = new ZTairManager();
		tairManager.setConfigServerList(confServers);
		tairManager.setGroupName(CacheValues.TAIR_GROUPNAME);
		tairManager.setCharset(CacheValues.TAIR_CHARSET);
		tairManager.setMaxWaitThread(CacheValues.TAIR_MAXWAITTHREAD);
		tairManager.initTair();
	}
	@Override
	public void clear() {
		throw new CacheException("method not implemented!");
	}

	@Override
	public void clear(int nameSpace) {
		throw new CacheException("method not implemented!");
	}

	@Override
	public void close() {
		tairManager.close();

	}

	@Override
	public void delete(String key) {
		tairManager.delete(defaulNameSpace, key);

	}

	@Override
	public void delete(int nameSpace, String key) {
		tairManager.delete(nameSpace, key);

	}

	@Override
	public Serializable get(String key) {
		return get(defaulNameSpace, key);
	}

	@Override
	public Serializable get(int nameSpace, String key) {
		Result<DataEntry> result=tairManager.get(nameSpace, key);
		Serializable ret = null;
		if (result.isSuccess()) {
		    DataEntry entry = result.getValue();
		    if(entry != null) {
		    	ret= (Serializable) entry.getValue();
		    } else {//数据不存在
		    	logger.info("result from taircache not exists!");
		    }
		} else {//获取数据失败
			String message= result.getRc().getMessage();
			if(message.equals("connection error or timeout"))
				ret="connection error or timeout";
			logger.info("get result from taircache failed!");
		}
		return ret;
	}

	@Override
	public void set(String key, Serializable value) {
//		delete(defaulNameSpace, key);
		set(defaulNameSpace, key, value);
	}

	@Override
	public void set(int nameSpace, String key, Serializable value) {
//		delete(nameSpace, key);
		tairManager.put(nameSpace, key, value);
	}

	@Override
	public void set(int nameSpace, String key, Serializable value,int expireTime) {
		tairManager.put(nameSpace, key, value, default_version, expireTime);

	}
	
	@Override
	public boolean add(int nameSpace, String key, Serializable value,
			int expireTime) {
		// TODO Auto-generated method stub
		return false;
	}

}

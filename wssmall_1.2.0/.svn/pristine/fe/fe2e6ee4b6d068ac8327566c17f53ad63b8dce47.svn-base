package commons;

import params.LoginUser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
 * 登录用户信息记录
* @作者 MoChunrun 
* @创建日期 2013-9-25 
* @版本 V 1.0
 */
public class CommonContext {
	private static Logger logger = Logger.getLogger(CommonContext.class);
	private static final CommonContext instance = new CommonContext();
	public static final String MEMBER_STAET = "member_";
	
	private Map info = new HashMap();
	
	private Map loginMap = new ConcurrentHashMap();
	
	private CommonContext(){}
	
	public static CommonContext getInstance(){
		return instance;
	}
	
	/**
	 * 保存信息
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-25 
	 * @param <T>
	 * @param key
	 * @param t
	 */
	public <T> void put(String key,T t){
		if(key.startsWith(MEMBER_STAET)){
			loginMap.put(key, t);
		}else{
			info.put(key, t);
		}
	}
	
	/**
	 * 获取信息
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-25 
	 * @param <T>
	 * @param key
	 * @return
	 */
	public <T> T get(String key){
		if(key.startsWith(MEMBER_STAET)){
			return (T) loginMap.get(key);
		}else{
			return (T) info.get(key);
		}
	}
	
	/**
	 * 删除信息
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-25 
	 * @param key
	 */
	public void remove(String key){
		if(key.startsWith(MEMBER_STAET)){
			loginMap.remove(key);
		}else{
			info.remove(key);
		}
	}
	
	public void clearTimeUser(){
		logger.info("==============clear=============");
		logger.info("loginuser--start:="+loginMap.size());
		Iterator it = loginMap.keySet().iterator();
		while(it.hasNext()){
			Object key = it.next();
			LoginUser lu = (LoginUser) loginMap.get(key);
			if(lu!=null){
				long loginTime = lu.getLoginTime();
				if((System.currentTimeMillis()-30*60*1000)>loginTime){
					logger.info(key);
					loginMap.remove(key);
				}
			}else{
				loginMap.remove(key);
			}
		}
		logger.info("loginuser--end:="+loginMap.size());
	}
	
	/**
	 * 清空所有信息
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-25
	 */
	public void clear(){
		loginMap.clear();
		info.clear();
	}

}
